package testcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC11 {

    static class RegisterData {
        public static final String VALID_EMAIL = "example@gmail.com"; // Email duy nhất
        public static final String EXPECTED_FORM_ERROR = "There're errors in the form. Please correct the errors and try again.";
        public static final String EXPECTED_PASSWORD_ERROR = "Invalid password length";
        public static final String EXPECTED_PID_ERROR = "Invalid ID length";
    }

    private static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void main(String[] args) {
        WebDriver driver = initializeDriver();

        // Bước 1: Điều hướng đến trang web QA Railway
        driver.get("http://railwayb1.somee.com/Page/HomePage.cshtml");

        // Bước 2: Nhấn vào tab Register
        WebElement registerTab = driver.findElement(By.xpath("//a[@href='/Account/Register.cshtml']"));
        registerTab.click();

        // Cuộn xuống dưới cùng để đảm bảo form nằm trong tầm nhìn
        scrollToBottom(driver);

        // Bước 3: Nhập email hợp lệ và để trống các trường Password, PID
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.sendKeys(RegisterData.VALID_EMAIL);

        // Bước 4: Nhấn nút Register
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        registerButton.click();

        // Bước 5: Kiểm tra thông báo lỗi phía trên form
        WebElement formErrorMessage = driver.findElement(By.xpath("//p[contains(@class, 'message') and contains(@class, 'error')]"));
        String actualFormError = formErrorMessage.getText().trim();

        // Bước 6: Kiểm tra thông báo lỗi bên cạnh trường Password
        WebElement passwordErrorMessage = driver.findElement(By.xpath("//label[@for='password' and contains(@class, 'validation-error')]"));
        String actualPasswordError = passwordErrorMessage.getText().trim();

        // Bước 7: Kiểm tra thông báo lỗi bên cạnh trường PID
        WebElement pidErrorMessage = driver.findElement(By.xpath("//label[@for='pid' and contains(@class, 'validation-error')]"));
        String actualPidError = pidErrorMessage.getText().trim();

        // So sánh các thông báo lỗi với kết quả mong đợi
        boolean formErrorMatches = actualFormError.equals(RegisterData.EXPECTED_FORM_ERROR);
        boolean passwordErrorMatches = actualPasswordError.equals(RegisterData.EXPECTED_PASSWORD_ERROR);
        boolean pidErrorMatches = actualPidError.equals(RegisterData.EXPECTED_PID_ERROR);

        if (formErrorMatches && passwordErrorMatches && pidErrorMatches) {
            System.out.println("TC11 Passed");
        } else {
            System.out.println("TC11 Failed");
        }
    }
}