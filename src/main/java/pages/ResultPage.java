package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResultPage {
	private WebDriver driver;
	private WebDriverWait wait;

	public ResultPage(WebDriver driver) {
		// Constructor for ResultPage
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	private By productSortList= By.cssSelector("#selectProductSort");
	private By sortedByList = By.cssSelector("#selectProductSort option:not(:has(option[selected='selected']))");

	public void selectSortOption(sortOptions option){
		driver.findElement(productSortList).click();
		 driver.findElements(sortedByList).stream()
				.filter(options->options.getText().equals(option.getValue()))
				.findFirst().orElseThrow().click();
	}

	public enum sortOptions {
		//predefined sorting options
		PRICE_ASCENDING("Price: Lowest first"),
		PRICE_DESCENDING("Price: Highest first"),
		PRODUCT_NAME_ASCENDING("Product Name: A to Z"),
		PRODUCT_NAME_DESCENDING("Product Name: Z to A"),
		REFERENCE_ASCENDING("Reference: Lowest first"),
		REFERENCE_DESCENDING("Reference: Highest first"),
		InSTOKE("In stock");

		// Enum to represent sorting options with a string value
		private final String value;

		// Constructor to initialize the enum with a string value
		sortOptions(String value) {
			this.value = value;
		}
		// Getter method to retrieve the value of the enum
		public String getValue() {
			return value;
		}
	}

}
