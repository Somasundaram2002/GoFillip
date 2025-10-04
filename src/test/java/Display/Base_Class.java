package Display;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Base_Class {
    AndroidDriver driver;
    AppiumDriverLocalService service;

    @BeforeClass
    public void AppiumTest() throws IOException {
        // Build Appium service
        Properties prop = new Properties();
        prop.load(getClass().getClassLoader().getResourceAsStream("appium.properties"));
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File(prop.getProperty("js.path")))
                .withIPAddress(prop.getProperty("server.ip"))
                .usingPort(Integer.parseInt(prop.getProperty("server.port")));

        service = AppiumDriverLocalService.buildService(builder);
        service.start();

        // Desired capabilities
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName("UiAutomator2")
                .setDeviceName("emulator-5554")
                .setPlatformVersion("10")
                .setAppPackage("com.wentrite.Mart")
                .setAppActivity("com.wentrite.Mart.MainActivity");

        options.setUiautomator2ServerInstallTimeout(Duration.ofSeconds(120));
        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(120));

        // Launch driver
        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

    }



    @AfterClass
    public void close() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
