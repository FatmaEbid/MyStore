package testCases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MyAccountPageTest extends BaseClass {
	@Test
	public void wishListTest(){
		indexPage.clickSignInButton();
		loginPage.login(properties.getProperty("email"), properties.getProperty("password") );
		//softAssert.assertEquals(myAccountPage.pageHeading(), "MY ACCOUNT");
		Assert.assertEquals(myAccountPage.pageHeading(), "MY ACCOUNT");
		myAccountPage.getMyAccountLists();
		Assert.assertTrue(myAccountPage.validateOrdersHistoryText());
	}

}
