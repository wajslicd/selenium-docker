package com.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	protected WebDriver driver;
	
	@BeforeTest
	public void setupDriver(ITestContext ctx) throws MalformedURLException {
		
		// BROWSER => chrome/firefox
		// HUB_HOST => localhost/ip address/hostname
		
		// String host = "localhost";
		String host = "192.168.99.100";   // required for windows instead of localhost
		DesiredCapabilities dc;
		
		if(System.getProperty("BROWSER") != null && System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
			dc = DesiredCapabilities.firefox();
		} else {
			dc = DesiredCapabilities.chrome();
		}
		
		if(System.getProperty("HUB_HOST") != null) {
			host = System.getProperty("HUB_HOST");
		}
		
		String testName = ctx.getCurrentXmlTest().getName();
		
		String completeUrl = "http://" + host + ":4444/wd/hub";
		dc.setCapability("name", testName);
		this.driver = new RemoteWebDriver(new URL(completeUrl), dc);
	}
	
	@AfterTest
	public void quitBrowser() {
		this.driver.quit();
	}
}
