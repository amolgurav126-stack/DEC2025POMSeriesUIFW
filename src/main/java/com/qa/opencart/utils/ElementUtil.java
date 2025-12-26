package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.ElementException;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.LoginPage;

import io.qameta.allure.Step;

public class ElementUtil {
	private Actions act;
	private WebDriver driver;
	private JavaScriptUtil jsutil;
	
	private static final Logger log = LogManager.getLogger(ElementUtil.class);

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsutil = new JavaScriptUtil(driver);
	}

	@Step("Clicking on element using locator: {0}")
	public void doClick(By locator) {
		log.info("Clicking on element using locator"+ locator);
		getElement(locator).click();
	}

	public String doElementGetText(By locator) {
		return getElement(locator).getText();
	}

	@Step("Entering values: {1} in field for locator: {0}")
	public void doSendKeys(By locator, String value) {
		log.info("entering the value : " + value + " into locator: " + locator);
		if (value == null) {
			log.error("value : " + value + " is null...");
			throw new ElementException("===value can not be null====");
		}
		WebElement ele = getElement(locator);
		ele.clear();
		ele.sendKeys(value);
	}

	public void doMultipleSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}

	public WebElement getElement(By locator) {
		WebElement element =  driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsutil.flash(element);
		}
		return element;
	}

	@Step("Verifying element is visible on page for locator: {0}")
	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
//			System.out.println("Element is not displayed on the page: " + locator);
			log.info("Element is not displayed on the page: " + locator);
			return false;
		}
	}
	
	@Step("Verifying element is visible on page for WebElement: {0}")
	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
//			System.out.println("Element is not displayed on the page: " + element);
			log.info("Element is not displayed on the page: " + element);
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed on the page: " + locator);
			return false;
		}
	}

	public String getDOMAttributeVal(By locator, String attrName) {
		return getElement(locator).getDomAttribute(attrName);
	}

	public String getDOMPropertyVal(By locator, String propName) {
		return getElement(locator).getDomProperty(propName);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);

		List<String> eleTextList = new ArrayList<String>();// PC= 0, VC= 10

		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public boolean isElementExist(By locator) {
		if (getElementsCount(locator) == 1) {
			System.out.println("Element" + locator + " is present on the page");
			return true;
		} else {
			System.out.println("Element" + locator + " is not present on the page");
			return false;
		}
	}

	public boolean isElementExist(By locator, int expectedEleCount) {
		if (getElementsCount(locator) == expectedEleCount) {
			System.out.println("Element" + locator + " is present on the page " + expectedEleCount + " times");
			return true;
		} else {
			System.out.println("Element" + locator + " is not present on the page " + expectedEleCount + " times");
			return false;
		}
	}

	public void clickElementUsingFEs(By locator, String eleText) {
		List<WebElement> eleList = getElements(locator);
		System.out.println("total number of elements are : " + eleList.size());

		// iterate footer list and click on contact us
		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(eleText)) {
				e.click();
				break;
			}
		}
	}

	public void doSearch(By searchLocator, String searchKey, By suggestionLocator, String suggestionValue)
			throws InterruptedException {
		doSendKeys(searchLocator, searchKey);
		Thread.sleep(4000);
		List<WebElement> suggestionEleList = getElements(suggestionLocator);
		System.out.println("Total suggestions are: " + suggestionEleList.size());

		for (WebElement e : suggestionEleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(suggestionValue)) {
				e.click();
				break;
			}
		}
	}

//	**************************SelectBasedDropDown utility section******************************************************

	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
	}

	public int getDropDownOptionsCount(By locator) {
		Select select = new Select(getElement(locator));
		return select.getOptions().size();
	}

	public List<String> getDropDownValuesList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println("Total options in drop-down are " + optionsList.size());
		List<String> optionsValueList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
//			System.out.println(text);
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	/**
	 * This method is used to select the value from drop-down without using select
	 * class methods
	 * 
	 * @param locator
	 * @param value
	 */
	public void selectDropDownValue(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);
		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.contains(value)) {
				e.click();
				break;
			}
		}
	}

	public String getFirstSelectedOption(By locator) {
		Select select = new Select(getElement(locator));
		return select.getFirstSelectedOption().getText();
	}

//	**************************Actions Class utility section******************************************************

	private void moveToElement(By locator) {
		act.moveToElement(getElement(locator)).perform();
	}

	public void menuSubMenuHandlingLevel2(By parentMenu, By childMenu) throws InterruptedException {
		moveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(childMenu);
	}

	public void menuSubMenuHandlingLevel3(By menuLevel1, By menuLevel2, By menuLevel3) throws InterruptedException {
		moveToElement(menuLevel1);
		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		doClick(menuLevel3);
	}

	public void menuSubMenuHandlingLevel4(By menuLevel1, By menuLevel2, By menuLevel3, By menuLevel4)
			throws InterruptedException {
		doClick(menuLevel1);
		Thread.sleep(2000);
		moveToElement(menuLevel2);
		Thread.sleep(2000);
		moveToElement(menuLevel3);
		Thread.sleep(2000);
		doClick(menuLevel4);
	}

	public void customDragAndDrop(By source, By target) {
		act.clickAndHold(getElement(source)).moveToElement(getElement(target)).release().build().perform();
	}

	public void actionsDragDrop(By source, By target) {
		act.dragAndDrop(getElement(source), getElement(target)).build().perform();
	}

	public void actionsPartialScrollDown() {
		act.sendKeys(Keys.PAGE_DOWN).perform();
	}

	public void actionsPartialScrollUp() {
		act.sendKeys(Keys.PAGE_UP).perform();
	}

	public void actionsFullScrollDown() {
		act.sendKeys(Keys.COMMAND).sendKeys(Keys.END).perform();
	}

	public void actionsFullScrollUp() {
		act.sendKeys(Keys.COMMAND).sendKeys(Keys.HOME).perform();
	}

	public void actionsScrollToElementAndClick(By scrollEle, By targetEle) {
		act.scrollToElement(getElement(scrollEle)).click(getElement(targetEle)).perform();
	}

	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doSendKeysWithPause(By locator, String value, long pauseTime) {
		char[] val = value.toCharArray();

		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}
	}

	public void doSendKeysWithPause(By locator, String value) {
		char[] val = value.toCharArray();

		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(200).perform();
		}
	}

//	********************************Wait utility section***********************

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timout
	 * @return the WebElement once it is located.
	 */
	public WebElement waitForElementPresence(By locator, int timout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timout));
		WebElement element =  wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsutil.flash(element);
		}
		return element;
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timout
	 * @return WebElement once it is located and visible
	 */
	
	@Step("waiting for element :{0} visible within the timeout: {1}")
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		if(Boolean.parseBoolean(DriverFactory.highlightEle)) {
			jsutil.flash(element);
		}
		return element;
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickElementWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertTextWithWait(int timeout) {
		return waitForAlert(timeout).getText();
	}

	public void acceptAlertWithWait(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlertWithWait(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public void sendKeysInJSPrompt(String value, int timeout) {
		waitForAlert(timeout).sendKeys(value);
	}

	public String waitForTitleContains(String fractionTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
		} catch (TimeoutException e) {
			System.out.println("Expected title value :" + fractionTitle + " is not present");
		}
		return driver.getTitle();
	}
	
    @Step("Waiting for title: {0} for timeout of {1}")
	public String waitForTitleIs(String expectedTitle, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.titleIs(expectedTitle));
		} catch (TimeoutException e) {
//			System.out.println("Expected title value :" + expectedTitle + " is not present");
			log.info("Expected title value :" + expectedTitle + " is not present");
		}
		return driver.getTitle();
	}
    
    @Step("Waiting for the fractionURL: {0} for total timeout of {1} ")
	public String waitForURLContains(String fractionURL, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));
		} catch (TimeoutException e) {
//			System.out.println("Expected title value :" + fractionURL + " is not present");
			log.info("Expected title value :" + fractionURL + " is not present");
		}
		return driver.getCurrentUrl();
	}

    @Step("Waiting for the URL: {0} for total timeout of {1}")
	public String waitForURLIs(String expectedURL, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.urlToBe(expectedURL));
		} catch (TimeoutException e) {
			System.out.println("Expected title value :" + expectedURL + " is not present");
		}
		return driver.getCurrentUrl();
	}

	public Boolean waitForWindow(int expectedNumberOfWindows, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindows));
	}

	public boolean waitForFrame(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
			return true;
		} catch (TimeoutException e) {
			System.out.println("Frame is not present on the page");
			return false;
		}
	}

	public boolean waitForFrame(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
			return true;
		} catch (TimeoutException e) {
			System.out.println("Frame is not present on the page");
			return false;
		}
	}

	public boolean waitForFrame(String nameOrID, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrID));
			return true;
		} catch (TimeoutException e) {
			System.out.println("Frame is not present on the page");
			return false;
		}

	}

	// ******************FluentWait Utils************//

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(java.util.NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=====ELEMENT NOT VISIBLE ON THE PAGE====");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(java.util.NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("=====ELEMENT NOT PRESENT ON THE PAGE====");
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public void waitForFrameWithFluentWait(By frameLocator, int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoSuchFrameException.class)
				.withMessage("=====FRAME NOT VISIBLE ON THE PAGE====");

		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public Alert waitForAlertWithFluentWait(int timeout, int pollingtime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingtime)).ignoring(NoAlertPresentException.class)
				.withMessage("=====Alert NOT VISIBLE ON THE PAGE====");

		return wait.until(ExpectedConditions.alertIsPresent());
	}

}
