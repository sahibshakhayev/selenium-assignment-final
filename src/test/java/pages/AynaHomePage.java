package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AynaHomePage extends BasePage {

    // 1. The main menu link
    private final By eServicesLinkLocator = By.xpath("/html/body/main/header/div[2]/div[1]/ul/li[2]/a");
    
    // 2. The specific service to trigger the modal (Elektron formulyar)
    private final By formulyarServiceLocator = By.xpath("/html/body/main/div[1]/div[3]/div/section[1]/div[2]/div[2]/div/div/div[2]/div/div[1]");

    public AynaHomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        this.driver.get("https://ayna.gov.az/az");
    }

    public void clickEServices() {
        WebElement link = waitAndReturnElement(eServicesLinkLocator);
        this.wait.until(ExpectedConditions.elementToBeClickable(link));
        link.click();
    }

    public void clickElektronFormulyarService() {
        WebElement service = waitAndReturnElement(formulyarServiceLocator);
        this.wait.until(ExpectedConditions.elementToBeClickable(service));
        service.click();
    }

    public void hoverOverEServices() {
        WebElement link = waitAndReturnElement(eServicesLinkLocator);
        // Use Actions to simulate a physical mouse hover
        new org.openqa.selenium.interactions.Actions(this.driver).moveToElement(link).perform();
    }
}