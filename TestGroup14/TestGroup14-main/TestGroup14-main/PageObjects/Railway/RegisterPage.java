package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RegisterPage {
    // Locators
    private final By txtEmail = By.id("email");
    private final By txtPassword = By.id("password");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtPid = By.id("pid");
    private final By btnRegister = By.xpath("//input[@type='submit' and @value='Register']");
    private final By lblSuccessMsg = By.xpath("//div[@id='content']/p");
    private final By lblErrorMsg = By.xpath("//p[@class='message error']");

    // Elements
    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    public WebElement getTxtPassword() {
        return Constant.WEBDRIVER.findElement(txtPassword);
    }

    public WebElement getTxtConfirmPassword() {
        return Constant.WEBDRIVER.findElement(txtConfirmPassword);
    }

    public WebElement getTxtPid() {
        return Constant.WEBDRIVER.findElement(txtPid);
    }

    public WebElement getBtnRegister() {
        return Constant.WEBDRIVER.findElement(btnRegister);
    }

    // Methods to enter values
    public void enterEmail(String email) {
        getTxtEmail().clear();
        getTxtEmail().sendKeys(email);
    }

    public void enterPassword(String password) {
        getTxtPassword().clear();
        getTxtPassword().sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        getTxtConfirmPassword().clear();
        getTxtConfirmPassword().sendKeys(confirmPassword);
    }

    public void enterPipPassword(String pipPassword) {
        getTxtPid().clear();
        getTxtPid().sendKeys(pipPassword);
    }

    public void clickRegisterButton() {
        getBtnRegister().click();
    }

    public void register(String email, String password, String confirmPassword, String pipPassword) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPipPassword(pipPassword);
        clickRegisterButton();
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessMsg)).getText().trim();
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMsg)).getText().trim();
    }
}
