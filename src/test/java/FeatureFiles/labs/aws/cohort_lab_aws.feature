@aws @cohort-lab-aws
Feature: Cohort Lab Request AWS functionality

  @smoke @AWS_TC5
  Scenario: AWS_TC5 - Normal Plan - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC5
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC6
  Scenario: AWS_TC6 - Plan with expiry date - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC6
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC7
  Scenario: AWS_TC7 - Plan with default policy - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC7
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC8
  Scenario: AWS_TC8 - Plan with configured duration - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC8
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed
