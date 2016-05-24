package com.ctliselenium.framework.datadriven.util;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.ctliselenium.framework.datadriven.TestBase;

public class TestDataProvider {
	
	
	@DataProvider(name="OrderingDataProvider")
	public static Object[][] getDataSuiteA(Method m)
	{
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+TestBase.prop.getProperty("xlsfileLocation")+Constants.FIRST_SUITE+".xlsx");
		return Utility.getData(m.getName(), xls1);

		
	}
	
	@DataProvider(name="BillingDataProvider")
	public static Object[][] getDataSuiteB(Method m)
	{
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+TestBase.prop.getProperty("xlsfileLocation")+Constants.SECOND_SUITE+".xlsx");
		return Utility.getData(m.getName(), xls1);

		
	}
	
	
	@DataProvider(name="SuiteCDataProvider")
	public static Object[][] getDataSuiteC(Method m)
	{
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(System.getProperty("user.dir")+TestBase.prop.getProperty("xlsfileLocation")+Constants.THIRD_SUITE+".xlsx");
		return Utility.getData(m.getName(), xls1);

		
	}
	
	
	

}
