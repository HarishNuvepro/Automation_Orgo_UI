Feature: batch provision with lazy Lab Request functionality

@TC39
Scenario: TC39 - Normal Plan - Batch Provision with lazy lab request - Account
  Given open the browser and enter the Url
  And login as tenant admin
  When click on select team option
  And get test data from excel TC39
  And search and select team
  And navigate to Batch Provisioning of Labs page
  And click on batch create button
  And in batch provision details page enter name and description
  And click on next button
  And in choose user page enter the user email addresses from excel in the search box and click on search
  And select all the search listed user checkboxes
  And click on next button
  And in the choose plan page provide plan id input in search box and select the listed plan
  And click on next button
  And click on next button
  And in the settings page choose Create on First Start (Lazy Create) option
  And click on next button
  And click on finish button
  And click on confirm button on create batch provisioning labs pop-up
  Then in the batch provisioning summary table check user listed and status and lab id and details is getting generated
  And copy those lab id's and navigate to all labs page
  Then validate all lab is in pending status in the latest action in all labs table
  And click on logout
  Then validate login page is displayed
  And iterate through each user from TC39 excel data
  And for each user login and launch their lazy lab and logout