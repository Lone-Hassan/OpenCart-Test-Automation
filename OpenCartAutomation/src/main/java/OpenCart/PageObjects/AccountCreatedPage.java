package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import OpenCart.AbstractComponents.AbstractComponents;

public class AccountCreatedPage extends PageHeader {

	WebDriver driver;
	RightColumnList RCL;
	public static final String Title = "Your Account Has Been Created!";
	public AccountCreatedPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL = new RightColumnList(driver);

	}

	@FindBy(css = ".btn.btn-primary")
	WebElement continueBtn;
	
	@FindBy(css=".list-group.mb-3>a")
	List<WebElement> rightColumnList;
	
	
	/**
	 * click on continue on Account Created button wait for my account page to load
	 *@return Object of MyAccountPage
	 **/
	public MyAccountPage Continue() {
		continueBtn.click();
		return new MyAccountPage(driver);
	}

	/**
	 * Clicks on the option from the list of right column
	 * @param option text of element to click*/
	public AbstractComponents ClickOnRightColumn(String option) {
		
		return RCL.ClickOnElement(option);
	}

}
