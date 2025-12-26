package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	// public constructor
	public SearchResultsPage(WebDriver driver) {
		this.driver= driver;
		eleutil = new ElementUtil(driver);
	}
	
	private final By resultHeader = By.cssSelector("div#content h1");
	private final By searchResults = By.cssSelector("div.product-thumb");
	
	public int getSearchResultsCount() {
		int resultsCount = eleutil.waitForElementsVisible(searchResults, AppConstants.DEFAULT_MEDIUM_WAIT).size();
		System.out.println("search is results count-->  "+ resultsCount );
		return resultsCount;
	}
	
	public String getResultsHeaderValue() {
		String header = eleutil.waitForElementVisible(resultHeader, AppConstants.DEFAULT_MEDIUM_WAIT).getText();
		System.out.println("Results header is ---> "+ header);
		return header;
	}
	
	public ProductInfoPage selectProduct(String productName) {
		System.out.println("Product name--> "+ productName);
		eleutil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}

}
