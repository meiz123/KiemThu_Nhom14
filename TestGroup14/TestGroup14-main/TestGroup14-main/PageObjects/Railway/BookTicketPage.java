package Railway;

import Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class BookTicketPage {
    // Locators
    private final By dateDropdown = By.name("Date");
    private final By departDropdown = By.name("DepartStation");
    private final By arriveDropdown = By.name("ArriveStation");
    private final By seatTypeDropdown = By.name("SeatType");
    private final By ticketAmountDropdown = By.name("TicketAmount");
    private final By bookButton = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private final By successMessage = By.xpath("//h1");

    // Elements
    public WebElement getDateDropdown() {
        return Constant.WEBDRIVER.findElement(dateDropdown);
    }

    public WebElement getDepartDropdown() {
        return Constant.WEBDRIVER.findElement(departDropdown);
    }

    public WebElement getArriveDropdown() {
        return Constant.WEBDRIVER.findElement(arriveDropdown);
    }

    public WebElement getSeatTypeDropdown() {
        return Constant.WEBDRIVER.findElement(seatTypeDropdown);
    }

    public WebElement getTicketAmountDropdown() {
        return Constant.WEBDRIVER.findElement(ticketAmountDropdown);
    }

    public WebElement getBookButton() {
        return Constant.WEBDRIVER.findElement(bookButton);
    }

    public WebElement getSuccessMessageElement() {
        return Constant.WEBDRIVER.findElement(successMessage);
    }

    // Methods
    public void bookTicket(String date, String departStation, String arriveStation, String seatType, String ticketAmount) {
        Select dateSelect = new Select(getDateDropdown());
        dateSelect.selectByVisibleText(date);

        Select departSelect = new Select(getDepartDropdown());
        departSelect.selectByVisibleText(departStation);

        Select arriveSelect = new Select(getArriveDropdown());
        arriveSelect.selectByVisibleText(arriveStation);

        Select seatTypeSelect = new Select(getSeatTypeDropdown());
        seatTypeSelect.selectByVisibleText(seatType);

        Select ticketAmountSelect = new Select(getTicketAmountDropdown());
        ticketAmountSelect.selectByVisibleText(ticketAmount);

        getBookButton().click();
    }

    public String getSuccessMessage() {
        return getSuccessMessageElement().getText();
    }

    public String getDepartFrom() {
        Select departSelect = new Select(getDepartDropdown());
        return departSelect.getFirstSelectedOption().getText();
    }

    public String getArriveAt() {
        Select arriveSelect = new Select(getArriveDropdown());
        return arriveSelect.getFirstSelectedOption().getText();
    }
}