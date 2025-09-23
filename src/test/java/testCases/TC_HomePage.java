package testCases;
import Utilities.A11y.AllureReportUtil;
import Utilities.A11y.LogUtilities;
import Utilities.LighthouseUtility;
import io.qameta.allure.*;
import io.qameta.allure.testng.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.IndexPage;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.*;

import static java.lang.System.getProperty;
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
	//Method for full page screenshot using AShot
	public byte[] captureFullPageScreenshot(WebDriver driver) throws IOException {
		Screenshot screenshot = new AShot()
				.shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(driver);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(screenshot.getImage(), "PNG", baos);
		return baos.toByteArray();
	}
		@Description("Click on Navigation Bar Options: Contact Us or Sign In, in TestNG")
	@Severity(SeverityLevel.MINOR)
	@Epic("EP001")
	@Owner("Fatma")
	@Tags(value = {@Tag("Regression"), @Tag("Navigation"), @Tag("Sanity")})
	@Link(name = "Yahoo", type = "link",url = "https://www.yahoo.com/")
	@Links(value= {@Link(name = "YouTube", type = "link",url = "https://www.youtube.com/"), @Link(name = "allure", url = "https://google.com")})
	@Test
	@TmsLinks(value = {@TmsLink("TC001"), @TmsLink( "TC002")})
	@Issues(value = {@Issue(value = "BUG-001"), @Issue("BUG-002")})
	@Issue("BUG-001")
	public void verifyNavOptions() throws IOException {
		LogUtilities.info("Info: Starting Menu Bar Options Test");
		AllureReportUtil.addStepWithScreenshot("main Page Screenshot", driver, "Main Page Screenshot");
		indexPage.menuBarOptions(IndexPage.navBarOptions.SIGN_IN);
		AllureReportUtil.addStepWithScreenshot("Sign In Page Screenshot", driver, "Sign In Page Screenshot");
		/*Allure.addAttachment("Page Screenshot", "image/png",
				new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)), "png");
*/
			assertTrue(indexPage.getPageUrl().contains("my-account")); //"contact" for contact us page
	}
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void verifyFooter(){
		LogUtilities.info("Info: Starting Footer Links Test");
		footerPage.clickOnEachMediaButtons();
		footerPage.clickOnFooterLinks();

	}
	@Test
	public void verifyMenuBarOptions() throws IOException {
		//Allure.parameter("Browser", getProperty("browser"));
		LogUtilities.info("Info: Starting Menu Bar Options Verification Test");
		AllureReportUtil.addStepWithScreenshot("Menu Bar Options Page Screenshot", driver, "Menu Bar Options Page Screenshot");
		//AllureReportUtil.attachScreenshot("Menu Bar Options Page Screenshot", driver);
		LighthouseUtility.runAuditForCurrentUrl(driver, LighthouseUtility.LighthouseCategory.ACCESSIBILITY);
		assertTrue(indexPage.checkMenuBarOptionsWithLeftMenu());
		File htmlReport = new File(getProperty("user.dir") + "/PDFFiles/Retest-Pay by Gift Card -SIT.pdf");
		Allure.addAttachment("PDF Report", "application/pdf", new FileInputStream(htmlReport), "pdf");
		//Take screenshot and attach to Allure report
		Allure.addAttachment("Page Screenshot", "image/png",
				new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)), "png");

	}

}
