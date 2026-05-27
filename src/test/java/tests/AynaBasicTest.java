package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.AynaHomePage;

import java.net.MalformedURLException;
import java.net.URL;

public class AynaBasicTest {

    private RemoteWebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        // Connect to Docker Selenium container
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
    }

    @Test
    public void testNavigationToEServices() {
        // Instantiate the Page Object
        AynaHomePage homePage = new AynaHomePage(this.driver);

        // Perform actions
        homePage.open();
        Assert.assertTrue(driver.getTitle().contains("Əsas"));
        homePage.clickEServices();

        // Verify the result by checking the current URL or page body
        String bodyText = homePage.getBodyText();
        Assert.assertTrue("The page should contain e-services content", bodyText.contains("Xidmətlər"));
    }

    @Test
    public void testBrowserHistoryNavigation() throws InterruptedException {
        AynaHomePage homePage = new AynaHomePage(this.driver);

        // 1. Open the Home Page and save its URL
        homePage.open();
        
        // Wait a moment for the URL to fully register, then save it
        Thread.sleep(1500); 
        String initialUrl = this.driver.getCurrentUrl();

        // 2. Navigate to a new page (E-Services)
        homePage.clickEServices();
        Thread.sleep(1500);
        String newUrl = this.driver.getCurrentUrl();

        // Verify we actually went to a new page
        Assert.assertNotEquals("URL should change after clicking E-Services", initialUrl, newUrl);

        // 3. Test the BACK button
        this.driver.navigate().back();
        Thread.sleep(1500);
        Assert.assertEquals("Browser should return to the initial URL", initialUrl, this.driver.getCurrentUrl());

        // 4. Test the FORWARD button
        this.driver.navigate().forward();
        Thread.sleep(1500);
        Assert.assertEquals("Browser should go forward to the E-Services URL", newUrl, this.driver.getCurrentUrl());
    }


    @Test
    public void testStaticHomePageContent() {
        AynaHomePage homePage = new AynaHomePage(this.driver);
        homePage.open();
        
        // Grab the text of the entire page body
        String bodyText = this.driver.findElement(org.openqa.selenium.By.tagName("body")).getText();
        
        // Verify standard static text is present
        Assert.assertTrue("The home page should display 'E-xidmətlər'", bodyText.contains("E-xidmətlər"));
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}