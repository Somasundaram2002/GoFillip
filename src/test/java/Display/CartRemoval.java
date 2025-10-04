package Display;

import org.testng.annotations.Test;
import utils.Login;
import utils.ProductPage;
import utils.ScrollUtils;

public class CartRemoval extends Base_Class{


    @Test(enabled = true)
    public void cartRemoval() throws InterruptedException {

        Login login = new Login();
        login.createAccount(driver);

        ScrollUtils scrollUtils = new ScrollUtils(driver);
        ProductPage p = new ProductPage(driver);

        scrollUtils.scrollToElement("Shampoo (400ml)", 5, true);
        p.addtoCart().click();

        driver.navigate().back();

        scrollUtils.scrollToElement("Apples (1kg)", 5, true);
        p.addtoCart().click();

        driver.navigate().back();

        scrollUtils.scrollToElement("USB-C Cable (1m)", 5, true);
        p.addtoCart().click();

        driver.navigate().back();

        p.getCartIcon().click();

        Thread.sleep(2000);

        try {
            p.getProductByName("Shampoo (400ml)");
            p.getProductByName("Apples (1kg)");
            p.getProductByName("USB-C Cable (1m)");
            System.out.println("Products found");
        } catch (Exception e) {
            System.out.println("BUG !!! Products not found");
        }

        Thread.sleep(2000);

        p.clearCart().click();

        Thread.sleep(2000);

        try {
            p.getProductByName("Shampoo (400ml)");
            p.getProductByName("Apples (1kg)");
            p.getProductByName("USB-C Cable (1m)");
            System.out.println("BUG !!! Products found");
        } catch (Exception e) {
            System.out.println("PASSED Products not found");
        }

        driver.navigate().back();

        Thread.sleep(1000);
    }


}
