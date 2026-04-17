package stepDefinitions;

import java.util.Map;

import org.testng.Assert;

import POM.LabsPage;
import POM.LabControlPanelPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class BatchProvision {

    public static String createdLabIds;

    @And("navigate to Batch Provisioning of Labs page")
    public void navigate_to_batch_provisioning_of_labs_page() throws Throwable {
        Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
        Thread.sleep(500);
        Hook.base.page.waitForLoadState();
        Thread.sleep(500);
        Hook.base.shDriver.click(Pages.LabsDropdownPage.getBatchProvisioningLink(), "batch provisioning link");
        Thread.sleep(1000);
    }

    @And("click on batch create button")
    public void click_on_batch_create_button() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getCreateBtn(), "batch create button");
        Thread.sleep(500);
    }

    @And("in batch provision details page enter name and description")
    public void in_batch_provision_details_page_enter_name_and_description() throws Throwable {
        String batchName = SingleLabRequest.testData.get("BatchName");
        String batchDescription = SingleLabRequest.testData.get("BatchDescription");

        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getBatchNameInput(), batchName, "batch name");
        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getBatchDescriptionInput(), batchDescription,
                "batch description");
        Thread.sleep(500);
    }

    @And("click on next button")
    public void click_on_next_button() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getNextBtn(), "next button");
        Thread.sleep(1000);
    }

    @And("in choose user page enter the user email address in the search box and click on search")
    public void in_choose_user_page_enter_the_user_email_address_in_the_search_box_and_click_on_search()
            throws Throwable {
        String userEmails = SingleLabRequest.testData.get("UserEmail");

        if (userEmails != null && userEmails.contains(",")) {
            String[] emailArray = userEmails.split(",");
            for (String email : emailArray) {
                Hook.base.shDriver.fill(Pages.BatchProvisionPage.getUserSearchInput(), email.trim(),
                        "user email search");
                Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserSearchBtn(), "search button");
                Thread.sleep(1000);
                Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserCheckbox(), "user checkbox");
                Thread.sleep(500);
            }
        } else {
            Hook.base.shDriver.fill(Pages.BatchProvisionPage.getUserSearchInput(), userEmails, "user email search");
            Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserSearchBtn(), "search button");
            Thread.sleep(1000);
        }
    }

    @And("select the search listed user checkbox")
    public void select_the_search_listed_user_checkbox() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserCheckbox(), "user checkbox");
        Thread.sleep(500);
    }

    @And("in the choose plan page provide plan id input in search box and select the listed plan")
    public void in_the_choose_plan_page_provide_plan_id_input_in_search_box_and_select_the_listed_plan()
            throws Throwable {
        String planId = SingleLabRequest.testData.get("PlanID");

        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getPlanSearchInput(), planId, "plan id search");
        Thread.sleep(1000);
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getPlanRowClick(planId), "plan row");
        Thread.sleep(500);
    }

    @And("click on finish button")
    public void click_on_finish_button() throws Throwable {

        Hook.base.shDriver.click(Pages.BatchProvisionPage.getFinishBtn(), "finish button");
        Thread.sleep(1000);
    }

    @And("click on confirm button on create batch provisioning labs pop-up")
    public void click_on_confirm_button_on_create_batch_provisioning_labs_popup() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getConfirmBtn(), "confirm button");
        Thread.sleep(2000);
    }

    @Then("in the batch provisioning summary table check user listed and status and lab id and details is getting generated")
    public void in_the_batch_provisioning_summary_table_check_user_listed_and_status_and_lab_id_and_details_is_getting_generated()
            throws Throwable {
        String userEmails = SingleLabRequest.testData.get("UserEmail");

        Hook.base.page.waitForLoadState();
        Thread.sleep(2000);

        String[] emailArray;
        if (userEmails != null && userEmails.contains(",")) {
            emailArray = userEmails.split(",");
        } else {
            emailArray = new String[] { userEmails };
        }

        StringBuilder allLabIds = new StringBuilder();

        for (String email : emailArray) {
            String trimmedEmail = email.trim();
            System.out.println("Validating user: " + trimmedEmail);

            boolean userFound = Hook.base.shDriver.isVisible(
                    Pages.BatchProvisionPage.getBatchSummaryRowByEmail(trimmedEmail), "user row");
            Assert.assertTrue(userFound, "User " + trimmedEmail + " should be listed in batch summary table");

            String status = Hook.base.shDriver.getText(
                    Pages.BatchProvisionPage.getBatchSummaryStatusByEmail(trimmedEmail), "status");
            System.out.println("  Status: " + status);
            Assert.assertTrue(status.contains("Success"), "Status should be Success for user " + trimmedEmail);

            String labId = Hook.base.shDriver.getText(
                    Pages.BatchProvisionPage.getBatchSummaryLabIdByEmail(trimmedEmail), "lab id");
            System.out.println("  Lab ID: " + labId);
            Assert.assertFalse(labId.isEmpty(), "Lab ID should not be empty for user " + trimmedEmail);

            String details = Hook.base.shDriver.getText(
                    Pages.BatchProvisionPage.getBatchSummaryDetailsByEmail(trimmedEmail), "details");
            System.out.println("  Details: " + details);
            Assert.assertFalse(details.isEmpty(), "Details should not be empty for user " + trimmedEmail);

            if (allLabIds.length() > 0) {
                allLabIds.append(" ");
            }
            allLabIds.append(labId);
        }

        createdLabIds = allLabIds.toString();
        System.out.println("All Lab IDs: " + createdLabIds);
        System.out.println("Batch provisioning summary validation passed for " + emailArray.length + " user(s)");
    }

    @And("copy those lab id's and navigate to all labs page")
    public void copy_those_lab_ids_and_navigate_to_all_labs_page() throws Throwable {
        Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
        Thread.sleep(500);
        Hook.base.page.waitForLoadState();
        Thread.sleep(500);
        Hook.base.shDriver.click(Pages.LabsDropdownPage.getLabsLink(), "labs link");
        Thread.sleep(1000);
    }

    @Then("validate all lab is completion status in the latest action in all labs table")
    public void validate_all_lab_is_completion_status_in_the_latest_action_in_all_labs_table() throws Throwable {
        String labIds = createdLabIds;
        String[] labIdArray = labIds.split(" ");

        LabsPage labsPage = new LabsPage(Hook.base.page);

        Hook.base.page.waitForLoadState();
        Thread.sleep(2000);

        Hook.base.shDriver.click(Pages.LabsPage.getSearchOptionDropdown(), "search option dropdown");
        Thread.sleep(500);

        Hook.base.shDriver.click(Pages.LabsPage.getSearchOptionByText("id"), "ID option");
        Thread.sleep(1000);

        String searchLabIds = String.join(" ", labIdArray);
        Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), searchLabIds, "search lab ids");
        Thread.sleep(1000);

        Hook.base.shDriver.click(Pages.LabsPage.getAllLabsSearchBtn(), "search button");
        Thread.sleep(2000);

        int maxWaitTime = 300;
        int interval = 10;
        int maxRetries = 5;

        for (int retry = 0; retry < maxRetries; retry++) {
            boolean allComplete = true;

            for (String labId : labIdArray) {
                Hook.base.page.waitForLoadState();

                String latestAction = Hook.base.shDriver.getText(labsPage.getLabLatestActionById(labId),
                        "latest action for " + labId);
                System.out.println("Lab ID " + labId + " - Latest Action: " + latestAction);

                String status = Hook.base.shDriver.getText(labsPage.getLabLatestActionStatusById(labId),
                        "status for " + labId);
                System.out.println("Lab ID " + labId + " - Status: " + status);

                if (status.trim().toLowerCase().contains("failed")) {
                    throw new RuntimeException("Lab creation failed! Lab ID: " + labId + ", Status: " + status);
                }

                if (!status.trim().contains("Complete")) {
                    allComplete = false;
                }
            }

            if (allComplete) {
                System.out.println("All labs (" + labIdArray.length + ") deployment is complete!");
                return;
            }

            if (retry < maxRetries - 1) {
                System.out.println("Not all labs are complete. Retrying... (" + (retry + 1) + "/" + maxRetries + ")");

                Hook.base.shDriver.click(Pages.LabsPage.getSelectAllCheckbox(), "select all checkbox");
                Thread.sleep(500);

                Hook.base.shDriver.click(Pages.LabsPage.getSyncLabStatusBtn(), "sync lab status button");
                Thread.sleep(10000);
            }
        }

        StringBuilder notCompleteLabs = new StringBuilder();
        for (String labId : labIdArray) {
            String status = Hook.base.shDriver.getText(labsPage.getLabLatestActionStatusById(labId),
                    "final status check for " + labId);
            if (!status.trim().contains("Complete")) {
                if (notCompleteLabs.length() > 0) {
                    notCompleteLabs.append(", ");
                }
                notCompleteLabs.append(labId).append("(").append(status).append(")");
            }
        }

        throw new RuntimeException("Timeout: Labs did not complete within " + maxWaitTime + " seconds. Not complete: "
                + notCompleteLabs.toString());
    }

    @Then("validate all lab is in pending status in the latest action in all labs table")
    public void validate_all_lab_is_in_pending_status_in_the_latest_action_in_all_labs_table() throws Throwable {
        String labIds = createdLabIds;
        String[] labIdArray = labIds.split(" ");

        LabsPage labsPage = new LabsPage(Hook.base.page);

        Hook.base.page.waitForLoadState();
        Thread.sleep(2000);

        Hook.base.shDriver.click(Pages.LabsPage.getSearchOptionDropdown(), "search option dropdown");
        Thread.sleep(500);

        Hook.base.shDriver.click(Pages.LabsPage.getSearchOptionByText("id"), "ID option");
        Thread.sleep(1000);

        String searchLabIds = String.join(" ", labIdArray);
        Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), searchLabIds, "search lab ids");
        Thread.sleep(1000);

        Hook.base.shDriver.click(Pages.LabsPage.getAllLabsSearchBtn(), "search button");
        Thread.sleep(2000);

        for (String labId : labIdArray) {
            Hook.base.page.waitForLoadState();

            String latestAction = Hook.base.shDriver.getText(labsPage.getLabLatestActionById(labId),
                    "latest action for " + labId);
            System.out.println("Lab ID " + labId + " - Latest Action: " + latestAction);

            String status = Hook.base.shDriver.getText(labsPage.getLabLatestActionStatusById(labId),
                    "status for " + labId);
            System.out.println("Lab ID " + labId + " - Status: " + status);

            Assert.assertTrue(status.trim().toLowerCase().contains("pending"),
                    "Lab ID " + labId + " should be in pending status but found: " + status);
        }

        System.out.println("All labs (" + labIdArray.length + ") are in pending status as expected!");
    }

    @And("in choose user page enter the user email addresses from excel in the search box and click on search")
    public void in_choose_user_page_enter_the_user_email_addresses_from_excel_in_the_search_box_and_click_on_search()
            throws Throwable {
        String userEmails = SingleLabRequest.testData.get("UserEmail");

        if (userEmails != null && userEmails.contains(",")) {
            String[] emailArray = userEmails.split(",");
            for (String email : emailArray) {
                Hook.base.shDriver.fill(Pages.BatchProvisionPage.getUserSearchInput(), email.trim(),
                        "user email search");
                Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserSearchBtn(), "search button");
                Thread.sleep(1000);
                Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserCheckbox(), "user checkbox");
                Thread.sleep(500);
            }
        } else {
            Hook.base.shDriver.fill(Pages.BatchProvisionPage.getUserSearchInput(), userEmails, "user email search");
            Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserSearchBtn(), "search button");
            Thread.sleep(1000);
        }
    }

    @And("select all the search listed user checkboxes")
    public void select_all_the_search_listed_user_checkboxes() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserCheckbox(), "user checkbox");
        Thread.sleep(500);
    }

    @And("in the settings page choose Create on First Start (Lazy Create) option")
    public void in_the_settings_page_choose_create_on_first_start_lazy_create_option() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getLazyCreateOption(), "lazy create option");
        Thread.sleep(500);
    }

    @And("for each copied lab id search and access lab to validate expiry date")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_expiry_date() throws Throwable {
        String labIds = createdLabIds;
        String[] labIdArray = labIds.split(" ");

        LabsPage labsPage = new LabsPage(Hook.base.page);
        LabControlPanelPage labControlPanelPage = new LabControlPanelPage(Hook.base.page);

        for (String labId : labIdArray) {
            System.out.println("Processing Lab ID: " + labId);

            Hook.base.page.waitForLoadState();
            Thread.sleep(1000);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), labId, "search lab id " + labId);
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAllLabsSearchBtn(), "search button");
            Thread.sleep(2000);

            Hook.base.shDriver.click(labsPage.getLabCheckboxById(labId), "lab checkbox");
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAccessLabBtn(), "access lab button");
            Thread.sleep(3000);

            Hook.base.page.waitForLoadState();

            String expiryDate = Hook.base.shDriver.getText(labControlPanelPage.getExpiryDate(), "expiry date");
            System.out.println("Lab ID " + labId + " - Expiry Date: " + expiryDate);

            Assert.assertFalse(expiryDate.isEmpty(), "Expiry date should not be empty for Lab ID: " + labId);

            Hook.base.page.goBack();
            Thread.sleep(2000);

            Hook.base.shDriver.click(Pages.LabsPage.getSelectAllCheckbox(), "deselect all");
            Thread.sleep(500);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), "", "clear search");
            Thread.sleep(500);
        }

        System.out.println("All lab expiry date validation completed for " + labIdArray.length + " lab(s)");
    }

    @And("for each copied lab id search and access lab to validate expiry duration")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_expiry_duration() throws Throwable {
        String labIds = createdLabIds;
        String[] labIdArray = labIds.split(" ");

        LabsPage labsPage = new LabsPage(Hook.base.page);
        LabControlPanelPage labControlPanelPage = new LabControlPanelPage(Hook.base.page);

        for (String labId : labIdArray) {
            System.out.println("Processing Lab ID: " + labId);

            Hook.base.page.waitForLoadState();
            Thread.sleep(1000);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), labId, "search lab id " + labId);
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAllLabsSearchBtn(), "search button");
            Thread.sleep(2000);

            Hook.base.shDriver.click(labsPage.getLabCheckboxById(labId), "lab checkbox");
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAccessLabBtn(), "access lab button");
            Thread.sleep(3000);

            Hook.base.page.waitForLoadState();

            String expiryDuration = Hook.base.shDriver.getText(labControlPanelPage.getLabDuration(), "expiry duration");
            System.out.println("Lab ID " + labId + " - Expiry Duration: " + expiryDuration);

            Assert.assertFalse(expiryDuration.isEmpty(), "Expiry duration should not be empty for Lab ID: " + labId);

            Hook.base.page.goBack();
            Thread.sleep(2000);

            Hook.base.shDriver.click(Pages.LabsPage.getSelectAllCheckbox(), "deselect all");
            Thread.sleep(500);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), "", "clear search");
            Thread.sleep(500);
        }

        System.out.println("All lab expiry duration validation completed for " + labIdArray.length + " lab(s)");
    }

    @And("for each copied lab id search and access lab to validate plan with deaful policy is attached")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_plan_with_deaful_policy_is_attached()
            throws Throwable {
        String labIds = createdLabIds;
        String[] labIdArray = labIds.split(" ");

        LabsPage labsPage = new LabsPage(Hook.base.page);
        LabControlPanelPage labControlPanelPage = new LabControlPanelPage(Hook.base.page);

        for (String labId : labIdArray) {
            System.out.println("Processing Lab ID: " + labId);

            Hook.base.page.waitForLoadState();
            Thread.sleep(1000);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), labId, "search lab id " + labId);
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAllLabsSearchBtn(), "search button");
            Thread.sleep(2000);

            Hook.base.shDriver.click(labsPage.getLabCheckboxById(labId), "lab checkbox");
            Thread.sleep(1000);

            Hook.base.shDriver.click(Pages.LabsPage.getAccessLabBtn(), "access lab button");
            Thread.sleep(3000);

            Hook.base.page.waitForLoadState();

            String policyName = SingleLabRequest.testData.get("PolicyName");
            System.out.println("Checking for default policy: " + policyName);

            Hook.base.shDriver.click(labControlPanelPage.getPoliciesTab(), "policies tab");
            Thread.sleep(1000);

            Hook.base.page.waitForLoadState();

            boolean policyFound = labControlPanelPage.getPolicyRowByName(policyName).first().isVisible();

            Assert.assertTrue(policyFound,
                    "Default policy '" + policyName + "' should be assigned to Lab ID: " + labId);

            System.out.println("Default policy '" + policyName + "' is attached to Lab ID: " + labId);

            Hook.base.page.goBack();
            Thread.sleep(2000);

            Hook.base.shDriver.click(Pages.LabsPage.getSelectAllCheckbox(), "deselect all");
            Thread.sleep(500);

            Hook.base.shDriver.fill(Pages.LabsPage.getAllLabsSearchInput(), "", "clear search");
            Thread.sleep(500);
        }

        System.out.println("All lab default policy validation completed for " + labIdArray.length + " lab(s)");
    }
}