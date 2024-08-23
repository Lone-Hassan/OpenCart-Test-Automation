package OpenCart;

import java.sql.SQLException;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import OpenCart.PageObjects.AccountCreatedPage;
import OpenCart.PageObjects.HomePage;
import OpenCart.PageObjects.LogOutPage;
import OpenCart.PageObjects.LoginPage;
import OpenCart.PageObjects.MyAccountPage;
import OpenCart.PageObjects.NewLetterSubscriptionPage;
import OpenCart.PageObjects.RegisterPage;
import OpenCart.resources.BaseTest;
import OpenCart.resources.dataProvider;

public class TS_01_RegistrationFunctionality extends BaseTest{
	
	RegisterPage RP;
	HomePage HP;
	
	/**
	 * Land On Register new account from Page header
	 * */
	@BeforeMethod(alwaysRun=true)
	public void visitRegistrationPage() {
		System.out.println("in Before method");
		HP=visit("http://localhost/opencart/upload/");
		
		RP = (RegisterPage) HP.selectAcount("Register");
		if(RP==null)
		{
			LogOutPage LOP = (LogOutPage) HP.selectAcount("Logout");	
			LOP.waitforPageLoad("Account Logout", 1);
			HP=LOP.Continue();
			RP = (RegisterPage) HP.selectAcount("Register");
		}
		
		//HP.waitforPageLoad("Register Account", 1);
	}
	
	@AfterMethod(alwaysRun=true)
	public void Logout() {
		System.out.println("in after method");
		LogOutPage LOP = (LogOutPage) HP.selectAcount("Logout");
		if (LOP!=null) {
			System.out.println("user was log in");
			HP=LOP.Continue();	
		}
		System.out.println("Out after method");
		
	}
	
	/**
	 * Validate Registering an Account by providing only the Mandatory fields
	 * */
	@Test(groups = {"Regression","Sanity"})
	public void TC_RF_001() throws SQLException {  
		
		
		OpenShopDB();
		db.delAccount("lone@ced13.com");
		RP.TypeFirstName("Hassan");
		RP.TypeLastName("Afzal");
		RP.TypeEmail("lone@ced13.com");
		RP.TypePassword("pass");
		RP.SelectSubscribe(true);
		RP.AgreeToPolicy(true);
		AccountCreatedPage ACP=RP.Continue();
		
		
		Assert.assertEquals(ACP.getTitle(), "Your Account Has Been Created!");
		MyAccountPage MAP=ACP.Continue();
		
		Assert.assertTrue(MAP.getTitle().equalsIgnoreCase(MyAccountPage.Title), "Landed On My Account Page");
		
		System.out.println("in TC_RF_001");
	}
	
	
	
	/**
	 * Validate proper notification messages are displayed for the mandatory fields,
	 *  when you don't provide any fields in the 'Register Account' page and submit
	 * */
	@Test(groups= {"Regression"})
	public void TC_RF_004() {
		
		
		RP.Continue();
		Assert.assertTrue(RP.EmailErr().length()>0,"Email Error displayed");
		Assert.assertTrue(RP.FirstNameErr().length()>0,"First Name Error displayed");
		Assert.assertTrue(RP.LastNameErr().length()>0,"Last Name Error displayed");
		Assert.assertTrue(RP.PasswordErr().length()>0,"Password Error displayed");
		Assert.assertEquals(RP.toastmsg(), "Warning: You must agree to the Privacy Policy!");
		System.out.println("in TC_RF_004");
	}
	
	
	
	/**
	 * Default subscription ON on creating new account without newsletter subscription 
	 * */
	@Test(groups= {"Regression"})
	public void TC_RF_005() throws SQLException {
		
		OpenShopDB();
		db.delAccount("lone@ced13.com");
		RP.TypeFirstName("Hassan");
		RP.TypeLastName("Afzal");
		RP.TypeEmail("lone@ced13.com");
		RP.TypePassword("pass");
		RP.SelectSubscribe(true);
		RP.AgreeToPolicy(true);
		AccountCreatedPage ACP =RP.Continue();
		Assert.assertFalse(ACP==null,"Landed on New Account Created page");
		NewLetterSubscriptionPage NSP = (NewLetterSubscriptionPage) ACP.ClickOnRightColumn("Newsletter");
		ACP.waitforPageLoad(NewLetterSubscriptionPage.Title, 2);
		Assert.assertTrue(NSP.getTitle().equalsIgnoreCase(NewLetterSubscriptionPage.Title), "Landed on Newsletter Subscription Page");
		Assert.assertTrue(NSP.Subscription_On(), "By Default not Subscribed");
		
		System.out.println("in TC_RF_005");
	}
	
	
	/**
	 * Default subscription off on creating new account without newsletter subscription 
	 * */
	@Test(groups= {"Regression"})
	public void TC_RF_006() throws SQLException {
		
		OpenShopDB();
		db.delAccount("lone@ced13.com");
		RP.TypeFirstName("Hassan");
		RP.TypeLastName("Afzal");
		RP.TypeEmail("lone@ced13.com");
		RP.TypePassword("pass");
		RP.SelectSubscribe(false);
		RP.AgreeToPolicy(true);
		RP.Continue();
		AccountCreatedPage ACP = new AccountCreatedPage(driver);
		ACP.waitforPageLoad( "Your Account Has Been Created!", 2);
		Assert.assertEquals(ACP.getTitle(), "Your Account Has Been Created!");
		
		ACP.ClickOnRightColumn("Newsletter");
		ACP.waitforPageLoad("Newsletter Subscription", 2);
		
		NewLetterSubscriptionPage NSP = new NewLetterSubscriptionPage(driver);
		Assert.assertTrue(NSP.getTitle().equalsIgnoreCase("Newsletter Subscription"), "Navigating to Newsletter Subscription");
		Assert.assertTrue(!NSP.Subscription_On(), "By Default not Subscribed");
		
		/*
		 * NSP.selectAcount("Logout"); NSP.waitforPageLoad("Account Logout", 2);
		 * driver.findElement(By.cssSelector("a[class='btn btn-primary']")).click();
		 * NSP.waitforPageLoad("Your Store", 2);
		 */
		System.out.println("in TC_RF_006");
	}
	
	/**
	 * 
	 * Validate different ways of navigating to 'Register Account' page
	 * 
	 * */
	@Test(groups={"Smoke"})
	public void TC_RF_007() {
		
		Assert.assertEquals(RP.getTitle(), RegisterPage.Title);
		LoginPage lp = (LoginPage) RP.selectAcount("Login");
		RP = lp.NewCustomer();
		Assert.assertTrue(lp.getTitle().equalsIgnoreCase(RegisterPage.Title),"Landed on Register Account");	
		RP.selectAcount("Login");
		RP=(RegisterPage) lp.ClickOnRightColumn("Register");
		Assert.assertTrue(RP.getTitle().equalsIgnoreCase(RegisterPage.Title),"Landed on Register Account");	
		System.out.println("in TC_RF_007");
	}
	
	
	
	/**  
	 * Validate Register account with invalid Password
	 * */
	@Test(dependsOnMethods = { "TC_RF_001" ,"TC_RF_005","TC_RF_006","TC_RF_015"},
								dataProvider = "passwordData",dataProviderClass = dataProvider.class,
								groups= {"Regression"})
				
	public void TC_RF_008(String password) throws SQLException  {
		
		OpenShopDB();
		db.delAccount("lone@ced13.com");
		RP.TypeFirstName("Hassan");
		RP.TypeLastName("Afzal");
		RP.TypeEmail("lone@ced13.com");
		RP.TypePassword(password);
		RP.SelectSubscribe(false);
		RP.AgreeToPolicy(true);
		RP.Continue();
		Assert.assertTrue(RP.PasswordErr()!=null, "Password Error shown!");
		System.out.println("in TC_RF_008");
		
	}
	
	
	/**
	 * Validate Registering an Account by providing the existing account details (i.e. existing email address)
	 * */
	@Test(groups= {"Regression"})
	public void TC_RF_009()  {
		
		//System.out.println("ExistingAccount_Not_Created");
		//HomePage HP = visit("http://localhost/opencart/upload/");
		RP.TypeFirstName("Hassan");
		RP.TypeLastName("Afzal");
		RP.TypeEmail("lone@abc.com");
		RP.TypePassword("pass");
		RP.SelectSubscribe(true);
		RP.AgreeToPolicy(true);
		RP.Continue();
		
		Assert.assertEquals(RP.toastmsg(), "Warning: E-Mail Address is already registered!");
		System.out.println("in TC_RF_009");
	}
	
	/**
	 * Validate Registering an Account by using the Keyboard keys (i.e Tab key)
	 * @throws SQLException 
	 **/
	@Test(groups= {"Regression"})
	public void TC_RF_012() throws SQLException {
		
		
		OpenShopDB();
		db.delAccount("123@abc.com");
		RP = new RegisterPage(driver);
		RP.selectAcount("Register");
		RP.waitforPageLoad("Register Account", 1);
		RP.TypeFirstName("Hassan");
		RP.Press(Keys.TAB);
		RP.TypeLastName("Lone");
		RP.Press(Keys.TAB);
		RP.TypeEmail("123@abc.com");
		RP.Press(Keys.TAB);
		RP.TypePassword("pass");
		RP.Press(Keys.TAB);
		RP.SelectSubscribe(false);
		RP.Press(Keys.TAB);
		RP.Press(Keys.TAB);
		RP.AgreeToPolicy(true);
		AccountCreatedPage ACP = RP.Continue();
		
		Assert.assertEquals(ACP.getTitle(), AccountCreatedPage.Title );
		/*
		 * RP.selectAcount("Logout"); RP.waitforPageLoad("Account Logout", 2);
		 * driver.findElement(By.cssSelector("a[class='btn btn-primary']")).click();
		 * RP.waitforPageLoad("Your Store", 2);
		 */
		System.out.println("in TC_RF_012");
	}


	/**
	 * Validate all the fields in the Register Account page have the proper placeholder
	 * */
	@Test(groups= {"Regression"})
	public void TC_RF_013() {
		Assert.assertEquals(RP.PlaceHolder("FirstName"), "First Name");
		Assert.assertEquals(RP.PlaceHolder("LastName"), "Last Name");
		Assert.assertEquals(RP.PlaceHolder("Email"), "E-Mail");
		Assert.assertEquals(RP.PlaceHolder("Password"), "Password");
		System.out.println("in TC_RF_013");
	}
	
	/**
	 * Validate the details that are provided while Registering an Account are stored in the Database 
	 * @throws SQLException 
	 * */
	@Test(dependsOnMethods = { "TC_RF_001" ,"TC_RF_005","TC_RF_006"},
			groups= {"Regression"})
	public void TC_RF_015() throws SQLException {
		
		OpenShopDB();
		Assert.assertTrue(db.AccountExists("lone@ced13.com"),"Account stored in database");
		System.out.println("in TC_RF_015");
	}
	
	/**
	 * Validate whether the Mandatory fields in the Register Account page are accepting only spaces
	 * */
	@Test (groups= {"Regression"})
	public void TC_RF_016() {
		
		RP.TypeFirstName("    ");
		RP.TypeLastName("    ");
		RP.TypeEmail("    ");
		RP.TypePassword("   ");
		RP.SelectSubscribe(true);
		RP.AgreeToPolicy(false);
		RP.Continue();
		
		
		Assert.assertTrue(RP.EmailErr().length()>0,"Email Error displayed");
		Assert.assertTrue(RP.FirstNameErr().length()>0,"First Name Error displayed");
		Assert.assertTrue(RP.LastNameErr().length()>0,"Last Name Error displayed");
		Assert.assertTrue(RP.PasswordErr().length()>0,"Password Error displayed");
		Assert.assertEquals(RP.toastmsg(), "Warning: You must agree to the Privacy Policy!");
		System.out.println("in TC_RF_016");
		
	}
	

}