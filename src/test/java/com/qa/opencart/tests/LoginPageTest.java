package com.qa.opencart.tests;

import org.testng.Assert;

import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP-001:Design login page of open cart application")
@Feature("F-1001: Open cart login page feature")
@Story("S-100: Open cart Login page functionality")

public class LoginPageTest extends BaseTest {
	
	
	@Description("Login page title test...")
	@Owner("Amol Gurav")
	@Severity(SeverityLevel.MINOR)
	
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginpage.getLoginPageTitle();
		ChainTestListener.log("Login page title is" + actTitle);
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Description("Login page URL Test..")
	@Owner("Amol Gurav")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void loginPageURLTest() {
		String actURL = loginpage.getLoginPageURL();
		ChainTestListener.log("Login page url is" + actURL);
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTIONURL));
	}
	
	@Description("Forgot password link verify test..")
	@Owner("Amol Gurav")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void isForgotPwdLinkExistTest() {
		Assert.assertTrue(loginpage.isForgotPwdLinkExist());
	}
	
	@Description("Login page headers test..")
	@Owner("Amol Gurav")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void isHeaderExistTest() {
		Assert.assertTrue(loginpage.isHeaderExist());
	}
	
	@Description("user is able to login to app with the correct credentials....")
	@Owner("Amol Gurav")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE)
	public void doLoginTest() {
	    accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertTrue(accPage.isLogoutLonkExist());
		
	}

}
