package com.qa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.utils.Constants;

/**
 * Since this class extends BaseTest, BaseTest is parent class and this class becomes child class
 * Child class should be able to access all obects of parent class
 * @author User
 *
 */

//@Listeners(ExtentReportListener.class)		// Add this annotataion only if you want to run via this class and expect ExtentReport
public class A_LoginPageTest extends BaseTest {
	
	String actual;
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println(" ------------- Starting test: "+m.getName()+" ---------------");
	}
	
	@Test(priority = 1)
	public void verifyLoginPageTitle() throws Exception {
		actual = lp.getLoginPageTitle();
		assertEquals(actual,Constants.LOGIN_PAGE_TITLE);
	}
	
	@Test(priority = 2)
	public void verifyForgotPasswordLink() throws Exception {
		assertTrue(lp.checkForgotPwdLinkExists());
	}
	
	@Test(priority = 3)
	public void verifySuccessfulLogin() throws Exception {
		lp.doLogin(props.getProperty("username"), props.getProperty("password"));
	}
}
