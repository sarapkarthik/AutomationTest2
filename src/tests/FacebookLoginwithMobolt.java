package tests;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FacebookLoginwithMobolt {

	private WebDriver driver;
	private String baseUrl;

	@Before
	public void start() {
		driver = new FirefoxDriver();
		baseUrl = "https://mts.mobolt.com/";
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
}

public void selectElementByCss(String str) {
	driver.findElement(By.cssSelector(str)).click();
}

@Test
public void actionPerformed() {
	driver.get(baseUrl + "/");

	selectElementByCss("span.ui-btn-inner");

	selectElementByCss("button.ui-btn-hidden");

	selectElementByCss("a.ui-link-inherit");

	driver.findElement(By.cssSelector("img[alt=\"Facebook-32\"]")).click();

	driver.findElement(By.id("email")).sendKeys("sarapkarthik@yahoo.com");
	driver.findElement(By.id("pass")).sendKeys("p@ssword!");
	driver.findElement(By.id("u_0_1")).click();
}

@After
public void end() {
	// driver.close();
	}
}
