package testCases;
import Utilities.A11y.AccessUtilities;
import Utilities.A11y.LogUtilities;
import Utilities.A11y.lighthouse.LighthouseUtility;
import Utilities.A11y.resources.AccessibilityReportGenerator;
import org.testng.annotations.*;
import pages.IndexPage;

import javax.swing.text.Utilities;
import java.io.IOException;
import java.util.EnumSet;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class TC_HomePage extends BaseTestcases {


	@Test
	public void verifyAccountCreation() {
 		LogUtilities.info("Info: Starting Account Creation Test");
		//Utilities.A11y.TestListener.info("Verifying Account Creation");
		indexPage.clickSignInButton();
		indexPage.getPageUrl();
		//String time = Action.getCurrentTime();
		//loginPage.creatNewAccount(time+"email@ght.com");

		//accountCreationPage.pageHeadingIsDisplayed();
		LighthouseUtility.runAuditForCurrentUrl(driver);
	}
	@Test
	public void verifyNavOptions(){
		LogUtilities.info("Info: Starting Menu Bar Options Test");
		indexPage.menuBarOptions(IndexPage.menuBArOptions.SIGN_IN);
		assertTrue(indexPage.getPageUrl().contains("my-account")); //"contact" for contact us page
	}
	@Test
	public void verifyFooter(){
		LogUtilities.info("Info: Starting Footer Links Test");
		footerPage.clickOnSpecialMediaButtons();
		footerPage.clickOnFooterLinks();
	}
	@Test
	public void verifyMenuBarOptions() throws IOException {
		LogUtilities.info("Info: Starting Menu Bar Options Verification Test");
		//AccessUtilities.scanPage(driver, EnumSet.of(AccessUtilities.WCAGLevel.AA), "Menu Bar Options Accessibility Report");
		AccessibilityReportGenerator.generateReportFromRaw(  "color-contrast",
				"serious",
				"Element's contrast could not be determined because it uses complex text shadows",
				driver.getCurrentUrl(),
				List.of("a[title='Contact us']"),
				"Chrome (Windows)",
				"WCAG 2.1 AA");
		//LighthouseUtility.runAuditForCurrentUrl(driver);
		assertTrue(indexPage.checkMenuBarOptionsWithLeftMenu());
	}








}
