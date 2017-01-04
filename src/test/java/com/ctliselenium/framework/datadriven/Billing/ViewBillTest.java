package com.ctliselenium.framework.datadriven.Billing;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.ErrorUtil;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class ViewBillTest extends TestBase {
	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "BillingDataProvider")
	public void ViewBillTest(Hashtable<String, String> table) {
		// it checks the runmodes of a test at all levels like suite , data
		validateRunmodes("ViewBillTest", Constants.SECOND_SUITE, table.get(Constants.RUNMODE_COL));

		doDefaultLogin(table.get(Constants.BROWSER_COL));
		wait(10000);
		// click("gotoControlCenter_xpath");
		mouseOver("billingTab_xpath", driver);
		wait(5000);
		click("viewBill_xpath");
		
		waitTillInvisible("loadImage_xpath", driver, 10);
		
		
		switchToFrame("_48_INSTANCE_4lB9JAqqpqKl_iframe");
		
		try {

			Assert.assertTrue(isElementPresent("advancedSearch_xpath"), "Advanced Search Option is not visible");

		}

		catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);
			Assert.fail("Advanced Search Option is not visible");
			

		}

	}

	@AfterTest

	public void closeBrowser() {

		quit();

	}

}