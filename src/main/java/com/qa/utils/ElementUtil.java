package com.qa.utils;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.qa.base.BasePage;
import com.qa.reports.ExtentReport;


public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}
	
	//---------- GENERIC UTILS ----------------------------------//
	public WebElement findElement(By locator) {
		try {
			waitForElementsVisible(locator, 10);
			WebElement ele = driver.findElement(locator);
			
			if (BasePage.highlightElement.equals("true")) {	// To check whether to flash element or not
				jsUtil.flash(ele);
			}
			
			return ele;
		} catch (Exception e) {
			ExtentReport.getTest().log(Status.FAIL, "Element not found using: " + locator);
			e.printStackTrace();
		}
		return null;
	}
	
	public List<WebElement> findElements(By locator) {
		waitForElementsVisible(locator, 10);
		return driver.findElements(locator);
	}
	
	public ElementUtil click(By locator) {
		WebElement ele = findElement(locator);
		ele.click();
		return this;
	}
	
	public ElementUtil clickAction(By locator) {
		WebElement ele = findElement(locator);
		new Actions(driver)
			.click(ele)
			.perform();
		return this;
	}
	
	public ElementUtil clickFromGroupOfElements(By locator, String elementTxt) {
		List<WebElement> linksList = findElements(locator);
		System.out.println(linksList.size());
		for (WebElement e : linksList) {
			String text = e.getText();
			System.out.println(text);
			if (text.trim().equals(elementTxt)) {
				e.click();
				break;
			}
		}
		return this;
	}
	
	public ElementUtil type(By locator,String txt) {
		WebElement ele = findElement(locator);
		ele.click();
		ele.clear();
		ele.sendKeys(txt);
		return this;
	}
	
	public ElementUtil typeAction(By locator,String txt) {
		WebElement ele = findElement(locator);
		
		new Actions(driver)
			.sendKeys(ele,txt)
			.perform();
		
		return this;
	}
	
	public String getText(By locator) {
		WebElement ele = findElement(locator);
		return ele.getText();
	}
	
	public String getTitle() {
		return driver.getTitle();
	}
	
	public boolean isDiplayed(By locator) {
		WebElement ele = findElement(locator);
		return ele.isDisplayed();
	}
	
	public boolean checkElementDisplayed(By locator) {
		if (findElements(locator).size() == 1)
			return true;
		return false;
	}
	
	public boolean checkElementDisplayed(By locator, int eleOccurrence) {
		if (findElements(locator).size() == eleOccurrence)
			return true;
		return false;
	}
	
	public boolean isEnabled(By locator) {
		WebElement ele = findElement(locator);
		return ele.isEnabled();
	}
	
	public boolean isSelected(By locator) {
		WebElement ele = findElement(locator);
		return ele.isSelected();
	}
	
	public boolean isElementDisabled(By locator, String attributeName) {
		WebElement ele = findElement(locator);
		if (ele.getAttribute(attributeName).equals("true")) {
			return true;
		}
		return false;
	}
	
	public String getElementAttribute(By locator, String attributeName) {
		WebElement ele = findElement(locator);
		return ele.getAttribute(attributeName);
	}
	
	public ElementUtil ddSelectByIndex(By locator, int index) {
		Select select = new Select(findElement(locator));
		select.selectByIndex(index);
		return this;
	}
	
	public ElementUtil ddSelectByVisibleText(By locator, String text) {
		Select select = new Select(findElement(locator));
		select.selectByVisibleText(text);
		return this;
	}
	
	public ElementUtil ddSelectByValue(By locator, String value) {
		Select select = new Select(findElement(locator));
		select.selectByValue(value);
		return this;
	}
	
	public boolean isDropDownValueSelected(Select select, String expValue) {
		if (select.getFirstSelectedOption().getText().contains(expValue)) {
			System.out.println(expValue + ": is selected");
			return true;
		}
		return false;
	}
	
	public void ddSelectDropDownFromOptionsList(By locator, String value) {
		Select select = new Select(findElement(locator));
		List<WebElement> optionsList = select.getOptions();
		iterateAndClick(optionsList, value);
	}

	public void selectDropDownWithoutSelect(By locator, String value) {
		List<WebElement> optionsList = findElements(locator);
		iterateAndClick(optionsList, value);

	}

	private void iterateAndClick(List<WebElement> optionsList, String value) {
		System.out.println("total options: " + optionsList.size());
		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}
	
	public void parentChildMenuHandle(By parent, By child) throws InterruptedException {
		Actions act = new Actions(driver);
		act.moveToElement(findElement(parent)).build().perform();
		Thread.sleep(2000);
		click(child);
	}
	
	public int rightClickItemsCount(By rightClick, By items) {
		return getRightClickList(rightClick, items).size();
	}
	
	public void clickOnRightClickItem(By rightClick, By items, String value) {
		Actions act = new Actions(driver);
		act.contextClick(findElement(rightClick)).perform();
		List<WebElement> menuList = findElements(items);
		for (WebElement e : menuList) {
			if (e.getText().equals(value)) {
				e.click();
				break;
			}
		}
	}

	/**
	 * 
	 * @param rightClick
	 * @param items
	 * @return
	 */
	public List<String> getRightClickList(By rightClick, By items) {
		List<String> itemValueList = new ArrayList<String>();
		Actions act = new Actions(driver);
		act.contextClick(findElement(rightClick)).perform();
		List<WebElement> menuList = findElements(items);
		System.out.println(menuList.size());

		for (WebElement e : menuList) {
			String text = e.getText();
			System.out.println(text);
			itemValueList.add(text);
		}
		return itemValueList;
	}
	
	//-------------------------------------------------------------------------------------//}

	/***********************************
	 * wait utils
	 *********************************/

	public Alert waitForJSAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public void acceptAlert(int timeOut) {
		waitForJSAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForJSAlert(timeOut).dismiss();
	}

	public String alertGetText(int timeOut) {
		Alert alert = waitForJSAlert(timeOut);
		String text = alert.getText();
		alert.accept();
		return text;
	}

	public void alertSendKeys(int timeOut, String value) {
		waitForJSAlert(timeOut).sendKeys(value);
	}

	public boolean waitForUrlContains(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlContains(urlFraction));
	}

	public boolean waitForUrlToBe(String urlValue, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.urlToBe(urlValue));
	}

	public String waitForTitleContains(int timeOut, String titleFraction) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
			return driver.getTitle();
		}
		return null;
	}

	public String waitForTitleToBe(int timeOut, String title) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		if (wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		return null;
	}

	public void waitForFrameUsingIDOrName(String frameIDOrName, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
	}

	public void waitForFrameUsingIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameUsingByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameUsingWebElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public List<WebElement> waitForElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public List<String> findElementsTextList(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsPresence(locator, timeOut);
		List<String> eleValList = new ArrayList<String>();
		for (WebElement e : eleList) {
			eleValList.add(e.getText());
		}
		return eleValList;
	}

	public void printElementsTextList(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsPresence(locator, timeOut);
		for (WebElement e : eleList) {
			System.out.println(e.getText());
		}
	}

	public List<String> getVisibleElementsTextList(By locator, int timeOut) {
		List<WebElement> eleList = waitForElementsVisible(locator, timeOut);
		List<String> eleValList = new ArrayList<String>();
		for (WebElement e : eleList) {
			eleValList.add(e.getText());
		}
		return eleValList;
	}

	public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	public WebElement waitForElementPresent(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementPresent(By locator, long timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut, intervalTime);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 */
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public WebElement waitForElementToClickable(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public void clickWhenReady(By locator, int timeOut) {
		waitForElementToClickable(locator, timeOut).click();
	}

//	public WebElement waitForElementPresenceWithWebDriverWait(By locator, int timeOut, int pollingTime) {
//		WebDriverWait wait = new WebDriverWait(driver, timeOut);
//		wait.withMessage(Error.TIME_OUT_WEB_ELEMENT_MESG).pollingEvery(Duration.ofMillis(pollingTime))
//				.ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
//		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//	}
//
//	public WebElement waitForElementPresenceWithFluetWait(By locator, int timeOut, int pollingTime) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
//				.withMessage(Error.TIME_OUT_WEB_ELEMENT_MESG).pollingEvery(Duration.ofMillis(pollingTime))
//				.ignoring(StaleElementReferenceException.class, NoSuchElementException.class);
//
//		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//	}
//
//	public Alert waitForAlertPresenceWithFluetWait(int timeOut, int pollingTime) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
//				.withMessage(Error.TIME_OUT_ALERT_MESG).pollingEvery(Duration.ofMillis(pollingTime))
//				.ignoring(NoAlertPresentException.class);
//
//		return wait.until(ExpectedConditions.alertIsPresent());
//	}
//
//	public WebDriver waitForFramePresenceWithFluetWait(By frameLocator, int timeOut, int pollingTime) {
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
//				.withMessage(Error.TIME_OUT_FRAME_MESG).pollingEvery(Duration.ofMillis(pollingTime))
//				.ignoring(NoSuchFrameException.class);
//
//		return wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
//	}

	/*********** custom wait util *************/
	public WebElement retryingElement(By locator, int timeOut) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = findElement(locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt: " + attempts + ": " + locator);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}

		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println(
						"Element is not found exception...tried for : " + timeOut + " with interval time of 500 ms");
			}
		}

		return element;

	}

	public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

		WebElement element = null;
		int attempts = 0;
		while (attempts < timeOut) {
			try {
				element = findElement(locator);
				break;
			} catch (NoSuchElementException e) {
				System.out.println("element is not found in attempt: " + attempts + ": " + locator);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

			}
			attempts++;
		}

		return element;

	}

}