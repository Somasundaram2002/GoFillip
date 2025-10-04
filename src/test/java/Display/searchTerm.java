package Display;

import org.testng.annotations.Test;
import utils.Login;
import utils.ProductPage;
import utils.ScrollUtils;

public class searchTerm extends Base_Class{

    @Test(enabled = true)
    public void searchTerm() throws InterruptedException {

        Login login = new Login();
        login.createAccount(driver);

        ScrollUtils scrollUtils = new ScrollUtils(driver);
        ProductPage p = new ProductPage(driver);

        p.getSearchOpenButton().click();

        Thread.sleep(2000);

        p.getSearchBox().sendKeys("ban");

        Thread.sleep(2000);

        try {
            p.getProductByName("Bananas (1kg)");
            System.out.println("Passed, related Product found for ban - banana ");
        } catch (Exception e) {
            System.out.println("BUG, related product not found");
        }

        p.getSearchBox().clear();
        p.getSearchBox().sendKeys("baby");

        Thread.sleep(2000);
        try {
            p.getProductByName("Baby Diapers (M, 20)");
            System.out.println("Passed, related Product found for baby - Baby Diapers (M, 20) ");
        } catch (Exception e) {
            System.out.println("BUG, related product not found");
        }

        p.getSearchBox().clear();
        p.getSearchBox().sendKeys("wat");

        Thread.sleep(2000);
        try {
            p.getProductByName("Sparkling Water (6x)");
            System.out.println("Passed, related Product found for wat - Sparkling Water (6x) ");
        } catch (Exception e) {
            System.out.println("BUG, related product not found");
        }

        driver.navigate().back();

        Thread.sleep(1000);
    }


}
