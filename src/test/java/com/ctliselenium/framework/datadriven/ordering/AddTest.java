package com.ctliselenium.framework.datadriven.ordering;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class AddTest extends TestBase {

	@BeforeTest
	public void initLogs() {
		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "OrderingDataProvider")
	public void AddTest(Hashtable<String, String> table) {
		validateRunmodes("AddTest", Constants.FIRST_SUITE,
				table.get(Constants.RUNMODE_COL));

		doDefaultLogin(table.get(Constants.BROWSER_COL));
		wait(10000);
		// click("gotoControlCenter_xpath");
		// click("close_xpath");
		mouseOver("ordersTab_xpath", driver);
		wait(5000);
		click("addlink_xpath");

		// Verify Title
		Assert.assertTrue(verifyTitle("addPageTitle"),
				"Titles do not match of Addpage");

		switchToFrame("_48_INSTANCE_ZSN4kDufaWYE_iframe");

		selectFromDropdown("changeOrderSelection", "Private Line");

		waitTillInvisible("loadimage_xpath", driver, 60);

		input("contactName_xpath", table.get("Name"));
		input("contactPhone_xpath", table.get("Phone"));
		input("contactEmail_xpath", table.get("Email"));
		input("accountNumber_xpath", table.get("AccountNumber"));
		click("calendar_xpath");
		waitTillInvisible("loadimage_xpath", driver, 60);

		click("single_xpath");

		// code to select date from calendar

		click("calendar_xpath");

		String date = table.get("PurchaseDate");
		System.out.println("The date is "+date);

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

		// driver.findElement(By.xpath("//td[text()='" + day + "']")).click();

		driver.findElement(By.xpath("//*[contains(@id,'" + day + "')]"))
				.click();

		wait(2000);

		quit();

	}
}