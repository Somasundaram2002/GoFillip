package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
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
        return driver.findElement(By.className("android.widget.EditText"));
    }

    public WebElement getSearchOpenButton() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"\uF563\")"));
    }

    public WebElement addtoCart() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Add to cart\")"));
    }


    public WebElement clearCart() {
        return driver.findElement(AppiumBy.accessibilityId("Clear cart"));
    }

    public WebElement setLocation() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"\uF3C5\")"));
    }

    public WebElement useCurrentLocation() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Use current location\")"));
    }

    public WebElement setAddressOk() {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Set location\")"));
    }
}
