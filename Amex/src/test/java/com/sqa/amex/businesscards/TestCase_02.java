package com.sqa.amex.businesscards;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestCase_02 extends TestCase {
	public TestCase_02(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(TestCase_02.class);
	}

	public void testApp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\DevTools\\chromedriver.exe");	
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().deleteAllCookies();
		driver.navigate().to("https://www.americanexpress.com/us/credit-cards/business/business-credit-cards");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);

		//locators
		WebElement olay = driver.findElement(By.xpath("//form[contains(@id,'bx-form-')]"));
		WebElement cross = driver.findElement(By.xpath("//a[contains(@id,'bx-close-inside-')]"));
		WebElement busiPlatinumCard = driver.findElement(By.xpath("//img[@alt='Business Platinum Card']"));
		
		try {
			busiPlatinumCard.click();
			String url = driver.getCurrentUrl();
			boolean platCreditCard = url.contains("american-express-business-platinum-credit-card-amex");
			System.out.println(" "+platCreditCard);
			Assert.assertTrue(platCreditCard);
		} catch (Exception e) {
			if (olay.isDisplayed()) {
				System.out.println("Overlay isDisplayed");
				cross.click();
				busiPlatinumCard.click();
				String url = driver.getCurrentUrl();
				boolean platCreditCard = url.contains("american-express-business-platinum-credit-card-amex");
				System.out.println(" "+platCreditCard);
				Assert.assertTrue(platCreditCard);
			}
			e.printStackTrace();
		}
        driver.quit();
	}
}
	       

