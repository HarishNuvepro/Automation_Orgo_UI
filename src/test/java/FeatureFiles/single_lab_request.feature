Feature: Single Lab Request functionality
  @TC01
  Scenario Outline: TC01 - Normal Plan - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC01
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And click on logout
    Then validate login page is displayed


  @TC04
  Scenario Outline: TC04 - Plan with expiry date - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC04
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry date values are loading on lab control panel
    And click on logout
    Then validate login page is displayed


     @TC07
  Scenario Outline: TC07 - Plan with default policy - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC07
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify default policy is assigned to the lab 
    And click on logout
    Then validate login page is displayed


  @TC10
  Scenario Outline: TC10 - Plan with configured duration - Single Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel TC10
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    Then verify expiry duration values are loading on lab control panel
    And click on logout
    Then validate login page is displayed






  

    

 