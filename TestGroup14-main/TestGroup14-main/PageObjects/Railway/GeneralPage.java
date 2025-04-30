package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class GeneralPage {
    // Locators for tabs
    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
    private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
    private final By logoutTab = By.xpath("//a[@href='/Account/Logout']");
    private final By welcomeMessage = By.xpath("//div[@class='account']/strong");
    private final By timetableTab = By.linkText("Timetable");

    // Check if tabs (My ticket, Change password, Logout) are displayed
    public boolean areTabsDisplayed() {
        return Constant.WEBDRIVER.findElement(myTicketTab).isDisplayed() &&
                Constant.WEBDRIVER.findElement(changePasswordTab).isDisplayed() &&
                Constant.WEBDRIVER.findElement(logoutTab).isDisplayed();
    }

    // Navigate to My ticket page
    public boolean navigateToMyTicketPage() {
        Constant.WEBDRIVER.findElement(myTicketTab).click();
        return Constant.WEBDRIVER.getCurrentUrl().contains("ManageTicket.cshtml");
    }

    // Navigate to Change password page
    public boolean navigateToChangePasswordPage() {
        Constant.WEBDRIVER.findElement(changePasswordTab).click();
        return Constant.WEBDRIVER.getCurrentUrl().contains("ChangePassword.cshtml");
    }

    // Get welcome message
    public String getWelcomeMessage() {
        WebElement element = Constant.WEBDRIVER.findElement(welcomeMessage);
        return element.getText().trim();
    }

    // Get Timetable tab locator
    public By getTimetableTab() {
        return timetableTab;
    }
}