package testCases;

import pages.HomePagePF;

import commonfiles.AdvantageBaseClass;
import commonfiles.AdvantageExtent;
import pages.HomePage;

import com.aventstack.extentreports.Status;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;  // Import the AfterMethod annotation
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestResult; // Import ITestResult for capturing test result

import java.io.IOException;

public class HomePageTestCases extends AdvantageBaseClass {

    private HomePage demoHome;  // Instance of the HomePage class
    private HomePagePF homePagePF;  // Instance of the HomePagePF class

    // Selector Variables
    private By exploreNowButtonSelector1 = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[1]");
    private By exploreNowButtonSelector2 = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[2]");
    private By exploreNowButtonSelector3 = By.xpath("(//button[@name='explore_now_btn'][normalize-space()='EXPLORE NOW'])[3]");
    private By specialOfferSelector = By.xpath("//h3[normalize-space()='SPECIAL OFFER']");
    private By popularItemsSelector = By.xpath("//h3[normalize-space()='POPULAR ITEMS']");
    private By contactUsSelector = By.xpath("//h1[normalize-space()='CONTACT US']");
    private By speakersImgSelector = By.xpath("//div[@id='speakersImg']");

    private By firstProductNameSelector = By.xpath("//p[@name='popular_item_16_name']");
    private By firstProductDetailsSelector = By.xpath("//label[@id='details_16']");
    private By secondProductNameSelector = By.xpath("//p[@name='popular_item_10_name']");
    private By secondProductDetailsSelector = By.xpath("//label[@id='details_10']");
    private By thirdProductNameSelector = By.xpath("//p[@name='popular_item_21_name']");
    private By thirdProductDetailsSelector = By.xpath("//label[@id='details_21']");

    private By specialOfferLinkSelector = By.partialLinkText("SPECIAL OFFER");
    private By popularItemsLinkSelector = By.cssSelector("li:nth-child(6) a:nth-child(1)");
    private By contactUsLinkSelector = By.cssSelector("li:nth-child(5) a:nth-child(1)");
    private By speakersLinkSelector = By.cssSelector("li:nth-child(8) a:nth-child(1)");

    @BeforeClass
    public void setUp() {
        invokeBrowser("chrome"); // Initialize browser
        demoHome = new HomePage(driver); // Initialize HomePage
        homePagePF = PageFactory.initElements(driver, HomePagePF.class); // Initialize HomePagePF with PageFactory
        AdvantageExtent.getInstance(); 

    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                screenShot(); // Capture screenshot if the test case fails
                System.out.println("ScreenShot Taken");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test(enabled = true, priority = 1)
    public void testScrollAndBackToTop() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        Thread.sleep(5000); // Wait for the page to load

        Long initialScrollPosition = demoHome.getScrollPosition();
        System.out.println("Initial scroll position: " + initialScrollPosition);

        demoHome.scrollDown(2500);
        Thread.sleep(3000); // Wait for scrolling to complete

        Long afterScrollPosition = demoHome.getScrollPosition();
        System.out.println("Scroll position after scrolling down: " + afterScrollPosition);

        demoHome.getScrollButton().click();
        Thread.sleep(3000); // Wait for the scroll action to complete

        Long finalScrollPosition = demoHome.getScrollPosition();
        System.out.println("Scroll position after clicking the back to top button: " + finalScrollPosition);

        Assert.assertEquals(finalScrollPosition, Long.valueOf(0), "Failed to return to the top of the page.");
        System.out.println("Successfully returned to the top of the page.");
        AdvantageExtent.createTest("ScrollButton").log(Status.PASS, "The ScrollUp Button works.");

        
        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 2)
    public void testFirstProduct() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        demoHome.navigateToPopularItems();
        demoHome.verifyProduct(firstProductNameSelector, firstProductDetailsSelector, "First product");
        AdvantageExtent.createTest("Popular Items Product One").log(Status.PASS, "Product One is linked to its respective Product page.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 2)
    public void testSecondProduct() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        demoHome.navigateToPopularItems();
        Thread.sleep(4000);
        demoHome.verifyProduct(secondProductNameSelector, secondProductDetailsSelector, "Second product");
        AdvantageExtent.createTest("Popular Items Product Two").log(Status.PASS, "Product Two is linked to its respective Product page.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 2)
    public void testThirdProduct() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        demoHome.navigateToPopularItems();
        demoHome.verifyProduct(thirdProductNameSelector, thirdProductDetailsSelector, "Third product");
        AdvantageExtent.createTest("Popular Items Product Three").log(Status.PASS, "Product Three is linked to its respective Product page.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 3)
    public void testExplorePages() throws InterruptedException {
        Thread.sleep(2000);
        homePagePF.navigateToHomePage(); // Use homePagePF for this method

        // Click on the "EXPLORE NOW" button and verify the URL
        WebElement exploreNowButton = homePagePF.getExploreNowButton1();
        homePagePF.verifyLink(exploreNowButton, "https://www.advantageonlineshopping.com/#/category/Tablets/3");

        Thread.sleep(2000); // Wait for the page to fully load
        driver.navigate().back();
        homePagePF.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='SPECIAL OFFER']"))); // Ensure the element is visible

        // Click on the specified element
        WebElement selectedElement = homePagePF.getSpecialOfferElement();
        homePagePF.clickElement(selectedElement);

        // Verify the second "EXPLORE NOW" button for Speakers
        WebElement exploreNowButton2 = homePagePF.getExploreNowButton2();
        homePagePF.verifyLink(exploreNowButton2, "https://www.advantageonlineshopping.com/#/category/Speakers/4");

        Thread.sleep(2000); // Wait for the page to fully load
        driver.navigate().back();
        homePagePF.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='SPECIAL OFFER']"))); // Ensure the element is visible

        // Click on the specified element for Laptops
        WebElement selectedElement1 = homePagePF.getLaptopElement();
        homePagePF.clickElement(selectedElement1);

        // Verify the third "EXPLORE NOW" button for Laptops
        WebElement exploreNowButton3 = homePagePF.getExploreNowButton3();
        homePagePF.verifyLink(exploreNowButton3, "https://www.advantageonlineshopping.com/#/category/Laptops/1");
        AdvantageExtent.createTest("Explore Pages").log(Status.PASS, "Respective Product Category Pages were Opened.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 4)
    public void testNavbarCoordinates() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        Thread.sleep(5000); // Wait for the page to load

        // Get coordinates for various elements
        int specialOfferY = demoHome.getElementYCoordinate(specialOfferSelector);
        int popularItemsY = demoHome.getElementYCoordinate(popularItemsSelector);
        int contactUsY = demoHome.getElementYCoordinate(contactUsSelector);
        int speakersImgY = demoHome.getElementYCoordinate(speakersImgSelector);

        // Verify positions after clicking links
        demoHome.clickAndVerifyLink(specialOfferLinkSelector, specialOfferY);
        demoHome.clickAndVerifyLink(popularItemsLinkSelector, popularItemsY);
        demoHome.clickAndVerifyLink(contactUsLinkSelector, contactUsY);
        demoHome.clickAndVerifyLink(speakersLinkSelector, speakersImgY);
        AdvantageExtent.createTest("NavBar").log(Status.PASS, "Page Scrolls to respective Sections.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @Test(enabled = true, priority = 5)
    public void testAddToCart() throws InterruptedException {
        Thread.sleep(2000);
        demoHome.navigateToHomePage();
        demoHome.addProductToCartAndVerify(); 
        AdvantageExtent.createTest("AddToCart").log(Status.PASS, "Product is added to Cart.");

        System.out.println("----------------------------------------------------------------------------------");
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        AdvantageExtent.getInstance().flush();
        closeBrowser(); // Close the browser from the base class
    }
}
