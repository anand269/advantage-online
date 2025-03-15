package pages;
import commonfiles.AdvantageBaseClass;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage extends AdvantageBaseClass{
    private WebDriver driver;
    public WebDriverWait wait;

    // Selector Variables 
    private By productTextSelector = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");
    private By goUpButtonSelector = By.xpath("//img[@name='go_up_btn']");
    private By popularItemsSelector = By.xpath("//a[normalize-space()='POPULAR ITEMS']");
    private By popularItemSelector = By.xpath("//p[@name='popular_item_16_name']");
    private By specialOfferSelector = By.xpath("(//a[normalize-space()='SPECIAL OFFER'])[1]");
    private By seeOfferButtonSelector = By.xpath("//button[@id='see_offer_btn']");
    private By productTitleSelector = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");
    private By addToCartButtonSelector = By.xpath("(//button[normalize-space()='ADD TO CART'])[1]");
    private By shoppingCartLinkSelector = By.xpath("//a[@id='shoppingCartLink']");
    private By cartProductSelector = By.xpath("/html[1]/body[1]/header[1]/nav[1]/ul[1]/li[2]/ul[1]/li[1]/tool-tip-cart[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/a[1]/h3[1]");
    private By removeButtonSelector = By.cssSelector(".removeProduct.iconCss.iconX");
    private By updatedTextSelector = By.cssSelector(".center.roboto-medium.ng-scope");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set timeout for waits
    }

    public void navigateToHomePage() {
        driver.get("https://www.advantageonlineshopping.com/#/");
        driver.manage().window().maximize();
    }

    public Long getScrollPosition() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.pageYOffset;");
    }

    public void scrollDown(int pixels) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0, " + pixels + ");");
    }

    public WebElement getScrollButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(goUpButtonSelector));
    }

    public void navigateToPopularItems() {
        try {
            Thread.sleep(4000);
            WebElement popularItemsLink = driver.findElement(popularItemsSelector);
            popularItemsLink.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(popularItemSelector));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyProduct(By productNameSelector, By productLinkSelector, String productLabel) {
        try {
            WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameSelector));
            String productName = productNameElement.getText();
            System.out.println(productLabel + " Name: " + productName);
            driver.findElement(productLinkSelector).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleSelector));
            String productPageTitle = driver.findElement(productTitleSelector).getText();
            Assert.assertEquals(productPageTitle, productName, productLabel + " name does not match!");

            driver.navigate().back(); // Navigate back to the popular items page
            wait.until(ExpectedConditions.visibilityOfElementLocated(productNameSelector)); // Wait for the product to be visible again

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void openPageAndScroll() {
        driver.get("https://www.advantageonlineshopping.com/#/");
        WebElement specialOfferLink = wait.until(ExpectedConditions.elementToBeClickable(specialOfferSelector));
        clickElement(specialOfferLink);
        wait.until(ExpectedConditions.visibilityOfElementLocated(specialOfferSelector));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 700);");
    }

    public void verifyLink(WebElement element, String expectedUrl) {
        clickElement(element);
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "The URL does not match the expected value.");
        System.out.println("URLs match: " + currentUrl);
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loader")));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click(); // Retry click
        }
    }

    public int getElementYCoordinate(By by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element.getLocation().getY();
    }

    public void clickAndVerifyLink(By linkBy, int expectedY) throws InterruptedException {
        WebElement linkElement = wait.until(ExpectedConditions.elementToBeClickable(linkBy));
        linkElement.click();
        Thread.sleep(3000); // Wait for navigation
        System.out.println("Scrolled to: " + driver.getCurrentUrl());

        int actualY = linkElement.getLocation().getY();
        System.out.println("Y-coordinate after clicking: " + actualY);
        Assert.assertTrue(isYWithinRange(actualY, expectedY), "Link is out of range!");
    }

    private boolean isYWithinRange(int actualY, int requiredY) {
        return (actualY >= requiredY - 200) && (actualY <= requiredY + 200);
    }

    public void mouseClickByLocator(By seeOfferButtonSelector2) throws InterruptedException {
        Thread.sleep(2000);
        WebElement el = driver.findElement(seeOfferButtonSelector2);
        Actions builder = new Actions(driver);
        builder.moveToElement(el).click(el).perform();
    }

    public void addProductToCartAndVerify() {
        try {
            // Open the desired URL
            driver.get("https://www.advantageonlineshopping.com/#/");
            driver.manage().window().maximize();

            Thread.sleep(5000); // Wait for the page to load

            // Click on the SPECIAL OFFER link
            mouseClickByLocator(seeOfferButtonSelector);

            // Wait for the SEE OFFER button to be clickable and click it
            WebElement seeOfferButton = wait.until(ExpectedConditions.elementToBeClickable(seeOfferButtonSelector));
            seeOfferButton.click();

            // Sleep for 5 seconds to wait for the page to load
            Thread.sleep(5000);

            // Get the product text
            String productText = driver.findElement(productTextSelector).getText();

            // Click on the ADD TO CART button
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonSelector));
            addToCartButton.click();

            // Hover over the shopping cart link
            WebElement shoppingCartLink = driver.findElement(shoppingCartLinkSelector);
            Actions actions = new Actions(driver);
            actions.moveToElement(shoppingCartLink).perform();

            // Sleep for 2 seconds to observe the hover effect
            Thread.sleep(2000);

            // Verify if the product was added to the cart
            WebElement cartProduct = driver.findElement(cartProductSelector);
            String cartProductText = cartProduct.getText();

            // Check if the added product matches the expected product
            if (productText.equals(cartProductText)) {
                System.out.println("Product successfully added to cart: " + cartProductText);
            } else {
                System.out.println("Product mismatch: Expected - " + productText + ", Found - " + cartProductText);
            }

            // Hover over the cart again to click remove
            actions.moveToElement(shoppingCartLink).perform();
            Thread.sleep(2000); // Wait for hover effect

            // Click on the Remove button
            WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(removeButtonSelector));
            removeButton.click();

            // Verify the text from the specified element after removal
            String updatedText = wait.until(ExpectedConditions.visibilityOfElementLocated(updatedTextSelector)).getText();
            System.out.println("Updated text after removing product: " + updatedText);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}