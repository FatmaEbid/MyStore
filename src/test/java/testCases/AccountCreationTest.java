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
 		LogUtilities.info("try");
		//Utilities.A11y.TestListener.info("Verifying Account Creation");
		IndexPage indexPage = new IndexPage(driver);
		indexPage.clickSignInButton();
		String time = Action.getCurrentTime();
		loginPage.creatNewAccount(time+"email@ght.com");
		accountCreationPage.pageHeadingIsDisplayed();
	}
}
