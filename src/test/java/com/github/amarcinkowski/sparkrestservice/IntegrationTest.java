package com.github.amarcinkowski.sparkrestservice;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IntegrationTest {

//	@Test
	public void testSearchReturnsResults() {
		DesiredCapabilities capabilities = DesiredCapabilities.phantomjs(); // chrome
		// System.setProperty("webdriver.chrome.driver",
		// "/usr/local/share/chromedriver");
		// ChromeDriver driver = new ChromeDriver();
		PhantomJSDriver driver = new PhantomJSDriver(capabilities);

		driver.get("http://localhost:4567/#/create");
		driver.manage().window().setSize(new Dimension(1024, 768));
		File f = driver.getScreenshotAs(OutputType.FILE);
		try {
			org.apache.commons.io.FileUtils.copyFile(f, new File("/tmp/screenshot.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.className("navbar-brand")).click();

		String nameTerm = "Company Ltd.";
		driver.findElement(By.id("name")).sendKeys(nameTerm);

		driver.findElement(By.cssSelector("button[type='submit']")).click();

		List<WebElement> results = driver.findElementsByCssSelector("label[for='title']");
		for (WebElement s : results.toArray(new WebElement[] {})) {
			System.out.print(s.getText());// + s.getAttribute("href"));
		}
		Assert.assertTrue(results.size() > 0);

	}

}
