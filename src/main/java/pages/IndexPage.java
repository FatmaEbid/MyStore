package pages;

import actionDriver.Action;
import base.BaseClass;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class IndexPage extends BaseClass {
	private final By searchBox = By.cssSelector("input#search_query_top");
	private final By searchButton = By.cssSelector("#search_query_top+button.btn.btn-default.button-search");
	private final By signInButton = By.cssSelector(".login");
	private final By logo = By.cssSelector(".logo.img-responsive");
	private final By menuBarButtons = By.xpath("//div[@class='nav']//a");
	private final By mainMenuBar = By.cssSelector(".menu-content> li:not(:has(a[title='Blog']))");
	private final By womanMenu = By.cssSelector(".sfHoverForce.sfHover ul[style='display: none;'] li:not(:has(a[class='sf-with-ul'] and li[class='category-thumbnail']))");
	private WebDriverWait wait;
	private WebDriver driver;
	private final By leftMenuBar = By.cssSelector(".block ul.tree.dynamized >li");
	private final By categoryTitle = By.cssSelector(".cat-name");
	private final By subMenuBar = By.cssSelector(".sfHoverForce ul[style='display: none;'] a");
	private final By productcounter = By.cssSelector("span.heading-counter");
	private final By productList =By.cssSelector(".product_list.grid.row .product-name");

	public IndexPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

@Description("Click on Navigation Bar Options: Contact Us or Sign In from the method")
	public void menuBarOptions(navBarOptions option) {

	switch (option) {
			case CONTACT_US:
				driver.findElement(By.linkText("Contact us")).click();
				break;
			case SIGN_IN:
				driver.findElement(By.linkText("Sign in")).click();
				break;
			default:
				throw new IllegalArgumentException("Invalid menu option: " + option);
		}
	}
	//ToDo: get the product list when click on each menu options
	public List<String> getProductList() {
		List<WebElement> products = driver.findElements(productList);
		List<String> product = new ArrayList<>();
		try {
			for (WebElement pro : products) {
				if (pro.isDisplayed()) {
					System.out.println("Product Name: " + pro.getText());
					product.add(pro.getText());
				} else {
					System.out.println("Product is not displayed: " + pro.getText());
				}
			}
		} catch (StaleElementReferenceException e) {
			System.err.println("Stale element encountered: " + e.getMessage());
		}
		return product;
	}
//ToDo: to get the category name and number of products in that category when click on each menu options
	public String getCategoryName(){
		WebElement categoryNameElement = driver.findElement(categoryTitle);
		String categoryName = categoryNameElement.getText();

		System.out.println("Category Name: " + categoryName);
		System.out.println("Number of products in this category: " + driver.findElement(productcounter).getText());
		return categoryName;
	}

	public boolean checkMenuBarOptionsWithLeftMenu() {
		boolean allPassed = true; // collect overall result

		List<WebElement> mainMenus = driver.findElements(mainMenuBar);

		for (int i = 0; i < mainMenus.size(); i++) {
			try {
				// Re-fetch to avoid stale elements
				mainMenus = driver.findElements(mainMenuBar);
				WebElement menu = mainMenus.get(i);
				wait.until(ExpectedConditions.elementToBeClickable(menu));

				String menuName = menu.getText().trim();
				System.out.println("\n>>> Main Menu: " + (menuName.isEmpty() ? "[No Name]" : menuName));

				menu.click();

				// Wait for category title and compare
				WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryTitle));
				String categoryName = titleElement.getText().trim();

				if (!categoryName.toLowerCase().contains(menuName.toLowerCase())) {
					System.err.println("❌ Mismatch: Category title '" + categoryName + "' does not match menu '" + menuName + "'");
					allPassed = false;
				} else {
					System.out.println("✅ Category Title: " + categoryName);
				}

				// Print submenu items
				List<String> subMenus = getSubMenuBar(); // Your method prints inside
				if (subMenus.isEmpty()) {
					System.out.println("⚠️ No submenus found for '" + menuName + "'");
				}

				// Print left menu items
				List<WebElement> leftMenus = driver.findElements(leftMenuBar);
				int visibleCount = 0;

				for (WebElement leftMenu : leftMenus) {
					if (leftMenu.isDisplayed()) {
						String name = leftMenu.getText().trim();
						if (!name.isEmpty()) {
							System.out.println("   - Left Menu: " + name);
							visibleCount++;
						}
					}
				}

				if (visibleCount == 0) {
					System.out.println("⚠️ No visible left menu for '" + menuName + "'");
					// Do not fail test; it’s an acceptable condition
				}

			} catch (Exception e) {
				System.err.println("❌ Error while processing menu index " + i + ": " + e.getMessage());
				allPassed = false;
			}
		}

		return allPassed;
	}


	public List<String> getSubMenuBar() {
		List<WebElement> subItems = driver.findElements(subMenuBar);
		List<String> subNames = new ArrayList<>();

		if (subItems.isEmpty()) {
			System.out.println("   ⚠️ No Submenu Found");
		} else {
			for (WebElement sub : subItems) {
				if (sub.isDisplayed()) {
					String name = sub.getDomAttribute("title");
					if (!name.isEmpty()) {
						System.out.println("      - " + name);
						subNames.add(name);
						System.out.println("   Submenu Items:" + subNames);
					}
				}
			}
		}
		return subNames;
	}
	public ResultPage selectOptionsFromMenuBar(menuList menuName) {
		List<WebElement> menus = driver.findElements(mainMenuBar);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(mainMenuBar));
		if (menus.isEmpty()) {
			System.out.println("⚠️ No menu Found");
		} else {
			menus.stream()
					.filter(e->
							e.getText().equals(menuName.getMenuName()))
					.findFirst().orElseThrow().click();
		}
		return new ResultPage(driver);
	}

	public SearchResultPage searchProduct(String productName) {
		driver.findElement(searchBox).sendKeys(productName);
		driver.findElement(searchButton).click();
		return new SearchResultPage(driver);
	}

	public LoginPage clickSignInButton() {
		driver.findElement(signInButton).click();
		return new LoginPage(driver);
	}

	public boolean verifyLogo() {
		return Action.isDisplayed(driver, driver.findElement(logo));
	}

	public String getPageUrl() {
		System.out.println("Current Url is: " + driver.getCurrentUrl());
		return driver.getCurrentUrl();
	}

	public enum navBarOptions {
		CONTACT_US("Contact us"),
		SIGN_IN("Sign in");
		private final String optionText;

		navBarOptions(String optionText) {
			this.optionText = optionText;
		}
	}
	public enum menuList {
		WOMAN("Women"),
		DRESSES("Dresses"),
		T_SHIRTS("T-shirts");
		private final String menuName;
		menuList(String menuName) {
			this.menuName = menuName;
		}
		public String getMenuName() {
			return menuName;
		}
	}
}