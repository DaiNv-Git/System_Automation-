package com.itlearn.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserFactory {

	public static WebDriver startApplication(WebDriver driver, String browserName, String appUrl) {

		if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("We do not support this browser: " + browserName);
			return null;
		}

		driver.manage().window().maximize();
		driver.get(appUrl);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}

	public static void quitBrowser(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}
}
