package com.ctliselenium.framework.datadriven;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.SkipException;

import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.Utility;
import com.ctliselenium.framework.datadriven.util.Xls_Reader;

public class TestBase {
	public static Properties prop;
	// public static Logger APPLICATION_LOG =Logger.getLogger("devpinoyLogger");

	public Logger APPLICATION_LOG = null;

	public void initLogs(Class<?> class1) {

		FileAppender appender = new FileAppender();
		// configure the appender here, with file location, etc
		appender.setFile(System.getProperty("user.dir") + "//target//reports//" + CustomListener.resultFolderName + "//"
				+ class1.getName() + ".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();

		APPLICATION_LOG = Logger.getLogger(class1);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
	}

	public WebDriver driver;

	public static void init() {
		if (prop == null) {
			String path = System.getProperty("user.dir") + "\\src\\test\\resources\\project.properties";
			prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void validateRunmodes(String testName, String suiteName, String dataRunmode) {

		APPLICATION_LOG.debug("validating runmode for" + testName + "in suite" + suiteName);

		init();

		boolean suiteRunmode = Utility.isSuiteRunnable(suiteName,
				new Xls_Reader(System.getProperty("user.dir") + prop.getProperty("xlsfileLocation") + "Suite.xlsx"));
		boolean testRunmode = Utility.isTestCaseRunnable(testName, new Xls_Reader(
				System.getProperty("user.dir") + prop.getProperty("xlsfileLocation") + suiteName + ".xlsx"));
		boolean dataSetRunmode = false;
		if (dataRunmode.equals(Constants.RUNMODE_YES)) {
			dataSetRunmode = true;
		}
		if (!(suiteRunmode && testRunmode && dataSetRunmode)) {
			APPLICATION_LOG.debug("skipping the test=" + testName + "inside the suite=" + suiteName);
			throw new SkipException("skipping the test=" + testName + "inside the suite=" + suiteName);
		}
	}

	/****************
	 * Generic functions
	 *******************************/

	public void openBrowser(String browserName) {
		if (browserName.equals("Mozilla"))
			driver = new FirefoxDriver();

		else if (browserName.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverexe"));
			driver = new ChromeDriver();
		}

		/*
		 * DesiredCapabilities cap=new DesiredCapabilities();
		 * if(browserName.equals("Mozilla")) { cap.setBrowserName("firefox");
		 * }else if (browserName.equals("Chrome")) {
		 * cap.setBrowserName("Chrome"); } cap.setPlatform(Platform.ANY);
		 * 
		 * try { driver=new RemoteWebDriver(new
		 * URL("http://localhost:4444/wd/hub"),cap); } catch
		 * (MalformedURLException e) {
		 * 
		 * e.printStackTrace(); }
		 */

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	public void navigate(String URLKey) {
		driver.get(prop.getProperty(URLKey));
	}

	public void click(String identifier) {
		try {
			if (identifier.endsWith("_xpath")) {
				driver.findElement(By.xpath(prop.getProperty(identifier))).click();
			} else if (identifier.endsWith("_id")) {
				driver.findElement(By.id(prop.getProperty(identifier))).click();
			} else if (identifier.endsWith("_name")) {
				driver.findElement(By.name(prop.getProperty(identifier))).click();
			}
		} catch (NoSuchElementException e) {
			Assert.fail("Element not found" + identifier );
		}

	}

	public void input(String identifier, String data) {
		if (identifier.endsWith("_xpath")) {
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
		} else if (identifier.endsWith("_id")) {
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
		} else if (identifier.endsWith("_name")) {
			driver.findElement(By.name(prop.getProperty(identifier))).sendKeys(data);
		}
	}

	// hovering mouse on web element
	public void mouseOver(String identifier, WebDriver driver) {

		WebElement we = null;

		if (identifier.endsWith("_xpath")) {
			we = driver.findElement(By.xpath(prop.getProperty(identifier)));
		} else if (identifier.endsWith("_id")) {
			we = driver.findElement(By.id(prop.getProperty(identifier)));
		} else if (identifier.endsWith("_name")) {
			we = driver.findElement(By.name(prop.getProperty(identifier)));
		}

		Actions action = new Actions(driver);
		action.moveToElement(we).build().perform();

	}

	public boolean verifyTitle(String expectedTitleKey) {
		String expectedTitle = prop.getProperty(expectedTitleKey);

		String actualTitle = driver.getTitle();

		if (actualTitle.equals(expectedTitle))
			return true;
		else
			return false;

	}

	public boolean isElementPresent(String identifier) {

		int size = 0;
		if (identifier.endsWith("_xpath")) {
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		}

		else if (identifier.endsWith("_id")) {
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();

		} else if (identifier.endsWith("_name")) {
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();

		}

		else {
			size = driver.findElements(By.xpath(identifier)).size();

		}

		if (size > 0)
			return true;
		else
			return false;
	}

	// to set the wait time
	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchToFrame(String id) {

		driver.switchTo().frame(id);

	}
	
	
	public void selectFromDropdown(String id, String value)
	{
		Select s =new Select(driver.findElement(By.id(id)));
		s.selectByVisibleText(value);
		
	}
	

	public void quit() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public String getText(String identifier) {

		String text = "";
		if (identifier.endsWith("_xpath")) {
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		} else if (identifier.endsWith("_id")) {
			text = driver.findElement(By.id(prop.getProperty(identifier))).getText();
		} else if (identifier.endsWith("_name")) {
			text = driver.findElement(By.name(prop.getProperty(identifier))).getText();
		}
		return text;
	}

	/****** Application specific ****************/

	public void doLogin(String browser, String userName, String password) {

		openBrowser(browser);

		navigate("testSiteUrl");

		input("loginUserName_xpath", userName);

		input("loginPassword_xpath", password);

		click("loginButton_xpath");

	}

	public void doDefaultLogin(String browser) {

		doLogin(browser, prop.getProperty("defaultUsername"), prop.getProperty("defaultpassword"));

	}

}
