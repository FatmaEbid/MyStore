package base;

import actionDriver.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public abstract class BaseClass {
	public static Properties properties;
	public static WebDriver driver;
	public static IndexPage indexPage;
	public static LoginPage loginPage;
	public static MyAccountPage myAccountPage;
	public static AccountCreationPage accountCreationPage;
	public static SearchResultPage searchResultPage;
	
	@BeforeTest
	public void loadConfig() throws FileNotFoundException, IOException {
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
			}else if(browser.equalsIgnoreCase("ie")) {
				driver = new InternetExplorerDriver();
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
		myAccountPage = new MyAccountPage(driver);
		searchResultPage = new SearchResultPage(driver);
	}
	/*@AfterMethod
	public void teardownBrowser() {
		driver.quit();
	}*/

}
