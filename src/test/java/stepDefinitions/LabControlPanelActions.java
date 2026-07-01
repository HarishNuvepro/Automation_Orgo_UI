package stepDefinitions;

import org.testng.Assert;

import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabControlPanelActions {

    private static final Logger log = LoggerFactory.getLogger(LabControlPanelActions.class);

    @Then("access lab control panel")
    public void access_lab_control_panel() throws Throwable {
        String labId = SingleLabRequest.getCreatedLabId();
        log.info("Accessing lab control panel for lab: {}", labId);

        Hook.base().shDriver.click(Pages.getLabsPage().getLabCheckboxById(labId), "lab checkbox for " + labId);
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAccessLabBtn(), "access lab button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();
    }

    @Then("submit challenge and wait for completion")
    public void submit_challenge_and_wait_for_completion() throws Throwable {
        log.info("Performing Submit Challenge action");

        // Expand Actions accordion
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getActionsAccordionLink(), "actions accordion");
        WaitUtils.pause(WaitUtils.MEDIUM);

        // Click Submit Challenge button
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getSubmitChallengeBtn(), "submit challenge button");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        // On RunScript page — click Submit
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getActionSubmitBtn(), "submit button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();

        // Back on Lab Control Panel — wait for Submit Challenge - Complete
        waitForActionStatus("Submit Challenge", 600);
        log.info("Submit Challenge completed successfully");
    }

    @Then("set iam password and wait for completion")
    public void set_iam_password_and_wait_for_completion() throws Throwable {
        String iamPassword = SingleLabRequest.testData().get("UserName");
        log.info("Performing Set IAM Password action");

        // Expand Actions accordion
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getActionsAccordionLink(), "actions accordion");
        WaitUtils.pause(WaitUtils.MEDIUM);

        // Click Set IAM Password button
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getSetIamPasswordBtn(), "set iam password button");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();

        // Fill new IAM password (fill() clears existing value first)
        Hook.base().shDriver.fill(
                Pages.getLabControlPanelPage().getIamPasswordInput(), iamPassword, "iam password input");
        WaitUtils.pause(WaitUtils.SHORT);

        // Click Submit
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getActionSubmitBtn(), "submit button");
        WaitUtils.pause(WaitUtils.LONG);
        Hook.base().page.waitForLoadState();

        // Back on Lab Control Panel — wait for Set IAM password - Complete
        waitForActionStatus("Set IAM password", 600);
        log.info("Set IAM Password completed successfully");
    }

    @Then("jump to console and verify new tab opens")
    public void jump_to_console_and_verify_new_tab_opens() throws Throwable {
        log.info("Performing Jump to Console action");

        // Expand Actions accordion
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getActionsAccordionLink(), "actions accordion");
        WaitUtils.pause(WaitUtils.MEDIUM);

        // Click Jump to Console button
        Hook.base().shDriver.click(
                Pages.getLabControlPanelPage().getJumpToConsoleBtn(), "jump to console button");
        WaitUtils.pause(WaitUtils.MEDIUM);

        // Wait for Connection Details popup to appear
        Pages.getLabControlPanelPage().getConnectionDetailsPopup()
                .waitFor(new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(30_000));
        log.info("Connection Details popup appeared");

        // Click Open Console — opens in a new tab
        com.microsoft.playwright.Page newTab = Hook.base().page.context().waitForPage(() ->
                Pages.getLabControlPanelPage().getOpenConsoleLink().click()
        );

        newTab.waitForLoadState();
        String newTabUrl = newTab.url();
        Assert.assertFalse(newTabUrl == null || newTabUrl.isEmpty(),
                "Jump to Console should open a new tab with a valid URL");
        log.info("Jump to Console opened new tab: {}", newTabUrl);

        // Close all extra tabs (redirect chain may open more than one)
        for (com.microsoft.playwright.Page p : Hook.base().page.context().pages()) {
            if (p != Hook.base().page) {
                p.close();
            }
        }

        // Force-close the Connection Details popup via JS (custom popup — Escape has no effect)
        try {
            Hook.base().page.evaluate(
                "var p = document.querySelector('[data-popup=\"popup-1\"]');" +
                "if (p) p.style.display = 'none';"
            );
            WaitUtils.pause(WaitUtils.SHORT);
        } catch (Exception e) {
            log.debug("Popup already dismissed");
        }
    }

    // ── helper ───────────────────────────────────────────────────────────────

    /**
     * Polls every 5 s until the Lab Control Panel status text contains
     * both the action name and "Complete" (e.g. "Submit Challenge - Complete").
     */
    private void waitForActionStatus(String actionName, int maxWaitSeconds)
            throws InterruptedException {
        int interval = 5;
        String statusText = "";

        for (int elapsed = 0; elapsed < maxWaitSeconds; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            statusText = Hook.base().shDriver.getText(
                    Pages.getLabControlPanelPage().getLatestStatusText(), "action status");
            log.info("{} | Status: {}", actionName, statusText);

            if (statusText.toLowerCase().contains(actionName.toLowerCase())
                    && statusText.toLowerCase().contains("complete")) {
                return;
            }

            Thread.sleep(interval * 1000L);
        }

        Assert.fail("Timeout: '" + actionName + "' did not reach Complete within "
                + maxWaitSeconds + "s. Last status: " + statusText);
    }
}
