package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MyTicketPage {
    // Locators
    private final By tblTickets = By.xpath("//table[@class='MyTable']//tr[contains(@class, 'OddRow') or contains(@class, 'EvenRow')]");
    private final By btnCancel = By.xpath("//input[@type='button' and @value='Cancel']");

    // Wait
    private WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));

    // Elements
    private List<WebElement> getTicketRows() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tblTickets));
    }

    private WebElement getBtnCancel(WebElement ticketRow) {
        return ticketRow.findElement(btnCancel);
    }

    // Methods
    public List<TicketInfo> getAllTickets() {
        List<WebElement> ticketRows = getTicketRows();
        List<TicketInfo> tickets = new ArrayList<>();

        for (WebElement row : ticketRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String departStation = cells.get(1).getText();
            String arriveStation = cells.get(2).getText();
            String seatType = cells.get(3).getText();
            String departDate = cells.get(4).getText();
            String bookDate = cells.get(5).getText();

            TicketInfo ticket = new TicketInfo(departStation, arriveStation, seatType, departDate, bookDate);
            tickets.add(ticket);
        }
        return tickets;
    }

    public WebElement findTicketRow(TicketInfo ticketInfo) {
        List<WebElement> ticketRows = getTicketRows();
        for (WebElement row : ticketRows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String departStation = cells.get(1).getText();
            String arriveStation = cells.get(2).getText();
            String seatType = cells.get(3).getText();
            String departDate = cells.get(4).getText();

            if (departStation.equals(ticketInfo.getDepartStation()) &&
                    arriveStation.equals(ticketInfo.getArriveStation()) &&
                    seatType.equals(ticketInfo.getSeatType()) &&
                    departDate.equals(ticketInfo.getDepartDate())) {
                return row;
            }
        }
        throw new RuntimeException("Ticket not found: " + ticketInfo.getDepartStation() + " to " +
                ticketInfo.getArriveStation() + ", " + ticketInfo.getSeatType() + ", " +
                ticketInfo.getDepartDate());
    }

    public void cancelTicket(TicketInfo ticketInfo) {
        WebElement ticketRow = findTicketRow(ticketInfo);
        WebElement cancelButton = getBtnCancel(ticketRow);

        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", cancelButton);

        wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelButton.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Constant.WEBDRIVER.switchTo().alert().accept();

        wait.until(ExpectedConditions.stalenessOf(ticketRow));
    }

    public static class TicketInfo {
        private String departStation;
        private String arriveStation;
        private String seatType;
        private String departDate;

        public TicketInfo(String departStation, String arriveStation, String seatType, String departDate, String bookDate) {
            this.departStation = departStation;
            this.arriveStation = arriveStation;
            this.seatType = seatType;
            this.departDate = departDate;
        }

        public String getDepartStation() { return departStation; }
        public String getArriveStation() { return arriveStation; }
        public String getSeatType() { return seatType; }
        public String getDepartDate() { return departDate; }
    }
}