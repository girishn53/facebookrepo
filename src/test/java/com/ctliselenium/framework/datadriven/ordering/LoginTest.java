package com.ctliselenium.framework.datadriven.ordering;

import java.util.Hashtable;

import org.apache.log4j.TTCCLayout;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.ErrorUtil;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;
import com.ctliselenium.framework.datadriven.util.Utility;
import com.ctliselenium.framework.datadriven.util.Xls_Reader;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;

public class LoginTest extends TestBase {

	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "OrderingDataProvider")
	public void loginTest(Hashtable<String, String> table) {

		// it checks the runmodes of a test at all levels like suite , testcases
		// and data
		validateRunmodes("loginTest", Constants.FIRST_SUITE, table.get(Constants.RUNMODE_COL));

		doLogin(table.get(Constants.BROWSER_COL), table.get(Constants.USERNAME_COL), table.get(Constants.PASSWORD_COL));
		try{
		Assert.assertTrue(isElementPresent("welcomemessage_xpath"), "Login not successful");
		}
		
		catch(Throwable e)
		{
			
			
			ErrorUtil.addVerificationFailure(e);
			//System.out.println("In catch block");
			Assert.fail("Login not successful");
		}
	
	}

	@AfterMethod
	public void closeBroser() {
		quit();
	}

}
