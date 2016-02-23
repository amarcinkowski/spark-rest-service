Feature: Add Company
        As a user I should able to add a new Company.
 
 Scenario: I open main page
        Given I navigate to "home"
        When I click on element having id "Add Company"
        Then I can see "Name*"
        And I can see "Phone number"
        And I can see "* Required fields"
