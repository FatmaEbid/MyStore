package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {
	@FindBy (css ="input#email") WebElement registeredEmail;
	@FindBy(css="input#passwd") WebElement passwrd;
	@FindBy(css="#SubmitLogin") WebElement signInBttn;
	@FindBy(css="#email_create") WebElement createEmailField;
	@FindBy(css="#SubmitCreate") WebElement submitBtn;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}
	public MyAccountPage login(String email, String password) {
		Action.type(registeredEmail, email);
		//System.out.println("Registered Email: "+registeredEmail.getText());
		Action.type(passwrd, password);
		Action.click(driver, signInBttn);
		return new MyAccountPage(driver);
	}
	public AccountCreationPage creatNewAccount(String newEmail){
		Action.type(createEmailField, newEmail);
		Action.click(driver, submitBtn);
		return new AccountCreationPage(driver);
	}




}
