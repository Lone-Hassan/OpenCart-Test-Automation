package OpenCart.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;

import OpenCart.PageObjects.HomePage;
import OpenCart.PageObjects.LoginPage;
import OpenCart.PageObjects.PageHeader;
import OpenCart.PageObjects.ProductDisplayPage;
import OpenCart.PageObjects.SearchPage;
import OpenCart.PageObjects.ShoppingCartPage;
import OpenCart.PageObjects.WishListPage;
import OpenCart.resources.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class addToCartStepImplementation extends BaseTest {

	// PageHeader PG;
	HomePage HP;
	SearchPage SP;
	ProductDisplayPage PD;
	ShoppingCartPage SCP;
	WishListPage WLP;

	@Given("The user is on home page")
	public void The_user_is_on_home_page() throws IOException {
		DriverInitialization();
		HP = visit(prop.getProperty("url"));
	}

	@Given("Enter any {string} name into the Search text box field")
	public void enter_any_name_into_the_search_text_box_field(String item) {
		// Write code here that turns the phrase above into concrete actions
		HP.inputSearch(item);

	}

	@Given("Click on the button having search icon")
	public void click_on_the_button_having_search_icon() {
		// Write code here that turns the phrase above into concrete actions
		SP = HP.pressSearchIcon();
	}

	@When("Click on the {string} in the Search results")
	public void click_on_the_in_the_search_results(String existingProduct) {
		// Write code here that turns the phrase above into concrete actions
		PD = SP.selectProductFromProductList(existingProduct);
	}

	@Then("Success message with text - {string} should be displayed")
	public void success_message_with_text_should_be_displayed(String msg) {
		// Write code here that turns the phrase above into concrete actions
		// Assert.assertEquals(PD.successMsg(), msg, "Product added to cart");
		Assert.assertTrue(HP.successMsg().contains("Success:"), msg);
	}

	@When("Click on the {string}")
	public void click_on_the_link(String linkInPageHeader) {
		// Write code here that turns the phrase above into concrete actions
		//System.out.println("----------------------------" + linkInPageHeader + "-----------------");
		switch (linkInPageHeader) {
		case "Shopping Cart":
			//System.out.println("in Shopoing cart");
			SCP = HP.gotoShoppingCart();
			break;
		case "Wish List":
			//System.out.println("in wish list");
			WLP = HP.gotoWhishList();
			break;
		case "Header Cart":
			HP.openheaderCart();
			break;
			
		default:
			System.out.println("in default");

		}

	}

	@Then("{string} should be successfully displayed in the Shopping Cart page")
	public void product_should_be_successfully_displayed_in_the_page(String item) {
		// Write code here that turns the phrase above into concrete actions
		List<String> nameList = SCP.getItemNameList();
		Assert.assertTrue(nameList.contains(item), item + " in the cart");
	}

	@Given("User is logged in with {string} and {string}")
	public void user_is_logged_in_with_and(String username, String password) {
		// Write code here that turns the phrase above into concrete actions

		LoginPage LP = (LoginPage) HP.selectAcount(PageHeader.AccountDropDown.Login);
		LP.login(username, password);

	}

	@Given("{string} is added to wish list")
	public void is_added_to_wish_list(String item_name) {
		// Write code here that turns the phrase above into concrete actions
		enter_any_name_into_the_search_text_box_field(item_name);
		click_on_the_button_having_search_icon();
		SP.addProductToWishList(item_name);
		Assert.assertTrue(SP.successMsg().contains("Success:"), item_name + " added to wish list");

	}

	@When("{string} {string} on the {string} page")
	public void on_the_page(String action, String product_name, String page) {
		// Write code here that turns the phrase above into concrete actions
		switch (page) {
		case "Wish list":
			WLP.add_to_Cart(product_name);
			break;
		case "Product Display":
			switch (product_name) {
			case "Related Product":
				PD.add_To_Cart_RelatedProduct();
				break;
			default:
				PD.add_To_Cart();
				break;
			}
			break;
		case "Search Results":
			SP.add_to_Cart(product_name);
			break;

		}
	}
	
	@Then("{string} from header cart")
	public void from_header_cart(String action) {
	    // Write code here that turns the phrase above into concrete actions
		switch(action) {
		
		case "View Cart":
			SCP=HP.viewCart();
			break;
		}
	}

}
