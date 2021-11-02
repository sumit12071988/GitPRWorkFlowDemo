package com.qa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.utils.Constants;

// @Listeners(ExtentReportListener.class)		// Add this annotataion only if you want to run via this class and expect ExtentReport
public class B_AccountsPageTest extends BaseTest {
	
	String actual;
	
	/**
	 * Any activity that navigates me from login page to Homepage 
	 * needs to be specified here
	 * @throws Exception
	 */
	@BeforeClass
	public void setUpAccountsPage() throws Exception {
		ap = lp.doLogin(props.getProperty("username"), props.getProperty("password"));
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println(" ------------- Starting test: "+m.getName()+" ---------------");
	}
	
	@Test(priority = 1)
	public void accountsPageTitleTest() throws Exception {
		actual = ap.getAccountPageTitle();
		assertEquals(actual, Constants.ACCOUNTS_PAGE_TITLE);
	}
	
	@Test(priority = 2)
	public void verifyBreadCrumb() throws Exception {
		assertEquals(ap.breadCrumbVerifier(), Constants.ACCOUNTS_PAGE_BREADCRUMB);
	}
	
	@Test(priority = 3)
	public void verifyAccountsPage() throws Exception {
		assertTrue(ap.pageVerifier());
	}
	
	@Test(priority = 4)
	public void verifyMyAccountAppearing() throws Exception {
		assertTrue(ap.isMyAccountExists());
	}
	
	@Test(priority = 5)
	public void verifyCountOfAccountSections() throws Exception {
		assertEquals(ap.getAccountSectionsCount(), 4);
	}
	
	@Test(priority = 6)
	public void verifyMyAccountSectionsList() {
		List<String> actualList = ap.getAccountSectionsList();
		List<String> expectedList = Constants.getAccountSectionsList();
		assertEquals(actualList, expectedList);
	}
	
	@Test(priority = 7)
	public void searchTest() {
		assertTrue(ap.doSearch("imac"));
	}
	
	
	
	
}
