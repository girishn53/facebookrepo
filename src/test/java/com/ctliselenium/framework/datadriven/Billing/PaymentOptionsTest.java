package com.ctliselenium.framework.datadriven.Billing;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
		mouseOver("billingTab_xpath", driver);
		wait(5000);
		click("viewBill_xpath");
		wait(10000);
		
		try {

			Assert.assertTrue(isElementPresent("makeAPayment_xpath"),
					"Make a payment button is not visible");

		} catch (Throwable t) {

			ErrorUtil.addVerificationFailure(t);

		}
		quit();

	}

}
