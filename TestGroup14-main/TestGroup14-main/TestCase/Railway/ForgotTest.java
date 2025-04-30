package Railway;

import Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ForgotTest {

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        Constant.WEBDRIVER.get("http://railwayb2.somee.com/Account/Login.cshtml");
    }

    @Test
    public void TC12() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickForgotPassword();

        ForgotPage forgotPage = new ForgotPage();
        String email = Constant.EMAILRES;
        forgotPage.forgotPassword(email);

        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        assertTrue(currentUrl.contains("PasswordChangeForm"), "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.!");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
            Constant.WEBDRIVER = null;
        }
    }
}
