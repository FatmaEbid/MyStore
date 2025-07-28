package base;

import actionDriver.Action;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
import pages.*;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class BaseClass {
	public static Properties properties;
	public static WebDriver driver;
	public static IndexPage indexPage;
	public static LoginPage loginPage;
	public static HomePage myAccountPage;
	public static AccountCreationPage accountCreationPage;
	public static SearchResultPage searchResultPage;
	public static WebDriverWait wait;
	public static Logger logger;
	
	@BeforeClass
	public void loadConfig() throws  IOException {
		try(FileInputStream file = new FileInputStream
				("src/Configration/config.properties")){

			properties = new Properties();
			properties.load(file);
			System.out.println("driver:"+ driver);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}


	}
	@BeforeMethod
	public void setup(){
		lunchBrowser();
	}
	public void lunchBrowser() {
		try{
			String browser = properties.getProperty("browser");
			if(browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			}else if(browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			}else if(browser.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			}
			Action.implicitWait(driver,15);
			driver.get(properties.getProperty("url"));
			driver.manage().window().maximize();

		}catch(Exception e){
			System.out.println("Check your code: "+ e.getMessage());
		}
		accountCreationPage = new AccountCreationPage(driver);
		indexPage = new IndexPage(driver);
		loginPage = new LoginPage(driver);
		myAccountPage = new HomePage(driver);
		searchResultPage = new SearchResultPage(driver);
		try{
			PropertyConfigurator.configure("src/test/resources/log4j2.xml");

		}catch (Exception e){
			logger.error("failed truncating table `{}`");
		}
	}
	@AfterMethod
	public void teardownBrowser() {

		if (driver != null) {
			driver.quit();
		}
		//driver.quit();
	}

}
