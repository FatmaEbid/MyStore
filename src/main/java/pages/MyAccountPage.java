package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class MyAccountPage extends BaseClass {
	public MyAccountPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "#center_column>.alert.alert-success")
	WebElement accountCreatedText;
	@FindBy(xpath = "//span[text()='Order history and details']") WebElement ordersHistoryText;

	@FindBy(css=".myaccount-link-list>li")
	List<WebElement> myAccountLists;
	@FindBy(css=".page-heading") WebElement myAccountHeading;


	// validate the page heading is displayed
	public String pageHeading(){
			String heading = myAccountHeading.getText();
		System.out.println("The heading of the page: "+heading);
			return heading;
	}

// get the number of elements in my account page
	public String  getMyAccountLists(){
		String myAccountListName =null;
		int numOfElements = myAccountLists.size();
		for (int i = 0; i < numOfElements; i++) {
			 myAccountListName = myAccountLists.get(i).getText();
			 int coun = i+1;
			System.out.println("Account Link List: "+ coun  + "-"+myAccountListName);

		}return myAccountListName;
	}

public boolean validateOrdersHistoryText() {
	System.out.println("Order History Link is displaying: "+ordersHistoryText.getText());
	return Action.isDisplayed(driver, ordersHistoryText);
}

	public String getCurrentUrl(){
		String actualUrl= driver.getCurrentUrl();
		System.out.println("current Url: "+actualUrl);
		return actualUrl;
	}
}
