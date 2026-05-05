@users
Feature: Users

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

  @TC3 @regression @editUser
  Scenario: Edit user details and validate
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for edit test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And edit the required inputs
    When click on save button
    Then validate the user details are updated
    And click on logout
    Then validate login page is displayed

  @TC4 @regression @removeUser
  Scenario: Remove a user from users list
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for deletion test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on remove button
    And click on delete button to confirm
    Then validate the user details are deleted
    And click on logout
    Then validate login page is displayed

  @TC5 @regression @deactivateUser
  Scenario: Deactivate a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
    And click on logout
    Then validate login page is displayed

  @TC6 @regression @activateUser
  Scenario: Activate a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on activate button
    Then validate user activated successfully
    And click on logout
    Then validate login page is displayed

  @TC7 @regression @changePassword
  Scenario: Change password for a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    When select the user and click on change password button
    And update the password and click on apply password button
    Then validate password changed successfully
    And click on logout
    Then validate login page is displayed

  @TC8 @regression @addToTeam
  Scenario: Add user to a team
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on add to teams button
    And select a team and click on add button
    Then validate user added to team successfully
    And click on logout
    Then validate login page is displayed

  @TC9 @regression @viewUserDetails
  Scenario: View user details and validate
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on view details
    Then validate the user details are displayed correctly
    And click on logout
    Then validate login page is displayed

  @TC10 @regression @changeLoginId
  Scenario: Change login ID for a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for login id change test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button for login id change
    And clear the old login id and update with new login id
    When click on save button
    Then validate the login id is changed successfully
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

  @TC13 @regression @changeUserRole
  Scenario: Change user role
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on edit button for role change
    And update the user role
    When click on save button
    Then validate the role is changed successfully
    And click on logout
    Then validate login page is displayed

  @TC14 @regression @changeEmailId
  Scenario: Change email ID for a user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for email id change test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button for email id change
    And clear the old email id and update with new email id
    When click on save button
    Then validate the email id is changed successfully
    And click on logout
    Then validate login page is displayed

  @TC15 @regression @importUser
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

  @TC16 @regression @importUserFromOrg
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

  @TC17 @regression @searchByEmail
  Scenario: Search user by email
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for email search test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And search the user by email
    Then validate user found by email search
    And click on logout
    Then validate login page is displayed

  @TC18 @regression @addToAllTeams
  Scenario: Add user to all teams
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on add to teams button
    And select all teams and click on add button
    Then validate user added to team successfully
    And click on logout
    Then validate login page is displayed

  @TC19 @regression @addToTeamAsAdmin
  Scenario: Add user to a team as Team Admin
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for team admin test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on add to teams button for team admin
    And select a team and add user as team admin
    Then validate user added to team successfully
    And click on logout
    Then validate login page is displayed

  @TC20 @regression @loginWithCreatedUser
  Scenario: Login with newly created user credentials
    Given open the browser and enter the Url
    And login with the created user credentials
    Then validate the home page is displayed for created user
    And click on logout
    Then validate login page is displayed
