package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest{
	
	@BeforeClass
	public void searchSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] searchKeysData() {
		return new Object[][] {
			{"macbook","Search - macbook"},
			{"canon", "Search - canon"},
			{"samsung", "Search - samsung"}
		};
	}

	@Test(dataProvider = "searchKeysData")
	public void searchTest(String searchKey, String searchHeader) {
		searchResultsPage=  accPage.doSearch(searchKey);
		String actHeader=  searchResultsPage.getResultsHeaderValue();
		Assert.assertEquals(actHeader, searchHeader);
	}
	
	
}
