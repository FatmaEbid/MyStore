package testCases;

import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.IndexPage;

public class IndexPageTest extends BaseClass {



	@Test
	public void verifyLogoPage(){
		boolean logo = indexPage.verifyLogo();
		Assert.assertTrue(logo);
	}
	@Test
	public void verifyTitlePage(){
		String actualTitle = indexPage.getPageTitle();
		Assert.assertEquals(actualTitle,properties.getProperty("pageTitle"));

	}



}
