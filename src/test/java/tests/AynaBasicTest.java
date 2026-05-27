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
        // Connect to your Docker Selenium container
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
    }

    @Test
    public void testNavigationToEServices() {
        // Instantiate the Page Object
        AynaHomePage homePage = new AynaHomePage(this.driver);

        // Perform actions
        homePage.open();
        homePage.clickEServices();

        // Verify the result by checking the current URL or page body
        String bodyText = homePage.getBodyText();
        Assert.assertTrue("The page should contain e-services content", bodyText.contains("Xidmətlər"));
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}