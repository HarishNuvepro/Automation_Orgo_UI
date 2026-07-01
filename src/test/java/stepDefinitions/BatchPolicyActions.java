package stepDefinitions;

import org.testng.Assert;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchPolicyActions {

    private static final Logger log = LoggerFactory.getLogger(BatchPolicyActions.class);

    // ── TC56 — Cleanup / Suspend / Resume ────────────────────────────────────

    @Then("assign cleanup policy to batch labs and validate action is complete")
    public void assign_cleanup_policy_to_batch_labs() throws Throwable {
        String policyName = getPolicyByIndex(0);
        log.info("Assigning Cleanup policy to batch labs: {}", policyName);
        assignBatchPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    @Then("assign suspend policy to batch labs and validate action is complete")
    public void assign_suspend_policy_to_batch_labs() throws Throwable {
        String policyName = getPolicyByIndex(1);
        log.info("Assigning Suspend policy to batch labs: {}", policyName);
        reSelectAllBatchLabs();
        assignBatchPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    @Then("assign resume policy to batch labs and validate action is complete")
    public void assign_resume_policy_to_batch_labs() throws Throwable {
        String policyName = getPolicyByIndex(2);
        log.info("Assigning Resume policy to batch labs: {}", policyName);
        reSelectAllBatchLabs();
        assignBatchPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    // ── TC58 — Quota Duration / Quota Budget / Remove / Quota Top-Up ────────────

    @Then("assign quota duration policy to batch labs and validate success message")
    public void assign_quota_duration_policy_to_batch_labs() throws Throwable {
        String policyName = getPolicyByIndex(0);
        log.info("Assigning Quota Duration policy to all batch labs: {}", policyName);
        Hook.base().shDriver.click(Pages.getLabsPage().getPoliciesDropdown(), "policies dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAssignPolicyLink(), "assign policy link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        Hook.base().page.locator("#policyAttachTypeFilter").selectOption("NLDurationQuotaPolicy");
        WaitUtils.pause(WaitUtils.MEDIUM);

        Hook.base().page.locator("#policyOperationModel .select2-selection--single").click();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().page.locator(".select2-search__field").fill(policyName);
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.locator(".select2-results__option:has-text('" + policyName + "')").first().click();
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Quota Duration policy selected: {}", policyName);

        Hook.base().page.locator("#policyOpBtn").click();
        WaitUtils.pause(WaitUtils.SHORT);

        Hook.base().page.locator(".alert-success, .toast-success, .alert.alert-success")
                .first()
                .waitFor(new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(30_000));
        log.info("Quota Duration policy '{}' applied to batch labs — success confirmed", policyName);
        WaitUtils.pause(WaitUtils.TWENTY_SECONDS);
    }

    @Then("assign quota budget policy to each batch lab and validate latest action {string} is complete")
    public void assign_quota_budget_policy_to_each_batch_lab(String expectedLatestAction) throws Throwable {
        String policyName = getPolicyByIndex(1);
        log.info("Assigning Quota Budget policy individually to each batch lab: {}", policyName);
        for (String labId : BatchProvision.getCreatedLabIds().split(" ")) {
            log.info("Assigning Quota Budget to lab: {}", labId);
            refreshAndSelectLab(labId);
            assignPerLabPolicyAndWait(labId, policyName, "NLCostQuotaPolicy");
        }
    }

    @Then("access each batch lab and remove quota budget policy")
    public void access_each_batch_lab_and_remove_quota_budget_policy() throws Throwable {
        String policyName = getPolicyByIndex(1);
        for (String labId : BatchProvision.getCreatedLabIds().split(" ")) {
            log.info("Accessing lab {} to remove quota budget policy: {}", labId, policyName);
            refreshAndSelectLab(labId);
            Hook.base().shDriver.click(Pages.getLabsPage().getAccessLabBtn(), "access lab");
            WaitUtils.pause(WaitUtils.EXTRA_LONG);
            Hook.base().page.waitForLoadState();

            Hook.base().shDriver.click(Pages.getLabControlPanelPage().getPoliciesTab(), "policies tab");
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().page.waitForLoadState();

            Hook.base().shDriver.fill(Pages.getLabControlPanelPage().getPolicySearchInput(),
                    policyName, "policy search");
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().shDriver.click(Pages.getLabControlPanelPage().getPolicyTableRowByName(policyName),
                    "policy row");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getLabControlPanelPage().getRemovePolicyBtn(), "remove policy");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getLabControlPanelPage().getConfirmRemovePolicyBtn(),
                    "confirm remove");
            WaitUtils.pause(WaitUtils.LONG);
            log.info("Quota Budget policy '{}' removed from lab {}", policyName, labId);

            Hook.base().page.goBack();
            WaitUtils.pause(WaitUtils.LONG);
            Hook.base().page.waitForLoadState();
        }
    }

    @Then("assign quota top-up budget policy to each batch lab and validate latest action {string} is complete")
    public void assign_quota_topup_budget_policy_to_each_batch_lab(String expectedLatestAction) throws Throwable {
        String policyName = getPolicyByIndex(2);
        log.info("Assigning Quota Top-Up Budget policy individually to each batch lab: {}", policyName);
        for (String labId : BatchProvision.getCreatedLabIds().split(" ")) {
            log.info("Assigning Quota Top-Up to lab: {}", labId);
            refreshAndSelectLab(labId);
            assignPerLabPolicyAndWait(labId, policyName, "NLCostQuotaPolicy");
        }
    }

    // ── TC57 — Budget policies (Assign Budget / Remove All / Set Budget) ────────

    @Then("assign budget policy {int} to batch labs and validate latest action {string} is complete")
    public void assign_budget_policy_to_batch_labs(int policyIndex, String expectedLatestAction)
            throws Throwable {
        String policyName = getPolicyByIndex(policyIndex - 1);
        log.info("Assigning budget policy {} [{}] to batch labs | Expected Latest Action: {}",
                policyIndex, policyName, expectedLatestAction);
        if (policyIndex > 1) reSelectAllBatchLabs();
        assignBatchPolicyAndWait(policyName, "NLActionNowPolicy", expectedLatestAction);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private String getPolicyByIndex(int index) {
        String policies = SingleLabRequest.testData().get("PolicyName");
        return policies.split(",")[index].trim();
    }

    /** Assigns a policy to a single already-selected lab and polls until that lab is Complete. */
    private void assignPerLabPolicyAndWait(String labId, String policyName,
            String filterType) throws Throwable {
        Hook.base().shDriver.click(Pages.getLabsPage().getPoliciesDropdown(), "policies dropdown");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().shDriver.click(Pages.getLabsPage().getAssignPolicyLink(), "assign policy link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        Hook.base().page.locator("#policyAttachTypeFilter").selectOption(filterType);
        WaitUtils.pause(WaitUtils.MEDIUM);

        Hook.base().page.locator("#policyOperationModel .select2-selection--single").click();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().page.locator(".select2-search__field").fill(policyName);
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.locator(".select2-results__option:has-text('" + policyName + "')").first().click();
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Policy selected for lab {}: {} (filter: {})", labId, policyName, filterType);

        Hook.base().page.locator("#policyOpBtn").click();
        WaitUtils.pause(WaitUtils.LONG);

        waitForSingleLabPolicyComplete(labId, policyName, 600);
    }

    /** Polls every 30 s on the batch details page. All batch labs are visible — no search needed. */
    private void waitForSingleLabPolicyComplete(String labId, String policyName,
            int maxWaitSeconds) throws InterruptedException {
        int interval = 30;
        String lastStatus = "";

        for (int elapsed = 0; elapsed < maxWaitSeconds; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "table refresh");
            WaitUtils.pause(WaitUtils.MEDIUM);

            try {
                lastStatus = Hook.base().shDriver.getText(
                        Pages.getLabsPage().getLabLatestActionStatusById(labId), "lab " + labId + " status");
                log.info("Lab {} | Policy: {} | Status: {}", labId, policyName, lastStatus);
                if (lastStatus.trim().equalsIgnoreCase("Complete")) {
                    log.info("Policy '{}' complete for lab {}", policyName, labId);
                    return;
                }
            } catch (Exception e) {
                log.info("Lab {} status not readable yet, retrying...", labId);
            }

            log.info("Policy '{}' not complete for lab {} — retrying in {}s ({}/{}s elapsed)",
                    policyName, labId, interval, elapsed + interval, maxWaitSeconds);
            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Policy '" + policyName + "' did not complete for lab "
                + labId + " within " + maxWaitSeconds + "s. Last status: '" + lastStatus + "'");
    }

    /**
     * Refreshes the batch details table, waits for it to fully reload,
     * then selects one lab's checkbox. This ensures the table is stable
     * before the click so the selection registers correctly.
     */
    private void refreshAndSelectLab(String labId) throws Throwable {
        Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "refresh table");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox " + labId);
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    /** Re-clicks select-all on the batch details page before each subsequent policy. */
    private void reSelectAllBatchLabs() throws Throwable {
        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.click(Pages.getBatchProvisionPage().getBatchDetailsSelectAllCheckbox(), "select all batch labs");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    /**
     * Labs are already selected on the batch details page.
     * Opens Policies → Assign → filter type → select policy → apply.
     * Then polls every 30 s until every batch lab shows the action complete.
     */
    private void assignBatchPolicyAndWait(String policyName, String filterType,
            String expectedLatestAction) throws Throwable {

        Hook.base().shDriver.click(Pages.getLabsPage().getPoliciesDropdown(), "policies dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAssignPolicyLink(), "assign policy link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        Hook.base().page.locator("#policyAttachTypeFilter").selectOption(filterType);
        WaitUtils.pause(WaitUtils.MEDIUM);

        Hook.base().page.locator("#policyOperationModel .select2-selection--single").click();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().page.locator(".select2-search__field").fill(policyName);
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.locator(".select2-results__option:has-text('" + policyName + "')").first().click();
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Policy selected: {} (filter: {})", policyName, filterType);

        Hook.base().page.locator("#policyOpBtn").click();
        WaitUtils.pause(WaitUtils.LONG);

        waitForBatchPolicyComplete(policyName, expectedLatestAction, 600);
    }

    /**
     * Polls every 30 s on the batch details page.
     * Selects all labs, refreshes, then reads every row's status label in one pass.
     */
    private void waitForBatchPolicyComplete(String policyName, String expectedLatestAction,
            int maxWaitSeconds) throws InterruptedException {
        int expectedCount = BatchProvision.getCreatedLabIds().split(" ").length;
        int interval = 30;
        String lastStatus = "";

        for (int elapsed = 0; elapsed < maxWaitSeconds; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            Hook.base().shDriver.click(
                    Pages.getBatchProvisionPage().getBatchDetailsSelectAllCheckbox(), "select all");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "table refresh");
            WaitUtils.pause(WaitUtils.MEDIUM);

            try {
                com.microsoft.playwright.Locator statusLabels =
                        Hook.base().page.locator("#mySubscriptionsTable tbody tr td label");
                int count = statusLabels.count();
                log.info("Policy '{}' — {} status labels visible (expected {})",
                        policyName, count, expectedCount);

                boolean allDone = count >= expectedCount;
                for (int i = 0; i < count; i++) {
                    lastStatus = statusLabels.nth(i).textContent().trim();
                    log.info("Policy: {} | Row {} | Status: {}", policyName, i + 1, lastStatus);
                    if (!lastStatus.equalsIgnoreCase("Complete")) allDone = false;
                }

                if (allDone) {
                    log.info("Policy '{}' complete for all {} batch labs", policyName, count);
                    return;
                }
            } catch (Exception e) {
                log.info("Status labels not readable yet, retrying...");
            }

            log.info("Policy '{}' not complete yet — retrying in {}s ({}/{}s elapsed)",
                    policyName, interval, elapsed + interval, maxWaitSeconds);
            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Policy '" + policyName + "' did not complete for all batch labs within "
                + maxWaitSeconds + "s. Last status: '" + lastStatus + "'");
    }
}
