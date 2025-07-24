package testCases;
import Utilities.A11y.LogUtilities;
import actionDriver.Action;
import base.BaseClass;
import org.testng.annotations.*;
import pages.IndexPage;


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
		AccessibilityReportGenerator.generateReport(driver, AccessibilityReportGenerator.WCAGLevel.WCAG21AAA);
		accountCreationPage.pageHeadingIsDisplayed();
	}
}
