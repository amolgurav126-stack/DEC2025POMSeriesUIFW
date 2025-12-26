package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class CommonPageTest extends BaseTest{
	
	@Test
	public void checkCommonElementsOnLoginPageTest() {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonPage.isLogoExist());
		softAssert.assertTrue(commonPage.isSearchFieldExist());
		List<String> actfooterLinksExist = commonPage.isFooterLinksExist();
		softAssert.assertEquals(actfooterLinksExist.size(), AppConstants.FOOTER_LINKS_COUNT);
		softAssert.assertAll();
	}
	
	@Test
	public void checkCommonElementsOnAccountsPageTest() {
		
	  loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(commonPage.isLogoExist());
		softAssert.assertTrue(commonPage.isSearchFieldExist());
		List<String> actfooterLinksExist = commonPage.isFooterLinksExist();
		softAssert.assertEquals(actfooterLinksExist.size(), AppConstants.FOOTER_LINKS_COUNT);
		softAssert.assertAll();
	}

}
