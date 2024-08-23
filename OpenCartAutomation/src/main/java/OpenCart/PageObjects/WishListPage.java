package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WishListPage extends PageHeader {

	WebDriver driver;
	public RightColumnList RCL;
	public static final String title = "My Wishlist";
	
	public WishListPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver=driver;
		PageFactory.initElements(driver, this);
		this.RCL= new RightColumnList(driver);
	}

	//button[aria-label='Add to Cart']
	
	@FindBy(css="#wishlist tbody tr")
	List<WebElement> item_tableRows;
	
	/**
	 * Scroll and Click on add to cart from wish list table
	 * @param Product_name to add in cart
	 * */
	
	public void add_to_Cart(String Product_name) {
		
		for (WebElement item: item_tableRows) {
			
			String item_name = item.findElement(By.cssSelector("td:nth-child(2)")).getText();
			if(item_name.equalsIgnoreCase(Product_name)) {
				
				WebElement addtocartBtn = item.findElement(By.cssSelector(".btn.btn-primary"));
				scrollTo(addtocartBtn);
				addtocartBtn.click();
			}
		}
		
		
	}
	
	
}
