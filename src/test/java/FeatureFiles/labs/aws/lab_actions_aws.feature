@aws @lab-actions-aws
Feature: Lab Actions AWS functionality

  @smoke @AWS_TC31
  Scenario: AWS_TC31 - Lab Actions - Create, Stop, Start and Delete Lab - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC31
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then perform stop action on the lab
    Then validate the lab is in stopped status
    Then perform start action on the lab
    Then validate the lab is in running status
    Then perform delete action on the lab
    Then validate the lab is deleted successfully
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC32
  Scenario: AWS_TC32 - Lab Actions - Create, Cleanup, Suspend and Resume via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC32
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign cleanup policy to the lab and validate action is complete
    Then assign suspend policy to the lab and validate action is complete
    Then assign resume policy to the lab and validate action is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC33
  Scenario: AWS_TC33 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC33
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign budget policy 1 to the lab and validate latest action "CreateAccountBudget" is complete
    Then assign budget policy 2 to the lab and validate latest action "RemoveAllAccountBudgets" is complete
    Then assign budget policy 3 to the lab and validate latest action "SetAccountBudget" is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC34
  Scenario: AWS_TC34 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC34
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign quota duration policy to the lab and validate success message
    Then assign quota budget policy to the lab and validate latest action "CreateAccountBudget" is complete
    Then access lab and remove quota budget policy
    And navigate to labs page
    And search the created lab by id
    Then assign quota top-up budget policy to the lab and validate latest action "SetAccountBudget" is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC35
  Scenario: AWS_TC35 - Lab Actions - Submit Challenge, Set IAM Password and Jump to Console - Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC35
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then access lab control panel
    Then submit challenge and wait for completion
    Then set iam password and wait for completion
    Then jump to console and verify new tab opens
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC36
  Scenario: AWS_TC36 - Lab Actions - Create, Stop, Start and Delete Lab - Cohort Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC36
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then perform stop action on the lab
    Then validate the lab is in stopped status
    Then perform start action on the lab
    Then validate the lab is in running status
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC37
  Scenario: AWS_TC37 - Lab Actions - Create, Cleanup, Suspend and Resume via Policy Assignment - Cohort Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC37
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign cleanup policy to the lab and validate action is complete
    Then assign suspend policy to the lab and validate action is complete
    Then assign resume policy to the lab and validate action is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC38
  Scenario: AWS_TC38 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Cohort Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC38
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign budget policy 1 to the lab and validate latest action "CreateAccountBudget" is complete
    Then assign budget policy 2 to the lab and validate latest action "RemoveAllAccountBudgets" is complete
    Then assign budget policy 3 to the lab and validate latest action "SetAccountBudget" is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC39
  Scenario: AWS_TC39 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Cohort Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC39
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then assign quota duration policy to the lab and validate success message
    Then assign quota budget policy to the lab and validate latest action "CreateAccountBudget" is complete
    Then access lab and remove quota budget policy
    And navigate to labs page
    And search the created lab by id
    Then assign quota top-up budget policy to the lab and validate latest action "SetAccountBudget" is complete
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC40
  Scenario: AWS_TC40 - Lab Actions - Submit Challenge, Set IAM Password and Jump to Console - Cohort Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC40
    And search and select team
    And navigate to labs page
    And click on request lab button
    And select plan using test data
    And click on subscribe button
    Then verify lab is created successfully
    And navigate to labs page
    And search the created lab by id
    Then access lab control panel
    Then submit challenge and wait for completion
    Then set iam password and wait for completion
    Then jump to console and verify new tab opens
    And register lab for final cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC41
  Scenario: AWS_TC41 - Lab Actions - Create, Stop, Start and Delete Lab - Batch Provision Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC41
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

  @regression @AWS_TC42
  Scenario: AWS_TC42 - Lab Actions - Cleanup, Suspend and Resume via Policy Assignment - Batch Provision Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC42
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
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign cleanup policy to batch labs and validate action is complete
    Then assign suspend policy to batch labs and validate action is complete
    Then assign resume policy to batch labs and validate action is complete
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC43
  Scenario: AWS_TC43 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Batch Provision Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC43
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
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign budget policy 1 to batch labs and validate latest action "CreateAccountBudget" is complete
    Then assign budget policy 2 to batch labs and validate latest action "RemoveAllAccountBudgets" is complete
    Then assign budget policy 3 to batch labs and validate latest action "SetAccountBudget" is complete
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC44
  Scenario: AWS_TC44 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Batch Provision Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC44
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
    And navigate to Batch Provisioning of Labs page
    Then search created batch and select checkbox
    And click on batch view button
    And select all labs in batch details
    Then assign quota duration policy to batch labs and validate success message
    Then assign quota budget policy to each batch lab and validate latest action "CreateAccountBudget" is complete
    Then access each batch lab and remove quota budget policy
    Then assign quota top-up budget policy to each batch lab and validate latest action "SetAccountBudget" is complete
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC45
  Scenario: AWS_TC45 - Lab Actions - Submit Challenge, Set IAM Password and Jump to Console - Batch Provision Account
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC45
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
    And select first batch lab for control panel actions
    And search the created lab by id
    Then access lab control panel
    Then submit challenge and wait for completion
    Then set iam password and wait for completion
    Then jump to console and verify new tab opens
    And register batch provision labs for cleanup
    And click on logout
    Then validate login page is displayed

  @regression @AWS_TC50
  Scenario: AWS_TC50 - Bulk Delete all labs created during the lab-actions run
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC50
    And search and select team
    And navigate to labs page
    Then delete all registered labs and wait for completion
    And click on logout
    Then validate login page is displayed
