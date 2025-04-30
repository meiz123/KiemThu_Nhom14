package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class ChangePasswordPage {
    // Locators
    private final By txtCurrentPassword = By.xpath("//input[@id='currentPassword']");
    private final By txtNewPassword = By.xpath("//input[@id='newPassword']");
    private final By txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By lblSuccessMsg = By.cssSelector("p.message.success");

    // Wait
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

    // Elements
    private WebElement getTxtCurrentPassword() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(txtCurrentPassword));
    }

    private WebElement getTxtNewPassword() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(txtNewPassword));
    }

    private WebElement getTxtConfirmPassword() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(txtConfirmPassword));
    }

    private WebElement getBtnChangePassword() {
        return wait.until(ExpectedConditions.elementToBeClickable(btnChangePassword));
    }

    private WebElement getLblSuccessMsg() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessMsg));
    }

    // Methods
    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        getTxtCurrentPassword().sendKeys(currentPassword);
        getTxtNewPassword().sendKeys(newPassword);
        getTxtConfirmPassword().sendKeys(confirmPassword);
        getBtnChangePassword().click();
    }

    public String getSuccessMessage() {
        return getLblSuccessMsg().getText().trim();
    }
}