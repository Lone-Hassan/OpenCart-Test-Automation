package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage extends PageHeader {

	WebDriver driver;
	public static final String Title = "Search";

	public SearchPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "input-search")
	WebElement searchCriteria;

	@FindBy(id = "input-category")
	WebElement inputCategory;

	// list of products on search page
	@FindBy(css = "#product-list .col.mb-3")
	List<WebElement> ProductList;

	/**
	 * @param item to search
	 */
	public void TypeSearchCriteria(String item) {

		searchCriteria.sendKeys(item);
	}

	/**
	 * Select category from drop down menu by test if not visible then scroll to it
	 * and select it
	 * 
	 * @param category to select
	 * @return true if category is present in drop down list
	 */
	public boolean selectCatagory(String category) {

		inputCategory.click();

		try {
			WebElement optionToSelect = inputCategory.findElement(By.xpath("//option[text()='" + category + "']"));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", optionToSelect);

			optionToSelect.click();
			return true;
		} catch (NoSuchElementException e) {

			return false;
		}
	}

	/**
	 * Scrolls to Click on the product and navigates to product description page.
	 * 
	 * @param product_name is the name of the product in the search list
	 * @return productPage object if product found or null
	 */
	public ProductDisplayPage selectProductFromProductList(String product_name) {

		ProductThumb PT = getProductFromList(product_name);
		
		
		if (PT != null) {
			String productHeading = PT.getProductName();
			scrollTo(PT.product);
			PT.product.click();
			waitforPageTitleIsSubString(productHeading, 1);
			return new ProductDisplayPage(driver);
		}

		return null;
	}

	/**
	 * Scroll to and Add product to the wish list
	 * 
	 * @param Product_name the product to add to wish list
	 */
	public void addProductToWishList(String Product_name) {

		ProductThumb product = getProductFromList(Product_name);
		if (product != null) {

			
			WebElement addToWishList = product.add_To_wishList_Btn();
			
			scrollTo(addToWishList);
			
			addToWishList.click();
		}
	}

	/**
	 * @param product thumb
	 * @return product heading in product thumb
	 */
	public String getProductName(WebElement product) {

		return product.findElement(By.cssSelector(".product-thumb .content .description h4")).getText();
	}

	/**
	 * Search for product in the product list
	 * 
	 * @return webElement of the product container in product list
	 * @param Product_name is the string name of product to search
	 */
	public ProductThumb getProductFromList(String Product_name) {
		for (WebElement product : ProductList) {
			ProductThumb PT = new ProductThumb(product);
			String productHeading=PT.getProductName();
			//String productHeading = getProductName(product);
			if (productHeading.contains(Product_name)) {
				return PT;
			}
		}
		return null;
	}

	/**
	 *Scroll to and Add product to cart by product name
	 * 
	 * @param product_name is the name of the product to add
	 * 
	 */
	public void add_to_Cart(String product_name) {
		// TODO Auto-generated method stub
		ProductThumb product = getProductFromList(product_name);
		if (product != null) {

			WebElement addToCart = product.add_To_Cart_Btn();
			
			scrollTo(addToCart);
			
			addToCart.click();

		}

	}
}
