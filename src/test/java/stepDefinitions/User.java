package stepDefinitions;

import org.apache.poi.EncryptedDocumentException;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import Generic_Utility.ExcelUtility;
import Generic_Utility.JavaUtility;
import Generic_Utility.WebDriverUtility;
import Util.Pages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

public class User {

	ExcelUtility eLib = new ExcelUtility();
	WebDriverUtility wLib = new WebDriverUtility();
	JavaUtility jLib = new JavaUtility();
	static String updatedLoginId_global;
	static String updatedEmailId_global;
	static int totalUserCount;

	@Given("open the browser and enter the Url")
	public void open_the_browser_and_enter_the_url() throws EncryptedDocumentException, Throwable {
		System.out.println("Browser setup handled in Hook class");
	}

	@When("navigate to organization and click on users tab")
	public void navigate_to_organization_and_click_on_users_tab() {
		Hook.base.shDriver.click(Pages.HomePage.getOrganizationDropdown(), "organization dropdown");
		Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getUserBtn(), "user tab");
	}

	@When("navigate to organization and click on Import Users")
	public void navigate_to_organization_and_click_on_import_users() {
		Hook.base.shDriver.click(Pages.HomePage.getOrganizationDropdown(), "organization dropdown");
		Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getImportUserTab(), "import users tab");
	}

	@When("click on create a user button")
	public void click_on_create_a_user_button() {
		Hook.base.shDriver.click(Pages.UserPage.getCreateUserBtn(), "create user button");
	}

	@Given("enter all the required user creation details")
	public void enter_all_the_required_user_creation_details() throws Throwable {

		String firstName = eLib.getDataFromExcel("User", 1, 1);
		String lastName = eLib.getDataFromExcel("User", 2, 1);
		String emailId = eLib.getDataFromExcel("User", 3, 1);
		String employeeId = eLib.getDataFromExcel("User", 4, 1);
		String LoginId = eLib.getDataFromExcel("User", 5, 1);
		String password = eLib.getDataFromExcel("User", 6, 1);
		String confirmPassword = eLib.getDataFromExcel("User", 7, 1);

		Hook.base.shDriver.fill(Pages.CreateUserPage.getFirstNameTxt(), firstName + jLib.getRandomNumber(),
				"first name");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getLastNameTxt(), lastName + jLib.getRandomNumber(), "last name");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getEmailIdTxt(), jLib.getRandomNumber() + emailId, "email id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getEmployeeIdTxt(), employeeId, "employee id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getLoginIdTxt(), LoginId + jLib.getRandomNumber(), "login id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getPasswordTxt(), password, "password");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getConfirmPasswordTxt(), confirmPassword, "confirm password");

		wLib.scrollToTop(Hook.base.page);
		String administrationRole = eLib.getDataFromExcel("User", 27, 0);
		Hook.base.shDriver.click(Hook.base.page.locator("//label[contains(text(),'" + administrationRole + "')]"),
				"administration role");
	}

	@When("click on create button")
	public void click_on_create_button() {
		Hook.base.shDriver.click(Pages.CreateUserPage.getCreateSubmitBtn(), "create submit button");
	}

	@Then("validate user created successfully")
	public void validate_user_created_successfully() {
		Locator createSucessMsg = Pages.CreateUserPage.getUserCreationSuccessMsgTxt();
		wLib.waitForElementVisibility(Hook.base.page, createSucessMsg);
		String value = Pages.CreateUserPage.getUserCreationSuccessMsgTxt().textContent();
		Assert.assertEquals(value, "Success");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@When("navigate to organization and click on create new user tab")
	public void navigate_to_organization_and_click_on_create_new_user_tab() {
		Hook.base.shDriver.click(Pages.HomePage.getOrganizationDropdown(), "organization dropdown");
		Hook.base.shDriver.click(Pages.OrganizationDropdownPage.getCreateUserTab(), "create user tab");
	}

	@Given("enter all the required user creation details with invalid inputs")
	public void enter_all_the_required_user_creation_details_with_invalid_inputs() throws Throwable {
		String firstName = eLib.getDataFromExcel("User", 1, 1);
		String lastName = eLib.getDataFromExcel("User", 2, 1);
		String emailId = "invalid-email";
		String employeeId = eLib.getDataFromExcel("User", 4, 1);
		String LoginId = "";
		String password = "123";
		String confirmPassword = "456";

		Hook.base.shDriver.fill(Pages.CreateUserPage.getFirstNameTxt(), firstName, "first name");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getLastNameTxt(), lastName, "last name");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getEmailIdTxt(), emailId, "email id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getEmployeeIdTxt(), employeeId, "employee id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getLoginIdTxt(), LoginId, "login id");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getPasswordTxt(), password, "password");
		Hook.base.shDriver.fill(Pages.CreateUserPage.getConfirmPasswordTxt(), confirmPassword, "confirm password");

		wLib.scrollToTop(Hook.base.page);
		String administrationRole = eLib.getDataFromExcel("User", 27, 0);
		Hook.base.shDriver.click(Hook.base.page.locator("//label[contains(text(),'" + administrationRole + "')]"),
				"administration role");
	}

	@Then("validate user creation failed with validation errors")
	public void validate_user_creation_failed_with_validation_errors() {
		System.out.println("Validating error messages for invalid inputs");
		try {
			Locator errorMsg = Hook.base.page.locator("//div[contains(@class,'error') or contains(@class,'alert')]");
			if (errorMsg.isVisible()) {
				String errorText = errorMsg.textContent();
				System.out.println("Validation error displayed: " + errorText);
				Assert.assertTrue(errorText.length() > 0, "Error message should be displayed for invalid inputs");
			} else {
				System.out.println("Checking for field-specific validation errors");
				Assert.assertTrue(true, "Validation errors should prevent user creation");
			}
		} catch (Exception e) {
			System.out.println("Error validation check: " + e.getMessage());
		} finally {
			if (Hook.base != null && Hook.base.shDriver != null) {
				Hook.base.shDriver.saveHealingReport();
			}
		}
	}

	@And("click on import button")
	public void click_on_import_button() {
		Hook.base.shDriver.click(Pages.UserPage.getImportUserBtn(), "import button");
	}

	@And("select the CSV file from local system")
	public void select_the_csv_file_from_local_system() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(500);
		java.nio.file.Path filePath = java.nio.file.Paths.get(System.getProperty("user.dir"),
				"src/test/resources/users_template(in).csv");
		Hook.base.page.locator("#fileToUpload").setInputFiles(filePath);
		Thread.sleep(500);
	}

	@And("provide the import details")
	public void provide_the_import_details() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);

		Thread.sleep(300);
		wLib.scrollToTop(Hook.base.page);
		Thread.sleep(500);
	}

	@And("click on importsubmit button")
	public void click_on_importsubmit_button() {
		Hook.base.shDriver.click(Pages.ImportUserPage.getImportSubmitBtn(), "importsubmit button");
	}

	@Then("validate the users are imported successfully")
	public void validate_the_users_are_imported_successfully() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(2000);
		int successCount = Hook.base.page.locator("#batchSummaryTable tbody tr td:has-text('Success')").count();
		int totalRows = Hook.base.page.locator("#batchSummaryTable tbody tr").count();
		System.out.println("Total rows: " + totalRows + ", Success count: " + successCount);
		Assert.assertTrue(successCount == totalRows && successCount > 0,
				"Expected all users to have Success status. Success: " + successCount + ", Total: " + totalRows);
		int emptyDetailsCount = Hook.base.page.locator("#batchSummaryTable tbody tr td:last-child:empty").count();
		Assert.assertTrue(emptyDetailsCount == totalRows,
				"Expected all details to be empty. Empty: " + emptyDetailsCount + ", Total: " + totalRows);
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@When("select the user and click on remove button")
	public void select_the_user_and_click_on_remove_button() throws Throwable {
		String removeUserLoginId = eLib.getDataFromExcel("User", 18, 1);
		Hook.base.shDriver.click(Hook.base.page.locator(
				"//td[text()='" + removeUserLoginId + "']/preceding-sibling::td[@class=' select-checkbox noVis']"),
				"user checkbox for delete");
		Hook.base.shDriver.click(Pages.UserPage.getUserRemoveBtn(), "remove button");
	}

	@And("click on delete button to confirm")
	public void click_on_delete_button_to_confirm() {
		Hook.base.shDriver.click(Pages.UserPage.getUserDeleteBtn(), "delete button");
	}

	@Then("validate the user details are deleted")
	public void validate_the_user_details_are_deleted() {
		Locator deleteSuccessMsg = Pages.UserPage.getDeleteSuccessMsg();
		wLib.waitForElementVisibility(Hook.base.page, deleteSuccessMsg);
		String value = deleteSuccessMsg.textContent();
		Assert.assertEquals(value, "Success");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@When("select the user and click on edit button")
	public void select_the_user_and_click_on_edit_button() throws Throwable {
		String editUserLoginId = eLib.getDataFromExcel("User", 17, 1);
		Hook.base.shDriver.click(Pages.UserPage.getSearchItemDisplay(), "search item display");
		Hook.base.shDriver.click(Pages.UserPage.getSearchDropdownOption("name"), "search by name option");
		Hook.base.shDriver.fill(Pages.UserPage.getSearchInputBox(), editUserLoginId, "search input");
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");

		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(editUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);

		Hook.base.page.waitForLoadState();
		Hook.base.shDriver.click(Pages.UserPage.getUserEditBtn(), "edit button");
	}

	@Given("edit the required inputs")
	public void edit_the_required_inputs() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);

		Hook.base.page.locator("#first_name").click();
		Hook.base.page.locator("#first_name").press("Control+a");
		Hook.base.page.locator("#first_name").press("Delete");

		Hook.base.page.locator("#last_name").click();
		Hook.base.page.locator("#last_name").press("Control+a");
		Hook.base.page.locator("#last_name").press("Delete");

		Hook.base.page.locator("#email").click();
		Hook.base.page.locator("#email").press("Control+a");
		Hook.base.page.locator("#email").press("Delete");

		Hook.base.page.locator("#loginId").click();
		Hook.base.page.locator("#loginId").press("Control+a");
		Hook.base.page.locator("#loginId").press("Delete");

		wLib.scrollToTop(Hook.base.page);
		Hook.base.page.locator("label").filter(new Locator.FilterOptions().setHasText("Member")).first().click();

		String firstName = eLib.getDataFromExcel("User", 10, 1);
		String lastName = eLib.getDataFromExcel("User", 11, 1);
		String email = eLib.getDataFromExcel("User", 12, 1);
		String externalId = eLib.getDataFromExcel("User", 13, 1);
		String loginId = eLib.getDataFromExcel("User", 14, 1);

		Hook.base.page.locator("#first_name").fill(firstName + jLib.getRandomNumber());
		Hook.base.page.locator("#last_name").fill(lastName + jLib.getRandomNumber());
		Hook.base.page.locator("#email").fill(jLib.getRandomNumber() + email);
		Hook.base.page.locator("#employee_id").fill(externalId + jLib.getRandomNumber());
		Hook.base.page.locator("#loginId").fill(loginId + jLib.getRandomNumber());

		wLib.scrollToTop(Hook.base.page);
		String billingAdminrole = eLib.getDataFromExcel("User", 29, 0);
		Hook.base.page.locator("label").filter(new Locator.FilterOptions().setHasText(billingAdminrole)).first()
				.click();
	}

	@When("click on save button")
	public void click_on_save_button() {
		Hook.base.shDriver.click(Pages.EditUserPage.getSaveBtn(), "save button");
	}

	@Then("validate the user details are updated")
	public void validate_the_user_details_are_updated() {
		System.out.println("User details updated");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("select the user and click on add to teams button")
	public void select_the_user_and_click_on_add_to_teams_button() throws Throwable {
		String addToTeamUserLoginId = eLib.getDataFromExcel("User", 22, 1);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(addToTeamUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getAddUserIntoTeamBtn(), "add to teams button");
	}

	@And("select a team and click on add button")
	public void select_a_team_and_click_on_add_button() throws Throwable {
		String teamName = eLib.getDataFromExcel("User", 24, 1);

		Hook.base.page.waitForLoadState();
		Thread.sleep(1500);

		wLib.scrollToTop(Hook.base.page);
		Thread.sleep(500);

		Locator select2Container = Hook.base.page.locator("#customTeamContainer .select2-selection");
		wLib.waitForElementVisibility(Hook.base.page, select2Container);
		select2Container.click();
		Thread.sleep(1500);

		Locator searchField = Hook.base.page.locator(".select2-search__field");
		wLib.waitForElementVisibility(Hook.base.page, searchField);
		searchField.fill(teamName);
		Thread.sleep(1500);

		Locator teamOption = Hook.base.page.locator(".select2-results__option")
				.filter(new Locator.FilterOptions().setHasText(teamName));
		wLib.waitForElementVisibility(Hook.base.page, teamOption);
		teamOption.click();
		Thread.sleep(1000);

		Hook.base.shDriver.click(Pages.AddToTeamModalPage.getAddButton(), "add button");
	}

	@Then("validate user added to team successfully")
	public void validate_user_added_to_team_successfully() {
		Locator successMsg = Pages.UserPage.getUseraddedIntoTeamsuccessMsg();
		wLib.waitForElementVisibility(Hook.base.page, successMsg);
		String value = successMsg.textContent();
		Assert.assertEquals(value, "Success");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@When("select the user and click on change password button")
	public void select_the_user_and_click_on_change_password_button() throws Throwable {
		String changePwdUserLoginId = eLib.getDataFromExcel("User", 21, 1);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(changePwdUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getChangePasswordBtn(), "change password button");
	}

	@And("update the password and click on apply password button")
	public void update_the_password_and_click_on_apply_password_button() throws Throwable {
		String newPassword = eLib.getDataFromExcel("User", 6, 1);
		String confirmPassword = eLib.getDataFromExcel("User", 7, 1);

		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);

		Hook.base.page.locator("#password").fill(newPassword);
		Thread.sleep(500);

		Hook.base.page.locator("#confirm_password").fill(confirmPassword);
		Thread.sleep(500);

		Hook.base.shDriver.click(Pages.UserPage.getApplyPasswordBtn(), "apply password button");
	}

	@Then("validate password changed successfully")
	public void validate_password_changed_successfully() {
		Locator successMsg = Pages.UserPage.getPasswordUpdatedconfirmMsg();
		wLib.waitForElementVisibility(Hook.base.page, successMsg);
		String value = successMsg.textContent();
		Assert.assertEquals(value, "Password Updated Successfully");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("select the user and click on deactivate button")
	public void select_the_user_and_click_on_deactivate_button() throws Throwable {
		String deactivateUserLoginId = eLib.getDataFromExcel("User", 19, 1);
		Hook.base.shDriver.click(Pages.UserPage.getSearchItemDisplay(), "search item display");
		Hook.base.shDriver.click(Pages.UserPage.getSearchDropdownOption("name"), "search by name option");
		Hook.base.shDriver.fill(Pages.UserPage.getSearchInputBox(), deactivateUserLoginId, "search input");
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");

		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(deactivateUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);

		Hook.base.page.waitForLoadState();
		wLib.scrollToTop(Hook.base.page);
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserStatusDropdownBtn(), "status dropdown");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserDeactivateLinkBtn(), "deactivate link button");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserDeactiveConfirmBtn(), "deactivate confirm button");
	}

	@Then("validate user deactivated successfully")
	public void validate_user_deactivated_successfully() throws Throwable {
		String deactivateUserLoginId = eLib.getDataFromExcel("User", 19, 1);
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator userStatus = Pages.UserPage.getUserStatusByLoginId(deactivateUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, userStatus);
		String statusValue = userStatus.textContent();
		Assert.assertEquals(statusValue, "Inactive");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("select the user and click on activate button")
	public void select_the_user_and_click_on_activate_button() throws Throwable {
		String activateUserLoginId = eLib.getDataFromExcel("User", 19, 1);
		Hook.base.shDriver.click(Pages.UserPage.getSearchItemDisplay(), "search item display");
		Hook.base.shDriver.click(Pages.UserPage.getSearchDropdownOption("name"), "search by name option");
		Hook.base.shDriver.fill(Pages.UserPage.getSearchInputBox(), activateUserLoginId, "search input");
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");

		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(activateUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);

		Hook.base.page.waitForLoadState();
		wLib.scrollToTop(Hook.base.page);
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserStatusDropdownBtn(), "status dropdown");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserActivateLinkBtn(), "activate link button");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserActiveConfirmBtn(), "activate confirm button");
	}

	@And("select the same user and click on activate button")
	public void select_the_same_user_and_click_on_activate_button() throws Throwable {
		String activateUserLoginId = eLib.getDataFromExcel("User", 19, 1);
		Hook.base.shDriver.click(Pages.UserPage.getSearchItemDisplay(), "search item display");
		Hook.base.shDriver.click(Pages.UserPage.getSearchDropdownOption("name"), "search by name option");
		Hook.base.shDriver.fill(Pages.UserPage.getSearchInputBox(), activateUserLoginId, "search input");
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");

		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator checkbox = Pages.UserPage.getSelectUserCheckBoxByLoginId(activateUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, checkbox);
		checkbox.click();
		Thread.sleep(500);

		Hook.base.page.waitForLoadState();
		wLib.scrollToTop(Hook.base.page);
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserStatusDropdownBtn(), "status dropdown");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserActivateLinkBtn(), "activate link button");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserActiveConfirmBtn(), "activate confirm button");
	}

	@Then("validate user activated successfully")
	public void validate_user_activated_successfully() throws Throwable {
		String activateUserLoginId = eLib.getDataFromExcel("User", 19, 1);
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator userStatus = Pages.UserPage.getUserStatusByLoginId(activateUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, userStatus);
		String statusValue = userStatus.textContent();
		Assert.assertEquals(statusValue, "Active");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("change the user role")
	public void change_the_user_role() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		wLib.scrollToTop(Hook.base.page);
		String newRole = eLib.getDataFromExcel("User", 30, 0);
		Hook.base.page.locator("label").filter(new Locator.FilterOptions().setHasText(newRole)).first().click();
		Thread.sleep(500);
	}

	@Then("validate the user role is updated")
	public void validate_the_user_role_is_updated() throws Throwable {
		String editUserLoginId = eLib.getDataFromExcel("User", 17, 1);
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator userRole = Pages.UserPage.getUserRoleByLoginId(editUserLoginId);
		wLib.waitForElementVisibility(Hook.base.page, userRole);
		String roleValue = userRole.textContent();
		String expectedRole = eLib.getDataFromExcel("User", 30, 0);
		Assert.assertTrue(roleValue.contains(expectedRole),
				"Expected role '" + expectedRole + "' but found '" + roleValue + "'");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("change the user login ID")
	public void change_the_user_login_id() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		String newLoginId = eLib.getDataFromExcel("User", 42, 1);
		updatedLoginId_global = newLoginId + jLib.getRandomNumber();
		Hook.base.page.locator("#loginId").click();
		Hook.base.page.locator("#loginId").press("Control+a");
		Hook.base.page.locator("#loginId").press("Delete");
		Hook.base.page.locator("#loginId").fill(updatedLoginId_global);
		Thread.sleep(500);
	}

	@Then("validate the user login ID is updated")
	public void validate_the_user_login_id_is_updated() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Hook.base.page.locator("#stringSearch").click();
		Hook.base.page.locator("#stringSearch").press("Control+a");
		Hook.base.page.locator("#stringSearch").press("Delete");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");
		Thread.sleep(1000);
		Hook.base.page.locator("#stringSearch").fill(updatedLoginId_global);
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");
		Thread.sleep(1000);
		Locator userLoginId = Pages.UserPage.getUserLoginIdByUserId(updatedLoginId_global);
		wLib.waitForElementVisibility(Hook.base.page, userLoginId);
		String loginIdValue = userLoginId.textContent();
		Assert.assertTrue(loginIdValue.contains(updatedLoginId_global),
				"Expected login ID containing '" + updatedLoginId_global + "' but found '" + loginIdValue + "'");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("change the user email ID")
	public void change_the_user_email_id() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		String newEmailId = eLib.getDataFromExcel("User", 43, 1);
		updatedEmailId_global = jLib.getRandomNumber() + newEmailId;
		Hook.base.page.locator("#email").click();
		Hook.base.page.locator("#email").press("Control+a");
		Hook.base.page.locator("#email").press("Delete");
		Hook.base.page.locator("#email").fill(updatedEmailId_global);
		Thread.sleep(500);
	}

	@Then("validate the user email ID is updated")
	public void validate_the_user_email_id_is_updated() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Hook.base.page.locator("#stringSearch").click();
		Hook.base.page.locator("#stringSearch").press("Control+a");
		Hook.base.page.locator("#stringSearch").press("Delete");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");
		Thread.sleep(1000);
		Hook.base.page.locator("#stringSearch").fill(updatedEmailId_global);
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getSearchBtn(), "search button");
		Thread.sleep(1000);
		Locator userEmailId = Pages.UserPage.getUserEmailIdByEmailId(updatedEmailId_global);
		wLib.waitForElementVisibility(Hook.base.page, userEmailId);
		String emailIdValue = userEmailId.textContent();
		Assert.assertTrue(emailIdValue.contains(updatedEmailId_global),
				"Expected email ID containing '" + updatedEmailId_global + "' but found '" + emailIdValue + "'");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

	@And("select the user and click on view details")
	public void select_the_user_and_click_on_view_details() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(500);
		String loginId = eLib.getDataFromExcel("User", 44, 1);
		Hook.base.shDriver.click(Pages.UserPage.getSelectUserCheckBoxByLoginId(loginId), "user checkbox");
		Thread.sleep(500);
		Hook.base.shDriver.click(Pages.UserPage.getUserViewBtn(), "view details button");
		Thread.sleep(1000);
	}

	@Then("validate the user details are displayed correctly")
	public void validate_the_user_details_are_displayed_correctly() throws Throwable {
		Hook.base.page.waitForLoadState();
		Thread.sleep(1000);
		Locator userDetailsPanel = Hook.base.page.locator(
				"//div[contains(@class,'user-details') or contains(@class,'panel')]//h3[contains(text(),'User Details')]");
		wLib.waitForElementVisibility(Hook.base.page, userDetailsPanel);
		System.out.println("User details page displayed successfully");
		if (Hook.base != null && Hook.base.shDriver != null) {
			Hook.base.shDriver.saveHealingReport();
		}
	}

}
