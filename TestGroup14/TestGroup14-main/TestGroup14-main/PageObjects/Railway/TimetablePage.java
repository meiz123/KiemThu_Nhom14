package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TimetablePage {
    public void clickBookTicket(String departStation, String arriveStation, String time) {
        WebElement timetableTab = Constant.WEBDRIVER.findElement(By.linkText("Timetable"));
        timetableTab.click();

        WebElement bookTicketLink = Constant.WEBDRIVER.findElement(By.xpath(
                "//tr[td[text()='" + departStation + "'] and td[text()='" + arriveStation + "'] and td[contains(text(),'" + time + "')]]/td/a[text()='book ticket']"
        ));
        ((org.openqa.selenium.JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", bookTicketLink);
        bookTicketLink.click();
    }
}