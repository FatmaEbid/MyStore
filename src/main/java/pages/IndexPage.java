package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IndexPage extends BaseClass {

	@FindBy (css = "input#search_query_top")
	WebElement searchBox;
	@FindBy(css="#search_query_top+button.btn.btn-default.button-search")
	WebElement searchButton;
	@FindBy(css=".login") WebElement signInButton;
	@FindBy(css=".logo.img-responsive") WebElement logo;

	public IndexPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}
	public SearchResultPage searchProduct(String productName) {
		Action.type(searchBox, productName);
		Action.click(driver, searchButton);
		return new SearchResultPage(driver);
	}
	public LoginPage clickSignInButton() {
		Action.click(driver, signInButton);
		return new LoginPage(driver);
	}
	public boolean verifyLogo() {
		return Action.isDisplayed(driver, logo);
	}
	public String getPageTitle() {
		String shopTitlePage = driver.getTitle();
		System.out.println("The page title is: " + shopTitlePage);
		return shopTitlePage;
	}

}
