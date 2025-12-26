package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class CommonPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private static final Logger log = LogManager.getLogger(CommonPage.class);
	
	// private By locators: page objects
	private  final By logo = By.cssSelector("img.img-responsive");
	private final By footerLinks = By.cssSelector("footer li a");
	private final By search = By.name("search");
	
	//public constructor
		public CommonPage(WebDriver driver) {
			this.driver = driver;
			eleUtil = new ElementUtil(driver);
		}
		
		public boolean isLogoExist() {
			return eleUtil.isElementDisplayed(logo);
		}
		
		public boolean isSearchFieldExist() {
			return eleUtil.isElementDisplayed(search);
		}
		
		public List<String> isFooterLinksExist() {
			List<WebElement> footerList = eleUtil.waitForElementsPresence(footerLinks, AppConstants.DEFAULT_SHORT_WAIT);
			log.info("Total footer links are : "+footerList.size());
			
			List<String> footerValueList = new ArrayList<String>();
			
			for(WebElement e : footerList) {
				String text = e.getText();
				footerValueList.add(text);
			}
			return footerValueList;
		}

}
