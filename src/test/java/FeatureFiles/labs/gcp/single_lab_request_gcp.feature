@gcp @single-lab-gcp
Feature: Single Lab Request GCP functionality

  @smoke @GCP_TC1
  Scenario: GCP_TC1 - Normal Plan - Single Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC1
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And select provision for others option
    And search and select user for lab
    And click on subscribe button
    Then verify lab is created successfully
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC2
  Scenario: GCP_TC2 - Plan with expiry date - Single Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC2
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And select provision for others option
    And search and select user for lab
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC3
  Scenario: GCP_TC3 - Plan with default policy - Single Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC3
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And select provision for others option
    And search and select user for lab
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC4
  Scenario: GCP_TC4 - Plan with configured duration - Single Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC4
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And select provision for others option
    And search and select user for lab
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed
