package com.ctliselenium.framework.datadriven.Billing;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.ErrorUtil;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class PaymentOptionsTest extends TestBase {

	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "BillingDataProvider")
	public void PaymentOptionsTest(Hashtable<String, String> table) {
		// it checks the runmodes of a test at all levels like suite , data
		validateRunmodes("PaymentOptionsTest", Constants.SECOND_SUITE,
				table.get(Constants.RUNMODE_COL));

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

			System.out
					.println("goToControlCenter_xpath element not fould and in catch block");

		}

		finally {

			mouseOver("billingTab_xpath", driver);
			wait(5000);
			click("viewBill_xpath");
			wait(1000);
			waitTillInvisible("loadImage_xpath", driver, 10);
			switchToFrame("_48_INSTANCE_4lB9JAqqpqKl_iframe");
			click("makeAPayment_xpath");

			// test for autopay option

			try {
				Assert.assertTrue(isElementPresent("autopay_xpath"),
						"Autopay option is not present");
			} catch (Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				System.out.println("Autopay option is not present");

			}

			// payByCreditcard_xpath

			try {
				Assert.assertTrue(isElementPresent("payByCreditcard_xpath"),
						"pay by credit card option is not present");
			} catch (Throwable t) {
				ErrorUtil.addVerificationFailure(t);
				System.out.println("pay by credit card option is not present");

			}

		}

	}

	@AfterTest

	public void closeBrowser() {

		quit();

	}
	
}
