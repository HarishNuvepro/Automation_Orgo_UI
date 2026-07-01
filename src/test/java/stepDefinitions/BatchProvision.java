package stepDefinitions;

import org.testng.Assert;

import POM.LabControlPanelPage;
import POM.LabsPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Generic_Utility.WaitUtils;

public class BatchProvision {

    private static final Logger log = LoggerFactory.getLogger(BatchProvision.class);

    // ThreadLocal — parallel scenarios each carry their own data
    private static final ThreadLocal<String> tlCreatedLabIds  = new ThreadLocal<>();
    private static final ThreadLocal<String> tlCreatedBatchName = new ThreadLocal<>();

    public static String getCreatedLabIds() {
        return tlCreatedLabIds.get();
    }

    public static String getCreatedBatchName() {
        return tlCreatedBatchName.get();
    }

    @And("navigate to Batch Provisioning of Labs page")
    public void navigate_to_batch_provisioning_of_labs_page() throws Throwable {
        SingleLabRequest.dismissBlockingModal();
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getLabsTab(), "labs tab");
        Pages.getLabsDropdownPage().getBatchProvisioningLink().waitFor(
                new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(10_000));
        Hook.base().shDriver.click(Pages.getLabsDropdownPage().getBatchProvisioningLink(), "batch provisioning link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
    }

    @And("click on batch create button")
    public void click_on_batch_create_button() throws Throwable {
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getCreateBtn(), "batch create button");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("in batch provision details page enter name and description")
    public void in_batch_provision_details_page_enter_name_and_description() throws Throwable {
        String randomSuffix     = String.valueOf(System.currentTimeMillis() % 100000);
        String batchName        = SingleLabRequest.testData().get("BatchName") + randomSuffix;
        String batchDescription = SingleLabRequest.testData().get("BatchDescription") + randomSuffix;
        tlCreatedBatchName.set(batchName);
        log.info("Batch name: {}", batchName);
        Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getBatchNameInput(), batchName, "batch name");
        Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getBatchDescriptionInput(), batchDescription, "batch description");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("click on next button")
    public void click_on_next_button() throws Throwable {
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getNextBtn(), "next button");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
    }

    @And("in choose user page enter the user email address in the search box and click on search")
    public void in_choose_user_page_enter_the_user_email_address_in_the_search_box_and_click_on_search() throws Throwable {
        String userEmails = SingleLabRequest.testData().get("UserEmail");

        if (userEmails != null && userEmails.contains(",")) {
            for (String email : userEmails.split(",")) {
                Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getUserSearchInput(), email.trim(), "user email search");
                SingleLabRequest.dismissDtButtonBackground();
                Hook.base().shDriver.click(Pages.getBatchProvisionPage().getUserSearchBtn(), "search button");
                WaitUtils.pause(WaitUtils.MEDIUM);
                Hook.base().shDriver.click(Pages.getBatchProvisionPage().getUserCheckbox(), "user checkbox");
                WaitUtils.pause(WaitUtils.SHORT);
            }
        } else {
            Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getUserSearchInput(), userEmails, "user email search");
            SingleLabRequest.dismissDtButtonBackground();
            Hook.base().shDriver.click(Pages.getBatchProvisionPage().getUserSearchBtn(), "search button");
            WaitUtils.pause(WaitUtils.MEDIUM);
        }
    }

    @And("select the search listed user checkbox")
    public void select_the_search_listed_user_checkbox() throws Throwable {
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getUserCheckbox(), "user checkbox");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("in the choose plan page provide plan id input in search box and select the listed plan")
    public void in_the_choose_plan_page_provide_plan_id_input_in_search_box_and_select_the_listed_plan() throws Throwable {
        String planId = SingleLabRequest.testData().get("PlanID");
        // Under parallel load the plan list table renders slowly — wait before filling
        try {
            Pages.getBatchProvisionPage().getPlanSearchInput().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(30_000));
        } catch (Exception e) {
            log.warn("Plan search input not visible after 30s: {}", e.getMessage());
        }
        Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getPlanSearchInput(), planId, "plan id search");
        WaitUtils.pause(WaitUtils.LONG);
        WaitUtils.waitForVisible(Pages.getBatchProvisionPage().getPlanRowClick(planId), WaitUtils.TEN_SECONDS);
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getPlanRowClick(planId), "plan row");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("click on finish button")
    public void click_on_finish_button() throws Throwable {
        // Wait for the finish button to become visible — under parallel load the wizard
        // final step CSS can take longer to render, causing intermittent not-visible failures
        WaitUtils.waitForVisible(Pages.getBatchProvisionPage().getFinishBtn(), WaitUtils.THIRTY_SEC);
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getFinishBtn(), "finish button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("click on confirm button on create batch provisioning labs pop-up")
    public void click_on_confirm_button_on_create_batch_provisioning_labs_popup() throws Throwable {
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getConfirmBtn(), "confirm button");
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("in the batch provisioning summary table check user listed and status and lab id and details is getting generated")
    public void in_the_batch_provisioning_summary_table_check_user_listed_and_status_and_lab_id_and_details_is_getting_generated() throws Throwable {
        String userEmails = SingleLabRequest.testData().get("UserEmail");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);
        // Batch summary table is populated asynchronously — wait for the first row before reading
        try {
            Pages.getBatchProvisionPage().getAllBatchSummaryRows().first().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(60_000));
        } catch (Exception e) {
            log.warn("Batch summary rows not visible after 60s: {}", e.getMessage());
        }

        String[] emailArray = (userEmails != null && userEmails.contains(","))
                ? userEmails.split(",")
                : new String[]{ userEmails };

        StringBuilder allLabIds = new StringBuilder();

        for (String email : emailArray) {
            String trimmedEmail = email.trim();

            boolean userFound = Hook.base().shDriver.isVisible(
                    Pages.getBatchProvisionPage().getBatchSummaryRowByEmail(trimmedEmail), "user row");
            Assert.assertTrue(userFound, "User " + trimmedEmail + " should be listed");

            String status = Hook.base().shDriver.getText(
                    Pages.getBatchProvisionPage().getBatchSummaryStatusByEmail(trimmedEmail), "status");
            Assert.assertTrue(status.contains("Success"), "Status should be Success for " + trimmedEmail);

            String labId = Hook.base().shDriver.getText(
                    Pages.getBatchProvisionPage().getBatchSummaryLabIdByEmail(trimmedEmail), "lab id");
            Assert.assertFalse(labId.isEmpty(), "Lab ID should not be empty for " + trimmedEmail);

            String details = Hook.base().shDriver.getText(
                    Pages.getBatchProvisionPage().getBatchSummaryDetailsByEmail(trimmedEmail), "details");
            Assert.assertFalse(details.isEmpty(), "Details should not be empty for " + trimmedEmail);

            if (allLabIds.length() > 0) allLabIds.append(" ");
            allLabIds.append(labId);
        }

        tlCreatedLabIds.set(allLabIds.toString());
        log.info("All Lab IDs: {}", getCreatedLabIds());
    }

    @And("copy those lab id's and navigate to all labs page")
    public void copy_those_lab_ids_and_navigate_to_all_labs_page() throws Throwable {
        SingleLabRequest.dismissBlockingModal();
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getLabsTab(), "labs tab");
        Pages.getLabsDropdownPage().getLabsLink().waitFor(
                new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(10_000));
        Hook.base().shDriver.click(Pages.getLabsDropdownPage().getLabsLink(), "labs link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
    }

    @Then("validate all lab is completion status in the latest action in all labs table")
    public void validate_all_lab_is_completion_status_in_the_latest_action_in_all_labs_table() throws Throwable {
        String[] labIdArray = getCreatedLabIds().split(" ");
        LabsPage labsPage   = new LabsPage(Hook.base().page);

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionDropdown(), "search option dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionByText("id"), "ID option");
        WaitUtils.pause(WaitUtils.MEDIUM);

        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), String.join(" ", labIdArray), "search lab ids");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        WaitUtils.pause(WaitUtils.LONG);

        int maxRetries = 5;
        for (int retry = 0; retry < maxRetries; retry++) {
            boolean allComplete = true;

            for (String labId : labIdArray) {
                Hook.base().page.waitForLoadState();
                String status = Hook.base().shDriver.getText(labsPage.getLabLatestActionStatusById(labId), "status for " + labId);
                log.info("Lab {} status: {}", labId, status);

                if (status.trim().toLowerCase().contains("failed")) {
                    throw new RuntimeException("Lab failed — ID: " + labId + ", Status: " + status);
                }
                if (!status.trim().contains("Complete")) allComplete = false;
            }

            if (allComplete) {
                log.info("All {} labs completed", labIdArray.length);
                return;
            }

            if (retry < maxRetries - 1) {
                Hook.base().shDriver.click(Pages.getLabsPage().getSelectAllCheckbox(), "select all");
                WaitUtils.pause(WaitUtils.SHORT);
                Hook.base().shDriver.click(Pages.getLabsPage().getSyncLabStatusBtn(), "sync button");
                WaitUtils.pause(WaitUtils.TEN_SECONDS);
            }
        }

        throw new RuntimeException("Timeout: Not all labs reached Complete status after " + maxRetries + " retries.");
    }

    @And("for each copied lab id search and access lab to validate expiry date")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_expiry_date() throws Throwable {
        String[] labIdArray         = getCreatedLabIds().split(" ");
        LabsPage labsPage           = new LabsPage(Hook.base().page);
        LabControlPanelPage lcp     = new LabControlPanelPage(Hook.base().page);

        for (String labId : labIdArray) {
            navigateToLab(labsPage, labId);
            String expiryDate = Hook.base().shDriver.getText(lcp.getExpiryDate(), "expiry date");
            Assert.assertFalse(expiryDate.isEmpty(), "Expiry date empty for Lab: " + labId);
            log.info("Lab {} expiry date: {}", labId, expiryDate);
            returnToLabsList();
        }
    }

    @And("for each copied lab id search and access lab to validate expiry duration")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_expiry_duration() throws Throwable {
        String[] labIdArray     = getCreatedLabIds().split(" ");
        LabsPage labsPage       = new LabsPage(Hook.base().page);
        LabControlPanelPage lcp = new LabControlPanelPage(Hook.base().page);

        for (String labId : labIdArray) {
            navigateToLab(labsPage, labId);
            String duration = Hook.base().shDriver.getText(lcp.getLabDuration(), "expiry duration");
            Assert.assertFalse(duration.isEmpty(), "Expiry duration empty for Lab: " + labId);
            log.info("Lab {} expiry duration: {}", labId, duration);
            returnToLabsList();
        }
    }

    @And("for each copied lab id search and access lab to validate plan with deaful policy is attached")
    public void for_each_copied_lab_id_search_and_access_lab_to_validate_plan_with_deaful_policy_is_attached() throws Throwable {
        String[] labIdArray     = getCreatedLabIds().split(" ");
        LabsPage labsPage       = new LabsPage(Hook.base().page);
        LabControlPanelPage lcp = new LabControlPanelPage(Hook.base().page);
        String policyName       = SingleLabRequest.testData().get("PolicyName");

        for (String labId : labIdArray) {
            navigateToLab(labsPage, labId);
            Hook.base().shDriver.click(lcp.getPoliciesTab(), "policies tab");
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().page.waitForLoadState();
            boolean policyFound = lcp.getPolicyRowByName(policyName).first().isVisible();
            Assert.assertTrue(policyFound, "Policy '" + policyName + "' missing for Lab: " + labId);
            log.info("Policy '{}' attached to Lab {}", policyName, labId);
            returnToLabsList();
        }
    }

    @Then("search created batch and select checkbox")
    public void search_created_batch_and_select_checkbox() throws Throwable {
        String batchName = getCreatedBatchName();
        log.info("Searching for batch: {}", batchName);
        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.fill(Pages.getBatchProvisionPage().getAllLabsSearchInput(), batchName, "batch search input");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getAllLabsSearchBtn(), "batch search button");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getBatchListRowCheckboxByName(batchName), "batch row checkbox");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("click on batch view button")
    public void click_on_batch_view_button() throws Throwable {
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getBatchViewBtn(), "batch view button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();
    }

    @And("select all labs in batch details")
    public void select_all_labs_in_batch_details() throws Throwable {
        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getBatchDetailsSelectAllCheckbox(), "select all labs");
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("All labs selected in batch details page");
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void navigateToLab(LabsPage labsPage, String labId) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "search " + labId);
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().shDriver.click(labsPage.getLabCheckboxById(labId), "lab checkbox");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getAccessLabBtn(), "access lab");
        WaitUtils.pause(WaitUtils.EXTRA_LONG);
        Hook.base().page.waitForLoadState();
    }

    private void returnToLabsList() throws Throwable {
        Hook.base().page.goBack();
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().shDriver.click(Pages.getLabsPage().getSelectAllCheckbox(), "deselect all");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), "", "clear search");
        WaitUtils.pause(WaitUtils.SHORT);
    }
}
