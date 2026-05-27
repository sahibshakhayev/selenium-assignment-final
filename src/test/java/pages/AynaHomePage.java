package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AynaHomePage extends BasePage {

    // Locator for the E-services (Elektron xidmətlər) link
    // This uses a slightly more complex XPath to hit your assignment requirements
    private final By eServicesLinkLocator = By.xpath("/html/body/main/header/div[2]/div[1]/ul/li[2]/a");

    public AynaHomePage(WebDriver driver) {
        super(driver);
    }

    // Action: Open the website
    public void open() {
        this.driver.get("https://ayna.gov.az/az");
    }

    // Action: Click the E-services link
    public void clickEServices() {
        WebElement eServicesLink = waitAndReturnElement(eServicesLinkLocator);
        eServicesLink.click();
    }
}