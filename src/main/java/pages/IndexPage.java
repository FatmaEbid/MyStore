package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IndexPage extends BaseClass {
	private WebDriver driver;
	WebDriverWait wait;

	private By searchBox =By.cssSelector("input#search_query_top" );
	private By searchButton =By.cssSelector("#search_query_top+button.btn.btn-default.button-search");
private By signInButton =By.cssSelector(".login");
	private By logo = By.cssSelector(".logo.img-responsive");


	public IndexPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}
	public SearchResultPage searchProduct(String productName) {
		Action.type(driver.findElement(searchBox), productName);
		Action.click(driver, driver.findElement(searchButton));
		return new SearchResultPage(driver);
	}
	public LoginPage clickSignInButton() {
		driver.findElement(signInButton).click();
		//Action.click(driver, signInButton);
		return new LoginPage(driver);
	}
	public boolean verifyLogo() {
		return Action.isDisplayed(driver, driver.findElement(logo));
	}
	public String getPageTitle() {
		System.out.println("The page title is: " + driver.getTitle());
		return driver.getTitle();
	}

}
