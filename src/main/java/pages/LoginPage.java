package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BaseClass {
	WebDriverWait wait;
	@FindBy (css ="input#email") WebElement registeredEmail;
	//private By registeredEmail = By.id("email");
	@FindBy(css="input#passwd") WebElement passwrd;
	@FindBy(css="#SubmitLogin") WebElement signInBttn;
	@FindBy(css="#email_create") WebElement createEmailField;
	@FindBy(css="#SubmitCreate") WebElement submitBtn;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	}
	public HomePage userLogin(String email, String password) {
		//wait.until(ExpectedConditions.visibilityOf(registeredEmail));
		//registeredEmail.sendKeys(email);
		//driver.findElement(registeredEmail).sendKeys(email);
		//passwrd.sendKeys(password);
		Action.type(registeredEmail, email);
		//System.out.println("Registered Email: "+registeredEmail.getText());
		Action.type(passwrd, password);
		Action.click(driver, signInBttn);
		return new HomePage(driver);
	}
	public AccountCreationPage creatNewAccount(String newEmail){
		Action.type(createEmailField, newEmail);
		Action.click(driver, submitBtn);
		return new AccountCreationPage(driver);
	}




}
