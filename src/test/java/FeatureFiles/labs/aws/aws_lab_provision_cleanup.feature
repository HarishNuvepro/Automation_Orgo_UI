@aws @aws-lab-provision-cleanup
Feature: AWS Lab Provision Cleanup

  @TC_CLEANUP_LABS @labcleanup
  Scenario: TC_CLEANUP_LABS - Bulk delete all labs created during TC1-TC16
    Given open the browser and enter the Url
    And login as tenant admin
    When click on select team option
    And get test data from excel AWS_TC1
    And search and select team
    And navigate to labs page
    Then delete all registered labs and wait for completion
    And click on logout
    Then validate login page is displayed
