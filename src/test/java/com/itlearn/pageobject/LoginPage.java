package com.itlearn.pageobject;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginPage extends BaseTest{

	WebDriver driver;
	//constructor
	public LoginPage(WebDriver lDriver)
	{
		this.driver=lDriver;
		
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath="//*[@id=\"header\"]/div/div/div[2]/a") WebElement loginclick;
	
	@FindBy(xpath="//*[@id=\"authform\"]/div[2]/div[1]/div[2]/input") WebElement uname;
	
	@FindBy(id="login-password") WebElement pass;
	
	@FindBy(xpath="//*[@id=\"authform\"]/div[2]/div[6]/div[2]") WebElement loginButton;

	@FindBy(xpath="//*[@id=\"gk-login-toggle\"]/i") WebElement logoutimage;
	
	@FindBy(xpath="//*[@id=\"login_drop_panel\"]/div/ul/li[3]/a") WebElement logoutclick;



	public void loginToPortal(String username, String password) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		loginclick.click();

		// Switch to the new window or tab
		String originalWindow = driver.getWindowHandle();
		for (String windowHandle : driver.getWindowHandles()) {
			if (!windowHandle.equals(originalWindow)) {
				driver.switchTo().window(windowHandle);
				break;
			}
		}

		// Wait until the username field is visible and ready
		wait.until(ExpectedConditions.visibilityOf(uname));
		uname.sendKeys(username);

		wait.until(ExpectedConditions.visibilityOf(pass));
		pass.sendKeys(password);

		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		loginButton.click();
	}




	public void logout()
	{
		logoutimage.click();
		logoutclick.click();
	}
	public void dashboardportal(String dash) throws IOException
	{
		String actualdash= driver.findElement(By.xpath("//*[@id=\"login-list\"]/li[1]/a")).getText();
		System.out.println(actualdash);
		
		if(actualdash.equals(dash))
			{
				System.out.println("Test Passed");
				
			}
			else
			{
				captureScreenShot(driver,"fetchDashboardText");
			}
		assertEquals(dash, actualdash);
//		String actualdash= "Dashboard";
		
	}
}
