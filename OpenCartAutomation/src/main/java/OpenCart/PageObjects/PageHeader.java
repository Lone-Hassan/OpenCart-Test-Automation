package OpenCart.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import OpenCart.AbstractComponents.AbstractComponents;

public class PageHeader extends AbstractComponents {
	WebDriver driver;

	public static class AccountDropDown {
		public static final String Register = "Register";
		public static final String Login = "Login";
		public static final String MyAccount = "My Account";
		public static final String Logout = "Logout";
		public static final String OrderHistory = "Order History";
		public static final String Transactions = "Transactions";
		public static final String Downloads = "Downloads";
	}

	public PageHeader(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="#header-cart")
	WebElement header_cart;
	
	@FindBy(css="#header-cart ul")
	WebElement header_cart_dropdown;
	
	@FindBy(css = ".img-fluid")
	WebElement logo;

	@FindBy(id = "form-currency")
	WebElement Currency;

	@FindBy(css = ".list-inline-item>.dropdown")
	WebElement Accounts;

	@FindBy(id = "wishlist-total")
	WebElement Wish_List;

	// .list-inline-item>a[title='Shopping Cart']
	@FindBy(css = ".list-inline-item>a[title='Shopping Cart']")
	WebElement ShoppingCart;

	// .list-inline-item>a[title='Checkout']
	@FindBy(css = ".list-inline-item>a[title='Checkout']")
	WebElement CheckOut;

	@FindBy(id = "alert")
	WebElement alertMsg;

	@FindBy(css = ".alert.alert-danger.alert-dismissible")
	WebElement alert;

	// .list-inline-item>.dropdown>ul>li
	@FindBy(css = ".dropdown>.dropdown-menu.dropdown-menu-right.show>li")
	List<WebElement> accountOptions;

	@FindBy(css = ".dropdown-menu.show>li")
	List<WebElement> currencyOptions;
	
	@FindBy(css="#search input")
	WebElement searchBox;
	
	@FindBy(css="#search button")
	WebElement searchIcon; 
	
	/**
	 * scroll to header cart and click then wait for 1 second for header cart dropdown
	 * */
	public void openheaderCart() {
		scrollTo(header_cart);
		header_cart.click();
		waitforWebElement(header_cart_dropdown,1);
		
	}
	
	/**
	 * View cart from header cart and wait for one second for page to load
	 * @return shopping cart page object
	 * */
	public ShoppingCartPage viewCart() {
		header_cart_dropdown.findElement(By.partialLinkText("View Cart")).click();
		waitforPageLoad(ShoppingCartPage.title,1);
		return new ShoppingCartPage(driver);
	}
	/**
	 * Types in the search box, if not in view scroll to it first
	 * @param item to search
	 * */
	public void inputSearch(String item) {
		
		scrollTo(searchBox);

		searchBox.sendKeys(item);
	}
	
	/**
	 * Clicks in the search icon, if not in view scroll to it first
	 * @return searchPage object
	 * */
	public SearchPage pressSearchIcon() {
		
		scrollTo(searchIcon);
		searchIcon.click();
		return new SearchPage(driver);
	}
	/**
	 * Check option is present in Account drop down in page header
	 * 
	 * @return boolean
	 */
	public boolean AccountDropdownContains(String option) {

		if (!elementInView(Accounts)) {

			scrollToElement(Accounts);
			waitForElementInView(Accounts);
		}

		boolean optExists = false;
		Accounts.click();

		for (WebElement opt : accountOptions) {

			if (opt.getText().equalsIgnoreCase(option)) {
				// System.out.println(Text + " :Found");
				optExists = true;
			}
		}

		Accounts.click();
		return optExists;
	}

	/**
	 * Scroll to Account drop down if not in view, Select account option from Page
	 * header and wait for corresponding page title.
	 * 
	 * @param opt is text of the option to click
	 * 
	 * @return object of the corresponding page if option is in the list and
	 *         clicked, null otherwise
	 */
	public AbstractComponents selectAcount(String opt) {

		if (!elementInView(Accounts)) {
			System.out.println("Accounts Not in view");
			scrollToElement(Accounts);
			waitForElementInView(Accounts);
		}
		Accounts.click();
		System.out.println("accounts clicked");
		if (SelectElementByText(accountOptions, opt)) {
			switch (opt) {
			case "Login":
				waitforPageLoad(LoginPage.Title, 1);
				return new LoginPage(driver);
			case "Register":
				waitforPageLoad(RegisterPage.Title, 1);
				return new RegisterPage(driver);
			case "My Account":
				waitforPageLoad(MyAccountPage.Title, 1);
				return new MyAccountPage(driver);
			case "Logout":
				waitforPageLoad(LogOutPage.Title, 1);
				return new LogOutPage(driver);
			default:
				Accounts.click();
				return null;
			}
		}

		Accounts.click();
		return null;
	}

	
	
	/**
	 * Scrolls and clicks on shopping cart link in page header
	 * @return Shopping cart page object
	 * */
	public ShoppingCartPage gotoShoppingCart() {
		if (!elementInView(ShoppingCart)) {
			scrollToElement(ShoppingCart);
			waitForElementInView(ShoppingCart);
		}
		ShoppingCart.click();
		waitforPageLoad(ShoppingCartPage.title,1);
		return new ShoppingCartPage(driver);
	}

	public WishListPage gotoWhishList() {
		
		if (!elementInView(Wish_List)) {
			scrollToElement(Wish_List);
			waitForElementInView(Wish_List);
		}
		Wish_List.click();
		waitforPageLoad(WishListPage.title,1);
		return new WishListPage(driver);
	}

	public boolean selectCurrency(String currency) {
		Currency.click();
		return SelectElementByText(currencyOptions, currency);
	}

	public void gotoCheckOut() {
		CheckOut.click();
	}

	public String toastmsg() {
		waitforWebElement(alertMsg.findElement(By.cssSelector("dirv")), 1);
		String alrtMsg = alertMsg.findElement(By.cssSelector("dirv")).getText();
		return alrtMsg;

	}
	/**
	 * Gets the success text message and closes toast
	 * @return text of the alert message.
	 * */
	public String successMsg() {
		
		String msg=alertMsg.findElement(By.cssSelector("div")).getText();
		driver.findElement(By.cssSelector(".btn-close")).click();
		//return null;
		return msg;
		
	}

	/**
	 * Click on Logo and wait for home page to load for one second.
	 * 
	 * @return HomePage Object or null if not directed to Home page.
	 * 
	 */
	public HomePage clickLogo() {

		logo.click();
		try {
			waitforPageLoad(HomePage.Title, 1);
			return new HomePage(driver);
		} catch (TimeoutException te) {
			return null;
		}
	}

}
