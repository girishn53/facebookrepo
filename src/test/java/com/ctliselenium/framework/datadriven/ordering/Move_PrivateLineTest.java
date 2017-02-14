package com.ctliselenium.framework.datadriven.ordering;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ctliselenium.framework.datadriven.TestBase;
import com.ctliselenium.framework.datadriven.util.Constants;
import com.ctliselenium.framework.datadriven.util.TestDataProvider;

public class Move_PrivateLineTest extends TestBase {

	@BeforeTest
	public void initLogs() {

		initLogs(this.getClass());
	}

	@Test(dataProviderClass = TestDataProvider.class, dataProvider = "OrderingDataProvider")
	public void MovePrivateLineTest(Hashtable<String, String> table) {
		validateRunmodes("ChangeLongDistanceTest", Constants.FIRST_SUITE,
				table.get(Constants.RUNMODE_COL));

		APPLICATION_LOG.debug("before login: in test move private line test");
		doDefaultLogin(table.get(Constants.BROWSER_COL));
		wait(25000);

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

		} finally {

			wait(10000);

			mouseOver("ordersTab_xpath", driver);

			click("movelink_xpath");

			wait(5000);
			
			
			switchToFrame("_48_INSTANCE_LF5JrZd60dAK_iframe");
			
			wait(2000);

			waitTillInvisible("loadimage_xpath", driver, 10);

			selectFromDropdown("changeServiceType_xpath", "Private Line");

			waitTillInvisible("loadImage_xpath", driver, 10);
			
			
			input("contactName_xpath", table.get("Name"));

			input("accountNumber_xpath", table.get("AccountNumber"));

			input("moveServiceId_xpath", table.get("ServiceId"));
			
			input("move_currentserviceaddress_xpath", table.get("Current Service Address"));
			input("move_Newserviceaddress_xpath", table.get("New Service Address"));
			wait(5000);

			click("calendar_xpath");

			String date = table.get("PurchaseDate");
			// System.out.println("The date is " + date);

			Date currentDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dateToBeSelected = null;
			try {
				dateToBeSelected = formatter.parse(date);

			} catch (java.text.ParseException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			String month = new SimpleDateFormat("MMMM")
					.format(dateToBeSelected);
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

			driver.findElement(By.xpath("//*[contains(@id,'" + day + "')]"))
					.click();

			wait(500);
			click("submitButton_xpath");
			waitTillInvisible("loadImage_xpath", driver, 10);

			
			

			
			
			
			

		}
	}

}
