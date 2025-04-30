package Railway;

import Constant.Constant;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class BookTicketTest {
    public BookTicketTest() {
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        WebDriverManager.chromedriver().setup();
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }
   @Test
    public void TCO4()
    {
        HomePage homePage=new HomePage();
        homePage.open();
        homePage.gotoBookTicketPage();
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        assertTrue(currentUrl.contains("/Account/Login.cshtml"));;
    }
    @Test
    public void TC14() {
        System.out.println("TC14 - User can book a ticket successfully after logging in");
        HomePage homePage = new HomePage();
        homePage.open();

        // Login with valid credentials
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME,Constant.PASSWORD);

        // Navigate to Book Ticket page
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicket(
                Constant.DEPART_DATE,
                Constant.DEPART_STATION,
                Constant.ARRIVE_STATION,
                Constant.SEAT_TYPE,
                Constant.TICKET_AMOUNT
        );

        // Check success message
        String actualSuccessMessage = bookTicketPage.getSuccessMessage();
        String expectedSuccessMessage = "Ticket Booked Successfully!";
        if (!actualSuccessMessage.equals(expectedSuccessMessage)) {
            System.out.println("Error message is not displayed as expected");
        }
    }

    @Test(description = "TC015: User can book ticket from Timetable")
    public void TC015() {
        System.out.println("TC015 - User can book ticket from Timetable");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 1: Login
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 1.1: Wait for "Timetable" tab to appear after successful login
        GeneralPage generalPage = new GeneralPage();
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(generalPage.getTimetableTab()));

        // Step 2: Go to Timetable and select book ticket
        TimetablePage timetablePage = new TimetablePage();
        timetablePage.clickBookTicket("Huế", "Sài Gòn", "7:30");

        // Step 3: Verify selected values
        BookTicketPage bookTicketPage = new BookTicketPage();
        String selectedDepart = bookTicketPage.getDepartFrom();
        String selectedArrive = bookTicketPage.getArriveAt();

        boolean match = selectedDepart.equals("Huế") && selectedArrive.equals("Sài Gòn");
        assertTrue(match, "Selected departure or arrival station does not match expected values.");
    }

    @Test(description = "TC16: User can cancel a ticket")
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket successfully");
        HomePage homePage = new HomePage();
        homePage.open();

        // Login with valid credentials
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Book a ticket
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicket(
                Constant.DEPART_DATE,
                Constant.DEPART_STATION,
                Constant.ARRIVE_STATION,
                Constant.SEAT_TYPE,
                Constant.TICKET_AMOUNT
        );

        // Navigate to My Ticket page
        GeneralPage generalPage = new GeneralPage();
        generalPage.navigateToMyTicketPage();
        MyTicketPage myTicketPage = new MyTicketPage();

        // Get tickets before cancel
        List<MyTicketPage.TicketInfo> ticketsBeforeCancel = myTicketPage.getAllTickets();
        int ticketCountBeforeCancel = ticketsBeforeCancel.size();
        assertTrue(ticketCountBeforeCancel > 0, "No ticket found to cancel.");

        // Find the ticket to cancel
        MyTicketPage.TicketInfo ticketToCancel = null;
        for (MyTicketPage.TicketInfo ticket : ticketsBeforeCancel) {
            if (ticket.getDepartStation().equals(Constant.DEPART_STATION) &&
                    ticket.getArriveStation().equals(Constant.ARRIVE_STATION) &&
                    ticket.getSeatType().equals(Constant.SEAT_TYPE) &&
                    ticket.getDepartDate().equals(Constant.DEPART_DATE)) {
                ticketToCancel = ticket;
                break;
            }
        }
        Assert.assertNotNull(ticketToCancel, "Could not find the ticket to cancel.");


        myTicketPage.cancelTicket(ticketToCancel);

        List<MyTicketPage.TicketInfo> ticketsAfterCancel = myTicketPage.getAllTickets();
        int ticketCountAfterCancel = ticketsAfterCancel.size();

        // Verify the ticket was canceled
        Assert.assertEquals(ticketCountAfterCancel, ticketCountBeforeCancel - 1, "Ticket was not canceled successfully.");
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
