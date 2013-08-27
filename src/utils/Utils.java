package utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Lists;

public class Utils {

    public static void wait(WebDriver driver, int timeToWaitInSecs) {
	driver.manage().timeouts()
		.implicitlyWait(timeToWaitInSecs, TimeUnit.SECONDS);
    }

    public static void openUrl(WebDriver driver, String url) {
	driver.get(url);
    }

    public static boolean areListsEqual(List<String> list1, List<String> list2) {
	for (String elem : list1) {
	    if (!list2.contains(elem)) {
		return false;
	    }
	}

	for (String elem : list2) {
	    if (!list1.contains(elem)) {
		return false;
	    }
	}

	return true;
    }

    public static String toString(List<String> list) {
	return Arrays.toString(list.toArray());
    }

    public void click(WebDriver driver, String locatorId) {
	driver.findElement(By.id(locatorId)).click();
    }

    public static List<String> getOptionsInSelectBox(WebDriver driver, String id) {
	Select select = new Select(driver.findElement(By.id(id)));
	List<String> existingOptions = Lists.newArrayList();
	for (WebElement elem : select.getOptions()) {
	    existingOptions.add(elem.getText());
	}

	return existingOptions;
    }

    public static void clickElementByid(WebDriver wb, String str) {
	wb.findElement(By.id(str)).click();
    }

}
