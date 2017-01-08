package com.ctliselenium.framework.datadriven.Billing;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class CreateBillingTicketTest extends TestBase {

	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "BillingDataProvider")
	public void CreateBillingTicketTest(Hashtable<String, String> table) {
		// it checks the runmodes of a test at all levels like suite , data
		validateRunmodes("CreateBillingTicketTest", Constants.SECOND_SUITE, table.get(Constants.RUNMODE_COL));
		doDefaultLogin(table.get(Constants.BROWSER_COL));

		wait(10000);

		try {

			boolean maintainPopUpisThere = isElementPresent("maintainPopUp_xpath");

			if (maintainPopUpisThere) {
				System.out.println("maintainenance pop up is present");

				click("goToControlCenter_xpath");// if this xpath is not found
													// it will throw an
													// ElementNotVisibleException
													// and test will stop, so I
													// put it in try block

			}

		}

		catch (ElementNotVisibleException e) {

			System.out.println("goToControlCenter_xpath element not fould and in catch block");

		}

		finally {

			// click("gotoControlCenter_xpath");
			mouseOver("billingTab_xpath", driver);
			// wait(5000);

			click("createBillingTicket_xpath");

			wait(2000);

			String accountNumber = table.get("Account # For Billing Ticket");

			waitTillInvisible("loadImage_xpath", driver, 10);
			// driver.findElement(By.xpath("//a[contains(text(),200020575)]")).click();

			// String xpath="//a[contains(text(),200070100)]";

			String xpath = "//*[contains(text(),'" + accountNumber + "')]";

			// System.out.println("xpath is "+xpath);

			driver.findElement(By.xpath(xpath)).click();

			wait(1000);

			waitTillInvisible("loadImage_xpath", driver, 10);

			input("billingTicketTitle_xpath", table.get("Billing ticket title"));

			click("categorizeThisIssue_xpath");

			wait(2000);

			click("billingAdressChange_xpath");
			input("whatIssue_xpath", table.get("What is issue"));

			click("billingTicketSubmit_xpath");

			waitTillInvisible("loadImage_xpath", driver, 10);

			Assert.assertTrue(isElementPresent("billingTicketConfirmationPopUp_xpath"),
					"Billing ticket is not created");

		}

	}

	@AfterTest

	public void closeBrowser() {

		quit();

	}

}
