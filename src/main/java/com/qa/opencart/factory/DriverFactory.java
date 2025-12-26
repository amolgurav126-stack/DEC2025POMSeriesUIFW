package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.error.AppError;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.pages.LoginPage;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	public static String highlightEle;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public OptionsManager optionsManager;
	
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

		
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		
		System.out.println("Browser name is: "+ browserName);
		
		highlightEle =  prop.getProperty("highlight");
		
		optionsManager = new OptionsManager(prop);
		
		switch (browserName) {
		case "chrome":
//			driver = new ChromeDriver();
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOption()));
			break;			
		case "edge":
//			driver = new EdgeDriver();
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOption()));
			break;			
		case "safari":
//			driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;			
		case "firefox":
//			driver = new FirefoxDriver();
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOption()));
			break;
			
		default:
			System.out.println(AppError.INVALID_BROWSER_MESG + ":"+ browserName);
			log.error(AppError.INVALID_BROWSER_MESG + ":"+ browserName);
			throw new FrameworkException("======INVALID BROWSER====");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
	}
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip= null;
		
		String envName = System.getProperty("env");
		log.info("Env name =======>"+ envName);
		try {
		if(envName==null) {
			log.warn("Environment is not passed..Hence running tcs on defualt QA environment");
			ip = new FileInputStream("./src/test/resources/config/config.qa.properties"); // make the connection with properties file
		}
		else {
			switch (envName.trim().toLowerCase()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
				break;
				
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.prod.properties");
				break;
				
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
				break;
				
			case "stage":
				ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
				break;

			default:
				log.error("Env value is invalid...plz pass the right env value..");
				throw new FrameworkException("===INVALID ENVIRONMENT===");
			}
		}
		
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
			try {
				prop.load(ip);   // load all the properties in prop
			} catch (IOException e) {
				e.printStackTrace();
			}		
	 
		return prop;
	}
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/*
	 * TakeScreenshot
	 */
	public static byte[] getScreenshotByte() {
		return  ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES);
	}
	
	public static File getScreenshotFile() {
		return  ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
	}
	
	public static String getScreenshotBase64() {
		return  ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BASE64);
	}

	

}
