package tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.AynaHomePage;
import pages.AynaLoginPage;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.UUID;

public class AynaLoginTest {

    private WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        
        // The "Smart" environment check (Local Docker vs GitHub Actions)
        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            this.driver = new ChromeDriver(options);
        } else {
            // Connect to local Docker Selenium container
            this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        }
        
        this.driver.manage().window().maximize();
    }

    @Test
    public void testInvalidAsanLoginShowsError() {
        // 1. Start at the Home Page and open the ASAN modal
        AynaHomePage homePage = new AynaHomePage(this.driver);
        homePage.open();
        homePage.hoverOverEServices();
        homePage.clickEServices();
        homePage.clickElektronFormulyarService();
        
        // 2. Switch to the Login Page Object to handle the pop-up and forms
        AynaLoginPage loginPage = new AynaLoginPage(this.driver);
        
        // Click the blue "Sistemə giriş" button on the AYNA site
        loginPage.clickSystemLogin(); 


        
        // Navigate the ASAN menu tabs based on your screenshots
        loginPage.navigateToIdLogin();
        
        // Attempt to log in with fake data
        loginPage.login(UUID.randomUUID().toString().substring(0, 7), "FakePassword123!");

        // 3. Verify the form catches the error
        String errorText = loginPage.getErrorMessage();
        
        // Assert that the red box appears and contains the expected text
        Assert.assertTrue("The ASAN login should reject fake credentials and show an error", 
            errorText.contains("yanlışdır"));
    }


    @Test
    public void testCookieManipulation() {
        AynaHomePage homePage = new AynaHomePage(this.driver);
        homePage.open();
        
        // 1. Add a custom cookie to the browser
        this.driver.manage().addCookie(new org.openqa.selenium.Cookie("automation_test", "passed"));
        
        // 2. Read it back to verify it was saved successfully
        org.openqa.selenium.Cookie testCookie = this.driver.manage().getCookieNamed("automation_test");
        Assert.assertNotNull("Cookie should be successfully added", testCookie);
        Assert.assertEquals("passed", testCookie.getValue());
    }

    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
}