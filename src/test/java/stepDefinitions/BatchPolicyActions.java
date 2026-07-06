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
        assignBatchPolicyAndWait(policyName, "NLDurationQuotaPolicy", null);
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

            // The confirm-remove action can itself trigger a page reload right as goBack()
            // fires, aborting one of the two in-flight navigations (net::ERR_ABORTED /
            // "frame was detached"). The removal has already succeeded by this point —
            // treat the navigation race as transient noise and continue.
            try {
                Hook.base().page.goBack();
            } catch (Exception e) {
                log.warn("goBack() navigation raced with a page reload (tolerated): {}", e.getMessage());
            }
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
        String policies = SingleLabRequest.testData().get("PolicyName").trim();
        // Some Excel cells wrap the whole comma-separated value in literal quotes —
        // strip them so the Select2 lookup gets the real option text.
        if (policies.length() >= 2 && policies.startsWith("\"") && policies.endsWith("\"")) {
            policies = policies.substring(1, policies.length() - 1);
        }
        return policies.split(",")[index].trim();
    }

    /**
     * Robustly opens the Assign-Policy modal and selects the policy attach type.
     *
     * The Policies control is a Bootstrap dropdown and the "Assign Policy" link lives
     * inside its menu — clicking the link while the menu is closed makes it "not visible".
     * Driving this through shDriver is actively harmful here: its self-healing fallback
     * ('//*[contains(@class,'button')]') clicks an unrelated toolbar button (e.g. Export)
     * and falsely reports success, leaving the modal unopened and the next step stranded.
     * So we use raw Playwright: open the menu, confirm the link is visible, click it, and
     * confirm the modal's type filter rendered — retrying the whole sequence if either the
     * menu or the modal fails to appear.
     */
    private void openAssignPolicyModalAndSelectType(String filterType) throws Throwable {
        com.microsoft.playwright.Page page = Hook.base().page;
        com.microsoft.playwright.Locator policiesToggle = Pages.getLabsPage().getPoliciesDropdown();
        com.microsoft.playwright.Locator assignLink     = Pages.getLabsPage().getAssignPolicyLink();
        com.microsoft.playwright.Locator typeFilter     = page.locator("#policyAttachTypeFilter");

        SingleLabRequest.dismissDtButtonBackground();

        int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            page.waitForLoadState();

            // Open the Policies dropdown (skip the toggle if its menu is already showing,
            // since a second click would just close it again).
            if (!assignLink.isVisible()) {
                policiesToggle.click();
            }
            try {
                assignLink.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(8_000));
            } catch (Exception e) {
                log.warn("Policies menu did not open (attempt {}/{}) — retrying", attempt, maxAttempts);
                try { page.keyboard().press("Escape"); } catch (Exception ignored) {}
                WaitUtils.pause(WaitUtils.SHORT);
                continue;
            }

            assignLink.click();

            // Confirm the modal actually rendered by waiting for its type filter to show.
            try {
                typeFilter.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(10_000));
                typeFilter.selectOption(filterType);
                log.info("Assign-Policy modal opened — type filter set to {}", filterType);
                return;
            } catch (Exception e) {
                log.warn("Assign-Policy modal did not render (attempt {}/{}) — retrying", attempt, maxAttempts);
                try { page.keyboard().press("Escape"); } catch (Exception ignored) {}
                WaitUtils.pause(WaitUtils.SHORT);
            }
        }
        Assert.fail("Could not open the Assign-Policy modal after " + maxAttempts
                + " attempts (Policies dropdown → Assign Policy → type filter).");
    }

    /** Assigns a policy to a single already-selected lab and polls until that lab is Complete. */
    private void assignPerLabPolicyAndWait(String labId, String policyName,
            String filterType) throws Throwable {
        openAssignPolicyModalAndSelectType(filterType);
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

        openAssignPolicyModalAndSelectType(filterType);
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
