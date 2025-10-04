pipeline {
  agent any // or 'any' if running on the same host
  options { timestamps(); timeout(time: 40, unit: 'MINUTES') }
  environment {
    ANDROID_HOME = "${env.WORKSPACE}/android-sdk"
    JAVA_OPTS = "-Xmx1g"
  }
  stages {
    stage('Checkout') { steps { checkout scm } }

    stage('Setup SDK & Tools') {
      steps {
        sh '''
          set -e
          mkdir -p $ANDROID_HOME
          curl -fsSL https://dl.google.com/android/repository/commandlinetools-linux-10406996_latest.zip -o cmdtools.zip
          unzip -q cmdtools.zip -d $ANDROID_HOME
          mkdir -p $ANDROID_HOME/cmdline-tools/latest
          mv $ANDROID_HOME/cmdline-tools/* $ANDROID_HOME/cmdline-tools/latest/ || true
          yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses
          $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager \
            "platform-tools" "platforms;android-34" \
            "system-images;android-34;google_apis;x86_64" "emulator"
        '''
      }
    }

    stage('Create & Boot Emulator') {
      steps {
        sh '''
          set -e
          echo "no" | $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd -n ci_avd -k "system-images;android-34;google_apis;x86_64" --force --device "pixel_6"
          $ANDROID_HOME/emulator/emulator -avd ci_avd -no-snapshot -no-audio -no-window -gpu swiftshader_indirect -no-boot-anim -wipe-data &
          adb wait-for-device
          # Wait until boot completed
          for i in $(seq 1 60); do
            booted=$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')
            if [ "$booted" = "1" ]; then echo "Emulator booted"; break; fi
            sleep 2
          done
          adb shell input keyevent 82 || true
        '''
      }
    }

    stage('Build & Test') {
      steps {
        sh '''
          mvn -B -e -DskipTests=false test \
            -DdeviceName=emulator-5554 \
            -DplatformName=Android \
            -DappPackage=${APP_PACKAGE:-com.example.app} \
            -DappActivity=${APP_ACTIVITY:-.MainActivity}
        '''
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
          archiveArtifacts allowEmptyArchive: true, artifacts: 'target/**/*.png'
        }
      }
    }
  }
  post {
    always {
      sh 'adb emu kill || true'
    }
  }
}
