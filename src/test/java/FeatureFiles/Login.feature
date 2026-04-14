
Feature: Login to the Orgo portal
  
  Scenario: Login orgo portal with valid credentials
    Given Open browser and enter the url
    And Login page should display
    When login as tenant admin
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed
    
  Scenario: Login orgo portal with valid username and invalid password
    Given Open browser and enter the url
    And Login page should display
    When Enter valid username and invalid password
    And click on Login 
    Then validate error message is displayed
    
  Scenario: Login orgo portal with invalid username and valid password
    Given Open browser and enter the url
    And Login page should display
    When Enter invalid username and valid password
    And click on Login 
    Then validate error message is displayed
    
  Scenario: Login orgo portal with invalid username and invalid password
    Given Open browser and enter the url
    And Login page should display
    When Enter invalid username and invalid password
    And click on Login 
    Then validate error message is displayed
    
  Scenario: Login orgo portal with empty values click on login button
    Given Open browser and enter the url
    And Login page should display
    When Enter empty username and password
    And click on Login 
    Then validate error message is displayed
    
  Scenario: Login orgo portal with user details
    Given Open browser and enter the url
    And Login page should display
    When login as user
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed
   
  Scenario: Login orgo portal with mspadmin details
    Given Open browser and enter the url
    And Login page should display
    When login as mspadmin
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed
    

