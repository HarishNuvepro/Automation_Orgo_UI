@gcp @gcp-lab-provision-cleanup
Feature: GCP Lab Provision Cleanup

  @TC_CLEANUP_GCP_LABS @gcplabcleanup
  Scenario: TC_CLEANUP_GCP_LABS - Bulk delete all GCP labs created during GCP_TC1-GCP_TC16
    Given open the browser and enter the Url
    And login as GCP tenant admin
    When click on select team option
    And get test data from excel GCP_TC1
    And search and select team
    And navigate to labs page
    Then delete all registered labs and wait for completion
    And click on logout
    Then validate login page is displayed
