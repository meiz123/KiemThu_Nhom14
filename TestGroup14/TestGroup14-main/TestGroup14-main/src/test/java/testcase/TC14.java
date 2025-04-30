package testcase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class TC14 {

    // Tách dữ liệu đăng nhập
    static class LoginData {
        public static final String VALID_USERNAME = "example@gmail.com"; // Giữ cố định theo yêu cầu
        public static final String VALID_PASSWORD = "123456788"; // Giả định mật khẩu hợp lệ
        public static final String EXPECTED_SUCCESS_MESSAGE = "Ticket Booked Successfully!"; // Cập nhật để khớp với thực tế
        public static final String SELECTED_DEPART_DATE = "4/26/2025"; // Ngày hợp lệ từ danh sách
    }

    // Tách phần khởi tạo trình duyệt
    private static WebDriver initializeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    // Tách phần đóng trình duyệt
    private static void closeDriver(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }

    // Tách phần cuộn trang
    private static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Luồng chính của test case
    public static void main(String[] args) {
        WebDriver driver = initializeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
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

            // Bước 3: Nhấn vào tab "Book ticket"
            WebElement bookTicketTab = driver.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"));
            bookTicketTab.click();

            // Cuộn xuống dưới cùng để đảm bảo form đặt vé nằm trong tầm nhìn
            scrollToBottom(driver);

            // Bước 4: Chọn "Depart date" từ danh sách (chọn ngày "4/26/2025")
            WebElement departDate = driver.findElement(By.xpath("//select[@name='Date']"));
            Select departDateSelect = new Select(departDate);
            departDateSelect.selectByVisibleText(LoginData.SELECTED_DEPART_DATE);

            // Bước 5: Chọn "Sài Gòn" cho "Depart from" và "Nha Trang" cho "Arrive at"
            WebElement departFrom = driver.findElement(By.name("DepartStation"));
            Select departFromSelect = new Select(departFrom);
            departFromSelect.selectByVisibleText("Sài Gòn");

            WebElement arriveAt = driver.findElement(By.name("ArriveStation"));
            Select arriveAtSelect = new Select(arriveAt);
            // Kiểm tra danh sách giá trị của ArriveStation
            List<WebElement> arriveAtOptions = arriveAtSelect.getOptions();
            System.out.println("Arrive Station options:");
            for (WebElement option : arriveAtOptions) {
                System.out.println(option.getText());
            }
            arriveAtSelect.selectByVisibleText("Nha Trang");

            // Bước 6: Chọn "Soft bed" cho "Seat type"
            WebElement seatType = driver.findElement(By.name("SeatType"));
            Select seatTypeSelect = new Select(seatType);
            // Kiểm tra danh sách giá trị của SeatType
            List<WebElement> seatTypeOptions = seatTypeSelect.getOptions();
            System.out.println("Seat Type options:");
            for (WebElement option : seatTypeOptions) {
                System.out.println(option.getText());
            }
            seatTypeSelect.selectByVisibleText("Soft bed");

            // Bước 7: Chọn "1" cho "Ticket amount"
            WebElement ticketAmount = driver.findElement(By.name("TicketAmount"));
            Select ticketAmountSelect = new Select(ticketAmount);
            ticketAmountSelect.selectByVisibleText("1");

            // Bước 8: Nhấn nút "Book ticket"
            WebElement bookTicketButton = driver.findElement(By.xpath("//input[@value='Book ticket']"));
            bookTicketButton.click();

            // Kiểm tra thông báo đặt vé thành công
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[@align='center' and contains(text(), 'Ticket Booked Successfully')]")));
            String actualSuccessMessage = successMessage.getText().trim();
            System.out.println("Actual success message: " + actualSuccessMessage);

            // Kiểm tra thông tin vé hiển thị trong bảng
            WebElement departStationValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tr[2]/td[1]"))); // Depart Station
            WebElement arriveStationValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tr[2]/td[2]"))); // Arrive Station
            WebElement seatTypeValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tr[2]/td[3]"))); // Seat Type
            WebElement departDateValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tr[2]/td[4]"))); // Depart Date
            WebElement ticketAmountValue = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table//tr[2]/td[7]"))); // Amount

            String actualDepartStation = departStationValue.getText().trim();
            String actualArriveStation = arriveStationValue.getText().trim();
            String actualSeatType = seatTypeValue.getText().trim();
            String actualDepartDate = departDateValue.getText().trim();
            String actualTicketAmount = ticketAmountValue.getText().trim();

            // So sánh kết quả mong đợi
            boolean successMessageMatches = actualSuccessMessage.equals(LoginData.EXPECTED_SUCCESS_MESSAGE);
            boolean departDateMatches = actualDepartDate.equals(LoginData.SELECTED_DEPART_DATE);
            boolean departStationMatches = actualDepartStation.equals("Sài Gòn");
            boolean arriveStationMatches = actualArriveStation.equals("Nha Trang");
            boolean seatTypeMatches = actualSeatType.equals("Soft bed");
            boolean ticketAmountMatches = actualTicketAmount.equals("1");

            if (successMessageMatches && departDateMatches && departStationMatches && arriveStationMatches && seatTypeMatches && ticketAmountMatches) {
                System.out.println("TC14 Passed: Đặt vé thành công và thông tin vé hiển thị chính xác.");
            } else {
                System.out.println("TC14 Failed: " +
                        "Success message matches: " + successMessageMatches + " (Thực tế: " + actualSuccessMessage + "), " +
                        "Depart date matches: " + departDateMatches + " (Thực tế: " + actualDepartDate + "), " +
                        "Depart station matches: " + departStationMatches + " (Thực tế: " + actualDepartStation + "), " +
                        "Arrive station matches: " + arriveStationMatches + " (Thực tế: " + actualArriveStation + "), " +
                        "Seat type matches: " + seatTypeMatches + " (Thực tế: " + actualSeatType + "), " +
                        "Ticket amount matches: " + ticketAmountMatches + " (Thực tế: " + actualTicketAmount + ")");
            }
        } catch (Exception e) {
            System.out.println("TC14 Failed with exception: " + e.getMessage());
        } finally {
            closeDriver(driver);
        }
    }
}