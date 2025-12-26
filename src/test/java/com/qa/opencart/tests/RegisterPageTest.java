package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtils;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtils;

public class RegisterPageTest extends BaseTest{
	
	@BeforeClass
	public void goToRegisterPage() {
		registerPage = loginpage.navigateToRegisterPage();
	}
	
	@DataProvider
	public Object[][] getRegData() {
		return new Object[][] {
			{"Amol", "Gurav","7655678904", "Amol12@1234", "yes"},
			{"diapli", "Amol","3746346364", "Deep@1234", "no"},
			{"Shreya", "Diapli","2353587256", "Shreya@1234", "yes"},
			{"Pallavi", "friend","7058435786", "Pallavi@1234", "no"},
		};
	}
	
		
	@DataProvider
	public Object[][] getExcelTestData() {
		return ExcelUtil.getTestData("register");
	}
	
	@DataProvider
	public Object[][] getCSVData() {
		return CSVUtils.csvData("OpenTestData");
	}

	
	@Test(dataProvider = "getCSVData")
	public void registerTestS(String firstname, String lastname,String telephone, String password, String subscribe) {
		Assert.assertTrue(registerPage.userRegister(firstname, lastname, StringUtils.getRandomEmail(), telephone, password, subscribe));
	}

}
