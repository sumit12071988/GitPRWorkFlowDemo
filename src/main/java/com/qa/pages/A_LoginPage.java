package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.base.BasePage;
import com.qa.utils.Constants;
import com.qa.utils.ElementUtil;

public class A_LoginPage extends BasePage {
	
	private ElementUtil eu;

	// 1. Constructor to receive the driver for this page
	public A_LoginPage(WebDriver driver) {
		this.driver = driver;	// this.driver is referring to parent page driver i.e. BasePage's driver
		eu = new ElementUtil(driver);
	}

	// 2. Page Locators | By locators
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[normalize-space()='Forgotten Password12']");
	private By registerBtn = By.xpath("//a[@class='list-group-item'][normalize-space()='Register']");
	
	// 3. Page Actions | Equivalent to "Steps to Reproduce"
	public String getLoginPageTitle() {
		eu.waitForTitleToBe(10, Constants.LOGIN_PAGE_TITLE);
		return eu.getTitle();
	}
	
	public boolean checkForgotPwdLinkExists() {
		return eu.isDiplayed(forgotPwdLink);
	}
	
	public B_AccountsPage doLogin(String user, String pwd) {
		System.out.println("Login with username: "+user+" and password: "+pwd);
		eu.type(username, user)
		  .type(password, pwd)
		  .click(loginBtn);
		
		return new B_AccountsPage(driver);
	}
	
	public D_RegistrationPage pageNavigator_RegistrationPage() {
		eu.click(registerBtn);
		return new D_RegistrationPage(driver);
	}
}
