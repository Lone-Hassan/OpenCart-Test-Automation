package OpenCart.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



/**
 * Represents the product thumb Object
 * */
public class ProductThumb {
	WebElement product;
	
	public ProductThumb(WebElement product) {
		this.product=product;
	}
	
	/**
	 * @return the product name this object representing
	 * */
	
	public String getProductName() {
		
		return product.findElement(By.cssSelector(".product-thumb .content .description h4")).getText();
	}
	
	/**
	 * @return WebElement of Add to cart button of this object
	 * */
	public WebElement add_To_Cart_Btn() {
		
		return product.findElement(By.cssSelector("button[title='Add to Cart']"));
	}
	/**
	 * @return WebElement of Add to wish list button of this object
	 * */
	public WebElement add_To_wishList_Btn() {
		
		return product.findElement(By.cssSelector("button[title='Add to Wish List']"));
	}
	/**
	 * @return WebElement of compare this product button of this object
	 * */
	public WebElement Compare_Product_Btn() {
		
		return product.findElement(By.cssSelector("button[title='Compare this Product']"));
	}

}
