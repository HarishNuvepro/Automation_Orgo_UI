package stepDefinitions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;

import Util.Pages;
import com.microsoft.playwright.Locator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Generic_Utility.WaitUtils;

public class LazyLab {

    private static final Logger log = LoggerFactory.getLogger(LazyLab.class);

    // Instance fields — Cucumber creates one LazyLab per scenario, so these are
    // already thread-safe
    private List<String> userCredentialsList;
    private List<String> labIdsList;

    @And("in the settings page select lazy create option")
    public void in_the_settings_page_select_lazy_create_option() throws Throwable {
        Pages.getBatchProvisionPage().getLazyCreateOption().check();
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @Then("validate all lab is in pending status in the latest action in all labs table")
    public void validate_all_lab_is_in_pending_status_in_the_latest_action_in_all_labs_table() throws Throwable {
        String[] labIdArray = BatchProvision.getCreatedLabIds().split(" ");

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionDropdown(), "search option dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionByText("id"), "ID option");
        WaitUtils.pause(WaitUtils.MEDIUM);

        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), String.join(" ", labIdArray),
                "search lab ids");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        WaitUtils.pause(WaitUtils.LONG);

        for (String labId : labIdArray) {
            Hook.base().page.waitForLoadState();
            String status = Hook.base().shDriver.getText(Pages.getLabsPage().getLabLatestActionStatusById(labId),
                    "status for " + labId);
            log.info("Lab {} status: {}", labId, status);
            Assert.assertTrue(status.trim().toLowerCase().contains("pending"),
                    "Lab " + labId + " should be pending but found: " + status);
        }

        log.info("All {} labs are in pending status", labIdArray.length);
    }

    @When("login sequentially for each user and launch labs")
    public void login_sequentially_for_each_user_and_launch_labs() {
        String userCredentials = SingleLabRequest.testData().get("UserName");
        String labIds = BatchProvision.getCreatedLabIds();

        userCredentialsList = (userCredentials != null && userCredentials.contains(","))
                ? Arrays.asList(userCredentials.split(","))
                : Collections.singletonList(userCredentials);

        labIdsList = (labIds != null && labIds.contains(" "))
                ? Arrays.asList(labIds.split(" "))
                : Collections.singletonList(labIds);

        log.info("Users to process: {}", userCredentialsList.size());
    }

    @Then("validate all labs are created successfully for all users")
    public void validate_all_labs_are_created_successfully_for_all_users() throws Throwable {
        for (int i = 0; i < userCredentialsList.size(); i++) {
            String[] parts = userCredentialsList.get(i).trim().split(":");
            String username = parts[0].trim();
            String password = parts[1].trim();
            String userLabId = labIdsList.get(i).trim();

            log.info("Processing user: {} | Lab: {}", username, userLabId);
            loginAsUser(username, password);
            searchAndAccessLab(userLabId);
            launchLabAndVerify();
            logout();

            if (i < userCredentialsList.size() - 1) {
                WaitUtils.pause(WaitUtils.THIRTY_SEC);
            }
        }
    }

    @Then("validate all labs are created and expiry date is set for all users")
    public void validate_all_labs_created_and_expiry_date_for_all_users() throws Throwable {
        for (int i = 0; i < userCredentialsList.size(); i++) {
            String[] parts    = userCredentialsList.get(i).trim().split(":");
            String username   = parts[0].trim();
            String password   = parts[1].trim();
            String userLabId  = labIdsList.get(i).trim();
            log.info("Processing user: {} | Lab: {}", username, userLabId);
            loginAsUser(username, password);
            launchLabForUser(userLabId);

            String expiryDate = Hook.base().shDriver.getText(
                    Pages.getLabControlPanelPage().getExpiryDate(), "expiry date");
            Assert.assertFalse(expiryDate == null || expiryDate.trim().isEmpty(),
                    "Expiry date should be set for user: " + username);
            log.info("User {} — expiry date confirmed: {}", username, expiryDate);

            logout();
            if (i < userCredentialsList.size() - 1) WaitUtils.pause(WaitUtils.THIRTY_SEC);
        }
    }

    @Then("validate all labs are created and default policy is attached for all users")
    public void validate_all_labs_created_and_default_policy_for_all_users() throws Throwable {
        String policyName = SingleLabRequest.testData().get("PolicyName");
        for (int i = 0; i < userCredentialsList.size(); i++) {
            String[] parts    = userCredentialsList.get(i).trim().split(":");
            String username   = parts[0].trim();
            String password   = parts[1].trim();
            String userLabId  = labIdsList.get(i).trim();
            log.info("Processing user: {} | Lab: {}", username, userLabId);
            loginAsUser(username, password);
            launchLabForUser(userLabId);

            Hook.base().shDriver.click(Pages.getLabControlPanelPage().getPoliciesTab(), "policies tab");
            WaitUtils.pause(WaitUtils.MEDIUM);
            Hook.base().page.waitForLoadState();
            boolean policyFound = Pages.getLabControlPanelPage().getPolicyRowByName(policyName).first().isVisible();
            Assert.assertTrue(policyFound,
                    "Policy '" + policyName + "' should be attached for user: " + username);
            log.info("User {} — policy '{}' confirmed attached", username, policyName);

            logout();
            if (i < userCredentialsList.size() - 1) WaitUtils.pause(WaitUtils.THIRTY_SEC);
        }
    }

    @Then("validate all labs are created and expiry duration is set for all users")
    public void validate_all_labs_created_and_expiry_duration_for_all_users() throws Throwable {
        for (int i = 0; i < userCredentialsList.size(); i++) {
            String[] parts    = userCredentialsList.get(i).trim().split(":");
            String username   = parts[0].trim();
            String password   = parts[1].trim();
            String userLabId  = labIdsList.get(i).trim();
            log.info("Processing user: {} | Lab: {}", username, userLabId);
            loginAsUser(username, password);
            launchLabForUser(userLabId);

            String duration = Hook.base().shDriver.getText(
                    Pages.getLabControlPanelPage().getLabDuration(), "expiry duration");
            Assert.assertFalse(duration == null || duration.trim().isEmpty(),
                    "Expiry duration should be set for user: " + username);
            log.info("User {} — expiry duration confirmed: {}", username, duration);

            logout();
            if (i < userCredentialsList.size() - 1) WaitUtils.pause(WaitUtils.THIRTY_SEC);
        }
    }

    @Then("validate that the lab has an expiry date set")
    public void validate_that_the_lab_has_an_expiry_date_set() {
        Hook.base().page.waitForLoadState();
        String expiryDate = Hook.base().shDriver.getText(Pages.getLabControlPanelPage().getExpiryDate(), "expiry date");
        Assert.assertFalse(expiryDate == null || expiryDate.trim().isEmpty(),
                "Expiry date should be set on the lab");
        log.info("Expiry date confirmed: {}", expiryDate);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private void launchLabForUser(String userLabId) throws Throwable {
        searchAndAccessLab(userLabId);
        launchLabAndVerify();
    }

    private void loginAsUser(String username, String password) throws Throwable {
        // Force-logout any existing session before logging in as a new user.
        String baseUrl = Generic_Utility.CredentialManager.getBaseUrl();
        try {
            String origin = Hook.base().page.url().replaceAll("(https?://[^/]+).*", "$1");
            Hook.base().page.navigate(origin + "/logout",
                    new com.microsoft.playwright.Page.NavigateOptions().setTimeout(30000));
            Hook.base().page.waitForLoadState();
        } catch (Exception e) {
            log.debug("Logout navigation: {}", e.getMessage());
        }

        // Navigate fresh to the base URL — guarantees a clean unauthenticated starting point.
        Hook.base().page.navigate(baseUrl,
                new com.microsoft.playwright.Page.NavigateOptions().setTimeout(60000));
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);

        Locator loginInput = Pages.getLoginPage().getUsernameTxt();
        if (!loginInput.isVisible()) {
            // Platform shows home page — click LOGIN link to open the sign-in form
            Locator loginLink = Pages.getLoginPage().getLoginDropdownBtn();
            if (loginLink.isVisible()) {
                loginLink.click();
                WaitUtils.pause(WaitUtils.MEDIUM);
            }
            loginInput = Pages.getLoginPage().getHeaderUsernameTxt();
        }
        loginInput.fill(username);
        WaitUtils.pause(WaitUtils.SHORT);
        Pages.getLoginPage().getPasswordTxt().fill(password);
        WaitUtils.pause(WaitUtils.SHORT);
        Pages.getLoginPage().getSignInBtn().click();
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.FIVE_SECONDS);

        // Navigate to My Labs page via Home dropdown → My Labs link
        Hook.base().shDriver.click(Pages.getHomePage().getHomeNavDropdown(), "home nav dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getHomePage().getMyLabsNavLink(), "my labs nav link");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);
    }

    private void searchAndAccessLab(String labId) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        // Select "Id" as search type
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionDropdown(), "search option dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getSearchOptionByText("id"), "ID search option");
        WaitUtils.pause(WaitUtils.SHORT);

        // Search with all batch-provisioned lab IDs (space-separated)
        String allLabIds = BatchProvision.getCreatedLabIds();
        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), allLabIds, "all lab ids search");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        // Select the first listed lab row's checkbox then click Access Lab
        Hook.base().shDriver.click(Pages.getLabsPage().getFirstRowSelectCheckbox(), "first lab row checkbox");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsPage().getAccessLabBtn(), "access lab button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);
    }

    private void launchLabAndVerify() throws Throwable {
        Hook.base().shDriver.click(Pages.getLabControlPanelPage().getLaunchLabButton(), "launch lab button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);
        // Status element loads asynchronously after launch — wait before polling
        try {
            Pages.getLabControlPanelPage().getLatestStatusText().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(30_000));
        } catch (Exception e) {
            log.warn("Deployment status element not visible after 30s: {}", e.getMessage());
        }

        int maxWaitTime = 300;
        int interval = 5;
        boolean launchRetried = false;

        for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            if (WaitUtils.handleGatewayTimeout(Hook.base().page)) {
                log.warn("Gateway timeout during lazy lab deployment polling — retrying after reload");
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
            if (status.trim().toLowerCase().contains("failed")) {
                if (!launchRetried) {
                    launchRetried = true;
                    log.warn("Launch failed status detected — waiting 90s then retrying launch...");
                    Thread.sleep(90_000L);
                    Hook.base().page.reload();
                    Hook.base().page.waitForLoadState();
                    WaitUtils.pause(WaitUtils.EXTRA_LONG);
                    try {
                        Hook.base().shDriver.click(Pages.getLabControlPanelPage().getLaunchLabButton(), "re-launch lab button");
                        Hook.base().page.waitForLoadState();
                        WaitUtils.pause(WaitUtils.EXTRA_LONG);
                    } catch (Exception reLaunchEx) {
                        throw new RuntimeException("Lab launch failed and re-launch button not found: " + reLaunchEx.getMessage());
                    }
                    // Reset polling timer for the re-launched lab
                    elapsed = 0;
                    maxWaitTime = 300;
                    continue;
                } else {
                    throw new RuntimeException("Lab failed even after re-launch attempt. Status: " + status);
                }
            }
            if (status.trim().contains("Complete")) {
                log.info("Lab deployment complete");
                return;
            }
            Thread.sleep(interval * 1000L);
        }

        throw new RuntimeException("Timeout: Lab did not complete within " + maxWaitTime + "s");
    }

    private void logout() throws Throwable {
        try {
            Hook.base().shDriver.click(Pages.getHomePage().getUserProfileDropdown(), "user profile dropdown");
            WaitUtils.pause(WaitUtils.SHORT);
            Hook.base().shDriver.click(Pages.getHomePage().getLogoutBtn(), "logout button");
        } catch (Exception e) {
            String baseUrl = Hook.base().page.url().replaceAll("(https?://[^/]+).*", "$1");
            Hook.base().page.navigate(baseUrl + "/logout");
        }

        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);

        if (!Pages.getLoginPage().getUsernameTxt().isVisible()) {
            Hook.base().page.reload();
            WaitUtils.pause(WaitUtils.EXTRA_LONG);
        }
    }
}