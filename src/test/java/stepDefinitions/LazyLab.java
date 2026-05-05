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

    // Instance fields — Cucumber creates one LazyLab per scenario, so these are already thread-safe
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

        Hook.base().shDriver.fill(Pages.getLabsPage().getAllLabsSearchInput(), String.join(" ", labIdArray), "search lab ids");
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.click(Pages.getLabsPage().getAllLabsSearchBtn(), "search button");
        WaitUtils.pause(WaitUtils.LONG);

        for (String labId : labIdArray) {
            Hook.base().page.waitForLoadState();
            String status = Hook.base().shDriver.getText(Pages.getLabsPage().getLabLatestActionStatusById(labId), "status for " + labId);
            log.info("Lab {} status: {}", labId, status);
            Assert.assertTrue(status.trim().toLowerCase().contains("pending"),
                    "Lab " + labId + " should be pending but found: " + status);
        }

        log.info("All {} labs are in pending status", labIdArray.length);
    }

    @When("login sequentially for each user and launch labs")
    public void login_sequentially_for_each_user_and_launch_labs() {
        String userCredentials = SingleLabRequest.testData().get("UserName");
        String labIds          = BatchProvision.getCreatedLabIds();

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
            String[] parts    = userCredentialsList.get(i).trim().split(":");
            String username   = parts[0].trim();
            String password   = parts[1].trim();
            String userLabId  = labIdsList.get(i).trim();

            log.info("Processing user: {} | Lab: {}", username, userLabId);
            loginAsUser(username, password);

            boolean isVisible = Hook.base().shDriver.isVisible(
                    Pages.getLabsPage().getLabTileById(userLabId), "lab " + userLabId);

            if (isVisible) {
                searchAndClickLabTile(userLabId);
            } else {
                for (String labId : labIdsList) {
                    if (Hook.base().shDriver.isVisible(Pages.getLabsPage().getLabTileById(labId.trim()), "lab " + labId)) {
                        searchAndClickLabTile(labId.trim());
                        break;
                    }
                }
            }

            launchLabAndVerify();
            logout();

            if (i < userCredentialsList.size() - 1) {
                WaitUtils.pause(WaitUtils.THIRTY_SEC);
            }
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

    private void loginAsUser(String username, String password) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.EXTRA_LONG);

        Locator loginInput = Pages.getLoginPage().getUsernameTxt();
        if (loginInput.isVisible()) {
            loginInput.fill(username);
            WaitUtils.pause(WaitUtils.SHORT);
            Pages.getLoginPage().getPasswordTxt().fill(password);
            WaitUtils.pause(WaitUtils.SHORT);
            Pages.getLoginPage().getSignInBtn().click();
            Hook.base().page.waitForLoadState();
            WaitUtils.pause(WaitUtils.FIVE_SECONDS);
            try {
                Pages.getHomePage().getMyLabsTab().first()
                        .waitFor(new Locator.WaitForOptions().setTimeout(30000));
            } catch (Exception e) {
                Pages.getHomePage().getMyLabsTab().first().click();
                WaitUtils.pause(WaitUtils.EXTRA_LONG);
            }
        } else {
            Pages.getHomePage().getMyLabsTab().first().click();
            WaitUtils.pause(WaitUtils.EXTRA_LONG);
        }
    }

    private void searchAndClickLabTile(String labId) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);

        Locator searchInput = Pages.getLabsPage().getAllLabsSearchInput();
        Locator searchBtn   = Pages.getLabsPage().getAllLabsSearchBtn();

        try {
            if (!searchInput.isVisible()) {
                searchInput = Hook.base().page.locator("input[placeholder*='Search'], input[type='text']").first();
                searchBtn   = Hook.base().page.locator("button:has-text('Search'), button[type='submit']").first();
            }
            if (searchInput.isVisible()) {
                searchInput.fill(labId);
                WaitUtils.pause(WaitUtils.MEDIUM);
                searchBtn.click();
                WaitUtils.pause(WaitUtils.LONG);
            }
        } catch (Exception e) {
            log.debug("Search not available, trying direct tile click");
        }

        Pages.getLabsPage().getLabTileById(labId).first().click();
        WaitUtils.pause(WaitUtils.LONG);
    }

    private void launchLabAndVerify() throws Throwable {
        Hook.base().shDriver.click(Pages.getLabControlPanelPage().getLaunchLabButton(), "launch lab button");

        int maxWaitTime = 300;
        int interval    = 5;

        for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
            Hook.base().page.waitForLoadState();
            String status = Hook.base().shDriver.getText(Pages.getLabControlPanelPage().getLatestStatusText(), "deployment status");
            log.info("Deployment Status: {}", status);
            if (status.trim().toLowerCase().contains("failed")) {
                throw new RuntimeException("Lab failed. Status: " + status);
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
