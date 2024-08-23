package OpenCart.resources;
import org.testng.annotations.DataProvider;

public class dataProvider {
	/**
	 * Valid login credentials
	 */
	@DataProvider(name = "ValidLoginData")
	public Object[][] ValidLoginData() {

		return new Object[][] { { "lone@admin.com", "admin" } };
	}

	/**
	 * valid credentials and new password for change password
	 */
	@DataProvider(name = "ChangePasswordData")
	public Object[][] ChangePasswordData() {

		return new Object[][] { { "change@change.com", "change", "change" } };
	}

	/**
	 * InValid login credentials
	 */
	@DataProvider(name = "InValidLoginData")
	public Object[][] InValidLoginData() {

		return new Object[][] { { "admin@adm.com", "admin" }, { "admin@admin.com", "adm" },
				{ "admin@adm.com", "adm" } };
	}
	
	/**
	 * change password data
	 * */
	@DataProvider(name = "passwordData")
	public Object[][] passwordData(){
		
		return new Object[][] {{"12"},{"12345678912345678912"}};
	}
}
