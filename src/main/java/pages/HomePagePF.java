package pages;

import commonfiles.AdvantageBaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePagePF extends AdvantageBaseClass {
    private WebDriver driver;
    public WebDriverWait wait;

    // By selector variables
    private By goUpButtonSelector = By.xpath("//img[@name='go_up_btn']");
    private By popularItemsLinkSelector = By.xpath("//a[normalize-space()='POPULAR ITEMS']");
    private By popularItemSelector = By.xpath("//p[@name='popular_item_16_name']");
    private By specialOfferSelector = By.xpath("(//a[normalize-space()='SPECIAL OFFER'])[1]");
    private By seeOfferButtonSelector = By.xpath("//button[@id='see_offer_btn']");
    private By productTitleSelector = By.xpath("//h1[@class='roboto-regular screen768 ng-binding']");
    private By addToCartButtonSelector = By.xpath("(//button[normalize-space()='ADD TO CART'])[1]");
    private By shoppingCartLinkSelector = By.xpath("//a[@id='shoppingCartLink']");
    private By exploreNowButton1Selector = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[1]");
    private By exploreNowButton2Selector = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[2]");
    private By exploreNowButton3Selector = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[3]");
    private By laptopElementSelector = By.cssSelector("div.uiview.ng-scope span:nth-child(3)");

    public HomePagePF(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set timeout for waits
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        driver.get("https://www.advantageonlineshopping.com/#/");
        driver.manage().window().maximize();
    }

    public WebElement getGoUpButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(goUpButtonSelector));
    }

    public WebElement getPopularItemsLink() {
        return wait.until(ExpectedConditions.elementToBeClickable(popularItemsLinkSelector));
    }

    public WebElement getPopularItem() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popularItemSelector));
    }

    public WebElement getExploreNowButton1() {
        return wait.until(ExpectedConditions.elementToBeClickable(exploreNowButton1Selector));
    }

    public WebElement getExploreNowButton2() {
        return wait.until(ExpectedConditions.elementToBeClickable(exploreNowButton2Selector));
    }

    public WebElement getExploreNowButton3() {
        return wait.until(ExpectedConditions.elementToBeClickable(exploreNowButton3Selector));
    }

    public WebElement getSpecialOfferElement() {
        return wait.until(ExpectedConditions.elementToBeClickable(specialOfferSelector));
    }

    public WebElement getSeeOfferButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(seeOfferButtonSelector));
    }

    public WebElement getProductTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productTitleSelector));
    }

    public WebElement getAddToCartButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonSelector));
    }

    public WebElement getShoppingCartLink() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartLinkSelector));
    }

    public WebElement getLaptopElement() {
        return wait.until(ExpectedConditions.elementToBeClickable(laptopElementSelector));
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public void verifyLink(WebElement element, String expectedUrl) {
        element.click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "URL did not match!");
    }
}
