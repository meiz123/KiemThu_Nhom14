package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ForgotPage {

    private final By txtEmail = By.xpath("//input[@id='email']");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");

    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(txtEmail);
    }

    public WebElement getBtnSend() {
        return Constant.WEBDRIVER.findElement(btnSendInstructions);
    }

    public void forgotPassword(String email) {
        WebElement emailField = getTxtEmail();
        WebElement btnSend = getBtnSend();

        emailField.clear(); // Gợi ý thêm để đảm bảo trường email trống trước khi nhập
        emailField.sendKeys(email);
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        btnSend.click();
    }
}
