package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;

public class ScrollUtils {

    private final AndroidDriver driver;

    // Constructor
    public ScrollUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    // Generic vertical scroll
    public void scrollDown() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.7);
        int endY = (int) (size.height * 0.4);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    /**
     * Scrolls vertically until the element with given text is visible.
     * Optionally clicks it automatically.
     *
     * @param visibleText The partial text of the element
     * @param maxScrolls  Maximum number of scroll attempts
     * @param click       If true, clicks the element when found
     * @return WebElement if found, else null
     */
    public WebElement scrollToElement(String visibleText, int maxScrolls, boolean click) {
        WebElement element = null;
        int scrolls = 0;

        while (scrolls < maxScrolls) {
            try {
                element = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"" + visibleText + "\")"
                ));

                // Check if the element is displayed on screen
                if (element.isDisplayed()) {
                    System.out.println(" Element found and visible: " + visibleText);
                    if (click) {
                        element.click();
                        System.out.println(" Element clicked: " + visibleText);
                    }
                    break; // Stop scrolling
                } else {
                    scrollDown();
                    scrolls++;
                    Thread.sleep(500); // small wait to allow element to appear
                }

            } catch (Exception e) {
                // Element not found yet, scroll and try again
                scrollDown();
                scrolls++;
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }

        if (element == null || !element.isDisplayed()) {
            System.out.println("❌ Element NOT found after " + maxScrolls + " scrolls: " + visibleText);
        }

        return element;
    }
}
