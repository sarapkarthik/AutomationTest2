package tests;

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

public class DropDownsArePopulatedWellOnUserSelections {

    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private WebDriver driver;
    private static final String BASE_URL = "https://mts.mobolt.com";

    private static final String CITY_DROPDOWN = "dependent_attribute_3";
    private static final String STATE_DROPDOWN = "dependent_attribute_2";
    private static final String COUNTRY_DROPDOWN = "dependent_attribute_1";

    private static final String COUNTRY_US = "US";
    private static final String STATE_NC = "NC";
    private static final String CITY_CARY = "Cary";

    private static final List<String> EXPECTED_STATE_LIST_IN_US = Lists
	    .newArrayList("All", "NC", "NJ", "State", "MN");
    private static final List<String> EXPECTED_CITY_LIST_IN_NC = Lists
	    .newArrayList("All", "City", "Cary");

    @Before
    public void start() {
	LOGGER.info("Test started! Waiting to initilize webdriver...");
	driver = new FirefoxDriver();
	Utils.wait(driver, 30);

	LOGGER.info("Attempting to open '" + BASE_URL + "'");
	Utils.openUrl(driver, BASE_URL);
    }

    @Test
    public void ensureDropDownsPopulatedWithRightValues() {
	clickSearchJobsButton();

	selectOption(COUNTRY_DROPDOWN, COUNTRY_US);

	assertStateListIsPopulatedCorrectly(driver);

	selectOption(STATE_DROPDOWN, STATE_NC);

	Utils.wait(driver, 5);

	assertCityListIsPopulatedCorrectly(driver);

	LOGGER.info("Test passed!");
    }

    private void clickSearchJobsButton() {
	driver.findElement(By.cssSelector("span.ui-btn-text")).click();
    }

    public void selectOption(String selectBoxId, String selectedOption) {

	Select select = new Select(driver.findElement(By.id(selectBoxId)));
	select.selectByVisibleText(selectedOption);

	LOGGER.info("Selected the option " + selectedOption + "'");
	logOptionsDetails(selectBoxId);

    }

    private void assertStateListIsPopulatedCorrectly(WebDriver driver) {

	driver.findElement(By.id(STATE_DROPDOWN)).click();

	assertOptionsMatch(STATE_DROPDOWN, EXPECTED_STATE_LIST_IN_US,
		"State options mismatch!");

    }

    private void assertCityListIsPopulatedCorrectly(WebDriver driver) {

	driver.findElement(By.id(CITY_DROPDOWN)).click();

	assertOptionsMatch(CITY_DROPDOWN, EXPECTED_CITY_LIST_IN_NC,
		"City options mismatch!");

	Select countryList = new Select(
		driver.findElement(By.id(CITY_DROPDOWN)));

	countryList.selectByVisibleText(CITY_CARY);

	WebElement selectedElement = countryList.getFirstSelectedOption();
	String selectedText = selectedElement.getText();
	LOGGER.info("Selected option is :" + selectedText);
    }

    private void assertOptionsMatch(String dropDownLocatorId,
	    List<String> expectedOptions, String errorMessage) {
	List<String> obtainedOptions = Utils.getOptionsInSelectBox(driver,
		dropDownLocatorId);

	System.out.println("\n Options undere the drop down box are :"
		+ obtainedOptions);

	errorMessage += "\nExpected: " + Utils.toString(expectedOptions)
		+ "\nObtained: " + Utils.toString(obtainedOptions);
	Assert.assertTrue(errorMessage,
		Utils.areListsEqual(obtainedOptions, expectedOptions));
    }

    private void logOptionsDetails(String selectedId) {
	WebElement selectBoxId = driver.findElement(By.id(selectedId));
	Select selectBox = new Select(selectBoxId);
	LOGGER.log(Level.INFO, "Number of options in the dropdown: "
		+ selectBox.getOptions().size());

    }

    @After
    public void tearDown() {
	driver.close();
	LOGGER.log(Level.INFO, "Driver closed!");
    }

}
