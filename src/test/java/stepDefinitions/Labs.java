package stepDefinitions;

import java.text.SimpleDateFormat;
import java.util.Date;

import Generic_Utility.ExcelUtility;
import Generic_Utility.WebDriverUtility;
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
		Hook.base.shDriver.click(Pages.LabsPage.getRequestLabBtn(), "request lab button");
		Thread.sleep(500);
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
		String status = Pages.LabsPage.getAllTableRows().first().locator("td:nth-child(10)").textContent();
		System.out.println("Latest Action Status: " + status);
		if (!status.trim().contains("Complete")) {
			throw new RuntimeException("Latest action status is not Complete. Actual: " + status);
		}
	}

}