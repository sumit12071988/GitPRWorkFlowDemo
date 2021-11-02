package com.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {
	private Properties props;
	public WebDriver driver;	// Will be useless after implementation of ThreadLocal logic
	private OptionsManager om;
	private ChromeOptions co;
	private FirefoxOptions fo; 
	public static String highlightElement;
	
	// ThreadLocal says to initialize the WebDriver with me
	//	ThreadLocal will create COPIES (WITH SAME IDENTIFIER) of driver instance for each Threads who is trying to access driver instance.
	//	This way multiple Threads in parallel will have multiple drivers with SAME IDENTIFIER i.e. SAME SESSION-ID
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	/**
	 * GETTER for driver
	 * @return
	 */
	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
	
	/**
	 * Used to read config.properties by loading the Properties file
	 * @return props 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Properties initProperties() throws FileNotFoundException, IOException {
		String configFilePath = System.getProperty("user.dir")
									+File.separator + "src"
									+File.separator + "main"
									+File.separator + "resources"
									+File.separator + "config.properties";
		props = new Properties();
		props.load(new FileInputStream(new File(configFilePath)));
		return props;
	}

	/**
	 * Used to initialize WebDriver based on the browser in config.properties
	 * Called by BaseTest page AFTER calling initProperties() method
	 * Whoever wants to call this driver needs to pass properties object
	 * @param props
	 * @return driver
	 * @throws Exception 
	 */
	public WebDriver initDriver(String browser) throws Exception {
		om = new OptionsManager(props);	// By the time initDriver() is called, initProperties() has already been called and object is already created.
		co = om.getChromeOptions();
		fo = om.getFirefoxOptions();

		highlightElement = props.getProperty("debugHighlight");
		
		System.out.println("----- Reading config file -------");
		System.out.println(" Browser   : "+browser
							+"\n Headless  : "+props.getProperty("headless")
							+"\n Incognito : "+props.getProperty("incognito")
							+"\n DebugMode : "+props.getProperty("debugHighlight")
							+"\n Run in Remote : "+props.getProperty("runOnRemote")
							+"\n Remote Hub URL : "+props.getProperty("remoteHubUrl")
				);
		

		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			
			if (props.getProperty("runOnRemote").equals("true")) { // Condition to check if remote is set as true
				initRemoteDriver("chrome");
			} else {
				System.out.println("Running "+browser+" on: LOCAL !!");
				tlDriver.set(new ChromeDriver(co));
			}
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			
			if (props.getProperty("runOnRemote").equals("true")) { // Condition to check if remote is set as true
				initRemoteDriver("firefox");
			} else {
				System.out.println("Running "+browser+" on: LOCAL !!");
				tlDriver.set(new FirefoxDriver(fo));
			}
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			System.out.println("Running "+browser+" on: LOCAL !!");
			tlDriver.set(new EdgeDriver());
			break;

		default:
			throw new Exception("Invalid browser! - " + browser);
		}
		System.out.println("---------- "+browser+" instance opened successfully !!--------");

		return getDriver();
	}

	private void initRemoteDriver(String browser) throws MalformedURLException {
		System.out.println("Running "+browser+" on: REMOTE !!");
		
		om = new OptionsManager(props);
		co = om.getChromeOptions();
		fo = om.getFirefoxOptions();
		
		URL remoteAddress = new URL(props.getProperty("remoteHubUrl"));
		DesiredCapabilities cap = null;
		
		if (browser.equals("chrome")) {
			cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, co);							
			tlDriver.set(new RemoteWebDriver(remoteAddress,cap));
		}
		
		else if (browser.equals("firefox")) {
			cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS,fo);			
			tlDriver.set(new RemoteWebDriver(remoteAddress,cap));
		}
		
	}
	


}
