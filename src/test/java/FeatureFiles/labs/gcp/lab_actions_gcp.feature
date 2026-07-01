@gcp @lab-actions-gcp
Feature: Lab Actions GCP functionality

  @smoke @GCP_TC45
  Scenario: GCP_TC45 - Lab Actions - Create, Stop, Start and Delete Lab - Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC45
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

  @regression @GCP_TC46
  Scenario: GCP_TC46 - Lab Actions - Create, Cleanup, Suspend and Resume via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC46
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

  @regression @GCP_TC47
  Scenario: GCP_TC47 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC47
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

  @regression @GCP_TC48
  Scenario: GCP_TC48 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC48
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

  @regression @GCP_TC49
  Scenario: GCP_TC49 - Lab Actions - Submit Challenge, Set IAM Password and Jump to Console - Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC49
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

  @regression @GCP_TC50
  Scenario: GCP_TC50 - Lab Actions - Create, Stop, Start and Delete Lab - Cohort Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC50
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

  @regression @GCP_TC51
  Scenario: GCP_TC51 - Lab Actions - Create, Cleanup, Suspend and Resume via Policy Assignment - Cohort Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC51
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

  @regression @GCP_TC52
  Scenario: GCP_TC52 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Cohort Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC52
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

  @regression @GCP_TC53
  Scenario: GCP_TC53 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Cohort Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC53
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

  @regression @GCP_TC54
  Scenario: GCP_TC54 - Lab Actions - Submit Challenge, Set IAM Password and Jump to Console - Cohort Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC54
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

  @regression @GCP_TC55
  Scenario: GCP_TC55 - Lab Actions - Create, Stop, Start and Delete Lab - Batch Provision Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC55
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

  @regression @GCP_TC56
  Scenario: GCP_TC56 - Lab Actions - Cleanup, Suspend and Resume via Policy Assignment - Batch Provision Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC56
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
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC57
  Scenario: GCP_TC57 - Lab Actions - Assign Budget, Remove All Budget and Set Budget via Policy Assignment - Batch Provision Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC57
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
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC58
  Scenario: GCP_TC58 - Lab Actions - Quota Duration, Quota Budget, Manually Remove Budget and Quota Top-Up Budget - Batch Provision Account
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC58
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
    And click on logout
    Then validate login page is displayed

  @regression @GCP_TC59
  Scenario: GCP_TC59 - Bulk Delete all labs created in GCP_TC46-GCP_TC54 in one shot
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC1
    And search and select team
    And navigate to labs page
    Then delete all registered labs and wait for completion
    And click on logout
    Then validate login page is displayed
