package OpenCart.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePasswordPage extends PageHeader {

	WebDriver driver;
	RightColumnList RCL;

	public static final String Title = "Change Password";

	public ChangePasswordPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.RCL = new RightColumnList(driver);
	}

	@FindBy(id = "input-password")
	WebElement old_password;

	@FindBy(id = "input-confirm")
	WebElement new_password;

	@FindBy(css = ".btn.btn-primary")
	WebElement Continue;

	@FindBy(css = "a.btn.btn-light")
	WebElement Back;

	public void TypeOldPassword(String pass) {

		old_password.sendKeys(pass);
	}

	public void TypeNewPassword(String pass) {

		new_password.sendKeys(pass);
	}
	public void Continue() {
		
		Continue.click();
	}
	
	public void Back() {
		
		Back.click();
	}
}
