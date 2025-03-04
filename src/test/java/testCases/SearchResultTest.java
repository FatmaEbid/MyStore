package testCases;

import base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchResultTest extends BaseClass {
	/*
 Verify user is navigated to ALL PRODUCTS page successfully
 Enter product name in search input and click search button
 Verify 'SEARCHED PRODUCTS' is visible
 Verify all the products related to search are visible
*/
	@Test
	public void verifySearchPage(){
		//Enter product name in search input and click search button
		indexPage.searchProduct(properties.getProperty("searchItem"));
		//Verify user is navigated to ALL PRODUCTS  Search page successfully
		Assert.assertTrue(searchResultPage.isSearchHeadingDisplayed());
		//searchResultPage.getItemsDescribtion();
		searchResultPage.getProductPrice();

	}
}
