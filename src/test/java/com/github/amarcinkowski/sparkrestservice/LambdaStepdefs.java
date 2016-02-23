package com.github.amarcinkowski.sparkrestservice;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import cucumber.api.java8.En;

public class LambdaStepdefs implements En {

	private final static DesiredCapabilities capabilities = DesiredCapabilities.phantomjs(); // chrome
	private final static PhantomJSDriver driver = new PhantomJSDriver(capabilities);

	public LambdaStepdefs() {
//		if (1 == 1) return;

		Given("^I navigate to \"([^\"]*)\"$", (String arg1) -> {
			driver.get(arg1);
		});

		When("^I click on element having id \"([^\"]*)\"$", (String arg1) -> {
			driver.findElementById(arg1).click();
		});

		Then("^I can see \"([^\"]*)\"$", (String arg1) -> {
			String xpath = String.format("//*[contains(test(), '%s')]", arg1);
			Assert.assertTrue(isElementPresent(driver, By.xpath(xpath)));
		});

	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true; // Success!
		} catch (NoSuchElementException ignored) {
			return false;
		}
	}
}
