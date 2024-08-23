package OpenCart;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import OpenCart.PageObjects.HomePage;
import OpenCart.PageObjects.LogOutPage;
import OpenCart.PageObjects.LoginPage;
import OpenCart.PageObjects.MyAccountPage;
import OpenCart.PageObjects.PageHeader;
import OpenCart.PageObjects.RightColumnList;
import OpenCart.resources.BaseTest;

public class TS_003_LogoutFunctionality extends BaseTest{

	//RegisterPage RP;
	LoginPage LP;
	HomePage HP;
	LogOutPage LOP; // logout page object  
	
	
	/**
	 * Make user log in and on home page before every test
	 * */
	@BeforeMethod(alwaysRun=true)
	public void LandOnMyAccount() {
		
		System.out.println("in Before method");
		HP = visit("http://localhost/opencart/upload/");
		LP=(LoginPage) HP.selectAcount(PageHeader.AccountDropDown.Login);
		if (LP!=null) {
			LP.login("lone@admin.com", "admin");
			HP=LP.clickLogo();
		}
	}
	
	/**
	 * make user log out after every test case
	 * */
	@AfterMethod(alwaysRun=true)
	public void LogoutAccount() {
		
		System.out.println("in after method");
		LOP = (LogOutPage) HP.selectAcount(PageHeader.AccountDropDown.Logout);
		if (LOP!=null) {
			System.out.println("user was log in");
			HP=LOP.Continue();	
		}
		System.out.println("Out after method");
	
	}
	/**
	 * Validate Logging out by selecting Logout option from 'My Account' drop menu
	 * */
	
	@Test(groups={"Sanity","Regression","Smoke"})
	public void TC_LG_001(){
		LOP=(LogOutPage) HP.selectAcount(PageHeader.AccountDropDown.Logout);
		Assert.assertTrue(LOP!=null,"Log Out Option not in list");
		System.out.println("Syccess! TC_LG_001");
		
	}
	
	/**
	 * Validate Logging out by selecting Logout option from 'Right Column' options
	 * */
	@Test(groups = {"Regression","Smoke","Sanity"})
	public void TC_LG_002() {
		
		MyAccountPage MAP= (MyAccountPage) HP.selectAcount(PageHeader.AccountDropDown.MyAccount);
		LOP = (LogOutPage) MAP.RCL.ClickOnElement(RightColumnList.Logout);
		Assert.assertEquals(LOP.getTitle(), LogOutPage.Title,"Lands on Logout Page");
		Assert.assertTrue(LOP.AccountDropdownContains(PageHeader.AccountDropDown.Login), "Accounts Drop Down contains Login option");
		HP=LOP.Continue();
		Assert.assertEquals(HP.getTitle(), HomePage.Title,"Lands on Home Page");
		System.out.println("Syccess! TC_LG_002");
	}
	
	public void TC_LG_003() {
		
		
	}
	
	/**
	 * Validate logging out and browsing back
	 * */
	@Test (groups = {"Regression"}) 
	public void TC_LG_004() {
		
		LOP = (LogOutPage) HP.selectAcount(PageHeader.AccountDropDown.Logout);
		LOP.NavigateBack();
		Assert.assertFalse(HP.AccountDropdownContains(PageHeader.AccountDropDown.Logout), "User is logged in");
		System.out.println("Syccess! TC_LG_004");
	}
	
	/**
	 * Validate Logout option is not displayed under 'Account' drop down options before logging in
	 * */
	@Test(groups = {"Regression"}) 
	public void TC_LG_005() {
		
		LOP = (LogOutPage) HP.selectAcount(PageHeader.AccountDropDown.Logout);
		if (LOP!=null)
		{
			LOP.Continue();
		}
		Assert.assertFalse(HP.AccountDropdownContains(PageHeader.AccountDropDown.Logout),"Log out option is in Account drop down");
		
		System.out.println("Syccess! TC_LG_005");
		
	}
	
	/**
	 * Validate Logout option is not displayed under 'Right Column List' drop down options before logging in
	 * */
	@Test (groups = {"Regression"}) 
	public void TC_LG_006() {
		
		LOP = (LogOutPage) HP.selectAcount(PageHeader.AccountDropDown.Logout);
		if (LOP!=null)
		{
			LOP.Continue();
		}
		HP.selectAcount(PageHeader.AccountDropDown.Register);
		Assert.assertFalse(HP.AccountDropdownContains(PageHeader.AccountDropDown.Logout),"Log out option is in Account drop down");
		System.out.println("Syccess! TC_LG_006");
		
	}
	
	public void TC_LG_007() {}
}
