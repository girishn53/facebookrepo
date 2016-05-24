package com.ctliselenium.framework.datadriven.ordering;

import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class LeastPerAssetTest extends TestBase {
	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}
	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "OrderingDataProvider")
	public void LeastPerAssetTest(Hashtable<String, String> table) {
		APPLICATION_LOG.debug("Executing test2");

		// it checks the runmodes of a test at all levels like suite , data
		validateRunmodes("LeastPerAssetTest", Constants.FIRST_SUITE, table.get(Constants.RUNMODE_COL));

		doDefaultLogin(table.get(Constants.BROWSER_COL));

		String leastpertext = getText("leastPerAsset_xpath");
		System.out.println(leastpertext);
		String temp[] = leastpertext.split("\\(");
		String compName = temp[0].trim();
		String percentageChange = temp[1].split("\\)")[0].split("%")[0];
		Assert.assertTrue(isElementPresent("//a[text()='"+compName+"']"), "least per asset company name is not present in table"+compName);
		Assert.assertTrue(isElementPresent("//td/span[text()='"+percentageChange+"']"), "percentage change is not present in table"+percentageChange);
		
}
	
	
}