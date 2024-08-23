package OpenCart.PageObjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import OpenCart.AbstractComponents.AbstractComponents;

public class LoginPage extends PageHeader {

	WebDriver driver;
	public RightColumnList RCL;
	public static final String Title = "Account Login";
	
	public LoginPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL= new RightColumnList(driver);
	}

	@FindBy(id = "input-email")
	WebElement inputemailId;

	@FindBy(id = "input-password")
	WebElement inputPassword;

	@FindBy(css = "button[type='submit']")
	WebElement loginBtn;

	@FindBy(css = ".text-end>a")
	WebElement Continue;

	/**
	 * Click on new customer button on Login Page and waits for 1 second for page title "Register Page"
	 * @return RegisterPage class object
	 * */
	public RegisterPage NewCustomer() {
		Continue.click();
		waitforPageLoad(RegisterPage.Title,1);
		return new RegisterPage(driver);

	}
	
	/**
	 * Clicks on the option from the list of right column
	 * @param option text of element to click
	 * @return */
	public AbstractComponents ClickOnRightColumn(String option) {
		
		return RCL.ClickOnElement(option);
	}

	/**
	 * Type in input email field
	 * @param email string to type
	 * */
	public void TypeEmail(String email) {
		inputemailId.sendKeys(email);

	}
	/**
	 * Type in input password field
	 * @param password string to type
	 * */
	public void TypePassword(String password) {
		inputPassword.sendKeys(password);
	}

	public void Submit() {
		loginBtn.click();
	}
	/**
	 * Clears input email field
	 * */
	public void clearEmail() {
		
		inputemailId.clear();
	}
	/**
	 * Clears input password field
	 * */
	public void clearPassword() {
		
		inputPassword.clear();
	}
	
	/**
	 * Performs Complete login steps wait for My account page Title if fails then return current page title
	 * @param email login emiail
	 * @param password login password
	 * @returns Title of the current page
	 * 
	 * */

	public String login(String email,String password) {
		
		TypeEmail(email);
		TypePassword(password);
		Submit();
		try {
			waitforPageLoad(MyAccountPage.Title,1);
			return getTitle();
		}catch(TimeoutException te) 
		{
			return getTitle();
		}
		
	}

}
