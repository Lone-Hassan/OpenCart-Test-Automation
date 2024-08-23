package OpenCart.PageObjects;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends PageHeader {
	WebDriver driver;
	RightColumnList RCL;
	public static final String Title="Register Account";
	public RegisterPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL = new RightColumnList(driver);
	}

	// input-firstname
	@FindBy(id = "input-firstname")
	WebElement firstname;
	// error-firstname
	@FindBy(id = "error-firstname")
	WebElement Err_firstname;

	@FindBy(id = "input-lastname")
	WebElement lastname;
	// error-lastname
	@FindBy(id = "error-lastname")
	WebElement Err_lastname;

	@FindBy(id = "input-email")
	WebElement email;
	// error-email
	@FindBy(id = "error-email")
	WebElement Err_email;

	@FindBy(id = "input-password")
	WebElement password;
	// error-password
	@FindBy(id = "error-password")
	WebElement Err_password;

	@FindBy(id = "input-newsletter")
	WebElement subscribe;

	@FindBy(css = "input[name='agree']")
	WebElement privacyPolicy;

	@FindBy(css = "button[type='submit']")
	WebElement continueBtn;

	// @FindBy(css = ".list-group.mb-3>a")
	// List<WebElement> rightColumnList;

	/**
	 * Return placeholder value of the specific text box on Register Page
	 * 
	 * @param Field name of the input field on Register Page
	 * @return String placeholder value of the input field or null if input field
	 *         doesn't exist or placeholder value is not set
	 * 
	 */
	public String PlaceHolder(String Field) {

		switch (Field) {

		case "FirstName":
			return firstname.getAttribute("placeholder");
		case "LastName":
			return lastname.getAttribute("placeholder");
		case "Email":
			return email.getAttribute("placeholder");
		case "Password":
			return password.getAttribute("placeholder");
		default:
			return null;
		}
	}

	public String FirstNameErr() {

		waitforWebElement(Err_firstname, 1);
		if (Err_firstname.isDisplayed())
			return Err_firstname.getText();
		else
			return "";

	}

	public String LastNameErr() {
		waitforWebElement(Err_lastname, 1);
		if (Err_lastname.isDisplayed())
			return Err_lastname.getText();
		else
			return "";
	}

	public String EmailErr() {
		waitforWebElement(Err_email, 1);
		if (Err_email.isDisplayed())
			return Err_email.getText();
		else
			return "";
	}

	/**
	 * Wait for Error to Appear Then check it is displayed then return its text or
	 * null
	 */
	public String PasswordErr() {
		try {
			waitforWebElement(Err_password, 1);

			if (Err_password.isDisplayed())
				return Err_password.getText();
			return null;
		} catch (org.openqa.selenium.TimeoutException e) {
			return null;
		}
	}

	/**
	 * Clicks on the option from the list of right column
	 * 
	 * @param option text of element to click
	 */
	public void ClickOnRightColumn(String option) {

		RCL.ClickOnElement(option);
	}

	public void TypeFirstName(String First_Name) {

		scrollToElement(firstname);
		waitForElementInView(firstname);
		firstname.sendKeys(First_Name);
	}

	public void TypeLastName(String Last_Name) {
		scrollToElement(lastname);
		waitForElementInView(lastname);
		lastname.sendKeys(Last_Name);

	}

	public void TypeEmail(String Email) {
		scrollToElement(email);
		waitForElementInView(email);
		email.sendKeys(Email);
	}

	public void TypePassword(String Password) {
		scrollToElement(password);
		waitForElementInView(password);
		password.sendKeys(Password);
	}

	public void SelectSubscribe(boolean check) {

		if (!elementInView(subscribe)) {

			scrollToElement(subscribe);
			waitForElementInView(subscribe);
		}

		if (check) {

			if (!subscribe.isSelected()) {
				subscribe.click();
			}
		} else {
			if (subscribe.isSelected()) {
				subscribe.click();
			}
		}
	}

	public void AgreeToPolicy(boolean agree) {

		if (!elementInView(privacyPolicy)) {
			scrollToElement(privacyPolicy);
			waitForElementInView(privacyPolicy);
		}

		if (agree) {

			if (!privacyPolicy.isSelected()) {
				privacyPolicy.click();
			}
		} else {
			if (privacyPolicy.isSelected()) {
				privacyPolicy.click();
			}
		}

	}
	/**
	 * Press continue on Registration Page wait for new account created page
	 * @return Page object of AccountCreatedPage or null if new account is not created  
	 * */

	public AccountCreatedPage Continue() {
		if (!elementInView(continueBtn)) {

			scrollToElement(continueBtn);
			waitForElementInView(continueBtn);
		}

		continueBtn.click();
		try {
			waitforPageLoad( AccountCreatedPage.Title, 1);
			return new AccountCreatedPage(driver);
		}catch (TimeoutException te) {
			return null;
		}
	}

}
