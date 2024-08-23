#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Validate Add to Cart feature
  As a user i want to add items to my cart to purchase them later

  Background: 
    Given The user is on home page

  @tag1
  Scenario Outline: Validate adding the product to Cart from 'Product Display' Page
    Given Enter any "<existing Product>" name into the Search text box field
    And Click on the button having search icon
    When Click on the "<existing Product>" in the Search results
    And  'Add to Cart' "<existing Product>" on the 'Product Display' page
    Then Success message with text - 'Success: You have added <existing Product> to your shopping cart!' should be displayed
    When Click on the 'Shopping Cart'
    Then "<existing Product>" should be successfully displayed in the Shopping Cart page

    Examples: 
      | existing Product |
      | iMac             |

  Scenario Outline: Validate adding the product to Cart from 'Wish List' Page
    Given User is logged in with "<user name>" and "<password>"
    And "<existing Product>" is added to wish list
    When Click on the 'Wish List'
    And  'Add to Cart' "<existing Product>" on the 'Wish list' page
    Then Success message with text - 'Success: You have added <existing Product> to your wish list!' should be displayed
    When Click on the 'Shopping Cart'
		Then "<existing Product>" should be successfully displayed in the Shopping Cart page
		
		Examples:
			| existing Product |user name     |password|
      | iMac             |lone@admin.com|admin   |
      
   Scenario Outline: Validate adding the product to Cart from 'Search Results' Page
   Given Enter any "<existing Product>" name into the Search text box field
   And Click on the button having search icon
   When 'Add to Cart' "<existing Product>" on the 'Search Results' page
   Then Success message with text - 'Success: You have added <existing Product> to your shopping cart!' should be displayed
   When Click on the 'Header Cart'
   And 'View Cart' from header cart
   Then "<existing Product>" should be successfully displayed in the Shopping Cart page
   
   Examples: 
      | existing Product |
      | iMac             |
    
	Scenario Outline: Validate adding the product to Cart from the Related Products section of the 'Product Display' Page
		Given Enter any "<existing Product>" name into the Search text box field
    And Click on the button having search icon
    When Click on the "<existing Product>" in the Search results
    And  'Add to Cart' 'Related Product' on the 'Product Display' page
    Then Success message with text - 'Success: You have added <existing Product> to your shopping cart!' should be displayed
    And Click on the 'Shopping Cart'
    Then "<existing Product>" should be successfully displayed in the Shopping Cart page
    Examples: 
      | existing Product |
      | Apple Cinema 30\"|