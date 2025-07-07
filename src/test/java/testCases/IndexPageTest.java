package testCases;

import Utilities.A11y.AccessibilityUtil;
import base.BaseClass;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.IndexPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class IndexPageTest extends BaseClass {


	@Test
	@Link(name = "Index Page", url = "https://www.indexpage.com")
	@Description("Verify the logo on the Index Page")
	@DisplayName("Verify Index Page Logo")
	@Epic("Index Page Logo Epic")
	@Story("Index Page Logo Story")
	@Severity(SeverityLevel.NORMAL)
	@Owner("Fatma")
	public void verifyLogoPage(){
		boolean logo = indexPage.verifyLogo();
		assertTrue(logo);
	}
	@Test
	@Description("Welcome to the Index Page")
	@DisplayName("Helllo Index Page")
	@Epic("Index Page Epic")
	@Story("Index Page Story")
	@Severity(SeverityLevel.CRITICAL)
	@Owner("Fatma")
	@Link(name = "Index Page", url = "https://www.indexpage.com")

	public void verifyTitlePage(){
		//Allure.getLifecycle().updateTestCase(testResult -> testResult.setName("Verify the title page ***** Allure Report"));
		String actualTitle = indexPage.getPageTitle();

AccessibilityUtil.HtmlCsRunner.analyzePage(driver,"a11yReport/htmlReport.html", AccessibilityUtil.WCAGLevel.AA);
		//assertTrue(driver.getCurrentUrl().contains(actualTitle));
		assertEquals(actualTitle,properties.getProperty("pageTitle"));

	}



}
