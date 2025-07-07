package testCases;

import actionDriver.Action;
import base.BaseClass;
import org.testng.annotations.*;
import pages.IndexPage;

public class AccountCreationTest extends BaseClass {
	@Test
	public void verifyAccountCreation() {
		IndexPage indexPage = new IndexPage(driver);
		indexPage.clickSignInButton();
		String time = Action.getCurrentTime();
		loginPage.creatNewAccount(time+"email@ght.com");
		accountCreationPage.pageHeadingIsDisplayed();
	}
}
