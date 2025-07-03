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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;
public class SameIdUnique {
	
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
		driver.close();
		driver.findElementByAccessibilityId("yesButton").click();
//		setUp();
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
		/*
		List<WebElement> menuItems = driver.findElements(By.className("TextBlock"));
		for (WebElement item : menuItems) {
		    String itemText = item.getText().trim();
		    if (itemText.equals("Admissiom Management")) {
		        item.click();
		        break;
		    }
		}
		*/
		driver.findElement(By.xpath("//Text[@Name='Admissiom Management']")).click();
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
		
		driver.findElementByAccessibilityId("Course").click();		
		
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Set<String> allWindowHandle = driver.getWindowHandles();
		for (String handle : allWindowHandle) {
		    driver.switchTo().window(handle);
		    if (driver.getPageSource().contains("Course")) {
		    	driver.manage().window().maximize();
		        break;
		    }
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElementByName("Show drop-down").click();
		driver.findElementByName("C003").click();
		//driver.findElementByAccessibilityId("13a6b32e-b8ec-45f0-aabf-ede03466432f").click();
		driver.findElementByName("Name").click();		
		new Actions(driver).sendKeys("IOT").perform();
		//driver.findElementByName("13a6b32e-b8ec-45f0-aabf-ede03466432f").sendKeys("IOT");
		//Actions actions = new Actions(driver);
		//actions.sendKeys("IOT").perform();
		driver.findElementByName("Total Number of Subjects").click();		
		new Actions(driver).sendKeys("10").perform();
		driver.findElementByName("Total Credit").click();		
		new Actions(driver).sendKeys("800").perform();
		//driver.findElementByAccessibilityId("cede2fbf-3097-4e6c-bbb1-7566b3f0e6e3").sendKeys("10");
		//driver.findElementByAccessibilityId("998481ed-362a-4712-9d00-c0e31608c93c").sendKeys("800");
		
		driver.findElement(By.xpath("//Text[@Name='Save and Close']")).click();
		/*
		List<WebElement> toggleButtons = driver.findElements(By.className("TextBlock"));
		for (WebElement toggle : toggleButtons) {
		    String name = toggle.getAttribute("Name");
		    if (name != null && name.trim().equals("Save and Close")) {
		        toggle.click();
		        break;
		    }
		}
		*/
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String errorText = driver.findElement(By.xpath("//*[contains(@Name, 'The value:')]")).getAttribute("Name");
		System.out.println("Popup Message: " + errorText);
		Assert.assertTrue(errorText.contains("already in use"), "Error message validation failed!");
		
		driver.findElementByAccessibilityId("okButton").click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebElement closeBtn = driver.findElement(By.xpath("//*[contains(@Name,'Close')]"));
        closeBtn.click(); 
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElementByAccessibilityId("yesButton").click();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains("OnBase")) {
                break;
            }
        }
		//driver.close();
		//driver.findElementByAccessibilityId("yesButton").click();
		//driver.findElement(By.name("Save and Close")).click();
		
		///////////////////// Select and click on a particular filter ////////////////////////
		//driver.findElementByAccessibilityId("TextBlock").click();
		
	}
}
