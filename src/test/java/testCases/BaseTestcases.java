package testCases;

import Utilities.A11y.AllureReportUtil;
import Utilities.A11y.LogUtilities;
import actionDriver.Action;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import pages.Footer;
import pages.IndexPage;
import pages.ResultPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class BaseTestcases {
	WebDriver driver;
	LogUtilities logger = new LogUtilities();
	IndexPage indexPage;
	Footer footerPage;
	ResultPage resultPage;


	public static Properties loadConfig(String configPath) {
		Properties properties = new Properties();
		try (FileInputStream file = new FileInputStream
				(configPath)) {
//"src/Configration/config.properties"

			properties.load(file);
			//System.out.println("driver:" + driver);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return properties;
	}

	@BeforeSuite
	public void cleanAllureResults() {
		File resultsDir = new File("allure-results");

		if (resultsDir.exists()) {
			for (File file : resultsDir.listFiles()) {
				file.delete();
			}
			resultsDir.delete(); // Optional: delete the folder itself
		}
	}

	@AfterSuite
	public void afterSuite() {
		AllureReportUtil.writeEnvironmentInfo(); // Write environment info to environment.properties
		// Generate and open the Allure report
		AllureReportUtil.generateAndOpenAllureReport("allure-results", "TestOutput/allure-report");
		// will backup the previous report in AllureReportBackup folder
		AllureReportUtil.backupReport("TestOutput/allure-report", "TestOutput/AllureReportBackup");
	}



	@BeforeMethod
	public void setup() {
		loadConfig("src/Configration/config.properties");
		lunchBrowser();
		String allureResultsDir = loadConfig("src/Configration/allure.properties").getProperty("allure.results.directory");
		System.setProperty("allure.results.directory", allureResultsDir);
	}

	public void lunchBrowser() {
		LogUtilities.info("Launching browser: " + loadConfig("src/Configration/config.properties").getProperty("browser"));


		try {
			String browser = loadConfig("src/Configration/config.properties").getProperty("browser");
			String seleniumHubUrl = "http://localhost:4444/wd/hub"; // Docker container URL

			if (browser.equalsIgnoreCase("chrome")) {
				//driver = new ChromeDriver(); //using if you want to run test locally

				// Running tests on Selenium Grid using Docker
				ChromeOptions options = new ChromeOptions();
				driver = new RemoteWebDriver(new URL(seleniumHubUrl), options);

			} else if (browser.equalsIgnoreCase("firefox")) {
				//driver = new FirefoxDriver(); //using if you want to run test locally
				// Running tests on Selenium Grid using Docker

				FirefoxOptions options = new FirefoxOptions();
				driver = new RemoteWebDriver(new URL(seleniumHubUrl), options);

			} else if (browser.equalsIgnoreCase("edge")) {
				//driver = new EdgeDriver(); //using if you want to run test locally
				// Running tests on Selenium Grid using Docker
				EdgeOptions options = new EdgeOptions();
				driver = new RemoteWebDriver(new URL(seleniumHubUrl), options);
			}
			Action.implicitWait(driver, 15);
			driver.get(loadConfig("src/Configration/config.properties").getProperty("url"));
			driver.manage().window().maximize();

		} catch (Exception e) {
			System.out.println("Check your code: " + e.getMessage());
		}
		try {
			PropertyConfigurator.configure("src/test/resources/log4j2.xml");

		} catch (Exception e) {
			LogUtilities.error("failed truncating table `{}`");
		}
		 indexPage= new IndexPage(driver);
		 footerPage = new Footer(driver);
		 resultPage = new ResultPage(driver);
	}

	@AfterMethod
	public void teardownBrowser() {

		if (driver != null) {
			driver.quit();
		}

	}
}
