package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest{
	
		
	@DataProvider
	public Object[][] getLoginTestData() {
		return new Object[][] {
			{"Amol@@@@24234", "sddgd#26353"},
			{"Amol@@24234", "sdsdsdgd#26353"},
			{"Amol##@@@@24234", "sdrteffvdgd#26353"},
			{"", ""},
		};
	}
	
	
	
	@Test(dataProvider = "getLoginTestData")
	public void negativeLoginTest(String UN, String PWD) {
	Assert.assertTrue(loginpage.doLoginWithInvalidCredentials(UN, PWD));
	}

}
