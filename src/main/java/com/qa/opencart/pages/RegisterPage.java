package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private final By firstname = By.id("input-firstname");
	private final By lastname = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telephone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmPwd = By.id("input-confirm");
	
	private final By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@value='1']");
	private final By subscribeNo = By.xpath("//label[@class='radio-inline']/input[@value='0']");
	
	private final By agreeCheckBox = By.xpath("//input[@name='agree']");
	
	private final By continueBtn = By.xpath("//input[@value='Continue']");
	
	private final By successMessg = By.cssSelector("div#content h1");

	private final By logoutLink = By.linkText("Logout");
	private final By registerLink = By.linkText("Register");
	
	public boolean userRegister(String firstname, String lastname, String email, String telephone, String password, String subscribe) {
		eleUtil.waitForElementVisible(this.firstname, AppConstants.DEFAULT_MEDIUM_WAIT).sendKeys(firstname);
		eleUtil.doSendKeys(this.lastname, lastname);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPwd, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);
		
		String successMesg = eleUtil.waitForElementVisible(successMessg, AppConstants.DEFAULT_MEDIUM_WAIT).getText();
		System.out.println(successMesg);
		
		if(successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		else {
			return false;
		}
		


	}




	
	
	
	
	
	
}
