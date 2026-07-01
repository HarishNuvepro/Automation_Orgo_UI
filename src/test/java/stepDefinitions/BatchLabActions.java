package stepDefinitions;

import org.testng.Assert;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchLabActions {

    private static final Logger log = LoggerFactory.getLogger(BatchLabActions.class);

    // ── Actions (run on batch details page after select-all) ─────────────────

    @Then("perform stop action on batch labs")
    public void perform_stop_action_on_batch_labs() throws Throwable {
        log.info("Performing Stop action on all batch labs");
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getStopLink(), "stop link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("perform start action on batch labs")
    public void perform_start_action_on_batch_labs() throws Throwable {
        log.info("Performing Start action on all batch labs");
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getStartLink(), "start link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("perform delete action on batch labs")
    public void perform_delete_action_on_batch_labs() throws Throwable {
        log.info("Performing Delete action on all batch labs");
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getDeleteLink(), "delete link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    // ── Validation (stays on batch details page, polls refresh button) ────────

    @Then("validate all batch labs are in stopped status")
    public void validate_all_batch_labs_are_in_stopped_status() throws Throwable {
        validateBatchAction("Stop", 600);
    }

    @Then("validate all batch labs are in running status")
    public void validate_all_batch_labs_are_in_running_status() throws Throwable {
        validateBatchAction("Start", 600);
    }

    @Then("validate all batch labs are deleted successfully")
    public void validate_all_batch_labs_are_deleted_successfully() throws Throwable {
        validateBatchDelete(600);
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void confirmActionModal() {
        try {
            Hook.base().page.locator(
                    "#confirmOperations button[type='submit'], " +
                    "#confirmOperations .btn-primary, " +
                    "#confirmOperations button:has-text('Confirm'), " +
                    "#confirmOperations button:has-text('Yes'), " +
                    "#confirmOperations button:has-text('OK')"
            ).first().click();
            log.info("Confirmation modal accepted");
            WaitUtils.pause(WaitUtils.SHORT);
        } catch (Exception e) {
            log.debug("No confirmation modal present");
        }
    }

    /**
     * Stays on the batch details page. Every 20 s: select all → refresh → read all row statuses.
     * Passes when every row shows actionName + Complete.
     */
    private void validateBatchAction(String actionName, int maxWaitSeconds) throws Throwable {
        int expectedCount = BatchProvision.getCreatedLabIds().split(" ").length;
        int interval = 20;
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
                log.info("{} — {} status labels visible (expected {})", actionName, count, expectedCount);

                boolean allDone = count >= expectedCount;
                for (int i = 0; i < count; i++) {
                    lastStatus = statusLabels.nth(i).textContent().trim();
                    log.info("Action: {} | Row {} | Status: {}", actionName, i + 1, lastStatus);
                    if (!lastStatus.equalsIgnoreCase("Complete")) allDone = false;
                }

                if (allDone) {
                    log.info("All {} batch labs reached '{}' Complete", count, actionName);
                    return;
                }
            } catch (Exception e) {
                log.info("Status labels not readable yet, retrying...");
            }

            log.info("{} not yet complete — retrying in {}s ({}/{}s elapsed)",
                    actionName, interval, elapsed + interval, maxWaitSeconds);
            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Not all batch labs reached '" + actionName
                + "' Complete within " + maxWaitSeconds + "s. Last status: '" + lastStatus + "'");
    }

    /**
     * Stays on the batch details page. Every 30 s: select all → refresh → check rows.
     * Passes when all rows are gone or show Delete Complete.
     */
    private void validateBatchDelete(int maxWaitSeconds) throws Throwable {
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
                com.microsoft.playwright.Locator allRows =
                        Hook.base().page.locator("#mySubscriptionsTable tbody tr");
                int rowCount = allRows.count();

                if (rowCount == 0) {
                    log.info("All batch lab rows gone — treated as deleted");
                    return;
                }

                com.microsoft.playwright.Locator statusLabels =
                        Hook.base().page.locator("#mySubscriptionsTable tbody tr td label");
                int count = statusLabels.count();
                log.info("Delete — {} rows, {} status labels visible (expected {})",
                        rowCount, count, expectedCount);

                boolean allDone = count >= expectedCount;
                for (int i = 0; i < count; i++) {
                    lastStatus = statusLabels.nth(i).textContent().trim();
                    log.info("Delete | Row {} | Status: {}", i + 1, lastStatus);
                    if (!lastStatus.equalsIgnoreCase("Complete")) allDone = false;
                }

                if (allDone) {
                    log.info("All {} batch labs deleted successfully", count);
                    return;
                }
            } catch (Exception e) {
                log.info("Status labels not readable yet, retrying...");
            }

            log.info("Delete not yet complete — retrying in {}s ({}/{}s elapsed)",
                    interval, elapsed + interval, maxWaitSeconds);
            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Not all batch labs reached Delete Complete within "
                + maxWaitSeconds + "s. Last status: '" + lastStatus + "'");
    }
}
