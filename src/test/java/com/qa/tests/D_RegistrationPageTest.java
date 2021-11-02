package com.qa.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;
import com.qa.utils.ExcelUtil;

// @Listeners(ExtentReportListener.class)		// Add this annotataion only if you want to run via this class and expect ExtentReport
public class D_RegistrationPageTest extends BaseTest {
	String actual;
	
	@BeforeClass
	public void setUpRegistrationPage() throws Exception {
		rp = lp.pageNavigator_RegistrationPage();
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println(" ------------- Starting test: "+m.getName()+" ---------------");
	}
	
	//-----------------------------------------------------------------------------------------------
	@DataProvider
	private Object[][] getTestData() throws InvalidFormatException, IOException {
		Object[][] data = ExcelUtil.getTestData("registration");
		return data;
	}
	
	@Test(dataProvider = "getTestData")
	public void loopablePart(String firstName, String lastName, String email, String telephone, String password, String subscribe) throws Exception {		
		boolean flag = rp.accountRegistration(firstName, lastName, email, telephone, password, subscribe);
		assertTrue(flag);
	}
	// ----------------------------------------------------------------------------------------------
	
	
}
