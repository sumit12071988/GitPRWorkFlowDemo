package com.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.inject.spi.PrivateElements;
import com.qa.base.BasePage;
import com.qa.utils.Constants;
import com.qa.utils.ElementUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class B_AccountsPage extends BasePage {
	private ElementUtil eu;

	// 1. Constructor to receive the driver for this page
	public B_AccountsPage(WebDriver driver) {
		this.driver = driver;	// this.driver is referring to parent page driver i.e. BasePage's driver
		eu = new ElementUtil(driver);
	}

	// 2. Page Locators | By locators
	private By pageVerifier = By.xpath("//a[normalize-space()='Your Store']");
	private By breadcrumbAccount = By.xpath("//a[normalize-space()='Account']");
	private By myAccount = By.xpath("//span[normalize-space()='My Account']");
	private By accountSectionHeaders = By.xpath("//div[@id='content']/h2");
	private By searchTxt = By.xpath("//div[@id='search']/input[@name='search']");
	private By searchBtn = By.xpath("//i[@class='fa fa-search']/parent::button");
	private By searchResult = By.xpath("//div[contains(@class,'product-layout')]/div");
	
	private By resultItems = By.xpath("//div[@class='row']//h4/a");

	// 3. Page Actions | Equivalent to "Steps to Reproduce"
	public String getAccountPageTitle() {
		eu.waitForTitleToBe(10, Constants.ACCOUNTS_PAGE_TITLE);
		return eu.getTitle();
	}

	public String breadCrumbVerifier() {
		if (eu.isDiplayed(breadcrumbAccount)) {
			return eu.getText(breadcrumbAccount);
		} else {
			return null;
		}
	}
	
	public boolean pageVerifier() {
		return eu.isDiplayed(pageVerifier);
	}
	
	public boolean isMyAccountExists() {
		if(eu.findElements(myAccount).size() > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getAccountSectionsCount() {
		return eu.findElements(accountSectionHeaders).size();
	}
	
	public List<String> getAccountSectionsList() {
		List<WebElement> list = eu.findElements(accountSectionHeaders);
		List<String> accountsList = new ArrayList();
		
		for(WebElement ele: list) {
			System.out.println(ele.getText());
			accountsList.add(ele.getText());
		}
		return accountsList;
	}
	
	public boolean doSearch(String txt) {
		eu.type(searchTxt, txt)
		  .click(searchBtn);
		
		if (eu.findElements(searchResult).size() > 0) {	// Checking if any product is found from search, it returns true
			return true;
		}else {
			return false;
		}
	}
	
	
	public C_ProductsInfoPage selectProductFromResults(String productName) {
		List<WebElement> resultItemList = eu.findElements(resultItems);
		System.out.println("Tota items displayed: " + resultItemList.size());

		for (WebElement ele : resultItemList) {
			if (ele.getText().equalsIgnoreCase(productName)) {
				ele.click();
				break;
			}
		}
		return new C_ProductsInfoPage(driver);
	}
	
}
