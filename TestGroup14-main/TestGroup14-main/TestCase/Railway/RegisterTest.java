package Railway;

import Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest {
    public RegisterTest() {
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @Test(description = "TC07: User can register successfully")
    public void TC07() {
        System.out.println("TC07 - User can register successfully");

        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();


        String email = Constant.Email;
        String password = Constant.PassRes;
        String confirmPassword = Constant.ConfirmPassRes;
        String pipPassword = Constant.PipPassRes;


        registerPage.register(email, password, confirmPassword, pipPassword);


        String successMessage = registerPage.getSuccessMessage();
        String expectedSuccessMessage = "Thank you for registering your account";


        Assert.assertEquals(successMessage, expectedSuccessMessage, "Success message does not match expected value.");
    }



    @Test(description = "TC010: User cannot register with mismatched passwords")
    public void TC010() {
        System.out.println("TC010 - User cannot register with mismatched passwords");
        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();

        String email = Constant.EMAILRES;
        String password = Constant.PassRes;
        String confirmPassFail = Constant.ConfirmPassFailRes;
        String pipPassword = Constant.PipPassRes;
        registerPage.register(email, password, confirmPassFail, pipPassword);
        String errorMessage = registerPage.getErrorMessage();
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(errorMessage, expectedErrorMessage, "Error message does not match expected value.");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - User can create new account");
        HomePage homePage = new HomePage();
        homePage.open();
        RegisterPage registerPage = homePage.gotoRegisterPage();

        registerPage.register(Constant.EMAILRES,"","","" );
        String errorMessage = registerPage.getErrorMessage();
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(errorMessage, expectedErrorMessage, "Error message does not match expected value.");
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
