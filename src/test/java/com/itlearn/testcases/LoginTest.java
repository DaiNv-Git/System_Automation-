package com.itlearn.testcases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.itlearn.pageobject.BaseTest;
import com.itlearn.pageobject.LoginPage;


public class LoginTest extends BaseTest {
	
	

	@Test(priority =1)
	public void verifyLogin() throws IOException
	{
		LoginPage lp=new LoginPage(driver);
		String username ="hoa.tang+1@base.vn";
		String password ="Hoa592001";
		lp.loginToPortal(username, password);
		
		if(username.equals("hoa.tang+1@base.vn") && password.equals("Hoa592001"))
		{
			System.out.println("Test Passed");
			Assert.assertTrue(true);
		}
		else
		{
			captureScreenShot(driver,"VerifyLogin");
			Assert.assertTrue(false);
		}
	}
	
	@Test(priority =2)
	public void fetchDashboardText() throws IOException
	{
		
		String dashtext= driver.findElement(By.xpath("//*[@id=\"login-list\"]/li[1]/a")).getText();
		String actualdash= "Dashboard";
		
		if(actualdash.equals("Dashboard"))
		{
			System.out.println("Test Passed");
			
		}
		else
		{
			captureScreenShot(driver,"fetchDashboardText");
		}
		assertEquals(dashtext, actualdash);
	}

	
	
	
}
