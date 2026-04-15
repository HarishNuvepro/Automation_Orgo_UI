package stepDefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import Generic_Utility.ExcelUtility;
import POM.HomePage;
import POM.LabControlPanelPage;
import POM.LabsPage;
import POM.SubscribePlanPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SingleLabRequest {

    ExcelUtility eLib = new ExcelUtility();
    public static Map<String, String> testData = new HashMap<>();
    public static String createdLabId;

    @When("get test data from excel {string}")
    public void get_test_data_from_excel(String tcId) throws Throwable {
        testData.clear();

        int rowCount = eLib.getRowCount("Lab");

        for (int i = 0; i <= rowCount; i++) {
            String tcIdValue = eLib.getDataFromExcel("Lab", i, 0);

            if (tcIdValue != null && tcIdValue.equals(tcId)) {
                testData.put("TC_ID", eLib.getDataFromExcel("Lab", i, 0));
                testData.put("PlanType", eLib.getDataFromExcel("Lab", i, 1));
                testData.put("LabRequestType", eLib.getDataFromExcel("Lab", i, 2));
                testData.put("LabType", eLib.getDataFromExcel("Lab", i, 3));
                testData.put("TeamID", eLib.getDataFromExcel("Lab", i, 4));
                testData.put("PlanID", eLib.getDataFromExcel("Lab", i, 5));

                System.out.println("Test data loaded for TC_ID: " + tcId);
                System.out.println("  PlanType: " + testData.get("PlanType"));
                System.out.println("  LabRequestType: " + testData.get("LabRequestType"));
                System.out.println("  LabType: " + testData.get("LabType"));
                System.out.println("  TeamID: " + testData.get("TeamID"));
                System.out.println("  PlanID: " + testData.get("PlanID"));
                return;
            }
        }

        throw new RuntimeException("Test data not found for TC_ID: " + tcId);
    }

    @And("click on select team option")
    public void click_on_select_team_option() throws Throwable {
        Hook.base.shDriver.click(Pages.HomePage.getTeamNameTab(), "select team option");
        System.out.println("Clicked on select team option");
    }

    @And("search and select team")
    public void search_and_select_team() throws Throwable {

        String teamName = testData.get("TeamID");

        Hook.base.page.waitForLoadState();

        Hook.base.shDriver.click(
                Pages.HomePage.getTeamDropdownSearchTxt(),
                "team search input");

        Hook.base.shDriver.fill(
                Pages.HomePage.getTeamDropdownSearchTxt(),
                teamName,
                "team search");

        Hook.base.page.waitForTimeout(1500);

        Hook.base.shDriver.click(
                Pages.HomePage.getTeamListLinkByName(teamName),
                "team link");

        System.out.println("Team selected: " + teamName);
        Thread.sleep(1000);
    }

    @And("navigate to labs page")
    public void navigate_to_labs_page() throws Throwable {
        Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
        Thread.sleep(500);
        Hook.base.page.waitForLoadState();
        Thread.sleep(500);
        Hook.base.shDriver.click(Pages.LabsDropdownPage.getLabsLink(), "labs link");
        Thread.sleep(1000);
    }

    @And("click on request lab button")
    public void click_on_request_lab_button() throws Throwable {
        Hook.base.shDriver.click(Pages.LabsPage.getRequestLabBtn(), "request lab button");

        Hook.base.page.waitForLoadState();
        System.out.println("Clicked on request lab button");
    }

    @And("select plan using test data")
    public void select_plan_using_test_data() throws Throwable {

        String planId = testData.get("PlanID");

        Hook.base.page.waitForLoadState();

        Hook.base.shDriver.click(
                Pages.LabStorePage.getPlanTileByPlanId(planId),
                "plan tile " + planId);

        Thread.sleep(500); // you can keep this if it's already stable

        System.out.println("Selected plan: " + planId);
    }

    @And("click on subscribe button")
    public void click_on_subscribe_button() throws Throwable {
        Hook.base.shDriver.click(Pages.SubscribePlanPage.getSubscribeButton(), "subscribe button");

        Hook.base.page.waitForLoadState();

        System.out.println("Clicked on subscribe button");
    }

    @Then("verify lab is created successfully")
    public void verify_lab_is_created_successfully() throws Throwable {

        LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base.page);

        int maxWaitTime = 300;
        int interval = 5;

        for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {

            String status = controlPanel.getLatestStatusText().textContent();
            System.out.println("Deployment Status: " + status);

            // Fail immediately if status is Failed
            if (status.trim().toLowerCase().contains("failed")) {
                throw new RuntimeException("Lab creation failed! Status: " + status);
            }

            if (status.trim().contains("Complete")) {

                System.out.println("Lab deployment is complete!");

                // ✅ Get current URL
                String currentUrl = Hook.base.page.url();
                System.out.println("Control Panel URL: " + currentUrl);

                // ✅ Extract id value
                String labId = currentUrl.split("id=")[1].split("&")[0];

                // ✅ Store globally (reuse your existing variable if needed)
                createdLabId = labId;

                System.out.println("Extracted Lab ID: " + labId);

                return;
            }

            Thread.sleep(interval * 1000);
        }

        throw new RuntimeException("Timeout: Lab deployment did not complete within " + maxWaitTime + " seconds");
    }

    // Commented out - Not used in current feature file
    // @And("verify the latest lab action status is {string}")
    // public void verify_the_latest_lab_action_status_is(String expectedStatus)
    // throws Throwable {
    // String labId = createdLabId;
    //
    // int maxWaitTime = 180;
    // int interval = 5;
    // String status = "";
    //
    // for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
    // status = Pages.LabsPage.getLabLatestActionStatusById(labId).textContent();
    // System.out.println("Latest Action Status for Lab ID " + labId + ": " +
    // status);
    //
    // if (status.trim().contains(expectedStatus)) {
    // System.out.println("Lab action status is " + expectedStatus + "!");
    // return;
    // }
    //
    // Thread.sleep(interval * 1000);
    // }
    //
    // throw new RuntimeException("Timeout: Latest action status did not become " +
    // expectedStatus + " within " + maxWaitTime + " seconds. Actual: " + status);
    // }

}