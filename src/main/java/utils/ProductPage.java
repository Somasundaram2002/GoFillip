package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class ProductPage {
    private AndroidDriver driver;

    public ProductPage(AndroidDriver driver) {
        this.driver = driver;
    }

    public WebElement getCategoryByName(String categoryName) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"" + categoryName + "\")"));
    }

    public WebElement getProductByName(String productName) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"));
    }

    public WebElement getAddButtonInstance(int index) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Add\").instance(" + index + ")"));
    }

    public WebElement getCartCount(String count) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + count + "\")"));
    }

    public WebElement getCartIcon() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"\uF203\")"));
    }

    public WebElement getSearchBox() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Search products...\")"));
    }

    public WebElement getSearchOpenButton() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"\uF563\")"));
    }
}
