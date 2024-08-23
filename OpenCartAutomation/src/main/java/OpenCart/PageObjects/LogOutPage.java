package OpenCart.PageObjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LogOutPage extends PageHeader{

	WebDriver driver;
	public RightColumnList RCL;
	public static final String Title = "Account Logout";
	
	public LogOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL= new RightColumnList(driver);
	}
	
	@FindBy(css=".btn.btn-primary")
	WebElement Continue;
	
	/**
	 * Click on Continue and wait for home page to load for one second.
	 * @return HomePage Object or null if not directed to Home page.
	 * 
	 * */
	public HomePage Continue() {
		
		Continue.click();
		try {
		waitforPageLoad(HomePage.Title,1);
		return new HomePage(driver);
		}catch(TimeoutException te) {
			return null;
		}
		
		
		
	}
}
