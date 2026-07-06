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

  @smoke @AWS_TC21
  Scenario: AWS_TC21 - Normal Plan (Cohort Plan) - Batch Provision with lazy lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC21
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

  @regression @AWS_TC22
  Scenario: AWS_TC22 - Plan with expiry date (Cohort Plan) - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC22
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

  @regression @AWS_TC23
  Scenario: AWS_TC23 - Plan with default policy (Cohort Plan) - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC23
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

  @regression @AWS_TC24
  Scenario: AWS_TC24 - Plan with configured duration (Cohort Plan) - Lazy Lab request - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC24
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

  @regression @negative @AWS_TC27
  Scenario: AWS_TC27 - Lazy Lab request with one invalid user email in the list - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC27
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
    And in the settings page select lazy create option
    And click on next button
    And click on finish button
    And click on confirm button on create batch provisioning labs pop-up
    Then validate only the valid user is listed as success in the batch summary
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC46
  Scenario: AWS_TC46 - Lazy Lab Actions - Create, Stop, Start and Delete Lab - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC46
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
    And click on logout
    Then validate login page is displayed
    When login sequentially for each user and launch labs
    Then validate all labs are created successfully for all users
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And search and select team
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then perform stop action on batch labs
    Then validate all batch labs are in stopped status
    And select all labs in batch details
    Then perform start action on batch labs
    Then validate all batch labs are in running status
    And select all labs in batch details
    Then perform delete action on batch labs
    Then validate all batch labs are deleted successfully
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC47
  Scenario: AWS_TC47 - Lazy Lab Actions - Cleanup, Suspend and Resume via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC47
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
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And search and select team
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign cleanup policy to batch labs and validate action is complete
    Then assign suspend policy to batch labs and validate action is complete
    Then assign resume policy to batch labs and validate action is complete
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC48
  Scenario: AWS_TC48 - Lazy Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC48
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
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And search and select team
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign budget policy 1 to batch labs and validate latest action "CreateAccountBudget" is complete
    Then assign budget policy 2 to batch labs and validate latest action "RemoveAllAccountBudgets" is complete
    Then assign budget policy 3 to batch labs and validate latest action "SetAccountBudget" is complete
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC49
  Scenario: AWS_TC49 - Lazy Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC49
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
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And search and select team
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign quota duration policy to batch labs and validate success message
    Then assign quota budget policy to each batch lab and validate latest action "CreateAccountBudget" is complete
    Then access each batch lab and remove quota budget policy
    Then assign quota top-up budget policy to each batch lab and validate latest action "SetAccountBudget" is complete
    And click on logout
    Then validate login page is displayed
