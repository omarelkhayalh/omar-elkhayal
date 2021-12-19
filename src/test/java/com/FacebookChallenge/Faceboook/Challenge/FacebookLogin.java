package com.FacebookChallenge.Faceboook.Challenge;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FacebookLogin {

	ChromeDriver webDriver;

	@DataProvider(name = "ExcelData")
	public  Object[][] userData() throws IOException
	{
		ExcelFileReader ER = new ExcelFileReader();
		return ER.getExcelData();
	}
	@BeforeTest
	public void openURL() {

		String chromePath= System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
		String facebookUrl="https:\\www.facebook.com";
		System.setProperty("webdriver.chrome.driver",chromePath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		webDriver = new ChromeDriver(options);
		webDriver.navigate().to(facebookUrl);
		webDriver.manage().window().maximize();


		WebDriverWait wait = new WebDriverWait(webDriver, 20);
		wait.until(ExpectedConditions.titleContains("Facebook"));
	}
	@Test
	public void loginElementsExist() {
		Boolean emailExists = webDriver.findElement(By.id("email")).isDisplayed();
		Assert.assertTrue(emailExists);

		Boolean pswdExist = webDriver.findElement(By.id("pass")).isDisplayed();
		Assert.assertTrue(pswdExist);

		Boolean loginButton = webDriver.findElement(By.name("login")).isDisplayed();
		Assert.assertTrue(loginButton);
	}

	@Test(dataProvider="ExcelData", dependsOnMethods = {"loginElementsExist"})
	public void UserLogin(String email, String Password)
	{

		webDriver.findElement(By.id("email")).sendKeys(email);
		webDriver.findElement(By.id("pass")).sendKeys(Password);
		webDriver.findElement(By.name("login")).click();
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.cssSelector(".bp9cbjyn.j83agx80.datstx6m.taijpn5t.oi9244e8.d74ut37n.dt6l4hlj.aferqb4h.q5xnexhs"))));
		WebElement element = webDriver.findElement(By.cssSelector(".bp9cbjyn.j83agx80.datstx6m.taijpn5t.oi9244e8.d74ut37n.dt6l4hlj.aferqb4h.q5xnexhs"));
		Assert.assertTrue(element.isDisplayed());
		closeUrl();
		openURL();
	}
	@Test
	public void UserFailedLogin()
	{
		webDriver.findElement(By.name("login")).click();		
		WebDriverWait wait = new WebDriverWait(webDriver, 30);
		wait.until(ExpectedConditions.visibilityOf(webDriver.findElement(By.cssSelector("._9ay7"))));
		WebElement element = webDriver.findElement(By.cssSelector("._9ay7"));
		Assert.assertTrue(element.isDisplayed());

	}
	@AfterTest
	public void closeUrl()
	{
		webDriver.close();
	}

}
