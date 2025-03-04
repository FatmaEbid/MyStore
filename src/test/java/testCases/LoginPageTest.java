package testCases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseClass {

@Test
	public void verifyLogin(){
// user can login with registeredEmail and password
	// verify the user landing on the homePage
	indexPage.clickSignInButton();
	loginPage.login(properties.getProperty("email"), properties.getProperty("password") );
	Assert.assertEquals(myAccountPage.getCurrentUrl(),properties.getProperty("homePageUrl"));
}
}
