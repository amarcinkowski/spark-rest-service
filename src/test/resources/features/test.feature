Feature: Add Company
        As a user I should able to add a new Company.
 
 Scenario: I open /create page
        Given I navigate to "http://localhost:4567"
        When I click on element having id "addcompany"
        Then I can see "Owners"