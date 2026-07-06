@login
Feature: Login to the Orgo portal

  @TC1 @smoke @positive
  Scenario: TC1 - Login orgo portal with valid credentials
    Given Open browser and enter the url
    And Login page should display
    When login as tenant admin
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed

  @TC2 @regression @negative
  Scenario: TC2 - Login orgo portal with valid username and invalid password
    Given Open browser and enter the url
    And Login page should display
    When Enter valid username and invalid password
    And click on Login
    Then validate error message is displayed

  @TC3 @regression @negative
  Scenario: TC3 - Login orgo portal with invalid username and valid password
    Given Open browser and enter the url
    And Login page should display
    When Enter invalid username and valid password
    And click on Login
    Then validate error message is displayed

  @TC4 @regression @negative
  Scenario: TC4 - Login orgo portal with invalid username and invalid password
    Given Open browser and enter the url
    And Login page should display
    When Enter invalid username and invalid password
    And click on Login
    Then validate error message is displayed

  @TC5 @regression @negative
  Scenario: TC5 - Login orgo portal with empty values click on login button
    Given Open browser and enter the url
    And Login page should display
    When Enter empty username and password
    And click on Login
    Then validate error message is displayed

  @TC6 @smoke @positive
  Scenario: TC6 - Login orgo portal with user details
    Given Open browser and enter the url
    And Login page should display
    When login as user
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed

  @TC7 @smoke @positive
  Scenario: TC7 - Login orgo portal with mspadmin details
    Given Open browser and enter the url
    And Login page should display
    When login as mspadmin
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed

  @TC8 @regression @positive
  Scenario: TC8 - Login orgo portal with sysadmin details
    Given Open browser and enter the url
    And Login page should display
    When login as sysadmin
    Then validate proper Home page is displayed
    And click on sysadmin logout
    Then validate login page is displayed

  @TC9 @regression @negative
  Scenario: TC9 - Login orgo portal with empty username and valid password
    Given Open browser and enter the url
    And Login page should display
    When Enter empty username and valid password
    And click on Login
    Then validate error message is displayed

  @TC10 @regression @negative
  Scenario: TC10 - Login orgo portal with valid username and empty password
    Given Open browser and enter the url
    And Login page should display
    When Enter valid username and empty password
    And click on Login
    Then validate error message is displayed

  @TC11 @regression @negative @specialCharLogin
  Scenario: TC11 - Login with special characters in username and password
    Given Open browser and enter the url
    And Login page should display
    When enter credentials with special characters
    And click on Login
    Then validate error message is displayed

  @TC12 @regression @positive @forgotPasswordLink
  Scenario: TC12 - Forgot password link visible on login page and navigates correctly
    Given Open browser and enter the url
    And Login page should display
    Then validate forgot password link is visible on login page
    When click on forgot password link
    Then validate forgot password form is displayed

  @TC13 @regression @positive @redirectAfterLogin
  Scenario: TC13 - Redirect after login with valid credentials and redirect URL param
    Given open browser and navigate to login page with redirect url param
    And Login page should display
    When login as tenant admin
    Then validate proper Home page is displayed
    And click on logout
    Then validate login page is displayed


  @TC14 @regression @positive @forgotLoginId
  Scenario: TC14 - Forgot Login ID with valid email shows success notification
    Given Open browser and enter the url
    And Login page should display
    When click on forgot login id link
    And enter a valid email for login id recovery
    And click on submit forgot login id button
    Then validate forgot login id success notification is displayed

  @TC15 @regression @negative @forgotLoginId
  Scenario: TC15 - Forgot Login ID with invalid email shows validation error
    Given Open browser and enter the url
    And Login page should display
    When click on forgot login id link
    And enter an invalid email format for login id recovery
    And click on submit forgot login id button
    Then validate forgot login id error notification is displayed
