package com.test;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class UnityClientTest {
	
	public static WindowsDriver driver = null;
	
	@BeforeClass
	public void setUp() {
		DesiredCapabilities cap= new DesiredCapabilities();
		cap.setCapability("app", "C:\\Program Files (x86)\\Hyland\\Unity Client\\obunity.exe");
		cap.setCapability("platformName", "Windows");
		cap.setCapability("deviceName", "WindowsPC");
		
		try {
			driver = new WindowsDriver(new URL("http://127.0.0.1:4723/"),cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	@AfterMethod
	public void cleanUp() {
		driver.quit();
		setUp();
	}
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
	@Test
	public void justOpen() {
		
		//////////////////////only login /////////////////////////////////////
		driver.findElementByAccessibilityId("userNameTextBox").sendKeys("MANAGER");
		driver.findElementByAccessibilityId("passwordPasswordBox").sendKeys("password");
		driver.findElementByAccessibilityId("loginButton").click();
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/////////////////////////// check whether correct application opens or not //////////////////////
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
		    driver.switchTo().window(handle);
		    if (driver.getPageSource().contains("OnBase")) {
		        break;
		    }
		}
		///////////////////////  Navigate to open an application /////////////////////////
		driver.findElementByAccessibilityId("applicationMenu").click();
		driver.findElementByAccessibilityId("applicationsMenuGroup").click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//////////////////// Opens a particular Application //////////////////////////////
		//System.out.println("hello");
		List<WebElement> menuItems = driver.findElements(By.className("TextBlock"));
		for (WebElement item : menuItems) {
		    String itemText = item.getText().trim();
		    if (itemText.equals("Admissiom Management")) {
		        item.click();
		        break;
		    }
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/////////////////// Filter Refresh to enable object creation option  /////////////////
		List<WebElement> images = driver.findElements(By.className("Image"));
		for (WebElement img : images) {
		    
		    if (img.getLocation().getX() > 400 && img.getLocation().getY() > 250) {   // x<471 and y<990
		        img.click();
		        break;
		    }
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		///////////////////// Select and click on a particular filter ////////////////////////
		//driver.findElementByAccessibilityId("TextBlock").click();
		List<WebElement> filters = driver.findElements(By.className("TextBlock"));
		for (WebElement item : filters) {
		    String itemText = item.getText().trim();
		    if (itemText.equals("Student Selection")) {
		        item.click();
		        break;
		    }
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/////////////////// Verify the column header names /////////////////////////////////////
		List<String> expectedHeaders = Arrays.asList("Sid", "Name","Addhar No.","Blood Group","Contact No.");
		List<WebElement> headers = driver.findElements(By.className("TextBlock"));
		List<String> actualHeaders = new ArrayList<>();

		for (WebElement header : headers) {
		    String text = header.getText().trim();
		    if (expectedHeaders.contains(text)) {
		        actualHeaders.add(text);
		        System.out.println("Found header: " + text);
		    }
		}

		if (actualHeaders.containsAll(expectedHeaders)) {
		    System.out.println("All headers are displayed correctly.");
		} else {
		    System.out.println("Missing headers.Expected: "+ expectedHeaders + ",Found: "+actualHeaders);
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		////////////////// Verify whether the rows are correctly displayed or not //////////////////
		List<String> expectedRecords = Arrays.asList("10015", "ABC5","789","O+","");
		List<WebElement> records = driver.findElements(By.className("SimpleTextBlock"));
		List<String> actualRecords = new ArrayList<>();
		boolean empty = true;
		
		/*
		///////for the rows which have all the non empty values in its corresponding fields//////
		for (WebElement record : records) {
		    String text = record.getText().trim();
		    if (!text.isEmpty() && expectedRecords.contains(text) && !actualRecords.contains(text)) {
		        actualRecords.add(text);
		        System.out.println("Found record: " + text);
		    }
		}
	 
		 */
	    ///////for the rows which exactly one empty value in its corresponding fields//////
		for (WebElement record : records) {
		    String text = record.getText().trim();
		    if (expectedRecords.contains(text)) {
		        if (text.isEmpty()) {
		            if (empty) {
		                actualRecords.add(text);
		                System.out.println("Found record: [contact no. field is empty]");
		                empty = false; 
		            }
		        } else if (!actualRecords.contains(text)) {
		            actualRecords.add(text);
		            System.out.println("Found record: " + text);
		        }
		    }
		}

		if (actualRecords.containsAll(expectedRecords)) {
		    System.out.println("All records are displayed correctly.");
		} else {
		    System.out.println("Missing records.Expected: "+ expectedRecords + ",Found: "+actualRecords);
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		///////Opens a particular instance from a record that is shown in the filter records//////
		List<WebElement> filteritems = driver.findElements(By.className("Cell"));
		Actions actions = new Actions(driver);
		for (WebElement item : filteritems) {
		    String itemText = item.getText().trim();
		    if (itemText.equals("ABC5")) {
		    	actions.moveToElement(item).doubleClick().perform();
		        break;
		    }
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Set<String> allWindowHandle = driver.getWindowHandles();
		for (String handle : allWindowHandle) {
		    driver.switchTo().window(handle);
		    if (driver.getPageSource().contains("Student")) {
		        break;
		    }
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/// Verify whether it is correctly opened or not ///////
		String val=driver.findElementByAccessibilityId("9369b217-aa2e-4c9e-8ad2-23c16de43923").getAttribute("Value.Value");
		if ("10015".equals(val)) {
		    System.out.println("SID is correctly displayed as 10015");
		} else {
		    System.out.println("SID mismatch. Expected: 10015, Found: " + val);
		}
		
	}

}
