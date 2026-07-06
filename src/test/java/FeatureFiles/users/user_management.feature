@users @useractions
Feature: User Actions

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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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

  @TC13 @regression @changeUserRole
  Scenario: Change user role
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
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
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    And click on logout
    Then validate login page is displayed
    And login with the created user credentials
    Then validate the home page is displayed for created user
    And click on logout
    Then validate login page is displayed

  @TC23 @regression @negative @editValidation
  Scenario: TC23 - Edit user with blank first name and invalid email
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for edit validation test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button for validation test
    And clear first name and enter invalid email format
    When click on save button
    Then validate edit validation errors are displayed
    And click on logout
    Then validate login page is displayed

  @TC24 @regression @negative @duplicateLoginIdEdit
  Scenario: TC24 - Edit user - change login ID to an existing login ID
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for duplicate login id edit test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And change login id to duplicate login id
    When click on save button
    Then validate duplicate login id error is displayed
    And click on logout
    Then validate login page is displayed

  @TC25 @regression @negative @duplicateEmailEdit
  Scenario: TC25 - Edit user - change email to an existing email
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for duplicate email edit test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And change email to duplicate email
    When click on save button
    Then validate duplicate email error is displayed
    And click on logout
    Then validate login page is displayed

  @TC26 @regression @searchByLoginId
  Scenario: TC26 - Search user by login ID
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for login id search test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And search the user by login id
    Then validate user found by login id search
    And click on logout
    Then validate login page is displayed

  @TC27 @regression @searchByName
  Scenario: TC27 - Search user by name
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for name search test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And search the user by name
    Then validate user found by name search
    And click on logout
    Then validate login page is displayed

  @TC28 @regression @filterByStatus
  Scenario: TC28 - Filter user list by Active and Inactive status
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
    When navigate to organization and click on users tab
    And filter users by inactive status
    Then validate deactivated user appears in inactive filter
    When filter users by active status
    Then validate deactivated user is not in active filter
    And click on logout
    Then validate login page is displayed

  @TC29 @regression @negative @deactivatedLogin
  Scenario: TC29 - Login with deactivated user credentials
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
    And click on logout
    Then validate login page is displayed
    And try to login with the deactivated user credentials
    Then validate login failed with invalid credentials error

  @TC30 @regression @negative @oldPasswordLogin
  Scenario: TC30 - Login with old password after password change
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for password change negative test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    When select the user and click on change password button
    And change password to new password for negative test
    Then validate password changed successfully
    And click on logout
    Then validate login page is displayed
    And try to login with the old password
    Then validate login failed with invalid credentials error

  @TC31 @regression @negative @importInvalid
  Scenario: TC31 - Import users with invalid CSV format
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the invalid CSV file from local system
    And provide the import details
    And click on importsubmit button
    Then validate import failed with row level errors
    And click on logout
    Then validate login page is displayed

  @TC32 @regression @negative @importDuplicate
  Scenario: TC32 - Import users with duplicate entries in CSV
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for duplicate import test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on import button
    And select the CSV file with duplicate entry from local system
    And provide the import details
    And click on importsubmit button
    Then validate import shows partial success with failure rows
    And click on logout
    Then validate login page is displayed

  @TC33 @regression @bulkDeleteUsers
  Scenario: TC33 - Bulk delete multiple selected users at once
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter first user details for bulk delete test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on create a user button
    And enter second user details for bulk delete test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select both bulk delete users and click on remove button
    And click on delete button to confirm
    Then validate both bulk delete users are removed
    And click on logout
    Then validate login page is displayed

  @TC34 @regression @viewUserDetailsValidate
  Scenario: TC34 - View user details and validate actual field values
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for view details validation test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on view details
    Then validate the user details show correct field values
    And click on logout
    Then validate login page is displayed

  @TC35 @regression @exportUsers
  Scenario: TC35 - Export users list as CSV and verify download
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on export button and select csv option
    Then validate csv file is downloaded
    And click on logout
    Then validate login page is displayed

  @TC36 @regression @negative @mandatoryFieldValidation
  Scenario: TC36 - Verify mandatory field validation messages are displayed during user creation
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And leave all user creation fields blank
    When click on create button
    Then validate mandatory field validation messages are displayed
    And click on logout
    Then validate login page is displayed

  @TC37 @regression @trimSpaces
  Scenario: TC37 - Verify leading and trailing spaces are trimmed correctly in user input fields
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with spaces around inputs
    When click on create button
    Then validate user created successfully
    Then validate user inputs were trimmed of leading and trailing spaces
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And edit user login id with surrounding spaces and save
    Then validate edited login id was trimmed of surrounding spaces
    And click on logout
    Then validate login page is displayed

  @TC38 @regression @negative @specialChars
  Scenario: TC38 - Verify special characters are handled correctly in user input fields
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with special characters
    When click on create button
    Then validate special characters are accepted or appropriate validation is shown
    And click on logout
    Then validate login page is displayed

  @TC39 @regression @negative @maxLength
  Scenario: TC39 - Verify maximum character limit validation works correctly for user fields
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with first name exceeding max length
    When click on create button
    Then validate max length validation is shown on create
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And update first name to a string exceeding max length
    When click on save button
    Then validate max length validation is shown on edit
    And click on logout
    Then validate login page is displayed

  @TC40 @regression @negative @invalidEmail
  Scenario: TC40 - Verify invalid email format is not accepted during user creation or edit
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with invalid email format
    When click on create button
    Then validate invalid email format is rejected on create
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And update email to invalid email format
    When click on save button
    Then validate invalid email format is rejected on edit
    And click on logout
    Then validate login page is displayed

  @TC41 @regression @negative @invalidLoginId
  Scenario: TC41 - Verify invalid login ID format is not accepted during user creation or edit
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details with invalid login id format
    When click on create button
    Then validate invalid login id format is rejected on create
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And update login id to invalid format
    When click on save button
    Then validate invalid login id format is rejected on edit
    And click on logout
    Then validate login page is displayed

  @TC42 @regression @negative @weakPassword
  Scenario: TC42 - Verify weak passwords are not accepted during password change
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on change password button
    And change password to weak password for negative test
    Then validate weak password error is displayed
    And close the change password modal popup
    And click on logout
    Then validate login page is displayed

  @TC43 @regression @negative @passwordPolicy
  Scenario: TC43 - Verify password policy validation is enforced during password change
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on change password button
    And change password to policy violating value for negative test
    Then validate password policy error is displayed
    And close the change password modal popup
    And click on logout
    Then validate login page is displayed

  @TC44 @regression @cancelEdit
  Scenario: TC44 - Verify user details remain unchanged when edit operation is cancelled
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for cancel edit test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on edit button
    And update the user details for cancel test
    And click on cancel button
    When navigate to organization and click on users tab
    And select the user and click on view details
    Then validate user details remain unchanged after cancel edit
    And click on logout
    Then validate login page is displayed

  @TC45 @regression @negative @deletedUserLogin
  Scenario: TC45 - Verify deleted users cannot login using previously valid credentials
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    And click on logout
    Then validate login page is displayed
    And login as tenant admin
    When navigate to organization and click on users tab
    And select the user and click on remove button
    And click on delete button to confirm
    Then validate the user details are deleted
    And click on logout
    Then validate login page is displayed
    And try to login with the deleted user credentials
    Then validate login failed with invalid credentials error

  @TC46 @regression @partialSearch
  Scenario: TC46 - Verify user search works correctly with partial match values
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for partial match search test
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And open advanced search dialog and disable exact match option
    And search by partial login id and verify results are returned
    And click on logout
    Then validate login page is displayed

  @TC47 @regression @negative @noRecordsSearch
  Scenario: TC47 - Verify search operation returns no records message correctly for invalid search input
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And search users with non existent login id
    Then validate no records found message is displayed
    And click on logout
    Then validate login page is displayed

  @TC48 @regression @importEmptyRows
  Scenario: TC48 - Verify CSV import handles empty rows correctly
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the CSV file with empty rows from local system
    And provide the import details
    And click on importsubmit button
    Then validate import succeeded and empty rows were skipped
    And click on logout
    Then validate login page is displayed

  @TC49 @regression @importPartialInvalid
  Scenario: TC49 - Verify CSV import handles partially invalid records correctly without impacting valid records
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the CSV file with mixed valid and invalid rows from local system
    And provide the import details
    And click on importsubmit button
    Then validate import shows partial success with valid and invalid rows
    And click on logout
    Then validate login page is displayed

  @TC50 @regression @negative @importUploadFailure
  Scenario: TC50 - Verify appropriate error message is displayed when CSV upload fails
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the invalid CSV file from local system
    And provide the import details
    And click on importsubmit button
    Then validate csv upload failed with error message
    And click on logout
    Then validate login page is displayed

  @TC51 @regression @negative @importUnsupportedFormat
  Scenario: TC51 - Verify unsupported file formats are not accepted during user import
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on import button
    And select the non CSV file from local system
    Then validate unsupported file format is rejected
    And click on logout
    Then validate login page is displayed

  @TC52 @regression @exportContent
  Scenario: TC52 - Verify exported users list file contains correct user details and column values
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    And enter user creation details for export content verification
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And click on export button and select csv option
    Then validate csv file is downloaded
    And validate exported csv contains the created user details
    And click on logout
    Then validate login page is displayed

  @TC53 @regression @pagination
  Scenario: TC53 - Verify pagination works correctly in Users listing page when multiple users exist
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And verify default users listing shows 10 entries
    And verify pagination controls are visible
    And click on next page in users listing
    Then validate next page shows different users
    And click on previous page in users listing
    Then validate previous page is displayed
    And click on logout
    Then validate login page is displayed

  # ── U58-U75: Additional coverage (cancel buttons, bulk ops, search variants, etc.) ──

  @TC54 @useractions @regression @negative @cancelRemove
  Scenario: TC54 - Cancel button on remove user dialog does not delete the user
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on remove button
    And click on cancel button in remove user dialog
    Then validate user is not deleted after cancel
    And click on logout
    Then validate login page is displayed

  @TC55 @useractions @regression @negative @cancelDeactivate
  Scenario: TC55 - Cancel button on deactivate confirmation dialog keeps user active
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And open the deactivate dialog for the user
    And click on cancel button in deactivate dialog
    Then validate user remains active after cancel
    And click on logout
    Then validate login page is displayed

  @TC56 @useractions @regression @positive @notifyCheckbox
  Scenario: TC56 - Send notification checkbox on password change is toggleable
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    When select the user and click on change password button
    And toggle the notify user checkbox
    And update the password and click on apply password button
    Then validate password changed successfully
    And click on logout
    Then validate login page is displayed

  @TC57 @useractions @regression @bulkActivate
  Scenario: TC57 - Bulk activate multiple selected users at once
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And select all users on first page using header checkbox
    And click on deactivate button for all selected users
    Then validate all users on first page are deactivated
    When select all users on first page using header checkbox
    And click on activate button for all selected users
    Then validate all users on first page are activated
    And click on logout
    Then validate login page is displayed

  @TC58 @useractions @regression @paginationPerPage
  Scenario: TC58 - Verify users listing entries per page dropdown changes results count
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And verify default users listing shows 10 entries
    And change entries per page to 25
    Then validate users listing shows 25 entries per page
    And change entries per page to 50
    Then validate users listing shows 50 entries per page
    And click on logout
    Then validate login page is displayed

  @TC59 @useractions @regression @paginationLastPage
  Scenario: TC59 - Verify pagination last page button navigates to the final page
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And change entries per page to 100
    And click on last page in users listing
    Then validate last page is displayed
    And click on first page in users listing
    Then validate first page is displayed
    And click on logout
    Then validate login page is displayed

  @TC60 @useractions @regression @refreshButton
  Scenario: TC60 - Refresh button on users listing reloads the data
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on refresh button on users listing
    Then validate users listing is reloaded
    And click on logout
    Then validate login page is displayed

  @TC61 @useractions @regression @exportExcel
  Scenario: TC61 - Export users list as Excel file and verify download
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on export button and select excel option
    Then validate excel file is downloaded
    And click on logout
    Then validate login page is displayed

  @TC62 @useractions @regression @exportPdf
  Scenario: TC62 - Export users list as PDF file and verify download
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on export button and select pdf option
    Then validate pdf file is downloaded
    And click on logout
    Then validate login page is displayed

  @TC63 @useractions @regression @exportFiltered
  Scenario: TC63 - Export filtered users list contains only filtered results
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on users tab
    And click on create a user button
    Given enter all the required user creation details
    When click on create button
    Then validate user created successfully
    When navigate to organization and click on users tab
    And select the user and click on deactivate button
    Then validate user deactivated successfully
    When navigate to organization and click on users tab
    And filter users by inactive status
    And click on export button and select csv option
    Then validate csv file is downloaded
    And validate exported csv contains only inactive users
    And click on logout
    Then validate login page is displayed

  @TC_CLEANUP @usercleanup
  Scenario: TC_CLEANUP - Bulk remove all users created during the test run
    Given open the browser and enter the Url
    And login as tenant admin
    Then bulk remove all registered users
    And click on logout
    Then validate login page is displayed
