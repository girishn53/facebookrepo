package com.ctliselenium.framework.datadriven.suiteC;

import java.util.Hashtable;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class Test1 extends TestBase {

	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "SuiteCDataProvider")
	public void test1(Hashtable<String, String> table) {
		// System.out.print("the runmode of test1");
		// System.out.println(table.get("Runmode"));
		validateRunmodes("test1", Constants.THIRD_SUITE, table.get(Constants.RUNMODE_COL));

		// test starts
		doDefaultLogin(table.get(Constants.BROWSER_COL));
		quit();

	}
}
