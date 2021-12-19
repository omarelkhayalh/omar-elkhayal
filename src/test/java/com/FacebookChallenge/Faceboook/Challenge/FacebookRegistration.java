package com.FacebookChallenge.Faceboook.Challenge;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FacebookRegistration {

	ChromeDriver webDriver;

	@DataProvider(name = "ExcelDataRegistration")
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
	public void mainElementsExist() {
		Boolean emailExists = webDriver.findElement(By.id("email")).isDisplayed();
		Assert.assertTrue(emailExists);

		Boolean pswdExist = webDriver.findElement(By.id("pass")).isDisplayed();
		Assert.assertTrue(pswdExist);

		Boolean loginButton = webDriver.findElement(By.name("login")).isDisplayed();
		Assert.assertTrue(loginButton);

	}

	@Test
	public void registerElementsExist() {

		webDriver.findElement(By.xpath ("//*[contains(text(),'Create New Account')]")).click();

		//WebDriverWait wait = new WebDriverWait(webDriver,30);
		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		Boolean firstName = webDriver.findElement(By.name("firstname")).isDisplayed();
		Assert.assertTrue(firstName);

		Boolean lastName = webDriver.findElement(By.name("lastname")).isDisplayed();
		Assert.assertTrue(lastName);

		Boolean email = webDriver.findElement(By.name("reg_email__")).isDisplayed();
		Assert.assertTrue(email);

		Boolean password = webDriver.findElement(By.id("password_step_input")).isDisplayed();
		Assert.assertTrue(password);

		Boolean dob = webDriver.findElement(By.id("u_0_t_1j")).isDisplayed();
		Assert.assertTrue(dob);

		Boolean gender = webDriver.findElement(By.id("u_0_w_hU")).isDisplayed();
		Assert.assertTrue(gender);

		Boolean createAccountButton = webDriver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div/div[1]/form/div[1]/div[10]/button")).isDisplayed();
		Assert.assertTrue(createAccountButton);
	}

	@Test(dataProvider="ExcelDataRegistration", dependsOnMethods = {"mainElementsExist"})
	public void userSignup(String firstname, String lastname, String email, String password, String day, String month, String year, String gender, String pronoun)
	{


		webDriver.findElement(By.name("firstname")).sendKeys(firstname);
		webDriver.findElement(By.name("lastname")).sendKeys(lastname);
		webDriver.findElement(By.name("reg_email__")).sendKeys(email);
		webDriver.findElement(By.name("password_step_input")).sendKeys(password);
		webDriver.findElement(By.name("birthday_day")).sendKeys(day);
		webDriver.findElement(By.name("birthday_month")).sendKeys(month);
		webDriver.findElement(By.name("birthday_year")).sendKeys(year);
		switch (gender) {
		case "Male":
			webDriver.findElement(By.id("u_n_3_ri")).click();
			break;
		case "Female":
			webDriver.findElement(By.id("u_n_2_B+")).click();
			break;
		case "Custom":
			webDriver.findElement(By.id("u_n_4_Dq")).click();
			switch(pronoun) {
			case "He: \"Wish him a happy birthday!\"":
				WebElement optionlist = webDriver.findElement(By.id("js_429"));
				Select  selectoption = new Select(optionlist);
				Assert.assertFalse(selectoption.isMultiple());
				Assert.assertEquals(3, selectoption.getOptions().size());
				selectoption.selectByVisibleText("He: \"Wish him a happy birthday!\"");
				break;
				case "She: \"Wish her a happy birthday!\"":
					 optionlist = webDriver.findElement(By.id("js_429"));
					  selectoption = new Select(optionlist);
					Assert.assertFalse(selectoption.isMultiple());
					Assert.assertEquals(3, selectoption.getOptions().size());
					selectoption.selectByVisibleText("She: \"Wish her a happy birthday!\"");
					break;
					case "They: \"Wish them a happy birthday!\"":
						 optionlist = webDriver.findElement(By.id("js_429"));
						  selectoption = new Select(optionlist);
						Assert.assertFalse(selectoption.isMultiple());
						Assert.assertEquals(3, selectoption.getOptions().size());
						selectoption.selectByVisibleText("They: \"Wish them a happy birthday!\"");
						break;

			}
			break;

		}
		Boolean element = webDriver.findElement(By.name("reg_email_confirmation__")).isDisplayed();
		if(element)
		{
			webDriver.findElement(By.name("reg_email_confirmation__")).sendKeys(email);
		}
		
		
		webDriver.findElement(By.name("websubmit")).click();	
		closeUrl();
	}

	@AfterTest
	public void closeUrl()
	{
		webDriver.close();
	}

}
