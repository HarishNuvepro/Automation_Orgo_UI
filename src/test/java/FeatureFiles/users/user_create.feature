@users @usercreate
Feature: User Creation

  @TC1 @createuser
  Scenario: Create a user with valid inputs
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    And click on logout
    Then validate login page is displayed

  @TC2 @regression @createuser
  Scenario: Create a user by using Create new user Tab
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on create new user tab
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    And click on logout
    Then validate login page is displayed

  @TC11 @regression @negative @createInvalid
  Scenario: Create a user with invalid inputs using users tab
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details with invalid inputs
    When click on create button
    Then validate user creation failed with validation errors
    And click on logout
    Then validate login page is displayed

  @TC12 @regression @negative @createInvalid
  Scenario: Create a user with invalid inputs using Create new user Tab
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on create new user tab
    Given enter all the required user creation details with invalid inputs
    When click on create button
    Then validate user creation failed with validation errors
    And click on logout
    Then validate login page is displayed

  @TC21 @regression @negative @duplicateLoginId
  Scenario: TC21 - Create a user with duplicate login ID
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details and capture login id for duplicate test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with same login id
    When click on create button
    Then validate duplicate login id error is displayed
    And click on logout
    Then validate login page is displayed

  @TC22 @regression @negative @duplicateEmail
  Scenario: TC22 - Create a user with duplicate email
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details and capture email for duplicate test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with same email
    When click on create button
    Then validate duplicate email error is displayed
    And click on logout
    Then validate login page is displayed
