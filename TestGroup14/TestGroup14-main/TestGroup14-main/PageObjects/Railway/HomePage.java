package Railway;

import Constant.Constant;
import org.openqa.selenium.By;

public class HomePage {
    // Locator for login tab, register tab, and book ticket tab
    private final By loginTab = By.xpath("//a[@href='/Account/Login.cshtml']");
    private final By registerTab = By.xpath("//a[@href='/Account/Register.cshtml']");
    private final By bookTicketTab = By.xpath("//a[@href='/Page/BookTicketPage.cshtml']");

    // Open the home page
    public void open() {
        Constant.WEBDRIVER.get(Constant.RAILWAY_URL + "/Page/HomePage.cshtml");
    }

    // Navigate to the login page
    public LoginPage gotoLoginPage() {
        Constant.WEBDRIVER.findElement(loginTab).click();
        return new LoginPage();
    }

    // Navigate to the register page
    public RegisterPage gotoRegisterPage() {
        Constant.WEBDRIVER.findElement(registerTab).click();
        return new RegisterPage();
    }

    // Navigate to the book ticket page
    public BookTicketPage gotoBookTicketPage() {
        Constant.WEBDRIVER.findElement(bookTicketTab).click();
        return new BookTicketPage();
    }
}