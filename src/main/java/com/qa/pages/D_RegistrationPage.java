package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.base.BasePage;
import com.qa.utils.ElementUtil;

public class D_RegistrationPage extends BasePage {
	private ElementUtil eu;
	
	// Constructor
	public D_RegistrationPage(WebDriver driver) {
		this.driver = driver;	// this.driver is referring to parent page driver i.e. BasePage's driver
		eu = new ElementUtil(driver);
	}
	
	// Page Locators
	private By firstName = By.xpath("//input[@id='input-firstname']");
	private By lastName = By.xpath("//input[@id='input-lastname']");
	private By email = By.xpath("//input[@id='input-email']");
	private By telephone = By.xpath("//input[@id='input-telephone']");
	private By password = By.xpath("//input[@id='input-password']");
	private By confirmPassword = By.xpath("//input[@id='input-confirm']");
	private By yesRB = By.xpath("//label[@class='radio-inline']/input[@value='1']");
	private By noRB = By.xpath("//label[@class='radio-inline']/input[@value='0']");
	private By tncCB = By.xpath("//input[@name='agree']");
	private By continueBtn = By.xpath("//input[@value='Continue']");
	private By successfulRegistrationTxt = By.xpath("//div[@id='content']/h1");
	private By logout = By.xpath("//div[@class='list-group']/a[contains(text(),'Logout')]");
	private By registerLink = By.xpath("//div[@class='list-group']/a[contains(text(),'Register')]");
	
	// Page Actions
	/**
	 * Example of method that supports DataProvider from Test classes.
	 * Ending of this method should show the same page as the starting of the method
	 * Thus, add all those steps which makes it a loop so that each iteration
	 * I'm at the same UI page where I need to feed the data
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param telephone
	 * @param password
	 * @param subscribe
	 * @return boolean value
	 */
	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		eu.type(this.firstName, firstName)
		  .type(this.lastName, lastName)
		  .type(this.email, email)
		  .type(this.telephone, telephone)
		  .type(this.password, password)
		  .type(this.confirmPassword, password);
		
		if (subscribe.equalsIgnoreCase("yes")) {
			eu.click(yesRB);
		}else {
			eu.click(noRB);
		}
		
		eu.click(tncCB);
		eu.click(continueBtn);
		
		if (eu.getText(successfulRegistrationTxt).contains("Your Account Has Been Created")) {
			eu.click(logout)
			  .click(registerLink);	
			return true;
		}
		return false;
	}
}
