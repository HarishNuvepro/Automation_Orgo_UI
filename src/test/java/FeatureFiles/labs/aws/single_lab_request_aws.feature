@aws @single-lab-aws
Feature: Single Lab Request AWS functionality

  @smoke @AWS_TC1
  Scenario: AWS_TC1 - Normal Plan - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC1
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC2
  Scenario: AWS_TC2 - Plan with expiry date - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC2
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

  @regression @AWS_TC3
  Scenario: AWS_TC3 - Plan with default policy - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC3
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

  @regression @AWS_TC4
  Scenario: AWS_TC4 - Plan with configured duration - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC4
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

  @regression @negative @AWS_TC29
  Scenario: AWS_TC29 - Requesting the same plan twice in a row - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC29
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And register lab for final cleanup
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then validate requesting the same plan again is handled without errors
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @negative @AWS_TC30
  Scenario: AWS_TC30 - Abandoning a lab request without subscribing creates no lab - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC30
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And abandon the lab request without subscribing
    Then validate no new lab was created from the abandoned request
    And click on logout
    Then validate login page is displayed
