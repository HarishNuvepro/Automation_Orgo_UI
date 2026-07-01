@roles
Feature: Roles Management

  @TC_ROLES_01 @regression @createRole
  Scenario: TC1 - Create a new role with company scope
    Given open the browser and enter the Url
    And login as tenant admin
    When navigate to organization and click on roles tab
    And click on create role button
    And enter the required role details
    When click on create role submit button
    Then validate role created successfully
    And click on logout
    Then validate login page is displayed
