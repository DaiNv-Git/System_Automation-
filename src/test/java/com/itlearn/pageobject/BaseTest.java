package com.itlearn.pageobject;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.itlearn.utilities.BrowserFactory;
import com.itlearn.utilities.ConfigDataProviders;
import com.itlearn.utilities.ReadExcelFile;

public class BaseTest {

	public WebDriver driver;
	public ConfigDataProviders config;
	public ReadExcelFile excel;
	public static ExtentReports extent;
	public static ExtentTest test;

	@BeforeSuite
	public void setUpSuite() {
		config = new ConfigDataProviders();
		excel = new ReadExcelFile();

		// ✅ Khởi tạo Extent Report
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter("./test-output/ExtentReport.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Test Results");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
	}

	@BeforeClass
	public void setUp() {
		driver = BrowserFactory.startApplication(driver, config.getBrowser(), config.getStagingUrl());
	}

	@AfterClass
	public void tearDown() {
		BrowserFactory.quitBrowser(driver);
	}

	// ✅ Tự động log kết quả từng test vào report
	@AfterMethod
	public void tearDownMethod(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.fail("Test Failed: " + result.getThrowable());
			String screenshotPath = captureScreenShot(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test Passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test Skipped");
		}
	}

	// ✅ Ghi report 1 lần cuối giảm chậm
	@AfterSuite
	public void generateReport() {
		extent.flush();
		// Có thể mở report nếu cần (comment nếu máy yếu)
		try {
			File htmlFile = new File("./test-output/ExtentReport.html");
			java.awt.Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ✅ Hàm chụp màn hình
	public String captureScreenShot(WebDriver driver, String testName) throws IOException {
		TakesScreenshot screenshot = ((TakesScreenshot) driver);
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		String path = "./Screenshots/" + testName + "_" + new Date().getTime() + ".png";
		FileUtils.copyFile(src, new File(path));
		return path;
	}

	// ✅ Khởi tạo test để dùng trong @Test
	@BeforeMethod
	public void startTest(Method method) {
		test = extent.createTest(method.getName());
	}

	// ✅ Ví dụ init Chrome
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
