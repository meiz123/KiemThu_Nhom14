package testcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC03 {

    static class LoginData {
        public static final String VALID_USERNAME = "example@gmail.com";
        public static final String INVALID_PASSWORD = "1111111111";
        public static final String EXPECTED_ERROR_MESSAGE = "There was a problem with your login and/or errors exist in your form.";
    }

    private static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    // Luồng chính của test case
    public static void main(String[] args) {
        WebDriver driver = initializeDriver();

        // Bước 1: Điều hướng đến trang web QA Railway
        driver.get("http://railwayb2.somee.com/Page/HomePage.cshtml");

        // Bước 2: Nhấn vào tab Login
        WebElement loginTab = driver.findElement(By.xpath("//a[@href='/Account/Login.cshtml']"));
        loginTab.click();

        // Bước 3: Nhập email hợp lệ và mật khẩu không hợp lệ
        WebElement emailField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        emailField.sendKeys(LoginData.VALID_USERNAME);
        passwordField.sendKeys(LoginData.INVALID_PASSWORD);

        // Bước 4: Nhấn nút Login
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
        loginButton.click();

        // Bước 5: Kiểm tra thông báo lỗi và so sánh với kết quả mong đợi
        WebElement errorMessage = driver.findElement(By.xpath("//p[contains(@class, 'message') and contains(@class, 'error')]"));
        String actualMessage = errorMessage.getText().trim();

        if (actualMessage.equals(LoginData.EXPECTED_ERROR_MESSAGE)) {
            System.out.println("TC03 Passed");
        } else {
            System.out.println("TC03 Failed");
        }

    }
}