package testCases;

import base.BaseClass;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.testng.Tag;
import io.qameta.allure.testng.Tags;
import org.testng.annotations.Test;
import pages.LoginPage;

import static org.testng.Assert.*;

public class LoginPageTest extends BaseClass {
	LoginPage loginPage;

@Tags( {@Tag("Regression"), @Tag("Login"), @Tag("Sanity")})
@Owner("Fatma")
@Description("Login with registered email and password")
@Test
	public void verifyLogin(){
// user can login with registeredEmail and password
	// verify the user landing on the homePage
	loginPage = indexPage.clickSignInButton();
	loginPage.userLogin(properties.getProperty("email"), properties.getProperty("password") );
	assertTrue(driver.getCurrentUrl().contains("my-account"));

}


}
