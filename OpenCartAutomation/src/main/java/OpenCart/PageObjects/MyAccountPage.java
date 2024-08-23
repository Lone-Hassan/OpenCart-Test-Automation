package OpenCart.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends PageHeader {

	WebDriver driver;
	public RightColumnList RCL;

	public static final String Title = "My Account";

	public MyAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL = new RightColumnList(driver);
	}

	@FindBy(css = ".alert.alert-success.alert-dismissible")
	WebElement alertMsg;

	public String alertMsg() {
		if (alertMsg.isDisplayed()) {
			return alertMsg.getText();
		}
		return "";
	}

}
