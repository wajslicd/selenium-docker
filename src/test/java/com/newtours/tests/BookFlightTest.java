package com.newtours.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.newtours.pages.FindFlightPage;
import com.newtours.pages.FlightConfirmationPage;
import com.newtours.pages.FlightDetailsPage;
import com.newtours.pages.RegistrationConfirmationPage;
import com.newtours.pages.RegistrationPage;
import com.tests.BaseTest;

public class BookFlightTest extends BaseTest{
	private String noOfPassengers;
	private String expectedPrice;
	
	@BeforeTest
	@Parameters({"noOfPassengers", "expectedPrice"})
	public void setupParameters(String noOfPassengers, String expectedPrice) {
		this.noOfPassengers = noOfPassengers;
		this.expectedPrice = expectedPrice;
	}
	
	@Test
	public void registrationPageTest() throws InterruptedException {
		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.goTo();
		registrationPage.enterUserDetails("selenium", "docker");
		registrationPage.enterUserCredentials("selenium", "docker");
		
		//Thread.sleep(20000);
		registrationPage.submit();
	}
	
	@Test(dependsOnMethods = "registrationPageTest") 
	public void registrationConfirmationPageTest() {
		RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
		registrationConfirmationPage.goToFlightDetailsPage();
	}
	
	@Test(dependsOnMethods = "registrationConfirmationPageTest")
	public void flightDetailsPageTest() {
		FlightDetailsPage flightDetailsPage = new FlightDetailsPage(driver);
		flightDetailsPage.selectPassengers(noOfPassengers);
		flightDetailsPage.goToFindFlightsPage();
	}
	
	@Test(dependsOnMethods = "flightDetailsPageTest")
	public void findFlightPageTest() {
		FindFlightPage findFlightPage = new FindFlightPage(driver);
		findFlightPage.submitFindFlightPage();
		findFlightPage.goToFlightConfirmationPage();
	}
	
	@Test(dependsOnMethods = "findFlightPageTest")
	public void flightConfirmationPageTest() {
		FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
		String actualPrice = flightConfirmationPage.getPrice();
		Assert.assertEquals(actualPrice, expectedPrice);
	}
}

