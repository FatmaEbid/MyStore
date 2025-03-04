package pages;

import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountCreationPage extends BaseClass {
	public AccountCreationPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//h1[contains(text(),'Create an account')]") WebElement pageHeading;

	public boolean pageHeadingIsDisplayed() {
		System.out.println("Page heading is : " + pageHeading.getText());
		return pageHeading.isDisplayed();
	}
}
