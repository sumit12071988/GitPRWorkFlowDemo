package com.qa.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.BaseTest;

//@Listeners(ExtentReportListener.class)		// Add this annotataion only if you want to run via this class and expect ExtentReport
public class C_ProductsInfoTest extends BaseTest {
	String actual;
	
	@BeforeClass
	public void setUpProductsInfoPage() throws Exception {
		ap = lp.doLogin(props.getProperty("username"), props.getProperty("password"));
	}
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println(" ------------- Starting test: "+m.getName()+" ---------------");
	}
	
	@Test(priority = 1)
	public void verifyProductInfoTest_MacBookPro() throws Exception {
		assertTrue(ap.doSearch("Macbook"));
		pip = ap.selectProductFromResults("MacBook Pro");
		assertTrue(pip.getProductImagesCount() == 4);
		Map<String, String> productInfoMap = pip.getProductInformation();
		System.out.println(productInfoMap);
		// {Brand=Apple, Availability=Out Of Stock, price=$2,000.00, Product Code=Product 18, pageHeader=MacBook Pro, Reward Points=800, exTaxPrice=$2,000.00}
		assertEquals(productInfoMap.get("pageHeader"), "MacBook Pro");
		assertEquals(productInfoMap.get("Brand"), "Apple");
		assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		assertEquals(productInfoMap.get("price"), "$2,000.00");
		assertEquals(productInfoMap.get("Product Code"), "Product 18");
		assertEquals(productInfoMap.get("Reward Points"), "800");
		assertEquals(productInfoMap.get("exTaxPrice"), "$2,000.00");
	}
	
	@Test(priority = 2)
	public void verifyProductInfoTest_iMac() throws Exception {
		assertTrue(ap.doSearch("iMac"));
		pip = ap.selectProductFromResults("iMac");
		assertTrue(pip.getProductImagesCount() == 3);
		Map<String, String> productInfoMap = pip.getProductInformation();
		System.out.println(productInfoMap);
		// {Brand=Apple, Availability=Out Of Stock, price=$100.00, Product Code=Product 14, pageHeader=iMac, exTaxPrice=$100.00}
		assertEquals(productInfoMap.get("pageHeader"), "iMac");
		assertEquals(productInfoMap.get("Brand"), "Apple");
		assertEquals(productInfoMap.get("Availability"), "Out Of Stock");
		assertEquals(productInfoMap.get("price"), "$100.00");
		assertEquals(productInfoMap.get("Product Code"), "Product 14");
		assertEquals(productInfoMap.get("exTaxPrice"), "$100.00");
	}
	
	
}
