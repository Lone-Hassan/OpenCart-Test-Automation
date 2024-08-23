package OpenCart;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import OpenCart.PageObjects.ChangePasswordPage;
import OpenCart.PageObjects.HomePage;
import OpenCart.PageObjects.LogOutPage;
import OpenCart.PageObjects.LoginPage;
import OpenCart.PageObjects.MyAccountPage;
import OpenCart.resources.BaseTest;
import OpenCart.resources.dataProvider;
import OpenCart.resources.Retry;

public class TS_002_LoginFunctionality extends BaseTest {

	LoginPage LP;
	HomePage HP;

	/**
	 * Lands On Login page, Logout first in case Not on Login page
	 */
	@BeforeMethod(alwaysRun=true)
	public void LandOnLoginPage() {

		System.out.println("in Before method");
		HP = visit("http://localhost/opencart/upload/");
		LP = (LoginPage) HP.selectAcount("Login");
		LP.waitforPageLoad(LoginPage.Title, 1);
		System.out.println("out Before method login functionality");
	}

	/**
	 * Log out before next test if logged in
	 */
	@AfterMethod(alwaysRun=true)
	public void LogOut() {
		System.out.println("IN After method");
		LogOutPage LOP = (LogOutPage) LP.selectAcount("Logout");
		if (LOP != null) {
			System.out.println("User Logged in");
			LP.waitforPageLoad("Account Logout", 3);
			driver.findElement(By.cssSelector("a[class='btn btn-primary']")).click();
			LP.waitforPageLoad(HomePage.Title, 1);
		}
		System.out.println("Out After Method");

	}

	/**
	 * Validate logging into the Application using valid credentials
	 */
	@Test(dataProvider = "ValidLoginData", dataProviderClass = dataProvider.class,
			groups={"Sanity","Regression"})
	public void TC_LF_001(String email, String password) {
		System.out.println("IN TC_LF_001");
		String PageTitle = LP.login(email, password);
		Assert.assertEquals(PageTitle, MyAccountPage.Title, "Login UnSuccefull");
		System.out.println("TC_LF_001 passed");
	}

	/**
	 * Validate logging into the Application using invalid credentials (i.e. Invalid
	 * email address and Invalid Password)
	 */
	@Test(dataProvider = "InValidLoginData", dataProviderClass = dataProvider.class
			,groups={"Sanity","Regression"})
	public void TC_LF_002(String email, String password) {
		System.out.println("IN TC_LF_002 ");
		String currentPage = LP.login(email, password);
		Assert.assertEquals(currentPage, LoginPage.Title, "Login Succefull");
		Assert.assertEquals(LP.toastmsg(), "Warning: No match for E-Mail Address and/or Password.");

		System.out.println("TC_LF_002 passed");
	}

	@Test(groups={"Regression"})
	public void TC_LF_005() {

		System.out.println("IN TC_LF_005 ");

		LP.Submit();
		Assert.assertEquals(LP.toastmsg(), "Warning: No match for E-Mail Address and/or Password.");

		System.out.println("TC_LF_005 passed");
	}

	/**
	 * Page back should not logout after successful login.
	 */
	@Test(dataProvider = "ValidLoginData", dataProviderClass = dataProvider.class
			,groups={"Regression"},retryAnalyzer = Retry.class)
	public void TC_LF_009(String email, String password) {
		System.out.println("IN TC_LF_009 ");

		String PageTitle = LP.login(email, password);
		System.out.println("current page is: " + PageTitle);
		Assert.assertEquals(PageTitle, MyAccountPage.Title, "Login UnSuccessfull");
		LP.NavigateBack();
		try {
			LP.waitforPageLoad(MyAccountPage.Title, 1);
			Assert.assertTrue(true, "Page back should not logout after successful login.");
		} catch (TimeoutException e) {
			Assert.assertTrue(false, "Page-back should not logout user after successful login.");
		}
		System.out.println("TC_LF_009 passed");
	}

	/**
	 * Page back should not log in user after successful login.
	 **/
	@Test(dataProvider = "ValidLoginData", dataProviderClass = dataProvider.class
			,groups={"Regression"})
	public void TC_LF_010(String email, String password) {

		String PageTitle = LP.login(email, password);
		Assert.assertEquals(PageTitle, MyAccountPage.Title, "Login UnSuccessfull");
		LP.selectAcount("Logout");
		LP.NavigateBack();

		Assert.assertTrue(LP.selectAcount("Logout") == null, "Page back should not log in user after succesful logout");
	}

	/**
	 * Warning: Your account has exceeded allowed number of login attempts.Please
	 * try again in 1 hour. Should be shown on 5th unsuccessful attempt.
	 */
	@Test(groups={"Regression"})
	public void TC_LF_012() {

		int Attempt = 1;
		do {

			LP.login("admin@adm.com", "adm");
			LP.clearEmail();
			LP.clearPassword();
			Attempt++;
			if (Attempt == 6) {
				Assert.assertTrue(LP.toastmsg().contains("Your account has exceeded allowed number of login attempts"),
						"On Fifth Login attemp message: Warning: Your account has exceeded allowed number of login attempts. "
								+ "Please try again in 1 hour.");
			}
			// LP.selectAcount("Login");
			// LP.waitforPageLoad("Account Login", 3);
		} while (Attempt < 6);

	}

	/**
	 * Validate the copying of the text entered into the Password field
	 * 
	 * @throws IOException
	 * @throws UnsupportedFlavorException
	 */
	@Test(groups={"Regression"})
	public void TC_LF_014() throws UnsupportedFlavorException, IOException {
		HP.clearClipBoard();
		String pass = "asdf";
		LP.TypePassword(pass);
		LP.TypePassword(Keys.chord(Keys.CONTROL, "a"));
		LP.TypePassword(Keys.chord(Keys.CONTROL, "c"));
		Assert.assertFalse(pass.equals(HP.GetClipBoard()), "Password content copied");

	}

	@Test(dataProvider = "ChangePasswordData", dataProviderClass = dataProvider.class
			,groups={"Regression"})
	public void TC_LF_016(String email, String password, String newPassword) {

		LP.login(email, password);
		ChangePasswordPage CPP = (ChangePasswordPage) LP.RCL.ClickOnElement("Password");

		CPP.TypeOldPassword(password);
		CPP.TypeNewPassword(newPassword);
		CPP.Continue();

		MyAccountPage MAP = new MyAccountPage(driver);

		Assert.assertEquals(MAP.alertMsg(), "Success: Your password has been successfully updated.");

		MAP.selectAcount("Logout");
		MAP.waitforPageLoad("Account Logout", 1);
		driver.findElement(By.cssSelector("a[class='btn btn-primary']")).click();
		MAP.waitforPageLoad(HomePage.Title, 2);
		MAP.selectAcount("Login");
		MAP.waitforPageLoad(LoginPage.Title, 1);
		LP.login(email, newPassword);
		try {
			LP.waitforPageLoad(MyAccountPage.Title, 1);
		} catch (TimeoutException e) {
			Assert.assertTrue(false, "Landed on MY Account Page");
		}
		Assert.assertTrue(true, "Landed on MY Account Page");
	}

}
