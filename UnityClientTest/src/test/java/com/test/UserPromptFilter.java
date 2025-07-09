package com.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class UserPromptFilter {
	public static WindowsDriver driver = null;
	public static Process process = null;
		@BeforeClass
		public void setUp() {
			

			String WAPServerPath="C:\\Users\\nghosh\\git\\repository\\UnityClientTest\\src\\test\\resources\\Windows Application Driver\\WinAppDriver.exe";
			ProcessBuilder builder = new ProcessBuilder(WAPServerPath).inheritIO();
			try {
				process = builder.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(5000); // Wait for the server to start
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
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
			process.destroy();
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
			//System.out.println("hello");
			driver.findElement(By.xpath("//Text[@Name='Admissiom Management']")).click();
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
			driver.findElement(By.xpath("//Text[@Name='Student Name']")).click();
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//*[contains(@Name, 'Duplicate entry')]
			driver.findElement(By.xpath("//*[contains(@Name, 'Duplicate entry')]")).sendKeys("abc*");
			driver.findElement(By.xpath("//Button[@Name='Search']")).click();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

}
