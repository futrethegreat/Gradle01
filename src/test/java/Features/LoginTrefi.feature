@Feature
Feature: LoginFeature
  This feature deals with the login functionality of the application

  Scenario Outline: Login with correct credentials Outline
    #Given I navigate to the main Trefi page
    #And I click on Sign In link
    Given I navigate to the secure Trefi page
    And On Secure Trefi Page I enter <UserName> and <Password>
    And I click Login button
    Then I should see the Tool main page

    Examples: 
      | UserName  | Password | FirstName | LastName |
      #| admin    			| admin    		| Admin     | Admin    |
      | 53001494H | USVeBrQm | David     | Sauce    |
