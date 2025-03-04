package actionDriver;

import base.BaseClass;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Action extends BaseClass {

	public static void scrollByVisibilityOfElement(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}


	public static void click(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();
	}


	public static boolean findElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			flag = ele.isDisplayed();
		} catch (Exception e) {
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Successfully found element.");
			} else {
				System.out.println("Unable to locate element.");
			}
		}
		return flag;
	}


	public static boolean isDisplayed(WebDriver driver, WebElement ele) {
		boolean flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isDisplayed();
			System.out.println(flag ? "The element is displayed." : "The element is not displayed.");
		} else {
			System.out.println("Element not found.");
		}
		return flag;
	}

	public static boolean isSelected(WebDriver driver, WebElement ele) {
		boolean flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isSelected();
			System.out.println(flag ? "The element is selected." : "The element is not selected.");
		} else {
			System.out.println("Element not found.");
		}
		return flag;
	}


	public static boolean isEnabled(WebDriver driver, WebElement ele) {
		boolean flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isEnabled();
			System.out.println(flag ? "The element is enabled." : "The element is not enabled.");
		} else {
			System.out.println("Element not found.");
		}
		return flag;
	}

	public static boolean type(WebElement ele, String text) {
		boolean flag = false;
		try {
			if (ele.isDisplayed()) {
				ele.clear();
				ele.sendKeys(text);
				flag = true;
			}
		} catch (Exception e) {
			System.out.println("Element not found.");
			flag = false;
		} finally {
			//System.out.println(flag ? "Successfully entered:"+ text : "Unable to enter value.");
		}
		return flag;
	}

	public static boolean selectBySendkeys(String value, WebElement ele) {
		boolean flag = false;
		try {
			ele.sendKeys(value);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Selected value from the dropdown." : "Could not select value from the dropdown.");
		}
		return flag;
	}

	public static boolean selectByIndex(WebElement element, int index) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByIndex(index);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Option selected by index." : "Option not selected by index.");
		}
		return flag;
	}

	public boolean selectByValue(WebElement element, String value) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByValue(value);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Option selected by value." : "Option not selected by value.");
		}
		return flag;
	}

	public static  boolean selectByVisibleText(String visibleText, WebElement ele) {
		boolean flag = false;
		try {
			Select s = new Select(ele);
			s.selectByVisibleText(visibleText);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Option selected by visible text." : "Option not selected by visible text.");
		}
		return flag;
	}


	public static boolean mouseHoverByJavaScript(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			String javaScript = "var evObj = document.createEvent('MouseEvents');" +
					"evObj.initMouseEvent('mouseover', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
					"arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, ele);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "MouseOver action performed using JavaScript." : "MouseOver action not performed.");
		}
		return flag;
	}

	public static boolean JSClick(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			flag = true;
		} catch (Exception e) {
			throw e;
		} finally {
			System.out.println(flag ? "JSClick action performed." : "JSClick action not performed.");
		}
		return flag;
	}

	public static boolean switchToFrameById(WebDriver driver, String idValue) {
		boolean flag = false;
		try {
			driver.switchTo().frame(idValue);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			System.out.println(flag ? "Frame with ID \"" + idValue + "\" selected." : "Frame with ID \"" + idValue + "\" not selected.");
		}
		return flag;
	}

	public static boolean switchToFrameByName(WebDriver driver, String nameValue) {
		boolean flag = false;
		try {
			driver.switchTo().frame(nameValue);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Frame with name \"" + nameValue + "\" selected." : "Frame with name \"" + nameValue + "\" not selected.");
		}
		return flag;
	}


	public static boolean switchToDefaultFrame(WebDriver driver) {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public static void mouseOverElement(WebDriver driver, WebElement element) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(flag ? "MouseOver action performed on element." : "MouseOver action not performed on element.");
		}
	}

	public static boolean moveToElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			new Actions(driver).moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	public static boolean mouseover(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean draggable(WebDriver driver, WebElement source, int x, int y) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDropBy(source, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Draggable action performed on element." : "Draggable action not performed on element.");
		}
		return flag;
	}

	public static boolean draganddrop(WebDriver driver, WebElement source, WebElement target) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDrop(source, target).perform();
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Drag and drop action performed." : "Drag and drop action not performed.");
		}
		return flag;
	}

	public static boolean slider(WebDriver driver, WebElement ele, int x, int y) {
		boolean flag = false;
		try {
			new Actions(driver).dragAndDropBy(ele, x, y).build().perform();
			Thread.sleep(5000);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Slider action performed." : "Slider action not performed.");
		}
		return flag;
	}

	public static boolean rightclick(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			new Actions(driver).contextClick(ele).perform();
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Right click action performed." : "Right click action not performed.");
		}
		return flag;
	}

	public static boolean switchWindowByTitle(WebDriver driver, String windowTitle, int count) {
		boolean flag = false;
		try {
			Set<String> windowList = driver.getWindowHandles();
			String[] array = windowList.toArray(new String[0]);
			if (array.length >= count) {
				driver.switchTo().window(array[count - 1]);
				if (driver.getTitle().contains(windowTitle)) {
					flag = true;
				}
			}
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Navigated to the window with title: " + windowTitle
					: "The window with title \"" + windowTitle + "\" is not selected.");
		}
		return flag;
	}

	public static boolean switchToNewWindow(WebDriver driver) {
		boolean flag = false;
		try {
			Set<String> windows = driver.getWindowHandles();
			Object[] popup = windows.toArray();
			if (popup.length > 1) {
				driver.switchTo().window(popup[1].toString());
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Switched to new window." : "Unable to switch to new window.");
		}
		return flag;
	}

	public static boolean switchToOldWindow(WebDriver driver) {
		boolean flag = false;
		try {
			Set<String> windows = driver.getWindowHandles();
			Object[] popup = windows.toArray();
			if (popup.length > 0) {
				driver.switchTo().window(popup[0].toString());
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Switched to old window." : "Unable to switch to old window.");
		}
		return flag;
	}

	public static int getColumncount(WebElement row) {
		List<WebElement> columns = row.findElements(By.tagName("td"));
		int count = columns.size();
		System.out.println("Column count: " + count);
		for (WebElement column : columns) {
			System.out.print(column.getText() + " | ");
		}
		return count;
	}

	public static int getRowCount(WebElement table) {
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int count = rows.size() - 1; // Assuming first row is header
		System.out.println("Row count: " + count);
		return count;
	}

	public static boolean handleAlertIfPresent(WebDriver driver) {
		boolean alertHandled = false;
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			alertHandled = true;
		} catch (NoAlertPresentException ex) {
			System.out.println("No alert was present.");
		}
		if (alertHandled) {
			System.out.println("The alert was handled successfully.");
		}
		return alertHandled;
	}

	public static boolean launchUrl(WebDriver driver, String url) {
		boolean flag = false;
		try {
			driver.navigate().to(url);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Successfully launched \"" + url + "\""
					: "Failed to launch \"" + url + "\"");
		}
		return flag;
	}


	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public static String getTitle(WebDriver driver) {
		String title = driver.getTitle();
		System.out.println("Title of the page is: \"" + title + "\"");
		return title;
	}

	public static String curretnUrl(WebDriver driver) {
		String url = driver.getCurrentUrl();
		System.out.println("Current URL is: \"" + url + "\"");
		return url;
	}

	public static boolean click1(WebElement locator, String locatorName) {
		boolean flag = false;
		try {
			locator.click();
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			System.out.println(flag ? "Able to click on \"" + locatorName + "\""
					: "Unable to click on \"" + locatorName + "\"");
		}
		return flag;
	}

	public static void fluentWait(WebDriver driver, WebElement element, int timeOut) {
		try {
			Wait<WebDriver> wait = new FluentWait<>(driver)
					.withTimeout(Duration.ofSeconds(timeOut))
					.pollingEvery(Duration.ofSeconds(2))
					.ignoring(NoSuchElementException.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void implicitWait(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}


	public static void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void pageLoadTimeOut(WebDriver driver, int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
	}

	public static String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\ScreenShots\\" + filename + "_" + dateName + ".png";

		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (Exception e) {
			System.out.println("Error taking screenshot: " + e.getMessage());
		}
		// Jenkins-friendly path
		String newImageString = "http://localhost:8082/job/MyStoreProject/ws/MyStoreProject/ScreenShots/"
				+ filename + "_" + dateName + ".png";
		return newImageString;
	}


	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date());
	}
}
