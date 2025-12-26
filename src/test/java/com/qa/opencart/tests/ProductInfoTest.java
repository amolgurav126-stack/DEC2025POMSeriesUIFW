package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtils;

public class ProductInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		accPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));		
	}
	
	@DataProvider
	public Object[][] getProducts() {
		return new Object[][] {
			{"macbook","MacBook Pro"},
			{"samsung", "Samsung SyncMaster 941BW"},
			{"imac","iMac"},
			{"canon","Canon EOS 5D"}
		};
	}
	
	
	
	
	@Test(dataProvider = "getProducts")
	public void productHeaderTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, productName);
	}
	
	@DataProvider
	public Object[][] getProductsImages() {
		return new Object[][] {
			{"macbook","MacBook Pro", 4},
			{"samsung", "Samsung SyncMaster 941BW",1},
			{"canon","Canon EOS 5D",3}
		};
	}
	
	
		
	@Test(dataProvider = "getProductsImages")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	@Test
	public void productInfDataTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String, String> productDataMap = productInfoPage.getProductInfo();
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(productDataMap.get("productname"), "MacBook Pro");
		
		softAssert.assertEquals(productDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(productDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(productDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(productDataMap.get("Product Code"), "Product 18");

		softAssert.assertEquals(productDataMap.get("productprice"), "$2,000.00");
		softAssert.assertEquals(productDataMap.get("extaxprice"), "$2,000.00");

		softAssert.assertAll();//7 --> 1 failed
		
	}
}
	
	//AAA pattern -- Arrange Act Assert
	// we can have multiple soft assertions in a single test case
	//but only one hard assert in the test case
