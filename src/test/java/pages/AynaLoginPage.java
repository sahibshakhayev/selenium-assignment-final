package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AynaLoginPage extends BasePage {

    // 1. The initial pop-up button on AYNA
    private final By systemLoginButton = By.xpath("//button[contains(text(), 'Sistemə giriş')]");

    // 2. The ASAN Login Method Selectors
    private final By otherMethodsTab = By.xpath("//*[contains(text(), 'Digər üsullar')]");
    private final By idNumberOption = By.xpath("//*[contains(text(), 'İdentifikasiya nömrəsi ilə')]");

    // 3. The Inputs and Submit Button
    // Complex XPath: Looks for the input field that comes after the label containing the text
    private final By finInput = By.xpath("//*[contains(text(), 'İdentifikasiya nömrəsi')]/following::input[1]");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By loginSubmitButton = By.xpath("/html/body/app-root/auth-layout/div[1]/div[2]/div/div/div/app-log-in/div[3]/div[2]/div");

    // 4. The Error Message
    private final By errorMessage = By.xpath("//*[contains(text(), 'FİN məlumatı və ya şifrə yanlışdır')]");

    public AynaLoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickSystemLogin() {
        waitAndReturnElement(systemLoginButton).click();
    }

    public void navigateToIdLogin() {
        waitAndReturnElement(otherMethodsTab).click();
        waitAndReturnElement(idNumberOption).click();
    }

    public void login(String fin, String password) {
        WebElement finElement = waitAndReturnElement(finInput);
        finElement.clear();
        finElement.sendKeys(fin);

        WebElement passElement = waitAndReturnElement(passwordInput);
        passElement.clear();
        passElement.sendKeys(password);

        waitAndReturnElement(loginSubmitButton).click();
    }

    public String getErrorMessage() {
        // Wait specifically for the error to become visible
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return this.driver.findElement(errorMessage).getText();
    }
}