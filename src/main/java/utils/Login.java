package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class Login {

    public void createAccount(AndroidDriver driver) {

        try {
        driver.findElement(By.id("android:id/button1")).click();

        driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button")).click(); }
        catch (Exception e) {
            System.out.println(e);
        }

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

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Home\")")).click();
    }

}
