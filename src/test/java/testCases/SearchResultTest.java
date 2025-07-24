package testCases;

import Utilities.A11y.resources.AccessibilityReportGenerator;
import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;



public class SearchResultTest extends BaseClass {
	/*
 Verify user is navigated to ALL PRODUCTS page successfully
 Enter product name in search input and click search button
 Verify 'SEARCHED PRODUCTS' is visible
 Verify all the products related to search are visible
*/
	@Test
	public void verifySearchPage() throws IOException {

		//Enter product name in search input and click search button
		indexPage.searchProduct(properties.getProperty("searchItem"));
		//Verify user is navigated to ALL PRODUCTS  Search page successfully
		Assert.assertTrue(searchResultPage.isSearchHeadingDisplayed());
		//searchResultPage.getItemsDescribtion();
		Utilities.A11y.resources.AccessibilityReportGenerator.generateReport(driver, AccessibilityReportGenerator.WCAGLevel.AA);
		searchResultPage.getProductPrice();

	}
}
