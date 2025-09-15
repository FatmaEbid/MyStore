package pages;

import io.qameta.allure.Description;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Footer {
	private WebDriverWait wait;
	private By socialMediaButtons = By.cssSelector("section[id='social_block'] li a");
	private By footerBlocks = By.cssSelector(".footer-block li a:not(#block_contact_infos)");
	private WebDriver driver;

	public Footer(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public List<String> clickOnFooterLinks() {
		List<String> urls = new ArrayList<>();
		String originalWindow = driver.getWindowHandle();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		List<WebElement> footerElements = driver.findElements(footerBlocks);

		for (int i = 0; i < footerElements.size(); i++) {
			try {
				// Re-fetch elements to avoid stale references
				footerElements = driver.findElements(footerBlocks);
				WebElement element = footerElements.get(i);

				// Try to get the link text (visible or not)
				String linkText = element.getText().trim();
				if (linkText.isEmpty()) {
					linkText = (String) js.executeScript("return arguments[0].textContent;", element);
					linkText = linkText != null ? linkText.trim() : "[No Text]";
				}

				// Check if element is visible and enabled
				if (element.isDisplayed() && element.isEnabled()) {
					System.out.println("Clicking on link: " + linkText);
					element.click();

					// Handle new tab if opened
					for (String windowHandle : driver.getWindowHandles()) {
						if (!windowHandle.equals(originalWindow)) {
							driver.switchTo().window(windowHandle);
							String currentUrl = driver.getCurrentUrl();
							urls.add(currentUrl);
							System.out.println("New window URL: " + currentUrl);
							driver.close();
							driver.switchTo().window(originalWindow);
							break;
						}
					}

					// Wait for footer to reload
					wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(footerBlocks));
				} else {
					System.err.println("Element not visible or clickable: " + linkText);
				}

			} catch (ElementNotInteractableException e) {
				System.err.println("Could not click element at index " + i + ": " + e.getMessage());
			}


		}return urls;

	}
@Description("Clicks on each social media button in the footer and returns their URLs.")
	// This method clicks on social media buttons in the footer and returns their URLs.
	public List<String> clickOnEachMediaButtons() {
		List<String> urls = new ArrayList<>();
		List<WebElement> footerElements = driver.findElements(socialMediaButtons);

//After that, driver.navigate().back() tries to return to the main page.
//But the footerElements list was created before the navigation, so after coming back, those elements are stale (no longer valid in the DOM).
//Selenium throws a StaleElementReferenceException or silently fails to interact with the next element.

		/*for (WebElement element : footerElements) {
			element.click();
			System.out.println("Clicked on: " + element.getDomAttribute("href"));
			urls.add(driver.getCurrentUrl());
			driver.navigate().back();
			//wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(socialMediaButtons));
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfAllElementsLocatedBy(socialMediaButtons)));
			System.out.println("Back to footer: " + driver.getCurrentUrl());
		}
		 return urls;*/
		/*
		* Why Looking to switch
		* Most social media links open in a new tab (target="_blank").
Instead of navigating away, you stay on the main page and just switch to the new tab.
You capture the URL, then close the new tab, and return to the original tab.
Since the main page never reloads, your original elements remain valid.
* */
		String originalWindow = driver.getWindowHandle();
		System.out.println("Original window handle: " + originalWindow);
		for (WebElement element : footerElements) {
			element.click();
			for (String windowHandle : driver.getWindowHandles()) {
				if (!windowHandle.equals(originalWindow)) {
					driver.switchTo().window(windowHandle);
					urls.add(driver.getCurrentUrl());
					System.out.println("New window URL: " + driver.getCurrentUrl());
					driver.close();
					driver.switchTo().window(originalWindow);
					break;
				}
			}
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(socialMediaButtons));
		}
		return urls;

	}

}
