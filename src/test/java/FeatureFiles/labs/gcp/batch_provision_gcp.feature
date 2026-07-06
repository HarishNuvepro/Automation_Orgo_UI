@gcp @batch-provision-gcp
Feature: Batch Provision Lab Request GCP functionality

  @smoke @GCP_TC9
  Scenario: GCP_TC9 - Normal Plan - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC9
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC10
  Scenario: GCP_TC10 - Plan with expiry date - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC10
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate expiry date
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC11
  Scenario: GCP_TC11 - Plan with default policy - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC11
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate plan with deaful policy is attached
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC12
  Scenario: GCP_TC12 - Plan with expiry duration - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC12
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate expiry duration
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @smoke @GCP_TC17
  Scenario: GCP_TC17 - Normal Plan (Cohort Plan) - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC17
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC18
  Scenario: GCP_TC18 - Plan with expiry date (Cohort Plan) - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC18
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate expiry date
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC19
  Scenario: GCP_TC19 - Plan with default policy (Cohort Plan) - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC19
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate plan with deaful policy is attached
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC20
  Scenario: GCP_TC20 - Plan with configured duration (Cohort Plan) - Batch Provision - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC20
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And for each copied lab id search and access lab to validate expiry duration
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @negative @GCP_TC26
  Scenario: GCP_TC26 - Batch Provision with one invalid user email in the list - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC26
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And search for each user email and select only the ones found
    And click on next button
    And in the choose plan page provide plan id input in search box and select the listed plan
    And click on next button
    And click on next button
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then validate only the valid user is listed as success in the batch summary
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @negative @GCP_TC28
  Scenario: GCP_TC28 - Batch Provision without selecting any user - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC28
    And search and select team
    And navigate to Batch Provisioning of Labs page
    And click on batch create button
    And in batch provision details page enter name and description
    And click on next button
    And attempt to click next without selecting any user
    Then validate user selection is required before proceeding
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC25
  Scenario: GCP_TC25 - Batch Provision with 10 users (scale test) - GCP Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC25
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
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
    And copy those lab id's and navigate to all labs page
    Then validate all lab is completion status in the latest action in all labs table
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed
