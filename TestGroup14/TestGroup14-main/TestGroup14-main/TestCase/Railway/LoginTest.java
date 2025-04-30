package Railway;

import Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LoginTest {
    public LoginTest() {
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }

    @Test
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password and see welcome message");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        GeneralPage generalPage = new GeneralPage();
        String welcomeMsg = generalPage.getWelcomeMessage();
        String expectedWelcomeMsg = "Welcome " + Constant.USERNAME;
        assertEquals(welcomeMsg, expectedWelcomeMsg, "Welcome message does not match expected value.");

    }

    @Test(description = "TC02: User cannot login without username")
    public void TC02() {
        System.out.println("TC02 - User cannot login without username");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);

        String expectedError = "There was a problem with your login and/or errors exist in your form.";
        String actualError = loginPage.getLblLoginErrorMsg().getText();
        assertEquals(actualError, expectedError, "Error message does not match expected value.");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "Invalid");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";
        if (!actualErrorMsg.equals(expectedErrorMsg)) {
            System.out.println("Error message is not displayed as expected");
        }
    }
    @Test
    public void TC05() {
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        String username=Constant.UNameFail;
        String password=Constant.PWFail;
        loginPage.enterUsername(username);
        for (int i = 0; i < 4; i++) {
            loginPage.enterPassword(password);
            loginPage.clickLoginButton();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String actualMessage = String.valueOf(loginPage.getLblLoginErrorMsg());
        String expectedMessage = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";

        System.out.println("Actual message: " + actualMessage);
        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected lockout warning.\nActual: " + actualMessage);

    }
    @Test
    public void TC06() {
        System.out.println("TC06 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        GeneralPage generalPage = new GeneralPage();

        // Check if tabs are displayed
        if (!generalPage.areTabsDisplayed()) {
            System.out.println("Error message is not displayed as expected: Tabs (My ticket, Change password, Logout) are not displayed");
        }

        // Check navigation to My ticket page
        if (!generalPage.navigateToMyTicketPage()) {
            System.out.println("Error message is not displayed as expected: Navigation to My ticket page failed");
        }

        // Check navigation to Change password page
        if (!generalPage.navigateToChangePasswordPage()) {
            System.out.println("Error message is not displayed as expected: Navigation to Change password page failed");
        }
    }

    @Test(description = "TC08: User can't login with an account hasn't been activated")
    public void TC08() {
        System.out.println("TC08 - User cannot login with an account that hasn't been activated");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.INACTIVE_USERNAME, Constant.INACTIVE_PASSWORD);

        String errorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";
        assertEquals(errorMsg, expectedErrorMsg, "Error message does not match expected value.");
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
