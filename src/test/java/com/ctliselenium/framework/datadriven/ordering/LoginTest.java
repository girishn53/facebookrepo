package com.ctliselenium.framework.datadriven.ordering;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;
import com.ctliselenium.framework.datadriven.util.Utility;
import com.ctliselenium.framework.datadriven.util.Xls_Reader;

public class LoginTest extends TestBase {
     
	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}
	
	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "OrderingDataProvider")
	public void loginTest(Hashtable<String, String> table) {
		APPLICATION_LOG.debug("executing test1");
		// it checks the runmodes of a test at all levels like suite , testcases and data
		validateRunmodes("loginTest", Constants.FIRST_SUITE, table.get(Constants.RUNMODE_COL));
		
		// test case starts from here
		
		doLogin(table.get(Constants.BROWSER_COL), table.get(Constants.USERNAME_COL),table.get( Constants.PASSWORD_COL));
		wait(5000);
		quit();
		
			
		}
		
}
	

	


