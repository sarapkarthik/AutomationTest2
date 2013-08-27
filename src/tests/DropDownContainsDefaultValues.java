package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import utils.Utils;

import com.google.common.collect.Lists;

public class DropDownContainsDefaultValues {
    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private WebDriver driver;
    private static final String BASE_URL = "https://mts.mobolt.com";
    private static final String DEFAULT_TEXT = "All";

    private String cityDropdown = "dependent_attribute_3";
    private String stateDropDown = "dependent_attribute_2";
    private String countryDropDown = "dependent_attribute_1";

    private static final List<String> DEFAULT_COUNTRY_OPTIONS = Lists
	    .newArrayList("Country", "All", "US");
    private static final List<String> DEFAULT_STATE_OPTIONS = Lists
	    .newArrayList("State", "All");
    private static final List<String> DEFAULT_CITY_OPTIONS = Lists
	    .newArrayList("City", "All");

    @Before
    public void setUp() {
	LOGGER.info("Test started! Waiting to initilize webdriver...");
	driver = new FirefoxDriver();
	Utils.wait(driver, 30);

	LOGGER.info("Attempting to open '" + BASE_URL + "'");
	Utils.openUrl(driver, BASE_URL);
	selectSearchJobs(driver);
    }

    @Test
    public void verifyDefaultValuesofCountryStateCity() {
	verifyDropDownValues(driver, countryDropDown, 3,
		DEFAULT_COUNTRY_OPTIONS);
	verifyDropDownValues(driver, stateDropDown, 2, DEFAULT_STATE_OPTIONS);
	verifyDropDownValues(driver, cityDropdown, 2, DEFAULT_CITY_OPTIONS);
	LOGGER.info("Test ended");
    }

    public static void verifyDropDownValues(WebDriver driver,
	    String dropDownLocatorId, int numOptions,
	    List<String> expectedOptions) {

	Select selectBox = new Select(driver.findElement(By
		.id(dropDownLocatorId)));
	LOGGER.log(Level.INFO,
		"Number of Options present in the drop down are: "
			+ selectBox.getOptions().size());
	assertEquals(numOptions, selectBox.getOptions().size());

	List<String> existingOptions = Utils.getOptionsInSelectBox(driver,
		dropDownLocatorId);
	String str = driver.findElement(By.id(dropDownLocatorId)).getText();
	LOGGER.log(Level.INFO, "List of options:" + str);
	Assert.assertTrue(
		"Obtained and expected drop down options do not match",
		Utils.areListsEqual(existingOptions, expectedOptions));

	assertEquals("All", selectBox.getFirstSelectedOption().getText());
    }

    public static void verifydefaultDisplayText(WebDriver driver, String id_val) {
	String obtainedText = getDefaultDisplayedText(driver, id_val);
	LOGGER.log(Level.INFO, "DefaultText Displayed is: " + obtainedText);
	assertEquals(DEFAULT_TEXT, obtainedText);

    }

    public static String getDefaultDisplayedText(WebDriver driver, String id_val) {
	Select select = new Select(driver.findElement(By.id(id_val)));
	WebElement w = select.getFirstSelectedOption();
	String obtainedText = w.getText();
	return obtainedText;
    }

    public static void selectSearchJobs(WebDriver driver) {
	driver.findElement(By.cssSelector("span.ui-btn-text")).click();
    }

    @After
    public void tearDown() {
	driver.close();
	LOGGER.log(Level.INFO, "Closed the driver. End of the test!");
    }

}
