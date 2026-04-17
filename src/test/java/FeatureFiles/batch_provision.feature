 
 Feature: batch provision Lab Request functionality
 
@TC27
Scenario: TC27 - Normal Plan - Batch Provision - Account
  Given open the browser and enter the Url
  And login as tenant admin
  When click on select team option
  And get test data from excel TC27
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
  And click on logout
  Then validate login page is displayed



@TC30
Scenario: TC30 - Plan with expiry date - Batch Provision - Account
  Given open the browser and enter the Url
  And login as tenant admin
  When click on select team option
  And get test data from excel TC30
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
  And click on logout
  Then validate login page is displayed


@TC36
Scenario: TC36 - Plan with expiry duration - Batch Provision - Account
  Given open the browser and enter the Url
  And login as tenant admin
  When click on select team option
  And get test data from excel TC36
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
  And click on logout
  Then validate login page is displayed



@TC33
Scenario: TC33 - Plan with default policy - Batch Provision - Account
  Given open the browser and enter the Url
  And login as tenant admin
  When click on select team option
  And get test data from excel TC33
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
  And click on logout
  Then validate login page is displayed