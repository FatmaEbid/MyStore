package testCases;

import actionDriver.Action;
import base.BaseClass;
import org.testng.annotations.Test;

public class AccountCreationTest extends BaseClass {
	@Test
	public void verifyAccountCreation() {
		indexPage.clickSignInButton();
		String time = Action.getCurrentTime();
		loginPage.creatNewAccount(time+"email@ght.com");
		accountCreationPage.pageHeadingIsDisplayed();
	}
}
