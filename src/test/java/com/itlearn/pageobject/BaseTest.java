package com.itlearn.pageobject;



import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.itlearn.utilities.BrowserFactory;
import com.itlearn.utilities.ConfigDataProviders;
import com.itlearn.utilities.ReadExcelFile;



public class BaseTest {
	
	public WebDriver driver;
	public ConfigDataProviders config;
	public ReadExcelFile excel;
	
	@BeforeSuite
	public void setUpSuite()
	{
		config=new ConfigDataProviders();
		excel =new ReadExcelFile();
	}
	@BeforeClass
	public void setUp()
	{
		driver=BrowserFactory.startApplication(driver,config.getBrowser(),config.getStagingUrl());
	}
	
 @AfterClass
	public void tearDown()
	{
		BrowserFactory.quitBrowser(driver);
	}
 
 public void captureScreenShot(WebDriver driver,String testName) throws IOException
 {
	 // Convert webdriver object to TakesScreenshot interface
	 TakesScreenshot screenshot= ((TakesScreenshot)driver);
	 
	 // Step 2 :call getScreenshotAs method to capture image file
	 
	 File src= screenshot.getScreenshotAs(OutputType.FILE);
	 File srcpath=new File("."+"//Screenshots//"+ testName + ".png");
	 
	 // Step 3 : copy image file to destination 
	 FileUtils.copyFile(src, srcpath);
	 
 }
 	//choose chorm to test
	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching Chrome browser...");
		System.setProperty("webdriver.chrome.driver", "resource\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		return driver;
	}

	private static WebDriver initEdgeDriver(String appURL) {
		System.out.println("Launching MS Edge browser...");
		System.setProperty("webdriver.edge.driver", "resource\\drivers\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		return driver;
	}
}
