Feature: Labs Management

  @createLabByTenantAdmin
  Scenario: Create a lab by tenant admin
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And search box enter search for team name
    And select the searched team from the list
    And navigate to labs and click on labs
    And click on request lab button
    And search for the plan id and click on that tile
    And click on subscribe button
    And navigate to labs and click on labs
    And verify the lab is created in the subscriptions table
    And verify the latest action status is "Complete"
    
   

