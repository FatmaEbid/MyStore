package pages;

import actionDriver.Action;
import base.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage extends BaseClass {
	public SearchResultPage(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}
@FindBy(css=".page-heading.product-listing")
WebElement searchHeadingPage;
	@FindBy(css="#pagination+.product-count") WebElement numberOfItem;
	@FindBy(css="span.price.product-price")
	List<WebElement> productPrice;
	@FindBy(css=".product-name") List<WebElement> productName;
	@FindBy(css=".right-block") List<WebElement> itemsDetails;
	@FindBy(css=".available-dif") List<WebElement> itemAvailable;
	@FindBy(css = ".out-of-stock") List<WebElement> itemsOutOfStock;

@FindBy(xpath="//ul[@class='product_list grid row']//a[@class='product_img_link']") List<WebElement> productImgs;
	// loop to get the product search product on the page

public void selectImag(String productName){
	for(WebElement img : productImgs){
		if(img.equals(productName)){
			Action.click(driver, img);
		}
	}

}

	// verify the search page displayed successfully
public boolean isSearchHeadingDisplayed() {
	System.out.println("Search heading: "+ searchHeadingPage.getText());
	return searchHeadingPage.isDisplayed();
}
// number of product available
	public String getProductPrice(){
	String price = "";
	String name = "";
	for(WebElement itemName:productName){
		for (WebElement item : productPrice) {
		if (itemName.isDisplayed()) {
			System.out.println("Product name: "+itemName.getText());
		}
			if (item.isDisplayed()) {
				System.out.println("Product price: " + item.getText());
			}
			}
	}return price;
}
/*public String getProductName(){
	String names = "";
	for(WebElement item:productName){
		if (item.isDisplayed()) {
			System.out.println("Product name: "+item.getText());
		}
	}return  name;
}*/
	// Get product names, price and shows if available or out stock

}
