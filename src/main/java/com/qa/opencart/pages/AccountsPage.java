package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleutil;
	
	// public constructor
	public AccountsPage(WebDriver driver) {
		this.driver= driver;
		eleutil = new ElementUtil(driver);
	}
	
	//private By locators
	private final By logoutLink = By.linkText("Logout");
	private final By headers = By.xpath("//div[@id='content']//h2");
	private final By search = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");
	
	//public methods
	public boolean isLogoutLonkExist() {
		WebElement logoutEle  = eleutil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_MEDIUM_WAIT);
		return eleutil.isElementDisplayed(logoutEle);
	}

	public List<String> getAccPageHeaders() {
		List<WebElement> headerList = eleutil.waitForElementsPresence(headers, AppConstants.DEFAULT_MEDIUM_WAIT);
		System.out.println("Count of header on accounts page : "+ headerList.size());
		List<String> accHeaderList = new ArrayList<String>();
		for(WebElement e: headerList) {
			String text = e.getText();
			System.out.println(text);
			accHeaderList.add(text);
		}
		return accHeaderList;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		System.out.println("Search Key-->"+ searchKey);
		WebElement searchEle = eleutil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_WAIT);
		searchEle.clear();
		searchEle.sendKeys(searchKey);
		eleutil.doClick(searchIcon);
		return new SearchResultsPage(driver);
	}
	
	
}
