package tests;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import utils.UserCredentials;
import utils.Utils;

public class GmailLoginFunctionalityTests {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private WebDriver driver;
    private static final String BASE_URL = "https://www.gmail.com/";

    @Before
    public void setUp() {
	LOGGER.info("Test started! Waiting to initilize webdriver...");
	driver = new FirefoxDriver();
	Utils.wait(driver, 30);

	LOGGER.info("Attempting to open '" + BASE_URL + "'");
	Utils.openUrl(driver, BASE_URL);
	Utils.wait(driver, 20);

    }

    @After
    public void tearDown() {
	clickSignOutButton();
	driver.close();
	LOGGER.log(Level.INFO, "Closed the driver. End of the test!");
    }

    @Test
    public void testLoginWithCorrectCredentials() {
	UserCredentials userCredentials = getUserCredsFromPropsFile("C:\\Automation\\data.properties");
	fillUsernameAndPassword(driver, userCredentials);

	clickSignInButton();
	verifyLoginSuccessful(driver, userCredentials.getUsername());
    }

    private void clickSignOutButton() {
	driver.findElement(By.id("gb_71")).click();
	LOGGER.log(Level.INFO, "Successfully Logged out from the Gmail!");
    }

    private void clickSignInButton() {
	Utils.clickElementByid(driver, "signIn");
	LOGGER.log(Level.INFO, "Successfully Logged into Gmail!");
    }

    private static void fillUsernameAndPassword(WebDriver wb, UserCredentials uc) {
	wb.findElement(By.name("Email")).sendKeys(uc.getUsername());
	wb.findElement(By.name("Passwd")).sendKeys(uc.getPassword());
    }

    private static UserCredentials getUserCredsFromPropsFile(String filePath) {
	Properties p = new Properties();

	try {
	    FileInputStream fis = new FileInputStream(filePath);
	    p.load(fis);
	} catch (Exception e) {
	    LOGGER.log(Level.SEVERE,
		    "Unable to open properties file to fetch credentials", e);
	}

	UserCredentials uc = new UserCredentials();
	uc.setUsername(p.getProperty("username"));
	uc.setPassword(p.getProperty("password"));

	return uc;
    }

    private static void verifyLoginSuccessful(WebDriver driver,
	    String emailAddress) {
	Utils.clickElementByid(driver, "gbg4");
	verifyEmailAddressVisible(driver, emailAddress);
	verifySignOutTextPresent(driver);
    }

    private static void verifySignOutTextPresent(WebDriver driver) {
	String obtainedText = driver.findElement(By.id("gb_71")).getText();
	LOGGER.log(Level.INFO, "Text read from DOM: " + obtainedText);
	assertEquals("Sign out", obtainedText);
    }

    private static void verifyEmailAddressVisible(WebDriver driver,
	    String expectedEmailAddress) {
	String emailAddressOnPage = driver.findElement(By.className("gbps2"))
		.getText();
	assertEquals(expectedEmailAddress, emailAddressOnPage);
    }
}
