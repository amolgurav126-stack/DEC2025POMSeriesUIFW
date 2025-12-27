package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	
	//private By locators
	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@value='Login']");
	private final By header = By.xpath("//h2[text()='New Customer']");
	private final By forgotPwdLink = By.linkText("Forgotten Password");
	private final By registerLink = By.linkText("Register");
	private final By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");


	//public constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	//public page methods
	@Step("getting login page title..")
	public String getLoginPageTitle() {	
		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("Login page title is::---> "+title);	
		log.info("Login page title is:---> "+title);
		return title;
	}
	
	@Step("getting login page url...")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_FRACTIONURL, AppConstants.DEFAULT_SHORT_WAIT);
//		System.out.println("Login page url is:---> "+url);
		log.info("Login page url is:---> "+url);
		return url;
	}
	
	@Step("Fogot password Link exist....")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	@Step("Page Header exist...")
	public boolean isHeaderExist() {
		return eleUtil.isElementDisplayed(header);
	}
	
	@Step("Login with correct username: {0} and correct {1}")
	public AccountsPage doLogin(String appUsername, String appPassword) {
//		System.out.println("Application credentials are:: "+ appUsername + " : "+ appPassword);
		log.info("Application credentials are: "+ appUsername + " : "+ appPassword);
		eleUtil.waitForElementVisible(email, AppConstants.DEFAULT_SHORT_WAIT).sendKeys(appUsername);
		eleUtil.doSendKeys(password, appPassword);		
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);		
	}
	
	@Step("Login with invlaid username: {0} and invalid {1}")
	public boolean doLoginWithInvalidCredentials(String invalidUN, String invalidPWD) {
		log.info("Application credentials are: "+ invalidUN + " : "+ invalidPWD);
		WebElement emailEle = eleUtil.waitForElementVisible(email, AppConstants.DEFAULT_SHORT_WAIT);
		emailEle.clear();
		emailEle.sendKeys(invalidUN);
		eleUtil.doSendKeys(password, invalidPWD);
		eleUtil.doClick(loginBtn);
		String errorMessg = eleUtil.doElementGetText(loginErrorMessg);
		if(errorMessg.contains(AppConstants.LOGIN_INVALID_CREDS_MESSG)) {
			return true;
		}
		if(errorMessg.contains(AppConstants.LOGIN_BLANK_CREDS_MESSG)) {
			return true;
		}		
		return false;
		
	}
	
	@Step("Navigate to register page..")
	public RegisterPage navigateToRegisterPage() {
		log.info("navigating to registration page");
		eleUtil.waitForElementVisible(registerLink, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);
	}

}
