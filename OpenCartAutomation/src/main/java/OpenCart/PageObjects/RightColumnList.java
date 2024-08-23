package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import OpenCart.AbstractComponents.AbstractComponents;

public class RightColumnList extends AbstractComponents {

	WebDriver driver;

	public static final String Login="Login";
	public static final String Register="Register";
	public static final String ForgottenPassword="Forgotten Password";
	public static final String MyAccount="My Account";
	public static final String AddressBook="Address Book";
	public static final String Newsletter="Newsletter";
	public static final String Logout="Logout";
	
	
	public RightColumnList(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = ".list-group.mb-3>a")
	List<WebElement> rightColumnList;

	/**
	 * Clicks on the option in Right column List
	 * 
	 * @param option Text for element to click
	 * 
	 * @return corresponding page object or null if option not found
	 * 
	 */
	public AbstractComponents ClickOnElement(String option) {

		if (SelectElementByText(rightColumnList, option)) {

			switch (option) {

			case "Login":
				LoginPage LP = new LoginPage(driver);
				LP.waitforPageLoad(LoginPage.Title, 1);
				return LP;
			case "Register":
				RegisterPage RP = new RegisterPage(driver);
				RP.waitforPageLoad(RegisterPage.Title, 1);
				return RP;
			case "Forgotten Password":
				return null;
			case "My Account":
				MyAccountPage MAP = new MyAccountPage(driver);
				MAP.waitforPageLoad(MyAccountPage.Title, 1);
				return MAP;
			case "Address Book":
				return null;
			case "Newsletter":
				NewLetterSubscriptionPage NSP = new NewLetterSubscriptionPage(driver);
				NSP.waitforPageLoad(NewLetterSubscriptionPage.Title, 1);
				return NSP;
			case "Logout":
				LogOutPage LOP = new LogOutPage(driver);
				LOP.waitforPageLoad(LogOutPage.Title, 1);
				return LOP;
			case "Password":

				ChangePasswordPage CPP = new ChangePasswordPage(driver);
				CPP.waitforPageLoad(ChangePasswordPage.Title, 1);
				return CPP;
			default:
				return null;
			}
		}

		return null;

	}
}
