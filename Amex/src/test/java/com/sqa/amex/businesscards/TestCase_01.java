package com.sqa.amex.businesscards;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestCase_01 extends TestCase {
	public TestCase_01(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(TestCase_01.class);
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
		// WebDriverWait wait = new WebDriverWait(driver,5);
		// wait.until(ExpectedConditions.visibilityOf(olay));

		//locators
		WebElement olay = driver.findElement(By.xpath("//form[contains(@id,'bx-form-')]"));
		WebElement cross = driver.findElement(By.xpath("//a[contains(@id,'bx-close-inside-')]"));
		WebElement allCard = driver.findElement(By.xpath("//h2[text()='All Cards']"));
		WebElement totalAllCards = driver.findElement(By.xpath("//*[@id='business-credit-cards-v3']/div[1]/div[3]/h2"));

		try {
			allCard.click();// clicking to see the overlay existing or not
			System.out.println("Overlay is notDisplayed");
			String actTotalCards = totalAllCards.getText();
			String actTotalCard = actTotalCards.replace("(", "");
			String actTotalCardReplaced = actTotalCard.replace(")", "");
			System.out.println("Total number of All Cards: " + extractNum(actTotalCardReplaced));
			System.out.println("actual Total All Cards: " + actTotalCards.substring(11, 13));
			Assert.assertEquals("14", actTotalCards.substring(11, 13));
		} catch (Exception e) {
			if (olay.isDisplayed()) {
				System.out.println("Overlay isDisplayed");
				cross.click();// closing the overlay to click each of the link
				//again try to click the expected card
				String actTotalCards = totalAllCards.getText();
				String actTotalCard = actTotalCards.replace("(", "");
				String actTotalCardReplaced = actTotalCard.replace(")", "");
				System.out.println("total number of cards: " + extractNum(actTotalCardReplaced));
				Assert.assertEquals("14", actTotalCards.substring(11, 13));
			}
			e.printStackTrace();
		}
		
		List<WebElement> otherCards = driver.findElements(By.xpath("//li[@class='item-unit']/h2"));
		for (WebElement card : otherCards) {
			String cardName = card.getText();
			try {
				assertTrue( card.isDisplayed());
				card.click();// clicking to see the overlay existing or not
				System.out.println("Overlay is notDisplayed");
				String actTotalCards = totalAllCards.getText();
				System.out.println("Total number of cards available for "+cardName+" : "+extractNum(actTotalCards));
			} catch (Exception e) {
				if (olay.isDisplayed()) {
					e.printStackTrace();
					System.out.println("Overlay isDisplayed");
					cross.click();// closing the overlay to click each of the link
					//again try to click the expected card
					card.click();// clicking to see the overlay existing or not
					String actTotalCards = totalAllCards.getText();
					System.out.println("Total number of cards available for "+cardName+" : "+extractNum(actTotalCards));
				}
			}
		}
		driver.quit();
	}

	public static ArrayList<Integer> extractNum(String expStr) {
		if (expStr.contains("(") || expStr.contains(")")) {
			expStr = expStr.replaceAll("[(),]","");
		}    
		ArrayList<Integer> num = new ArrayList<Integer>();
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(expStr);
		while (m.find()) {
			num.add(Integer.parseInt(m.group()));
		}
		return num;
	}


	
}
