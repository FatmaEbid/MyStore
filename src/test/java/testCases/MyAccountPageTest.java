package testCases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class MyAccountPageTest extends BaseClass {
	LoginPage loginPage;
	@Test
	public void wishListTest(){
		loginPage = indexPage.clickSignInButton();
		loginPage.userLogin(properties.getProperty("email"), properties.getProperty("password") );
		//softAssert.assertEquals(myAccountPage.pageHeading(), "MY ACCOUNT");
		Assert.assertEquals(myAccountPage.pageHeading(), "MY ACCOUNT");
		myAccountPage.getMyAccountLists();
		Assert.assertTrue(myAccountPage.validateOrdersHistoryText());
	}

}
