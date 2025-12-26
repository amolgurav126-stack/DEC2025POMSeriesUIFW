package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleutil;
	Map<String, String> productMap;

	// public constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleutil = new ElementUtil(driver);
	}

	private final By header = By.cssSelector("div#content h1");
	private final By productImages = By.cssSelector("ul.thumbnails img");

	private final By productMeta = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	public String getProductHeader() {
		String headerVal = eleutil.waitForElementVisible(header, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("product header :--> " + headerVal);
		return headerVal;
	}

	public int getProductImagesCount() {
		int imagesCount = eleutil.waitForElementsPresence(productImages, AppConstants.DEFAULT_SHORT_WAIT).size();
		System.out.println("Total number of images :" + imagesCount);
		return imagesCount;
	}

	public Map<String, String> getProductInfo() {

		// productMap = new HashMap<String, String>();//random order

		// productMap = new LinkedHashMap<String, String>();//exact insertion order

		productMap = new TreeMap<String, String>();// sorted order with the keys

		productMap.put("productname", getProductHeader());
		productMap.put("productimages", String.valueOf(getProductImagesCount()));

		getProductMetaData();
		getProductPriceData();

		System.out.println("===========Product Data========: \n" + productMap);
		return productMap;
	}

	public Map<String, String> getProductMetaData() {

		List<WebElement> productMetaList = eleutil.waitForElementsVisible(productMeta, AppConstants.DEFAULT_SHORT_WAIT);

		System.out.println("Total meta data are " + productMetaList.size());

//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: Out Of Stock

		for (WebElement e : productMetaList) {
			String metaText = e.getText();
			String[] data = metaText.split(":");
			String metaKey = data[0];
			String metaValue = data[1];
			productMap.put(metaKey, metaValue);
		}
		return productMap;
	}

	public Map<String, String> getProductPriceData() {
		List<WebElement> productPriceList = eleutil.waitForElementsVisible(productPriceData,
				AppConstants.DEFAULT_SHORT_WAIT);

		System.out.println("Total price data are " + productPriceList.size());

		productMap = new HashMap<String, String>();

//		$2,000.00
//		Ex Tax: $2,000.00
		String priceValue = productPriceList.get(0).getText();
		String ExTaxValue = productPriceList.get(1).getText().split(":")[1].trim();

		productMap.put("ProductPrice", priceValue);
		productMap.put("ExTax", ExTaxValue);

		return productMap;
	}

}
