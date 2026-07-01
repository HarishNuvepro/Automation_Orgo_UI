@gcp @cohort-lab-gcp
Feature: Cohort Lab Request GCP functionality

  @smoke @GCP_TC5
  Scenario: GCP_TC5 - Normal Plan - Cohort Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC5
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

  @regression @GCP_TC6
  Scenario: GCP_TC6 - Plan with expiry date - Cohort Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC6
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

  @regression @GCP_TC7
  Scenario: GCP_TC7 - Plan with default policy - Cohort Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC7
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

  @regression @GCP_TC8
  Scenario: GCP_TC8 - Plan with configured duration - Cohort Lab request - GCP Account Lab
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC8
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
