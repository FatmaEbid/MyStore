package testCases;

import Utilities.A11y.LogUtilities;
import actionDriver.Action;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.Footer;
import pages.IndexPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTestcases {
	WebDriver driver;
	Properties properties;
	LogUtilities logger = new LogUtilities();
	IndexPage indexPage;
	Footer footerPage;

	@BeforeClass
	public void loadConfig() throws IOException {
		try (FileInputStream file = new FileInputStream
				("src/Configration/config.properties")) {

			properties = new Properties();
			properties.load(file);
			System.out.println("driver:" + driver);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@BeforeMethod
	public void setup() {
		lunchBrowser();
	}

	public void lunchBrowser() {
		LogUtilities.info("Launching browser: " + properties.getProperty("browser"));

		try {
			String browser = properties.getProperty("browser");
			if (browser.equalsIgnoreCase("chrome")) {
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				driver = new EdgeDriver();
			}
			Action.implicitWait(driver, 15);
			driver.get(properties.getProperty("url"));
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
	}

	@AfterMethod
	public void teardownBrowser() {

		if (driver != null) {
			driver.quit();
		}

	}
}
