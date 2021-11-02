package com.qa.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.pages.A_LoginPage;
import com.qa.pages.B_AccountsPage;
import com.qa.pages.C_ProductsInfoPage;
import com.qa.pages.D_RegistrationPage;

/**
 * Used to do initial Setup like 
 * a) Initialize Properties
 * b) Initialize driver
 * c) Create object of Page classes
 * d) Pass driver to all page classes
 * e) Open browser and navigate to URL
 * 
 * @note: The objects of page classes created here	 will be available to its child classes
 * @author User
 *
 */
public class BaseTest {
	public BasePage bp;
	public Properties props;
	public WebDriver driver;
	public A_LoginPage lp;
	public B_AccountsPage ap;
	public C_ProductsInfoPage pip;
	public D_RegistrationPage rp;

	/**
	 * After testng.xml, the compiler comes here since we're using @BeforeClass
	 * This class initiliazes properties file and driver
	 * @param browserValueFromParameters
	 * @throws Exception
	 */
	@Parameters({"browser"})
	@BeforeClass
	public void setUp(@Optional("chrome") String browserValueFromParameters) throws Exception { // If we run from ClassFile instead of testng.xml file 
		bp = new BasePage();																	//	@Parameters will not give value to  browserValueFromParameters 
		props = bp.initProperties();															//	In such cases, value set with @Optional will be given to it.
		driver = bp.initDriver(browserValueFromParameters);		// Driver has been initialized. 
																//	Now we need to start passing driver to all Page Classes
		lp = new A_LoginPage(driver);							// At this stage driver has opened an EMPTY browser
																// Pass driver to all page classes
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(props.getProperty("url"));					// driver loads the URL in the browser
	}

	@AfterClass
	public void tearDown() throws Exception {		
		driver.quit();
	}

}
