package stepDefinitions;

import java.text.SimpleDateFormat;
import java.util.Date;

import Generic_Utility.ExcelUtility;
import Generic_Utility.WebDriverUtility;
import POM.LabControlPanelPage;
import POM.LabStorePage;
import POM.LabsDropdownPage;
import POM.LabsPage;
import POM.OrganizationDropdownPage;
import POM.SubscribePlanPage;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class Labs {

	ExcelUtility eLib = new ExcelUtility();
	WebDriverUtility wLib = new WebDriverUtility();
	public static String testStartTime;
	public static String createdLabId;

	@When("click on select team option")
	public void click_on_select_team_option() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		testStartTime = sdf.format(new Date());
		Hook.base.shDriver.click(Pages.HomePage.getTeamNameTab(), "select team tab");
	}

	@And("search box enter search for team name")
	public void search_box_enter_search_for_team_name() throws Throwable {
		String teamName = eLib.getDataFromExcel("Lab", 16, 1);
		Hook.base.page.waitForLoadState();
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.HomePage.getTeamDropdownSearchTxt(), "team search input");
		Hook.base.page.locator("#teamdropdownsearch").fill(teamName);
		Thread.sleep(500);
	}

	@And("select the searched team from the list")
	public void select_the_searched_team_from_the_list() throws Throwable {
		String teamName = eLib.getDataFromExcel("Lab", 16, 1);
		Hook.base.shDriver.click(Pages.HomePage.getTeamListLinkByName(teamName), "team link");
		Thread.sleep(500);
	}

	@And("navigate to labs and click on labs")
	public void navigate_to_labs_and_click_on_labs() throws Throwable {
		Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getLabsTab(), "labs tab");
		Thread.sleep(500);
		Hook.base.page.waitForLoadState();
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.LabsDropdownPage.getLabsLink(), "labs link");
		Thread.sleep(1000);
	}

	@And("click on request lab button")
	public void click_on_request_lab_button() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(2000);
		com.microsoft.playwright.Locator requestBtn = Hook.base.page.locator("//button[@id='marketplace']");
		requestBtn.scrollIntoViewIfNeeded();
		Thread.sleep(500);
		requestBtn.click();
		Thread.sleep(2000);
	}

	@And("search for the plan id and click on that tile")
	public void search_for_the_plan_id_and_click_on_that_tile() throws Throwable {
		String planId = eLib.getDataFromExcel("Lab", 17, 1);
		Hook.base.page.waitForLoadState();
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.LabStorePage.getPlanTileByPlanId(planId), "plan tile " + planId);
		Thread.sleep(500);
	}

	@And("click on subscribe button")
	public void click_on_subscribe_button() throws Throwable {
		Hook.base.shDriver.click(Pages.SubscribePlanPage.getSubscribeButton(), "subscribe button");
		Thread.sleep(5000);
	}

	@And("verify the lab is created in the subscriptions table")
	public void verify_the_lab_is_created_in_the_subscriptions_table() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(2000);
		createdLabId = Pages.LabsPage.getAllTableRows().first().locator("td:nth-child(2)").textContent();
		System.out.println("Created Lab ID: " + createdLabId);
		String createdOn = Pages.LabsPage.getAllTableRows().first().locator("td:nth-child(6)").textContent();
		System.out.println("Lab Created On: " + createdOn);
		String currentDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		boolean dateMatch = createdOn.contains(currentDate);
		System.out.println("Date matches today: " + dateMatch);
		if (!dateMatch) {
			throw new RuntimeException("Created date does not match current date");
		}
	}

	@And("verify the latest action status is \"Complete\"")
	public void verify_the_latest_action_status_is_Complete() throws Throwable {
		int maxWaitTime = 180;
		int interval = 5;
		String status = "";
		for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
			status = Pages.LabsPage.getAllTableRows().first().locator("td:nth-child(10)").textContent();
			System.out.println("Latest Action Status: " + status);
			if (status.trim().contains("Complete")) {
				System.out.println("Lab creation is complete!");
				return;
			}
			Thread.sleep(interval * 1000);
		}
		throw new RuntimeException("Timeout: Latest action status did not become Complete within " + maxWaitTime + " seconds. Actual: " + status);
	}

	@And("click on the created lab to open Lab Control Panel")
	public void click_on_the_created_lab_to_open_lab_control_panel() throws Throwable {
		Hook.base.shDriver.click(Pages.LabsPage.getAllTableRows().first().locator("td:nth-child(2)"), "lab link");
		Thread.sleep(1000);
	}

	@And("verify the lab is ready to use")
	public void verify_the_lab_is_ready_to_use() throws Throwable {
		LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base.page);
		String isReadyToUse = controlPanel.getIsReadyToUseValue().textContent();
		System.out.println("Is Ready To Use: " + isReadyToUse);
		if (!isReadyToUse.trim().equals("true")) {
			throw new RuntimeException("Lab is not ready to use. Actual: " + isReadyToUse);
		}
		System.out.println("Lab is ready to use");
	}

	@And("wait until the deployment status shows \"Complete\"")
	public void wait_until_the_deployment_status_shows_Complete() throws Throwable {
		LabControlPanelPage controlPanel = new LabControlPanelPage(Hook.base.page);
		int maxWaitTime = 300;
		int interval = 5;
		for (int elapsed = 0; elapsed < maxWaitTime; elapsed += interval) {
			String status = controlPanel.getLatestStatusText().textContent();
			System.out.println("Deployment Status: " + status);
			if (status.trim().contains("Complete")) {
				System.out.println("Lab deployment is complete!");
				return;
			}
			Thread.sleep(interval * 1000);
		}
		throw new RuntimeException("Timeout: Lab deployment did not complete within " + maxWaitTime + " seconds");
	}

}