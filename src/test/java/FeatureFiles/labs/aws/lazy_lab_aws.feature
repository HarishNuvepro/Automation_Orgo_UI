@aws @batch-provision-aws @lazy-lab-aws
Feature: Batch Provision with Lazy Lab Request AWS functionality

  @smoke @AWS_TC13
  Scenario: AWS_TC13 - Normal Plan - Batch Provision with lazy lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC13
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And in choose user page enter the user email address in the search box and click on search
    And select the search listed user checkbox
    And click on next button
    And in the choose plan page provide plan id input in search box and select the listed plan
    And click on next button
    And click on next button
    And in the settings page select lazy create option
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is in pending status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed
    When login sequentially for each user and launch labs
    Then validate all labs are created successfully for all users

  @regression @AWS_TC14
  Scenario: AWS_TC14 - Plan with expiry date - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC14
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And in choose user page enter the user email address in the search box and click on search
    And select the search listed user checkbox
    And click on next button
    And in the choose plan page provide plan id input in search box and select the listed plan
    And click on next button
    And click on next button
    And in the settings page select lazy create option
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is in pending status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed
    When login sequentially for each user and launch labs
    Then validate all labs are created and expiry date is set for all users

  @regression @AWS_TC15
  Scenario: AWS_TC15 - Plan with default policy - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC15
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And in choose user page enter the user email address in the search box and click on search
    And select the search listed user checkbox
    And click on next button
    And in the choose plan page provide plan id input in search box and select the listed plan
    And click on next button
    And click on next button
    And in the settings page select lazy create option
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is in pending status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed
    When login sequentially for each user and launch labs
    Then validate all labs are created and default policy is attached for all users

  @regression @AWS_TC16
  Scenario: AWS_TC16 - Plan with configured duration - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC16
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And in choose user page enter the user email address in the search box and click on search
    And select the search listed user checkbox
    And click on next button
    And in the choose plan page provide plan id input in search box and select the listed plan
    And click on next button
    And click on next button
    And in the settings page select lazy create option
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is in pending status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed
    When login sequentially for each user and launch labs
    Then validate all labs are created and expiry duration is set for all users
