package Railway;

import Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ChangePasswordTest {
    public ChangePasswordTest() {
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @Test(description = "TC09: User can change password")
    public void TC09() {
        System.out.println("TC09 - User can change password successfully");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        GeneralPage generalPage = new GeneralPage();
        generalPage.navigateToChangePasswordPage();
        ChangePasswordPage changePasswordPage = new ChangePasswordPage();

        changePasswordPage.changePassword(Constant.PASSWORD, Constant.NEW_PASSWORD, Constant.NEW_PASSWORD);

        String successMsg = changePasswordPage.getSuccessMessage();
        String expectedSuccessMsg = "Your password has been updated";
        Assert.assertTrue(successMsg.contains(expectedSuccessMsg), "Success message does not contain expected value: " + expectedSuccessMsg);
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