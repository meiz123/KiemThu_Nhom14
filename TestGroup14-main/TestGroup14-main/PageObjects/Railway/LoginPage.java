package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage {
    // Locators
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.xpath("//input[@value='Login']");
    private final By errorMessageLabel = By.xpath("//p[contains(@class, 'message') and contains(@class, 'error')]");
    private final By ForgotPassword= By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");


    // Enter username
    public void enterUsername(String username) {
        Constant.WEBDRIVER.findElement(usernameField).sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        Constant.WEBDRIVER.findElement(passwordField).sendKeys(password);
    }

    // Click login button
    public void clickLoginButton() {
        Constant.WEBDRIVER.findElement(loginButton).click();
    }

    // Perform login action
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // Get error message element
    public WebElement getLblLoginErrorMsg() {
        return Constant.WEBDRIVER.findElement(errorMessageLabel);
    }
    public void clickForgotPassword()
    {
        Constant.WEBDRIVER.findElement(ForgotPassword).click();
    }
}
