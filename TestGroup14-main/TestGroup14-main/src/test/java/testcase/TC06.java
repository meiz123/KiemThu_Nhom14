package testcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC06 {

    static class LoginData {
        public static final String VALID_USERNAME = "example@gmail.com";
        public static final String VALID_PASSWORD = "123456788";
    }

    private static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public static void main(String[] args) {
        WebDriver driver = initializeDriver();

        // Bước 1: Điều hướng đến trang web QA Railway
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");

        // Bước 2: Đăng nhập với tài khoản hợp lệ
        WebElement loginTab = driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']"));
        loginTab.click();

        WebElement emailField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        emailField.sendKeys(LoginData.VALID_USERNAME);
        passwordField.sendKeys(LoginData.VALID_PASSWORD);

        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
        loginButton.click();

        // Bước 3: Kiểm tra sự xuất hiện của các tab "My ticket", "Change password" và "Logout"
        WebElement myTicketTab = driver.findElement(By.xpath("//a[@href='/Page/ManageTicket.cshtml']"));
        WebElement changePasswordTab = driver.findElement(By.xpath("//a[@href='/Account/ChangePassword.cshtml']"));
        WebElement logoutTab = driver.findElement(By.xpath("//a[@href='/Account/Logout']"));

        boolean tabsDisplayed = myTicketTab.isDisplayed() && changePasswordTab.isDisplayed() && logoutTab.isDisplayed();

        // Bước 4: Kiểm tra điều hướng đến trang "My ticket"
        myTicketTab.click();
        String myTicketUrl = driver.getCurrentUrl();
        boolean myTicketRedirected = myTicketUrl.contains("/Page/ManageTicket.cshtml");

        // Bước 5: Tìm lại và kiểm tra điều hướng đến trang "Change password"
        changePasswordTab = driver.findElement(By.xpath("//a[@href='/Account/ChangePassword.cshtml']"));
        changePasswordTab.click();
        String changePasswordUrl = driver.getCurrentUrl();
        boolean changePasswordRedirected = changePasswordUrl.contains("/Account/ChangePassword.cshtml");

        // Kết quả kiểm tra
        if (tabsDisplayed && myTicketRedirected && changePasswordRedirected) {
            System.out.println("TC06 Passed");
        } else {
            System.out.println("TC06 Failed");
        }
    }
}