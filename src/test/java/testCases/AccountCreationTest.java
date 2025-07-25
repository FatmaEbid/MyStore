package testCases;
import Utilities.A11y.LogUtilities;
import Utilities.A11y.lighthouse.LighthouseCategory;
import Utilities.A11y.lighthouse.LighthouseOutputFormat;
import Utilities.A11y.lighthouse.LighthouseUtility;
import actionDriver.Action;
import base.BaseClass;
import org.testng.annotations.*;
import pages.IndexPage;

import java.util.List;
import java.util.Map;


public class AccountCreationTest extends BaseClass {
	@BeforeMethod
	public void setUp() {
		LogUtilities.info("Setting up the test environment");
		//Utilities.A11y.TestListener.info("Setting up the test environment");

	}

	@Test
	public void verifyAccountCreation() {
 		LogUtilities.info("Info: Starting Account Creation Test");
		//Utilities.A11y.TestListener.info("Verifying Account Creation");
		IndexPage indexPage = new IndexPage(driver);
		indexPage.clickSignInButton();
		String time = Action.getCurrentTime();
		loginPage.creatNewAccount(time+"email@ght.com");
		/*LogUtilities.error("Error: Account creation failed");
		LogUtilities.warn("warning: Account creation might not be successful");
		LogUtilities.fatal("fatal: Account creation test failed");*/
		//AccessibilityReportGenerator.generateReport(driver, AccessibilityReportGenerator.WCAGLevel.WCAG21AAA);
		accountCreationPage.pageHeadingIsDisplayed();
	}
	@Test(dependsOnMethods = "verifyAccountCreation")
	public void lighthouseTest(){
		LogUtilities.info("Info:Starting Lighthouse Test");
		// Run Lighthouse with multiple categories and formats
		String url = driver.getCurrentUrl();
		Map<LighthouseOutputFormat, String> reports = LighthouseUtility.runLighthouseAudit(
				url,
				"exampleAccessibilityReport",
				List.of(LighthouseCategory.ACCESSIBILITY, LighthouseCategory.PERFORMANCE),
				List.of(LighthouseOutputFormat.HTML, LighthouseOutputFormat.HTML)
		);
	}
}
