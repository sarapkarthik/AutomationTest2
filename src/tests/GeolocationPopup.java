package tests;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GeolocationPopup {
    WebDriver driver;

    @Before
    public void start() {
	driver = new FirefoxDriver();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.get("https://mts.mobolt.com/");

    }

    @Test
    public void test() {

	driver.findElement(By.cssSelector("span.ui-btn-inner")).click();

	String msg = driver.switchTo().alert().getText();

	// wb.switchTo().alert().accept();
	System.out.println(msg);
    }

    @After
    public void end() {
	driver.close();
    }
}
