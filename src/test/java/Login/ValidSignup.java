package Login;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.annotations.Test;


public class ValidSignup extends Base_Class {

    @Test
    public void Validlogin() throws InterruptedException {

        driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uDB82\uDF55\"]")).click();

        driver.findElement(AppiumBy.accessibilityId("Sign up")).click();

        String username = "somu";

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Jane Doe\")")).sendKeys(username);

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"you@example.com\")")).sendKeys("abcd@gmail.com");

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"••••••••\")")).sendKeys("secret_key");

        driver.hideKeyboard();

        driver.findElement(AppiumBy.accessibilityId("Create account")).click();

        System.out.println(driver.findElement(By.id("com.wentrite.Mart:id/alert_title")).getText());

        driver.findElement(By.id("android:id/button1")).click();

        String usernameafterlogin = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"somu\")")).getText();

        if (username.equals(usernameafterlogin)) {
            System.out.println("User name logged successfully");
        } else {
            System.out.println("User name not logged failed");
        }

        Thread.sleep(3000);
    }

}