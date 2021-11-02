package com.qa.base;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	
	private Properties props;
	private ChromeOptions co;
	private FirefoxOptions fo;
	
	public OptionsManager(Properties props) {
		this.props = props;
	}
	
//	For other arguments in ChromeOptions, refer https://peter.sh/experiments/chromium-command-line-switches/
	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();

		co.addArguments(
				 "--ignore-certificate-errors" 		// Ignoring certificate errors
				,"--allow-running-insecure-content"	// Allow running insecure contents
				,"--no-sandbox" 					// No dev mode/ sandbox mode
				,"--disable-dev-shm-usage" 			// No abnormal crashing
		);
		
		if (props.getProperty("headless").equals("true")) {
			co.addArguments("--headless");
		}
		if (props.getProperty("incognito").equals("true")) {
			co.addArguments("--incognito");		
		}		
		return co;
	}
	
	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (props.getProperty("headless").equals("true")) {
			fo.addArguments("--headless");
		}
		if (props.getProperty("incognito").equals("true")) {
			fo.addArguments("--incognito");
		}		
		return fo;
	}
	
}
