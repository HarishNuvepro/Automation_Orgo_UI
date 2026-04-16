Feature: cohort Lab Request functionality 
 @TC15
 Scenario Outline: TC15 - Normal Plan - cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC15
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And click on logout
    Then validate login page is displayed


  @TC18
  Scenario Outline: TC18 - Plan with expiry date - cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC18
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And click on logout
    Then validate login page is displayed


  @TC21
  Scenario Outline: TC21 - Plan with default policy - cohort Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC21
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab 
    And click on logout
    Then validate login page is displayed



    @TC24
  Scenario Outline: TC24 - Plan with configured duration - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC24
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And click on logout
    Then validate login page is displayed