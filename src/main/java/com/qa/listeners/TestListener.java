package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.qa.base.BasePage;
import com.qa.reports.ExtentReport;

public class TestListener implements ITestListener {
		
	String className, methodName;
	WebDriver driver;
	
	public void onStart(ITestContext context) {
	}
	
	public void onTestStart(ITestResult result) {
		String qualifiedName = result.getMethod().getQualifiedName();	// com.qa.tests.A_LoginPageTest.verifyLoginPageTitle
		methodName = result.getMethod().getMethodName();			// verifyLoginPageTitle
		
		int last = qualifiedName.lastIndexOf(".");						// 28
		int mid = qualifiedName.substring(0, last).lastIndexOf(".");	// 12
		className = qualifiedName.substring(mid + 1, last);		// A_LoginPageTest
		
		ExtentReport.startTest(result.getName(), result.getMethod().getDescription())
				.assignCategory(className)
				.assignAuthor("Sumit");
	}
	
	public void onTestSuccess(ITestResult result) {
		ExtentReport.getTest().log(Status.PASS, "Test Passed");
		ExtentReport.getReporter().flush();
	}
	
	public void onTestSkipped(ITestResult result) {
		ExtentReport.getTest().log(Status.SKIP, "Test Skipped");
		ExtentReport.getReporter().flush();
	}
	
	public void onTestFailure(ITestResult result) {		
		if (result.getThrowable() != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			result.getThrowable().printStackTrace(pw);
		}
		try {
			ExtentReport.getTest().fail("Test Failed. Capturing screenshot for Troubleshooting"+"\n",MediaEntityBuilder.createScreenCaptureFromPath(captureScreenshotAsFILE()).build());
			ExtentReport.getTest().fail("Test Failed. Capturing screenshot for Troubleshooting"+"\n",MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenShotAsBase64()).build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ExtentReport.getTest().fail(result.getThrowable());
		ExtentReport.getReporter().flush();
	}	
	
	public void onFinish(ITestContext context) {
	}
	
	/**
	 * This method with take screenshot
	 * @return path of screenshot
	 */
	private String captureScreenshotAsFILE() {
		BasePage bp = new BasePage();
		driver = bp.getDriver();
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		String destinationImagePath = System.getProperty("user.dir") 
									+ File.separator + "screenshots" 
									+ File.separator + className 
									+ File.separator + methodName + ".jpg";	
		
		File destFile = new File(destinationImagePath);
		try {
			FileUtils.copyFile(srcFile, destFile);
			Reporter.log("<a href='" + destinationImagePath + "'> <img src='" + destinationImagePath + "' height='800' width='400'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinationImagePath;
	}
	
	private String captureScreenShotAsBase64() throws IOException {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// -------- Encoding File object to Base64 ByteArray -------------------------
		byte[] encoded = null;
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));

		// Convert ByteArray to String
		String base64String = new String(encoded, StandardCharsets.US_ASCII);
		
		return base64String;
	}

}
