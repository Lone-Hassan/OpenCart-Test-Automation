package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDisplayPage extends PageHeader{

	WebDriver driver;
	
	public ProductDisplayPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="button-cart")
	WebElement addToCart;
	
	@FindBy(css=".row.row-cols-1.row-cols-sm-2.row-cols-md-3.row-cols-xl-4 .col.mb-3")
	List<WebElement> relatedProducts;
	
	/**
	 * Scroll to and click on Add to cart button for this product
	 * */
	public void add_To_Cart() {
		scrollTo(addToCart);
		addToCart.click();
	}
	
	/**
	 * Add product to cart from related product search list by name
	 * @param product_name: Heading of the product to add to cart 
	 * */
	public void add_To_Cart_RelatedProduct_ByName(String product_name) {
		
		for (WebElement product: relatedProducts)
		{
			
			ProductThumb PT = new ProductThumb(product);
			String productHeading = PT.getProductName();
			if(productHeading.equalsIgnoreCase(product_name))
			{
			WebElement addToCart=PT.add_To_Cart_Btn();
			scrollTo(addToCart);
			addToCart.click();
			}
		}
	}

	public void add_To_Cart_RelatedProduct() {
		// TODO Auto-generated method stub
		if (!relatedProducts.isEmpty())
		{
			ProductThumb PT = new ProductThumb(relatedProducts.get(0));
			scrollTo(PT.add_To_Cart_Btn());
			PT.add_To_Cart_Btn().click();
		}
	}
}
