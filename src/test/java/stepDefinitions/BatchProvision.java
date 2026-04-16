package stepDefinitions;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;

import Generic_Utility.ExcelUtility;
import POM.LabsPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BatchProvision {

    ExcelUtility eLib = new ExcelUtility();
    public static Map<String, String> testData = new HashMap<>();
    public static String createdLabIds;

    @And("get test data from excel TC27")
    public void get_test_data_from_excel_tc27() throws Throwable {
        testData.clear();

        int rowCount = eLib.getRowCount("BatchProvision");

        for (int i = 0; i <= rowCount; i++) {
            String tcIdValue = eLib.getDataFromExcel("BatchProvision", i, 0);

            if (tcIdValue != null && tcIdValue.equals("TC27")) {
                testData.put("TC_ID", eLib.getDataFromExcel("BatchProvision", i, 0));
                testData.put("TeamID", eLib.getDataFromExcel("BatchProvision", i, 1));
                testData.put("PlanID", eLib.getDataFromExcel("BatchProvision", i, 2));
                testData.put("UserEmail", eLib.getDataFromExcel("BatchProvision", i, 3));
                testData.put("BatchName", eLib.getDataFromExcel("BatchProvision", i, 4));
                testData.put("BatchDescription", eLib.getDataFromExcel("BatchProvision", i, 5));

                System.out.println("Test data loaded for TC_ID: TC27");
                System.out.println("  TeamID: " + testData.get("TeamID"));
                System.out.println("  PlanID: " + testData.get("PlanID"));
                System.out.println("  UserEmail: " + testData.get("UserEmail"));
                System.out.println("  BatchName: " + testData.get("BatchName"));
                System.out.println("  BatchDescription: " + testData.get("BatchDescription"));
                return;
            }
        }

        throw new RuntimeException("Test data not found for TC_ID: TC27");
    }

    @And("navigate to Batch Provisioning of Labs page")
    public void navigate_to_batch_provisioning_of_labs_page() throws Throwable {
        Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
        Thread.sleep(500);
        Hook.base.page.waitForLoadState();
        Thread.sleep(500);
        Hook.base.shDriver.click(Pages.LabsDropdownPage.getBatchProvisioningLink(), "batch provisioning link");
        Thread.sleep(1000);
    }

    @And("click on create button")
    public void click_on_create_button() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getCreateBtn(), "create button");
        Thread.sleep(500);
    }

    @And("in batch provision details page enter name and description")
    public void in_batch_provision_details_page_enter_name_and_description() throws Throwable {
        String batchName = testData.get("BatchName");
        String batchDescription = testData.get("BatchDescription");

        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getBatchNameInput(), batchName, "batch name");
        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getBatchDescriptionInput(), batchDescription,
                "batch description");
        Thread.sleep(500);
    }

    @And("click on next button")
    public void click_on_next_button() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getNextBtn(), "next button");
        Thread.sleep(1000);
    }

    @And("in choose user page enter the user email address in the search box and click on search")
    public void in_choose_user_page_enter_the_user_email_address_in_the_search_box_and_click_on_search()
            throws Throwable {
        String userEmail = testData.get("UserEmail");

        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getUserSearchInput(), userEmail, "user email search");
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserSearchBtn(), "search button");
        Thread.sleep(1000);
    }

    @And("select the search listed user checkbox")
    public void select_the_search_listed_user_checkbox() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getUserCheckbox(), "user checkbox");
        Thread.sleep(500);
    }

    @And("in the choose plan page provide plan id input in search box and select the listed plan")
    public void in_the_choose_plan_page_provide_plan_id_input_in_search_box_and_select_the_listed_plan()
            throws Throwable {
        String planId = testData.get("PlanID");

        Hook.base.shDriver.fill(Pages.BatchProvisionPage.getPlanSearchInput(), planId, "plan id search");
        Thread.sleep(1000);
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getPlanCheckbox(), "plan checkbox");
        Thread.sleep(500);
    }

    @And("click on finish button")
    public void click_on_finish_button() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getFinishBtn(), "finish button");
        Thread.sleep(1000);
    }

    @And("click on confirm button on create batch provisioning labs pop-up")
    public void click_on_confirm_button_on_create_batch_provisioning_labs_popup() throws Throwable {
        Hook.base.shDriver.click(Pages.BatchProvisionPage.getConfirmBtn(), "confirm button");
        Thread.sleep(2000);
    }

    @Then("in the batch provisioning summary table check user listed and status and lab id and details is getting generated")
    public void in_the_batch_provisioning_summary_table_check_user_listed_and_status_and_lab_id_and_details_is_getting_generated()
            throws Throwable {
        String userEmail = testData.get("UserEmail");

        Hook.base.page.waitForLoadState();
        Thread.sleep(2000);

        String userListed = Hook.base.shDriver.getText(Pages.BatchProvisionPage.getUserListedInSummary(),
                "user listed");
        System.out.println("User listed in summary: " + userListed);

        String status = Hook.base.shDriver.getText(Pages.BatchProvisionPage.getStatusInSummary(), "status");
        System.out.println("Status: " + status);

        String labId = Hook.base.shDriver.getText(Pages.BatchProvisionPage.getLabIdInSummary(), "lab id");
        System.out.println("Lab ID: " + labId);

        createdLabIds = labId;

        Assert.assertTrue(userListed.contains(userEmail.split("@")[0]) || userListed.contains(userEmail),
                "User should be listed in summary");
        Assert.assertFalse(status.isEmpty(), "Status should not be empty");
        Assert.assertFalse(labId.isEmpty(), "Lab ID should not be empty");

        System.out.println("Batch provisioning summary validation passed");
    }

    @And("copy those lab id's and navigate to all labs page")
    public void copy_those_lab_ids_and_navigate_to_all_labs_page() throws Throwable {
        Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
        Thread.sleep(500);
        Hook.base.page.waitForLoadState();
        Thread.sleep(500);
        Hook.base.shDriver.click(Pages.LabsDropdownPage.getLabsLink(), "labs link");
        Thread.sleep(1000);
    }

    @Then("validate all lab is completion status in the latest action in all labs table")
    public void validate_all_lab_is_completion_status_in_the_latest_action_in_all_labs_table() throws Throwable {
        String labId = createdLabIds;

        LabsPage labsPage = new LabsPage(Hook.base.page);

        int maxWaitTime = 300;
        int interval = 5;

        for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
            Hook.base.page.waitForLoadState();

            String latestAction = Hook.base.shDriver.getText(labsPage.getLabLatestActionById(labId), "latest action");
            System.out.println("Latest Action for Lab ID " + labId + ": " + latestAction);

            String status = Hook.base.shDriver.getText(labsPage.getLabLatestActionStatusById(labId), "status");
            System.out.println("Status for Lab ID " + labId + ": " + status);

            if (status.trim().toLowerCase().contains("failed")) {
                throw new RuntimeException("Lab creation failed! Status: " + status);
            }

            if (status.trim().contains("Complete")) {
                System.out.println("Lab deployment is complete for Lab ID: " + labId);
                return;
            }

            Thread.sleep(interval * 1000);
        }

        throw new RuntimeException(
                "Timeout: Lab deployment did not complete within " + maxWaitTime + " seconds for Lab ID: " + labId);
    }
}