package testCases;

import base.BaseClass;
import org.testng.annotations.Test;
import pages.LoginPage;

import static org.testng.Assert.*;

public class LoginPageTest extends BaseClass {
	LoginPage loginPage;

@Test
	public void verifyLogin(){
// user can login with registeredEmail and password
	// verify the user landing on the homePage
	loginPage = indexPage.clickSignInButton();
	//loginPage.userLogin("emailqwer@domain.com", "12345");
	loginPage.userLogin(properties.getProperty("email"), properties.getProperty("password") );
	assertTrue(driver.getCurrentUrl().contains("my-account"));

}


}
