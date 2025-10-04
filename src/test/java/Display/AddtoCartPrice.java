package Display;

import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Login;
import utils.ProductPage;

public class AddtoCartPrice extends Base_Class{

    @Test(enabled = true)
    public void AddToCartprice() throws InterruptedException {
        Login login = new Login();
        login.createAccount(driver);

        ProductPage productPage = new ProductPage(driver);

        // Go to Fruits & Vegetables section
        productPage.getCategoryByName("Fruits & Vegetables").click();
        Thread.sleep(2000);

        String prod1 = productPage.getProductByName("Bananas (1kg)").getText();
        System.out.println("added product " + prod1);

        String prod1priceText = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"₹50.00\").instance(0)")).getText();
        double price1 = Double.parseDouble(prod1priceText.replace("₹", ""));
        System.out.println("added product price " + price1);

        productPage.getAddButtonInstance(0).click();
        driver.navigate().back();

        String count = productPage.getCartCount("1").getText();
        Thread.sleep(2000);
        Assert.assertEquals(count, "1", "BUG! Product count should be 1 after first add.");

        // Go to Dairy & Eggs section
        productPage.getCategoryByName("Dairy & Eggs").click();
        Thread.sleep(2000);

        String prod2 = productPage.getProductByName("Milk (1L)").getText();
        System.out.println("added product " + prod2);

        String prod2priceText = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"₹100.00\")")).getText();
        double price2 = Double.parseDouble(prod2priceText.replace("₹", ""));
        System.out.println("added product price " + price2);

        productPage.getAddButtonInstance(0).click();
        driver.navigate().back();

        Thread.sleep(2000);
        String count2 = productPage.getCartCount("2").getText();
        Assert.assertEquals(count2, "2", "BUG! Product count should be 2 after second add.");

        productPage.getCartIcon().click();

        String addedProd1 = productPage.getProductByName("Bananas (1kg)").getText();
        String addedProd2 = productPage.getProductByName("Milk (1L)").getText();

        Assert.assertEquals(addedProd1, prod1, "BUG! First product not found in cart.");
        Assert.assertEquals(addedProd2, prod2, "BUG! Second product not found in cart.");

        double totalPrice = price1 + price2;
        System.out.println("Total cart price: ₹" + totalPrice);

        String totalText = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").textContains(\"₹\").instance(3)")).getText();
        double actualTotal = Double.parseDouble(totalText.replace("₹", ""));

        System.out.println(actualTotal);

        Assert.assertEquals(actualTotal, totalPrice, "BUG! Cart total price mismatch.");
    }

}
