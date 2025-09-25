package Login;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utilities.JsonReader;

import java.time.Duration;

public class InvalidLogin extends Base_Class {

    @BeforeMethod
    public void login() {
        try {
            driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uDB82\uDF55\"]")).click();
        } catch (Exception e) {
            System.out.println(e);
        }
        driver.findElement(AppiumBy.accessibilityId("Log in")).click();
    }

    @AfterMethod
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().textContains(\"Log out\"))"
                    )));
            ele.click();
            driver.findElement(By.id("android:id/button1")).click();
        } catch (Exception e) {
            // Logout not needed
        }
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getTestData() {
        String jsonFilePath = "resources/testdata/invalidSignupData.json";
        return JsonReader.getJSONData(jsonFilePath, "invalidLoginData");
    }


    @Test(dataProvider = "invalidLoginData")
    public void testLoginScenarios(String testCase, String email, String password, String expectedResult) {
        System.out.println("Testing scenario: " + testCase);
        System.out.println("Testing scenario: " + expectedResult);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Fill email field
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"you@example.com\")")));
        emailField.clear();
        emailField.sendKeys(email);

        // Fill password field
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(
                AppiumBy.androidUIAutomator("new UiSelector().text(\"••••••••\")")));
        passwordField.clear();
        passwordField.sendKeys(password);

        driver.hideKeyboard();
        driver.findElement(AppiumBy.accessibilityId("Log in")).click();

        // Verify results
        try {
            WebElement alert = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("android:id/button1")));
            System.out.println("BUG: " + testCase + " - Success alert should not appear");
            alert.click();
        } catch (TimeoutException e) {
            System.out.println("CORRECT: " + testCase + " - No success alert - test case passed");
        }
    }
}
