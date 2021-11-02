package com.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class E_SignupPage {
	WebDriver driver;
	
	// Parameterized constructor
	public E_SignupPage(WebDriver driver) {
		this.driver = driver;
	}
	
	// Page Locators
	By signUp = By.id("Signup");
	
	// Page Methods
	public void clickSignUp() {
		driver.findElement(signUp).click();
	}
}
