package com.qa.opencart.tests;

import java.util.List;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP:1001 : Open cart dashboard features..")
@Story("COP-100: Accounts page functionality after login")
@Feature("Accounts page features..")

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accPageSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	@Owner("Amol Gurav")
	@Description("Logout link exist test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void isLogoutLonkExistTest() {
		Assert.assertTrue(accPage.isLogoutLonkExist());
	}
	
	@Owner("Amol Gurav")
	@Description("Accounts pages headers test..")
	@Severity(SeverityLevel.NORMAL)
	@Test
	public void getAccPageHeadersTest() {
		List<String> actHeaderList = accPage.getAccPageHeaders();
		Assert.assertEquals(actHeaderList.size(), AppConstants.ACC_PAGE_HEADER_COUNT);
		Assert.assertEquals(actHeaderList, AppConstants.expectedAccPageHeadersList );

	}

}
