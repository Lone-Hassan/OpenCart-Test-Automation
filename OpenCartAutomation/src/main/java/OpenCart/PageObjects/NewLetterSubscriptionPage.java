package OpenCart.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewLetterSubscriptionPage extends PageHeader {

	WebDriver driver;
	public static final String Title = "Newsletter Subscription"; 
	public NewLetterSubscriptionPage(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "input-newsletter")
	WebElement Subscription_Cheeckbox;
	
	

	public boolean Subscription_On() {

		return Subscription_Cheeckbox.isSelected();
	}

	public void Subscribe(boolean subscribe) {
		
		if (subscribe) {
			if (!Subscription_On()) {
				Subscription_Cheeckbox.click();
			}
		}else
		{
			if(Subscription_On()) {
				Subscription_Cheeckbox.click();
			}
		}
	}
}
