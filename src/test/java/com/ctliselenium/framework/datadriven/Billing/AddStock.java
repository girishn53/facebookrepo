package com.ctliselenium.framework.datadriven.Billing;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.ErrorUtil;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;
import com.ctliselenium.framework.datadriven.util.Utility;
import com.ctliselenium.framework.datadriven.util.Xls_Reader;

public class AddStock extends TestBase {
	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "BillingDataProvider")
	public void addStock(Hashtable<String, String> table) throws IOException {
		validateRunmodes("addStock", Constants.SECOND_SUITE, table.get(Constants.RUNMODE_COL));
		doDefaultLogin(table.get(Constants.BROWSER_COL));
		try {

			Assert.assertTrue(verifyTitle("loginPageTitle"), "Titles didn't match"); //

		} catch (Throwable t) {
			ErrorUtil.addVerificationFailure(t);
		}

		System.out.println("Inside add stock ");
		click("addStock_xpath");
		// driver.findElement(By.xpath(prop.getProperty("addStock_xpath"))).sendKeys(Keys.ENTER);
		input("stockName_xpath", table.get("Stock Name"));

		click("calendar_xpath");

		String date = table.get("PurchaseDate");

		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateToBeSelected = null;
		try {
			dateToBeSelected = formatter.parse(date);

		} catch (ParseException e) {

			e.printStackTrace();
		}
		String month = new SimpleDateFormat("MMMM").format(dateToBeSelected);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateToBeSelected);
		int year = cal.get(Calendar.YEAR);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String month_yearExpected = month + " " + year;
		while (true) {
			String month_yearDisplayed = getText("monthAndYearText_xpath");
			if (month_yearDisplayed.equals(month_yearExpected))
				break;

			if (currentDate.after(dateToBeSelected))
				click("calBack_xpath");
			else
				click("calFront_xpath");
		}

		driver.findElement(By.xpath("//td[text()='" + day + "']")).click();
		input("quantity_xpath", table.get("Quantity"));
		input("price_xpath", table.get("Price"));
		
	}

	
}
