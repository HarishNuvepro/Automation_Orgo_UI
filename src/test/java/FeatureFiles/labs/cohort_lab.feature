@labs @cohort-lab
Feature: Cohort Lab Request functionality

  @smoke @TC5
  Scenario Outline: TC5 - Normal Plan - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC5
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And click on logout
    Then validate login page is displayed

  @regression @TC6
  Scenario Outline: TC6 - Plan with expiry date - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC6
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And click on logout
    Then validate login page is displayed

  @regression @TC7
  Scenario Outline: TC7 - Plan with default policy - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC7
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab
    And click on logout
    Then validate login page is displayed

  @regression @TC8
  Scenario Outline: TC8 - Plan with configured duration - Cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC8
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And click on logout
    Then validate login page is displayed
