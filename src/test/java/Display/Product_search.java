package Display;


import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.JsonReader;
import utils.Login;
import utils.ProductPage;
import utils.ScrollUtils;

import java.util.HashMap;
import java.util.List;

public class Product_search extends Base_Class{


    public void swipeInSection(String sectionName, String direction) {
        try {
            // Find section header
            WebElement sectionHeader = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + sectionName + "\")"));

            // Find the product container below the header
            WebElement productContainer = sectionHeader.findElement(By.xpath("./following-sibling::*[contains(@class, 'RecyclerView') or contains(@class, 'HorizontalScrollView')]"));

            // Swipe within this container
            JavascriptExecutor js = (JavascriptExecutor) driver;
            HashMap<String, Object> swipeObject = new HashMap<>();
            swipeObject.put("elementId", ((RemoteWebElement) productContainer).getId());
            swipeObject.put("direction", direction);
            js.executeScript("mobile: swipeGesture", swipeObject);

            System.out.println("✅ Swiped " + direction + " in " + sectionName + " section");

        } catch (Exception e) {
            System.out.println("❌ Failed to swipe in " + sectionName + " section");
        }
    }


    @DataProvider(name = "categoryData")
    public Object[][] getCategoryData() {
        return JsonReader.getJSONData("resources/testdata/categorySearch.json", "categories");
    }

    @Test(enabled = false, dataProvider = "categoryData")
    public void Prod_search(String categoryName, String searchKeyword) {

        System.out.println("\nCategory search name: " + categoryName);

        try {
            // Select category dynamically
            WebElement category = driver.findElement(
                    By.xpath("//android.view.ViewGroup[@content-desc='" + categoryName + "']/android.widget.ImageView")
            );
            category.click();

            Thread.sleep(2000);

            // Products in category
            List<WebElement> products = driver.findElements(
                    By.xpath("//android.widget.TextView[contains(@text,'₹') or contains(@text,'$') or contains(@text,'Rs') or contains(@text,'Price')]"));


            System.out.println("Total products in category: " + products.size());

            // Go back to home
            driver.navigate().back();

            // Open search
            driver.findElement(By.xpath("//android.widget.TextView[@text=\"\uF563\"]")).click();

            driver.findElement(By.xpath("//android.widget.EditText[@text=\"Search products...\"]"))
                    .sendKeys(searchKeyword);

            Thread.sleep(2000);

            // Search results
            List<WebElement> searchResults = driver.findElements(
                    By.xpath("//android.widget.TextView[contains(@text,'kg')]")
            );

            if (searchResults.size() > 0) {
                System.out.println("Search returned " + searchResults.size() + " products:");
                for (WebElement product : searchResults) {
                    System.out.println(" - " + product.getText());
                }
            } else {
                WebElement noProducts = driver.findElement(
                        By.xpath("//android.widget.TextView[@text=\"No products found\"]")
                );
                System.out.println(noProducts.getText());
                System.out.println("BUG!!! No products found when searching with keyword: " + searchKeyword);
            }

            driver.navigate().back();

            driver.navigate().back();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Test(enabled = false)
    public void AddToCart() throws InterruptedException {
        ProductPage productPage = new ProductPage(driver);

        productPage.getCategoryByName("Fruits & Vegetables").click();
        Thread.sleep(2000);

        String prod1 = productPage.getProductByName("Bananas (1kg)").getText();
        System.out.println("added product " + prod1);

        productPage.getAddButtonInstance(0).click();
        driver.navigate().back();

        String count = productPage.getCartCount("1").getText();
        Thread.sleep(2000);
        Assert.assertEquals(count, "1", "BUG! Product count after first add mismatch.");

        productPage.getCategoryByName("Dairy & Eggs").click();
        Thread.sleep(2000);

        String prod2 = productPage.getProductByName("Milk (1L)").getText();
        System.out.println("added product " + prod2);

        productPage.getAddButtonInstance(0).click();
        driver.navigate().back();

        Thread.sleep(2000);
        String count2 = productPage.getCartCount("2").getText();
        Assert.assertEquals(count2, "2", "BUG! Product count after second add mismatch.");

        productPage.getCartIcon().click();

        String addedProd1 = productPage.getProductByName("Bananas (1kg)").getText();
        String addedProd2 = productPage.getProductByName("Milk (1L)").getText();

        Assert.assertEquals(addedProd1, prod1, "First product not found in cart.");
        Assert.assertEquals(addedProd2, prod2, "Second product not found in cart.");

        System.out.println("Selected products added successfully.");
    }

    @Test(enabled = false)
    public void AddToCartprice() throws InterruptedException {
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

    @Test(enabled = false)
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

    @Test (enabled = false)
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

    @Test(enabled = false)
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

    @Test
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
