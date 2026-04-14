Feature: Users

 @createuser
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

  @createuser
  Scenario: Create a user by using Create new user Tab
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on create new user tab
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    And click on logout
    Then validate login page is displayed

  @createInvalid
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

  @createInvalid
  Scenario: Create a user with invalid inputs using Create new user Tab
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on create new user tab
    Given enter all the required user creation details with invalid inputs
    When click on create button
    Then validate user creation failed with validation errors
    And click on logout
    Then validate login page is displayed

  @removeUser
  Scenario: Remove a user from users list
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on remove button
    And click on delete button to confirm
    Then validate the user details are deleted
    And click on logout
    Then validate login page is displayed

  @editUser
  Scenario: Edit user details and validate
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And edit the required inputs
    When click on save button
    Then validate the user details are updated
    And click on logout
    Then validate login page is displayed

  @addToTeam
  Scenario: Add user to a team
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on add to teams button
    And select a team and click on add button
    Then validate user added to team successfully
    And click on logout
    Then validate login page is displayed

  @changePassword
  Scenario: Change password for a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    When select the user and click on change password button
    And update the password and click on apply password button
    Then validate password changed successfully
    And click on logout
    Then validate login page is displayed

  @deactivateUser
  Scenario: Deactivate a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
    And click on logout
    Then validate login page is displayed

  @activateUser
  Scenario: Activate a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on activate button
    Then validate user activated successfully
    And click on logout
    Then validate login page is displayed

  @changeUserRole
  Scenario: Change user role
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And change the user role
    When click on save button
    Then validate the user role is updated
    And click on logout
    Then validate login page is displayed

  @changeUserLoginId
  Scenario: Change user login ID
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And change the user login ID
    When click on save button
    Then validate the user login ID is updated
    And click on logout
    Then validate login page is displayed

  @changeUserEmailId
  Scenario: Change user email ID
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And change the user email ID
    When click on save button
    Then validate the user email ID is updated
    And click on logout
    Then validate login page is displayed

  @importUser
  Scenario: Import users from CSV file
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the CSV file from local system
    And provide the import details
    And click on importsubmit button
    Then validate the users are imported successfully
    And click on logout
    Then validate login page is displayed

  @importUserFromOrg
  Scenario: Import users from CSV file via Organization
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on Import Users
    And select the CSV file from local system
    And provide the import details
    And click on importsubmit button
    Then validate the users are imported successfully
    And click on logout
    Then validate login page is displayed

  @viewUserDetails
  Scenario: View user details and validate
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on view details
    Then validate the user details are displayed correctly
    And click on logout
    Then validate login page is displayed

