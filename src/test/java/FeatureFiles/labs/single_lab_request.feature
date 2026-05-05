@labs @single-lab
Feature: Single Lab Request functionality

  @smoke @TC1
  Scenario Outline: TC1 - Normal Plan - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC1
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And click on logout
    Then validate login page is displayed

  @regression @TC2
  Scenario Outline: TC2 - Plan with expiry date - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC2
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And click on logout
    Then validate login page is displayed

  @regression @TC3
  Scenario Outline: TC3 - Plan with default policy - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC3
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab
    And click on logout
    Then validate login page is displayed

  @regression @TC4
  Scenario Outline: TC4 - Plan with configured duration - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC4
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And click on logout
    Then validate login page is displayed
