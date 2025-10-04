package Display;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utils.Login;
import utils.ProductPage;
import utils.ScrollUtils;

public class CheckoutProduct extends Base_Class{

    @Test(enabled = true)
    public void CheckOutProduct() throws InterruptedException {
        Login login = new Login();
        login.createAccount(driver);

        ScrollUtils scrollUtils = new ScrollUtils(driver);
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
        Thread.sleep(3000);

        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"View my orders\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Cancel\")")).click();
        Thread.sleep(3000);

        try {
            productPage.getProductByName("Wireless Earbuds");
            System.out.println("BUG: added product visible in cart");
        } catch (Exception e) {
            System.out.println("product not visible in orders page");
        }
    }

}
