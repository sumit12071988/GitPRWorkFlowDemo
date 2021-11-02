package com.qa.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@SuppressWarnings("deprecation")
public class ExtentReport {
	public static ExtentReports extentReports;
	public final static String filePath = "Extent.html";
	public static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
	
	public synchronized static ExtentReports getReporter() {
		if (extentReports == null) {			
			ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter("Extent.html");
			extentHtmlReporter.config().setDocumentTitle("Selenium Framework");
			extentHtmlReporter.config().setReportName("My Web Tests");
			extentHtmlReporter.config().setTheme(Theme.DARK);
			extentHtmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
			
			extentReports = new ExtentReports();
			extentReports.attachReporter(extentHtmlReporter);
		}
		return extentReports;
	}	
	
	public static synchronized ExtentTest startTest(String testName, String desc) {
		ExtentTest extentTest = getReporter().createTest(testName, desc);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), extentTest);
		return extentTest;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}
}

