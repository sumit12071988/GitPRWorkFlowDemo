package com.qa.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.base.BasePage;
import com.qa.utils.ElementUtil;

public class C_ProductsInfoPage extends BasePage {
	private ElementUtil eu;
	
	// Constructor
	public C_ProductsInfoPage(WebDriver driver) {
		this.driver = driver;	// this.driver is referring to parent page driver i.e. BasePage's driver
		eu = new ElementUtil(driver);
	}
	
	// Page Locators
	private By productNameHeader =  By.xpath("//div[@id='content']//h1");
	private By productMetaData = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[1]/li");
	private By productPrice = By.xpath("(//div[@class='col-sm-4']//ul[@class='list-unstyled'])[2]/li");
	private By quantity = By.xpath("//input[@id='input-quantity']");
	private By addToCart = By.id("button-cart");
	private By productImages = By.xpath("//ul[@class='thumbnails']/li//img");
	
	// Page Methods
	public Map<String, String> getProductInformation() {
		Map<String, String> productInfoMap = new HashMap<>();
		productInfoMap.put("pageHeader", eu.getText(productNameHeader));
		
		List<WebElement> productMetaDataList = eu.findElements(productMetaData);
		for(WebElement ele: productMetaDataList) {
			String completeTxt= ele.getText();				// Brand: Apple
			String[] strArray = completeTxt.split(":");		// Brand,  Apple
			String keyPart = strArray[0].trim();			// K = Brand
			String valuePart = strArray[1].trim();			// V = Apple
			productInfoMap.put(keyPart, valuePart);			// [{Brand = Apple}]
		}
		List<WebElement> productPriceList = eu.findElements(productPrice);
			WebElement ele = productPriceList.get(0);	// $2,000.00
			String eleTxt = ele.getText();				// $2,000.00
			productInfoMap.put("price", eleTxt);
			
			WebElement exTaxElement = productPriceList.get(1);	// Ex Tax: $2,000.00
			String exTaxTxt = exTaxElement.getText(); 			// Ex Tax: $2,000.00
			String exTaxInUSD = exTaxTxt.split(":")[1].trim();
			productInfoMap.put("exTaxPrice",exTaxInUSD);	
			
			return productInfoMap;
	}
	
	
	public void selectQuantity(String quantity) {
		eu.type(this.quantity, quantity);	// this.quantity refers to the class variable		
	}
	
	public void addToCart() {
		eu.click(addToCart);
	}
	
	public int getProductImagesCount() {
		int imageCount = eu.findElements(productImages).size();
		System.out.println("Total images found: "+imageCount);
		return imageCount;
	}
	
}
