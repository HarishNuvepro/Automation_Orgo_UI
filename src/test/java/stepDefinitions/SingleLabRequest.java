package stepDefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import Generic_Utility.TestDataManager;
import POM.LabControlPanelPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Generic_Utility.WaitUtils;

public class SingleLabRequest {

    private static final Logger log = LoggerFactory.getLogger(SingleLabRequest.class);

    // ThreadLocal so parallel scenarios don't share test data or lab IDs
    private static final ThreadLocal<Map<String, String>> tlTestData =
            ThreadLocal.withInitial(HashMap::new);
    private static final ThreadLocal<String> tlCreatedLabId = new ThreadLocal<>();

    public static Map<String, String> testData() {
        return tlTestData.get();
    }

    public static String getCreatedLabId() {
        return tlCreatedLabId.get();
    }

    /** Lets other flows (e.g. batch provisioning) point the shared "created lab"
     *  reference at one of their own labs, so single-lab steps like "search the
     *  created lab by id" / "access lab control panel" can be reused unchanged. */
    public static void setCreatedLabId(String labId) {
        tlCreatedLabId.set(labId);
    }

    @When("get test data from excel {word}")
    public void get_test_data_from_excel(String tcId) {
        testData().clear();
        testData().putAll(TestDataManager.getLabData(tcId));
        log.info("[TC:{}] Test data loaded | Thread: {}", tcId, Thread.currentThread().getName());
    }

    @And("click on select team option")
    public void click_on_select_team_option() {
        Hook.base().shDriver.click(Pages.getHomePage().getTeamNameTab(), "select team option");
    }

    @And("search and select team")
    public void search_and_select_team() throws Throwable {
        String teamName = testData().get("TeamID");

        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.click(Pages.getHomePage().getTeamDropdownSearchTxt(), "team search input");
        Hook.base().shDriver.fill(Pages.getHomePage().getTeamDropdownSearchTxt(), teamName, "team search");
        Hook.base().page.waitForTimeout(1500);
        Hook.base().shDriver.click(Pages.getHomePage().getTeamListLinkByName(teamName), "team link");

        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("navigate to labs page")
    public void navigate_to_labs_page() throws Throwable {
        dismissBlockingModal();
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getLabsTab(), "labs tab");
        // Wait for the dropdown item to become visible before clicking — avoids race where
        // dropdown animation hasn't finished and the link is still hidden.
        Pages.getLabsDropdownPage().getLabsLink().waitFor(
                new com.microsoft.playwright.Locator.WaitForOptions()
                        .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                        .setTimeout(10_000));
        Hook.base().shDriver.click(Pages.getLabsDropdownPage().getLabsLink(), "labs link");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
    }

    @And("click on request lab button")
    public void click_on_request_lab_button() {
        // Full load wait ensures the toolbar (including Request Lab button) is rendered
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getRequestLabBtn(), "request lab button");
        Hook.base().page.waitForLoadState();
    }

    @And("select plan using test data")
    public void select_plan_using_test_data() throws Throwable {
        String planId = testData().get("PlanID");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);
        // Under parallel load the plan store renders slowly — wait for the tile before clicking
        try {
            Pages.getLabStorePage().getPlanTileByPlanId(planId).waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(30_000));
        } catch (Exception e) {
            log.warn("Plan tile {} not visible after 30s — attempting click anyway: {}", planId, e.getMessage());
        }
        Hook.base().shDriver.click(Pages.getLabStorePage().getPlanTileByPlanId(planId), "plan tile " + planId);
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().page.waitForLoadState();
    }

    /** Closes any Bootstrap modal (e.g. Feedback Form) that may be blocking navigation. */
    public static void dismissBlockingModal() {
        try {
            com.microsoft.playwright.Locator closeBtn = Hook.base().page.locator(
                    ".modal.in button[data-dismiss='modal'], .modal.show button[data-dismiss='modal']," +
                    " .modal.in .close, .modal.show .close");
            if (closeBtn.count() > 0 && closeBtn.first().isVisible()) {
                closeBtn.first().click();
                WaitUtils.pause(WaitUtils.MEDIUM);
                log.info("Dismissed blocking modal before navigation");
            }
        } catch (Exception ignored) {}
    }

    /** Dismisses a DataTables dt-button-background overlay that can block pointer events. */
    public static void dismissDtButtonBackground() {
        try {
            com.microsoft.playwright.Locator overlay =
                    Hook.base().page.locator("div.dt-button-background");
            if (overlay.count() > 0 && overlay.first().isVisible()) {
                overlay.first().click();
                WaitUtils.pause(WaitUtils.SHORT);
                log.info("Dismissed DataTables button-background overlay");
            }
        } catch (Exception ignored) {}
        try { Hook.base().page.keyboard().press("Escape"); } catch (Exception ignored) {}
    }

    @And("select provision for others option")
    public void select_provision_for_others_option() {
        Hook.base().shDriver.click(Pages.getSubscribePlanPage().getProvisionForOthersRadio(), "provision for others radio");
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Selected 'Lab for Others' radio button");
    }

    @And("search and select user for lab")
    public void search_and_select_user_for_lab() throws Throwable {
        String forUserId = testData().get("ForUserId");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getSubscribePlanPage().getUserSelect2Container(), "user select2 container");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.fill(Pages.getSubscribePlanPage().getUserSelect2SearchInput(), forUserId, "user search input");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getSubscribePlanPage().getUserSelect2OptionByLoginId(forUserId), "user option for " + forUserId);
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Selected user '{}' for lab provisioning", forUserId);
    }

    @And("click on subscribe button")
    public void click_on_subscribe_button() {
        Hook.base().shDriver.click(Pages.getSubscribePlanPage().getSubscribeButton(), "subscribe button");
        Hook.base().page.waitForLoadState();
    }

    @Then("verify lab is created successfully")
    public void verify_lab_is_created_successfully() throws Throwable {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);
        // Lab control panel loads asynchronously — give the status element a short
        // window to appear before falling into the real polling loop below (which
        // handles a not-yet-visible element fine regardless). Observed across a run
        // of 15 scenarios: this wait failed 8/15 times at 30s — since the loop below
        // tolerates a miss either way, a shorter wait just saves time on those cases
        // without changing the outcome for the ones where it succeeds quickly.
        try {
            controlPanel.getLatestStatusText().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(10_000));
        } catch (Exception e) {
            log.warn("Deployment status element not visible after 10s — proceeding: {}", e.getMessage());
        }

        int maxWaitTime = 300;
        int interval    = 5;
        int blankCount  = 0;

        // Tracks real wall-clock time rather than counting loop iterations —
        // a reload+recovery cycle (page.reload() + waitForLoadState() + pause)
        // takes far longer than one `interval`, so an iteration counter silently
        // let this loop run for 9+ minutes on a genuinely stuck deployment
        // (observed on AWS_TC6) instead of honoring the stated 300s budget.
        long startTime = System.currentTimeMillis();
        long maxWaitMillis = maxWaitTime * 1000L;

        while (System.currentTimeMillis() - startTime < maxWaitMillis) {
            Hook.base().page.waitForLoadState();
            if (WaitUtils.handleGatewayTimeout(Hook.base().page)) {
                log.warn("Gateway timeout during lab creation polling — retrying after reload");
                blankCount = 0;
                Thread.sleep(interval * 1000L);
                continue;
            }

            String status = "";
            try {
                status = (String) Hook.base().page.evaluate(
                        "() => { var el = document.getElementById('deploymentStatus'); return el ? el.textContent : ''; }");
            } catch (Exception navEx) {
                log.debug("Evaluate interrupted by navigation — waiting for load state then retrying");
                WaitUtils.pause(WaitUtils.MEDIUM);
                Hook.base().page.waitForLoadState();
                try {
                    status = (String) Hook.base().page.evaluate(
                            "() => { var el = document.getElementById('deploymentStatus'); return el ? el.textContent : ''; }");
                } catch (Exception e2) {
                    log.debug("Second evaluate also failed — continuing poll cycle: {}", e2.getMessage());
                    Thread.sleep(interval * 1000L);
                    continue;
                }
            }
            log.info("Deployment Status: {}", status);

            if (status.trim().isEmpty()) {
                blankCount++;
                // After 60s of blank status, reload the page — backend stall recovery
                if (blankCount * interval >= 60) {
                    log.warn("Deployment status blank for {}s — reloading page to recover", blankCount * interval);
                    Hook.base().page.reload();
                    Hook.base().page.waitForLoadState();
                    WaitUtils.pause(WaitUtils.EXTRA_LONG);
                    blankCount = 0;
                }
                Thread.sleep(interval * 1000L);
                continue;
            }
            blankCount = 0;

            if (status.trim().toLowerCase().contains("failed")) {
                throw new RuntimeException("Lab creation failed! Status: " + status);
            }

            if (status.trim().contains("Complete")) {
                String currentUrl = Hook.base().page.url();
                String labId      = currentUrl.split("id=")[1].split("&")[0];
                tlCreatedLabId.set(labId);
                log.info("Lab created — ID: {}", labId);
                return;
            }

            Thread.sleep(interval * 1000L);
        }

        long actualElapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        throw new RuntimeException("Timeout: Lab deployment did not complete within "
                + maxWaitTime + "s (actual elapsed: " + actualElapsedSeconds + "s)");
    }

    // ── Group B negative tests — duplicate plan request / abandoned request ──

    /**
     * The app's actual duplicate-subscription behavior wasn't known ahead of
     * time, so this accepts either outcome: a warning/error blocking the repeat
     * request, or the app allowing it and creating a second lab. Either is fine —
     * the point is confirming the app handles a repeat request cleanly rather
     * than erroring out or hanging.
     */
    @Then("validate requesting the same plan again is handled without errors")
    public void validate_requesting_the_same_plan_again_is_handled_without_errors() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        boolean warningShown = false;
        try {
            warningShown = Hook.base().page.locator("div.alert, .toast, span[data-notify='message']")
                    .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(
                            java.util.regex.Pattern.compile("already|duplicate|exist",
                                    java.util.regex.Pattern.CASE_INSENSITIVE)))
                    .first().isVisible();
        } catch (Exception ignored) {
        }

        if (warningShown) {
            log.info("Duplicate plan request correctly blocked with a warning/error message");
        } else {
            log.info("Duplicate plan request was allowed by the app — validating a second lab was created");
            verify_lab_is_created_successfully();
        }
    }

    /** No explicit Cancel button exists on the Subscribe Plan page — abandonment
     *  is simulated by navigating straight to the labs listing without ever
     *  clicking Subscribe. */
    @And("abandon the lab request without subscribing")
    public void abandon_the_lab_request_without_subscribing() throws Throwable {
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getLabsTab(), "labs tab");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsDropdownPage().getLabsLink(), "labs link");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    /**
     * A global "total labs" count comparison is unreliable on this shared, live
     * environment — other concurrent activity can shift the count independent of
     * this scenario (the same class of issue previously found with TC57 in the
     * Users module). Instead of comparing counts, this confirms no success
     * indicator appeared and we landed cleanly on the plain labs listing (not a
     * lab-detail page carrying a newly created lab's id).
     */
    @Then("validate no new lab was created from the abandoned request")
    public void validate_no_new_lab_was_created_from_the_abandoned_request() throws Throwable {
        String currentUrl = Hook.base().page.url();
        Assert.assertFalse(currentUrl.contains("id="),
                "URL should not carry a lab id after abandoning the request, but was: " + currentUrl);

        boolean successIndicatorVisible = false;
        try {
            successIndicatorVisible = Hook.base().page
                    .locator("span[data-notify='message'], .toast, div.alert-success")
                    .filter(new com.microsoft.playwright.Locator.FilterOptions().setHasText(
                            java.util.regex.Pattern.compile("created|success",
                                    java.util.regex.Pattern.CASE_INSENSITIVE)))
                    .first().isVisible();
        } catch (Exception ignored) {
        }
        Assert.assertFalse(successIndicatorVisible,
                "No 'lab created'/success indicator should be visible after abandoning the request");

        boolean labsPageLoaded = Pages.getLabsPage().getRefreshBtn().isVisible();
        Assert.assertTrue(labsPageLoaded,
                "Should land cleanly on the labs listing page after abandoning the request");

        log.info("Confirmed: no success indicator after abandoning the lab request; landed cleanly on labs listing");
    }

    @Then("verify expiry date values are loading on lab control panel")
    public void verify_expiry_date_values() {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);
        Hook.base().page.waitForLoadState();
        WaitUtils.handleGatewayTimeout(Hook.base().page);
        String expiryDate = Hook.base().shDriver.getText(controlPanel.getExpiryDate(), "expiry date");
        Assert.assertFalse(expiryDate == null || expiryDate.trim().isEmpty(),
                "Expiry Date should not be empty");
        log.info("Expiry Date: {}", expiryDate);
    }

    @Then("verify expiry duration values are loading on lab control panel")
    public void verify_expiry_duration_values() {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);
        Hook.base().page.waitForLoadState();
        String expiryDuration = Hook.base().shDriver.getText(controlPanel.getLabDuration(), "expiry duration");
        Assert.assertFalse(expiryDuration == null || expiryDuration.trim().isEmpty(),
                "Expiry Duration should not be empty");
        log.info("Expiry Duration: {}", expiryDuration);
    }

    @Then("verify default policy is assigned to the lab")
    public void verify_default_policy_is_assigned() throws Throwable {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);
        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.click(controlPanel.getPoliciesTab(), "policies tab");
        Hook.base().page.waitForLoadState();

        String policyName  = testData().get("PolicyName");
        boolean policyFound = controlPanel.getPolicyRowByName(policyName).first().isVisible();
        Assert.assertTrue(policyFound, "Default policy '" + policyName + "' should be assigned");
        log.info("Default policy '{}' is assigned to the lab", policyName);
    }
}
