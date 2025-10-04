package Display;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utils.Login;
import utils.ProductPage;
import utils.ScrollUtils;

public class checkoutNavigation extends Base_Class{

    @Test(enabled = true)
    public void checkoutAddress() throws InterruptedException {

        Login login = new Login();
        login.createAccount(driver);

        ScrollUtils scrollUtils = new ScrollUtils(driver);
        ProductPage p = new ProductPage(driver);

        p.setLocation().click();
        p.useCurrentLocation().click();

        Thread.sleep(2000);

        String location = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Google Building 40, Mountain View, California\")")).getText();

        scrollUtils.scrollToElement("Wireless Earbuds", 5, true);

        WebElement addToCart = driver.findElement(AppiumBy.accessibilityId("Add to cart"));
        addToCart.click();

        if (addToCart.isDisplayed()) {
            System.out.println("BUG: 'Add to cart' button is still visible after adding product.");
        } else {
            System.out.println("'Add to cart' button is no longer visible as expected.");
        }

        driver.navigate().back();

        ProductPage productPage = new ProductPage(driver);
        productPage.getCartIcon().click();

        try {
            productPage.getProductByName("Wireless Earbuds");
            System.out.println("added product visible in cart");
        } catch (Exception e) {
            System.out.println("BUG! product not added ");
        }

        driver.findElement(By.xpath("//android.widget.TextView[@text=\"Cash on delivery\"]")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"View my orders\")")).click();

        Thread.sleep(2000);

        p.getProductByName("Wireless Earbuds").click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Get help\")")).click();

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Chat\")")).click();

        String errormsg = driver.findElement(By.id("android:id/message")).getText();
        System.out.println(errormsg);

        driver.findElement(By.id("android:id/button1")).click();

        scrollUtils.scrollToElement("How do I request a refund?", 5, true);

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Home\")")).click();

        try {
            String homepage = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Welcome to Gofillip\")")).getText();
            System.out.println(homepage);
        } catch (Exception e) {
            System.out.println("Home page not found , Failed to load home page");
        }

        Thread.sleep(2000);


    }

}
