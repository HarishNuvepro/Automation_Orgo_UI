package stepDefinitions;

import org.testng.Assert;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyActions {

    private static final Logger log = LoggerFactory.getLogger(PolicyActions.class);

    // ── TC46 — Cleanup / Suspend / Resume (Action Now, validates Status=Complete) ─

    @Then("assign cleanup policy to the lab and validate action is complete")
    public void assign_cleanup_policy_to_the_lab() throws Throwable {
        String policyName = getPolicyByIndex(0);
        log.info("Assigning Cleanup policy: {}", policyName);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    @Then("assign suspend policy to the lab and validate action is complete")
    public void assign_suspend_policy_to_the_lab() throws Throwable {
        String policyName = getPolicyByIndex(1);
        log.info("Assigning Suspend policy: {}", policyName);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    @Then("assign resume policy to the lab and validate action is complete")
    public void assign_resume_policy_to_the_lab() throws Throwable {
        String policyName = getPolicyByIndex(2);
        log.info("Assigning Resume policy: {}", policyName);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLActionNowPolicy", null);
    }

    // ── TC47 — Budget policies via Action Now (validates Latest Action + Status) ─

    @Then("assign budget policy {int} to the lab and validate latest action {string} is complete")
    public void assign_budget_policy_and_validate_action(int policyIndex, String expectedLatestAction)
            throws Throwable {
        String policyName = getPolicyByIndex(policyIndex - 1);
        log.info("Assigning budget policy {} [{}] | Expected Latest Action: {}",
                policyIndex, policyName, expectedLatestAction);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLActionNowPolicy", expectedLatestAction);
    }

    // ── TC48 — Quota Duration, Quota Budget, Manual Remove, Quota Top-Up ─────

    @Then("assign quota duration policy to the lab and validate success message")
    public void assign_quota_duration_policy_to_the_lab() throws Throwable {
        String policyName = getPolicyByIndex(0);
        log.info("Assigning Quota Duration policy: {}", policyName);
        reSearchLabById();
        assignQuotaDurationPolicy(policyName);
    }

    @Then("assign quota budget policy to the lab and validate latest action {string} is complete")
    public void assign_quota_budget_policy(String expectedLatestAction) throws Throwable {
        String policyName = getPolicyByIndex(1);
        log.info("Assigning Quota Budget policy: {} | Expected Latest Action: {}",
                policyName, expectedLatestAction);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLCostQuotaPolicy", expectedLatestAction);
    }

    @Then("access lab and remove quota budget policy")
    public void access_lab_and_remove_quota_budget_policy() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        String policyName = getPolicyByIndex(1); // quota budget policy assigned in previous step
        log.info("Accessing lab {} to remove quota budget policy: {}", labId, policyName);

        // Select checkbox then click Access Lab button
        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox for " + labId);
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAccessLabBtn(), "access lab button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();

        // Open the Policies section in the Lab Control Panel
        Hook.base().shDriver.click(Pages.getLabControlPanelPage().getPoliciesTab(), "policies tab");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        // Search for the policy by name in the policyTable search box
        Hook.base().shDriver.fill(
                Pages.getLabControlPanelPage().getPolicySearchInput(), policyName, "policy search input");
        WaitUtils.pause(WaitUtils.MEDIUM);

        // Click the row to select it (row turns active)
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getPolicyTableRowByName(policyName),
                "policy row " + policyName);
        WaitUtils.pause(WaitUtils.SHORT);

        // Click Remove button — opens confirmation modal
        Hook.base().shDriver.click(Pages.getLabControlPanelPage().getRemovePolicyBtn(), "remove policy button");
        WaitUtils.pause(WaitUtils.SHORT);

        // Confirm removal in the modal
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getConfirmRemovePolicyBtn(), "confirm remove policy");
        WaitUtils.pause(WaitUtils.LONG);
        log.info("Quota budget policy '{}' removed from lab {}", policyName, labId);
    }

    @Then("assign quota top-up budget policy to the lab and validate latest action {string} is complete")
    public void assign_quota_topup_budget_policy(String expectedLatestAction) throws Throwable {
        String policyName = getPolicyByIndex(2);
        log.info("Assigning Quota Top-Up Budget policy: {} | Expected Latest Action: {}",
                policyName, expectedLatestAction);
        reSearchLabById();
        assignPolicyAndWait(policyName, "NLCostQuotaPolicy", expectedLatestAction);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private String getPolicyByIndex(int index) {
        String policies = SingleLabRequest.testData().get("PolicyName");
        String[] parts = policies.split(",");
        return parts[index].trim();
    }

    /** Re-applies the lab ID search so the row stays visible before each policy step. */
    private void reSearchLabById() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);

        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "re-search lab ID");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "re-search button");
        WaitUtils.pause(WaitUtils.LONG);

        Hook.base().page.waitForLoadState();
    }

    /**
     * Assigns a Quota Duration policy and validates a success notification appears.
     * No table-level action polling — success message is sufficient.
     */
    private void assignQuotaDurationPolicy(String policyName) throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();

        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox for " + labId);
        WaitUtils.pause(WaitUtils.SHORT);

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

        // Wait up to 30 s for the success notification to appear
        Hook.base().page.locator(".alert-success, .toast-success, .alert.alert-success")
                .first()
                .waitFor(new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(30_000));
        log.info("Quota Duration policy '{}' applied — success message confirmed", policyName);
    }

    /**
     * Full policy assignment flow for Action Now / Quota Budget / Quota Top-Up:
     * select checkbox → Policies → Assign → filter type → Select2 → Apply → poll.
     *
     * @param filterType           e.g. "NLActionNowPolicy", "NLCostQuotaPolicy"
     * @param expectedLatestAction null = only Status=Complete checked;
     *                             non-null = also asserts Latest Action text matches.
     */
    private void assignPolicyAndWait(String policyName, String filterType,
            String expectedLatestAction) throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();

        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox for " + labId);
        WaitUtils.pause(WaitUtils.SHORT);

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

        waitForPolicyActionComplete(labId, policyName, expectedLatestAction, 600);
    }


    /**
     * Polls every 30 s (max 10 min).
     * Pass: Status = "Complete" AND (expectedLatestAction == null OR action text matches).
     */
    private void waitForPolicyActionComplete(String labId, String policyName,
            String expectedLatestAction, int maxWaitSeconds) throws InterruptedException {
        int interval = 30;
        String latestAction = "";
        String latestStatus = "";

        for (int elapsed = 0; elapsed < maxWaitSeconds; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "table refresh");
            WaitUtils.pause(WaitUtils.MEDIUM);

            Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "re-search lab ID");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "re-search button");
            WaitUtils.pause(WaitUtils.MEDIUM);

            latestAction = Hook.base().shDriver.getText(
                    Pages.getLabsPage().getLabLatestActionById(labId), "latest action");
            latestStatus = Hook.base().shDriver.getText(
                    Pages.getLabsPage().getLabLatestActionStatusById(labId), "latest status");
            log.info("Lab {} | Policy: {} | Latest Action: {} | Status: {}",
                    labId, policyName, latestAction, latestStatus);

            boolean statusOk = latestStatus.trim().equalsIgnoreCase("Complete");
            boolean actionOk = expectedLatestAction == null
                    || latestAction.trim().equalsIgnoreCase(expectedLatestAction);

            if (statusOk && actionOk) {
                log.info("Policy '{}' confirmed complete — action: {}, status: {}",
                        policyName, latestAction, latestStatus);
                return;
            }

            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Policy '" + policyName + "' for lab " + labId
                + " did not complete within " + maxWaitSeconds + "s."
                + " Last: action='" + latestAction + "', status='" + latestStatus + "'"
                + (expectedLatestAction != null
                        ? " (expected action: '" + expectedLatestAction + "')" : ""));
    }
}
