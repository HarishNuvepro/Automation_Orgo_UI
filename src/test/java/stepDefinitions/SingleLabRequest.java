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
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getLabsTab(), "labs tab");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getLabsDropdownPage().getLabsLink(), "labs link");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("click on request lab button")
    public void click_on_request_lab_button() {
        Hook.base().shDriver.click(Pages.getLabsPage().getRequestLabBtn(), "request lab button");
        Hook.base().page.waitForLoadState();
    }

    @And("select plan using test data")
    public void select_plan_using_test_data() throws Throwable {
        String planId = testData().get("PlanID");
        Hook.base().page.waitForLoadState();
        Hook.base().shDriver.click(Pages.getLabStorePage().getPlanTileByPlanId(planId), "plan tile " + planId);
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("click on subscribe button")
    public void click_on_subscribe_button() {
        Hook.base().shDriver.click(Pages.getSubscribePlanPage().getSubscribeButton(), "subscribe button");
        Hook.base().page.waitForLoadState();
    }

    @Then("verify lab is created successfully")
    public void verify_lab_is_created_successfully() throws Throwable {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);

        int maxWaitTime = 300;
        int interval    = 5;

        for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
            Hook.base().page.waitForLoadState();

            String status = Hook.base().shDriver.getText(controlPanel.getLatestStatusText(), "deployment status");
            log.info("Deployment Status: {}", status);

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

        throw new RuntimeException("Timeout: Lab deployment did not complete within " + maxWaitTime + "s");
    }

    @Then("verify expiry date values are loading on lab control panel")
    public void verify_expiry_date_values() {
        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base().page);
        Hook.base().page.waitForLoadState();
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
