package OpenCart.PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShoppingCartPage extends PageHeader{

	WebDriver driver;
	public static final String title="Shopping Cart";
	
	public ShoppingCartPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	//#shopping-cart tbody tr td:nth-child(2)
	@FindBy(css="#shopping-cart tbody tr")
	List<WebElement> itemsList;
	
	public List<String> getItemNameList() {
		List<String> itemsByName= new ArrayList<String>();
		for(WebElement item: itemsList) {
			
			itemsByName.add(item.findElement(By.cssSelector("td:nth-child(2) a")).getText());
			
		}
		return itemsByName;
	}

}
