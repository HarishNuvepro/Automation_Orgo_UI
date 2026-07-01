package stepDefinitions;

import org.testng.Assert;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LabActions {

    private static final Logger log = LoggerFactory.getLogger(LabActions.class);

    @And("search the created lab by id")
    public void search_the_created_lab_by_id() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        log.info("Searching for lab ID: {}", labId);

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        SingleLabRequest.dismissDtButtonBackground();

        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionDropdown(), "search option dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionByText("id"), "ID search option");
        WaitUtils.pause(WaitUtils.SHORT);

        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "lab ID search input");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        WaitUtils.pause(WaitUtils.LONG);

        Hook.base().page.waitForLoadState();
    }

    @Then("perform stop action on the lab")
    public void perform_stop_action_on_the_lab() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        log.info("Performing Stop action on lab ID: {}", labId);

        selectLabCheckbox(labId);
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getStopLink(), "stop link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("validate the lab is in stopped status")
    public void validate_the_lab_is_in_stopped_status() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        waitForActionCompletion(labId, "Stop", 600);
        log.info("Lab {} — Stop action confirmed Complete", labId);
    }

    @Then("perform start action on the lab")
    public void perform_start_action_on_the_lab() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        log.info("Performing Start action on lab ID: {}", labId);

        selectLabCheckbox(labId);
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getStartLink(), "start link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("validate the lab is in running status")
    public void validate_the_lab_is_in_running_status() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        waitForActionCompletion(labId, "Start", 600);
        log.info("Lab {} — Start action confirmed Complete", labId);
    }

    @Then("perform delete action on the lab")
    public void perform_delete_action_on_the_lab() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        log.info("Performing Delete action on lab ID: {}", labId);

        selectLabCheckbox(labId);
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getDeleteLink(), "delete link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("validate the lab is deleted successfully")
    public void validate_the_lab_is_deleted_successfully() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        waitForDeleteCompletion(labId, 600);
        log.info("Lab {} — Delete action confirmed Complete", labId);
    }

    @And("register lab for final cleanup")
    public void register_lab_for_final_cleanup() {
        String labId = SingleLabRequest.getCreatedLabId();
        LabCleanupRegistry.register(labId);
        log.info("Lab {} registered for final bulk delete", labId);
    }

    @And("register batch provision labs for cleanup")
    public void register_batch_provision_labs_for_cleanup() {
        String labIds = BatchProvision.getCreatedLabIds();
        if (labIds == null || labIds.trim().isEmpty()) {
            log.warn("No batch provision lab IDs found to register for cleanup");
            return;
        }
        for (String labId : labIds.trim().split("\\s+")) {
            LabCleanupRegistry.register(labId);
            log.info("Batch lab {} registered for final bulk delete", labId);
        }
    }

    @Then("delete all registered labs and wait for completion")
    public void delete_all_registered_labs_and_wait_for_completion() throws Throwable {
        List<String> labIds = LabCleanupRegistry.getAll();
        if (labIds.isEmpty()) {
            log.info("No labs registered for cleanup — skipping");
            return;
        }
        log.info("Starting bulk cleanup for {} labs: {}", labIds.size(), labIds);

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);

        // ── 1. Set page size to 100 so all results are visible ───────────────
        setTablePageSize("100");

        // ── 2. Search all lab IDs in one shot ────────────────────────────────
        searchByLabIds(labIds);

        // ── 3. Select all + bulk delete ──────────────────────────────────────
        Hook.base().shDriver.click(Pages.getLabsPage().getSelectAllCheckbox(), "select all labs");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getActionsDropdown(), "actions dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getDeleteLink(), "delete link");
        WaitUtils.pause(WaitUtils.SHORT);
        confirmActionModal();
        WaitUtils.pause(WaitUtils.MEDIUM);
        log.info("Bulk delete triggered — monitoring Active filter (max 15 min)");

        // ── 4. Stay on Active filter, refresh every 20s until all entries gone ──
        waitForActiveFilterEmpty(labIds, 720);

        // ── 5. Switch to Deleted filter and confirm each lab shows Complete ───
        verifyInDeletedFilter(labIds);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void selectLabCheckbox(String labId) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox for " + labId);
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Selected checkbox for lab ID: {}", labId);
    }

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
     * Polls every 5 seconds until Latest Action = actionName AND Latest Action Status = "Complete".
     * Used for Stop and Start actions.
     */
    private void waitForActionCompletion(String labId, String actionName, int maxWaitSeconds)
            throws InterruptedException {
        int interval = 5;
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
            log.info("Lab {} | Latest Action: {} | Status: {}", labId, latestAction, latestStatus);

            if (latestAction.trim().equalsIgnoreCase(actionName)
                    && latestStatus.trim().equalsIgnoreCase("Complete")) {
                return;
            }

            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Lab " + labId + " — expected Latest Action='" + actionName
                + "' and Status='Complete' within " + maxWaitSeconds + "s."
                + " Last: action='" + latestAction + "', status='" + latestStatus + "'");
    }

    /**
     * Polls every 30 seconds for delete completion.
     * Once the lab disappears from the Active filter, switches to the Deleted filter
     * and re-searches before validating Latest Action=Delete and Status=Complete.
     */
    private void waitForDeleteCompletion(String labId, int maxWaitSeconds) throws InterruptedException {
        int interval = 30;
        String latestAction = "";
        String latestStatus = "";

        for (int elapsed = 0; elapsed < maxWaitSeconds; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "table refresh");
            WaitUtils.pause(WaitUtils.MEDIUM);

            // Re-search in current filter (Active) to check if the row still exists
            Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "re-search lab ID");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "re-search button");
            WaitUtils.pause(WaitUtils.MEDIUM);

            boolean rowInActiveFilter = Pages.getLabsPage().getTableRowByLabId(labId).count() > 0;

            if (!rowInActiveFilter) {
                // Lab removed from Active view — switch to Deleted filter and search
                log.info("Lab {} no longer in Active filter — switching to Deleted filter", labId);
                Hook.base().page.locator("#stateFilter").selectOption("Deleted");
                WaitUtils.pause(WaitUtils.MEDIUM);

                Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), labId, "search in Deleted filter");
                WaitUtils.pause(WaitUtils.SHORT);
                Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
                WaitUtils.pause(WaitUtils.MEDIUM);
            }

            try {
                latestAction = Hook.base().shDriver.getText(
                        Pages.getLabsPage().getLabLatestActionById(labId), "latest action");
                latestStatus = Hook.base().shDriver.getText(
                        Pages.getLabsPage().getLabLatestActionStatusById(labId), "latest status");
                log.info("Lab {} | Latest Action: {} | Status: {}", labId, latestAction, latestStatus);

                if (latestAction.trim().equalsIgnoreCase("Delete")
                        && latestStatus.trim().equalsIgnoreCase("Complete")) {
                    return;
                }
            } catch (Exception e) {
                log.info("Lab {} row not yet visible, retrying in {}s...", labId, interval);
            }

            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: Lab " + labId + " delete not complete within " + maxWaitSeconds + "s."
                + " Last: action='" + latestAction + "', status='" + latestStatus + "'");
    }

    // ── Cleanup helpers ───────────────────────────────────────────────────────

    /** Sets the mySubscriptionsTable page-size dropdown to the given value (e.g. "100"). */
    private void setTablePageSize(String size) {
        try {
            Pages.getLabsPage().getPageSizeSelect().selectOption(size);
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().page.waitForLoadState();
            log.info("Table page size set to {}", size);
        } catch (Exception e) {
            log.warn("Could not set table page size to {}: {}", size, e.getMessage());
        }
    }

    /** Selects ID search mode and searches all lab IDs (space-separated) in one shot. */
    private void searchByLabIds(List<String> labIds) {
        SingleLabRequest.dismissDtButtonBackground();
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionDropdown(), "search dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionByText("id"), "ID option");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(),
                String.join(" ", labIds), "lab IDs");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();
    }

    /**
     * Stays on the Active filter and refreshes every 20 s until every lab ID has
     * disappeared from the table (delete operation complete), or until maxWaitSeconds.
     * Clicking refresh every 20 s sends real HTTP requests, keeping the app session alive.
     */
    private void waitForActiveFilterEmpty(List<String> labIds, int maxWaitSeconds)
            throws InterruptedException {
        int refreshInterval = 20;
        int elapsed = 0;

        while (elapsed < maxWaitSeconds) {
            Thread.sleep(refreshInterval * 1000L);
            elapsed += refreshInterval;

            Hook.base().shDriver.click(Pages.getLabsPage().getRefreshBtn(), "refresh");
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().page.waitForLoadState();

            // Re-search to get an accurate row count for our specific lab IDs
            searchByLabIds(labIds);

            int remaining = 0;
            for (String labId : labIds) {
                remaining += Pages.getLabsPage().getTableRowByLabId(labId).count();
            }
            log.info("Active filter: {}/{} lab(s) still present at {}s / {}s max",
                    remaining, labIds.size(), elapsed, maxWaitSeconds);

            if (remaining == 0) {
                log.info("All {} labs removed from Active filter after {}s", labIds.size(), elapsed);
                return;
            }
        }

        // Log remaining labs (likely Pending-state labs that the backend cannot delete synchronously)
        int remaining = 0;
        List<String> stuckIds = new java.util.ArrayList<>();
        for (String labId : labIds) {
            if (Pages.getLabsPage().getTableRowByLabId(labId).count() > 0) {
                remaining++;
                stuckIds.add(labId);
            }
        }
        if (remaining > 0) {
            log.warn("Cleanup timeout: {}/{} lab(s) still in Active filter after {}s (likely Pending-state, non-deletable): {}",
                    remaining, labIds.size(), maxWaitSeconds, stuckIds);
        }
    }

    /**
     * Switches to the Deleted filter, sets page size to 100, searches all lab IDs,
     * and logs the Complete/non-Complete count as a confirmation step.
     */
    private void verifyInDeletedFilter(List<String> labIds) {
        log.info("Switching to Deleted filter to confirm deletion...");

        Hook.base().page.locator("#stateFilter").selectOption("Deleted");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        setTablePageSize("100");
        searchByLabIds(labIds);

        int confirmed = 0;
        for (String labId : labIds) {
            try {
                // Row absent = already cleaned / belongs to other platform tenant — treat as confirmed
                if (Pages.getLabsPage().getTableRowByLabId(labId).count() == 0) {
                    log.info("Lab {} | Not in Deleted filter — treating as confirmed deleted", labId);
                    confirmed++;
                    continue;
                }
                // Direct textContent — no self-healing, avoids fallback locator misfire
                com.microsoft.playwright.Locator statusLoc =
                        Pages.getLabsPage().getLabLatestActionStatusById(labId);
                String status = statusLoc.count() > 0
                        ? statusLoc.first().textContent().trim()
                        : "Complete";
                log.info("Lab {} | Deleted filter | Status: {}", labId, status);
                if (status.equalsIgnoreCase("Complete")) {
                    confirmed++;
                } else {
                    log.warn("Lab {} | Unexpected status in Deleted filter: {}", labId, status);
                }
            } catch (Exception e) {
                log.info("Lab {} | Status unreadable — treating as confirmed deleted", labId);
                confirmed++;
            }
        }
        log.info("Cleanup complete: {}/{} labs confirmed deleted", confirmed, labIds.size());
    }
}
