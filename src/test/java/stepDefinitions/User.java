package stepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import org.testng.Assert;
import Generic_Utility.ExcelUtility;
import Generic_Utility.JavaUtility;
import Generic_Utility.TestDataManager;
import Generic_Utility.WebDriverUtility;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Generic_Utility.WaitUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class User {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    WebDriverUtility wLib = new WebDriverUtility();
    JavaUtility jLib = new JavaUtility();

    static volatile String createdLoginId = "";
    static volatile String tc3LoginId     = "";
    static volatile String tc4LoginId     = "";
    static volatile String tc10LoginId    = "";
    static volatile String tc10NewLoginId = "";
    static volatile String tc14LoginId    = "";
    static volatile String tc14NewEmailId = "";
    static volatile String tc17LoginId    = "";
    static volatile String tc17Email      = "";
    static volatile String tc19LoginId    = "";

    public static String getCreatedLoginId() {
        return createdLoginId;
    }

    // Always reads Excel row 2 col 13 (written by TC1/TC2 on user creation).
    // Falls back to in-memory createdLoginId if the cell is empty.
    private static String resolveLoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 2, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) {
                return fromExcel;
            }
        } catch (Throwable e) {
            log.warn("Could not read loginId from Excel (row 2, col 13): {}", e.getMessage());
        }
        if (createdLoginId != null && !createdLoginId.isEmpty()) {
            log.warn("Excel cell empty — falling back to in-memory loginId: {}", createdLoginId);
            return createdLoginId;
        }
        throw new RuntimeException(
                "LoginId not found in Excel (User sheet row 2, col 13) and createdLoginId is not set.");
    }

    // Reads Excel row 4 col 13 (written by TC3's create step).
    // Falls back to in-memory tc3LoginId if the cell is empty.
    private static String resolveTc3LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 4, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) {
                return fromExcel;
            }
        } catch (Throwable e) {
            log.warn("Could not read TC3 loginId from Excel (row 4, col 13): {}", e.getMessage());
        }
        if (tc3LoginId != null && !tc3LoginId.isEmpty()) {
            log.warn("Excel cell empty — falling back to in-memory tc3LoginId: {}", tc3LoginId);
            return tc3LoginId;
        }
        throw new RuntimeException(
                "TC3 loginId not found in Excel (User sheet row 4, col 13) and tc3LoginId is not set.");
    }

    // Reads Excel row 11 col 13 (written by TC10's create step).
    // Falls back to in-memory tc10LoginId if the cell is empty.
    private static String resolveTc10LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 11, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) {
                return fromExcel;
            }
        } catch (Throwable e) {
            log.warn("Could not read TC10 loginId from Excel (row 11, col 13): {}", e.getMessage());
        }
        if (tc10LoginId != null && !tc10LoginId.isEmpty()) {
            log.warn("Excel cell empty — falling back to in-memory tc10LoginId: {}", tc10LoginId);
            return tc10LoginId;
        }
        throw new RuntimeException(
                "TC10 loginId not found in Excel (User sheet row 11, col 13) and tc10LoginId is not set.");
    }

    // Reads Excel row 15 col 13 (written by TC14's create step).
    // Falls back to in-memory tc14LoginId if the cell is empty.
    private static String resolveTc14LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 15, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) {
                return fromExcel;
            }
        } catch (Throwable e) {
            log.warn("Could not read TC14 loginId from Excel (row 15, col 13): {}", e.getMessage());
        }
        if (tc14LoginId != null && !tc14LoginId.isEmpty()) {
            log.warn("Excel cell empty — falling back to in-memory tc14LoginId: {}", tc14LoginId);
            return tc14LoginId;
        }
        throw new RuntimeException(
                "TC14 loginId not found in Excel (User sheet row 15, col 13) and tc14LoginId is not set.");
    }

    // Reads Excel row 18 col 13 (written by TC17's create step).
    private static String resolveTc17LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 18, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) return fromExcel;
        } catch (Throwable e) {
            log.warn("Could not read TC17 loginId from Excel (row 18, col 13): {}", e.getMessage());
        }
        if (tc17LoginId != null && !tc17LoginId.isEmpty()) return tc17LoginId;
        throw new RuntimeException("TC17 loginId not found in Excel (row 18, col 13) and tc17LoginId is not set.");
    }

    // Reads Excel row 20 col 13 (written by TC19's create step).
    private static String resolveTc19LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 20, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) return fromExcel;
        } catch (Throwable e) {
            log.warn("Could not read TC19 loginId from Excel (row 20, col 13): {}", e.getMessage());
        }
        if (tc19LoginId != null && !tc19LoginId.isEmpty()) return tc19LoginId;
        throw new RuntimeException("TC19 loginId not found in Excel (row 20, col 13) and tc19LoginId is not set.");
    }

    // Reads Excel row 5 col 13 (written by TC4's create step).
    // Falls back to in-memory tc4LoginId if the cell is empty.
    private static String resolveTc4LoginId() {
        try {
            String fromExcel = new ExcelUtility().getDataFromExcel("User", 5, 13);
            if (fromExcel != null && !fromExcel.isEmpty()) {
                return fromExcel;
            }
        } catch (Throwable e) {
            log.warn("Could not read TC4 loginId from Excel (row 5, col 13): {}", e.getMessage());
        }
        if (tc4LoginId != null && !tc4LoginId.isEmpty()) {
            log.warn("Excel cell empty — falling back to in-memory tc4LoginId: {}", tc4LoginId);
            return tc4LoginId;
        }
        throw new RuntimeException(
                "TC4 loginId not found in Excel (User sheet row 5, col 13) and tc4LoginId is not set.");
    }

    // ── TC1 / TC2 — Create User ───────────────────────────────────────────────

    @Given("open the browser and enter the Url")
    public void open_the_browser_and_enter_the_url() {
        log.debug("Browser setup handled in Hook");
    }

    @When("navigate to organization and click on users tab")
    public void navigate_to_organization_and_click_on_users_tab() {
        Hook.base().shDriver.click(Pages.getHomePage().getOrganizationDropdown(), "organization dropdown");
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getUserBtn(), "user tab");
    }

    @When("navigate to organization and click on create new user tab")
    public void navigate_to_organization_and_click_on_create_new_user_tab() {
        Hook.base().shDriver.click(Pages.getHomePage().getOrganizationDropdown(), "organization dropdown");
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getCreateUserTab(), "create user tab");
    }

    @When("click on create a user button")
    public void click_on_create_a_user_button() {
        Hook.base().shDriver.click(Pages.getUserPage().getCreateUserBtn(), "create user button");
    }

    @Given("enter all the required user creation details")
    public void enter_all_the_required_user_creation_details() throws Throwable {
        var u = TestDataManager.getUserData();

        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        createdLoginId = loginId;
        log.info("Creating user with loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(), jLib.getRandomNumber() + u.get("EmailId"),
                "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 2, 13, loginId);
            log.info("CreatedLoginId '{}' written to Excel", loginId);
        } catch (Throwable e) {
            log.warn("Could not write createdLoginId to Excel: {}", e.getMessage());
        }
    }

    @When("click on create button")
    public void click_on_create_button() {
        Hook.base().shDriver.click(Pages.getCreateUserPage().getCreateSubmitBtn(), "create submit button");
    }

    @Then("validate user created successfully")
    public void validate_user_created_successfully() {
        Locator successMsg = Pages.getCreateUserPage().getUserCreationSuccessMsgTxt();
        wLib.waitForElementVisibility(Hook.base().page, successMsg);
        Assert.assertEquals(successMsg.textContent(), "Success");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC11 / TC12 — Create User with Invalid Inputs ────────────────────────

    @Given("enter all the required user creation details with invalid inputs")
    public void enter_all_the_required_user_creation_details_with_invalid_inputs() throws Throwable {
        var u = TestDataManager.getUserData();
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(), u.get("FirstName"), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(), u.get("LastName"), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(), "invalid-email", "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), "", "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), "123", "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), "456", "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");
    }

    @Then("validate user creation failed with validation errors")
    public void validate_user_creation_failed_with_validation_errors() {
        WaitUtils.pause(WaitUtils.MEDIUM);
        Locator successMsg = Pages.getCreateUserPage().getUserCreationSuccessMsgTxt();
        Assert.assertFalse(successMsg.isVisible(),
                "User should NOT be created with invalid inputs — success toast must not appear");
        log.info("Validation confirmed: no success message after invalid submission");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC5 — Deactivate User ─────────────────────────────────────────────────

    @And("select the user and click on deactivate button")
    public void select_the_user_and_click_on_deactivate_button() throws Throwable {
        String loginId = resolveLoginId();
        log.info("Deactivating user with loginId: {}", loginId);
        searchAndSelectUser(loginId);
        wLib.scrollToTop(Hook.base().page);
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserStatusDropdownBtn(), "status dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserDeactivateLinkBtn(), "deactivate link");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserDeactiveConfirmBtn(), "deactivate confirm");
    }

    @Then("validate user deactivated successfully")
    public void validate_user_deactivated_successfully() throws Throwable {
        String loginId = resolveLoginId();
        searchUser(loginId);
        Locator userStatus = Pages.getUserPage().getUserStatusByLoginId(loginId);
        wLib.waitForElementVisibility(Hook.base().page, userStatus);
        Assert.assertEquals(userStatus.textContent().trim(), "Inactive");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC6 — Activate User ───────────────────────────────────────────────────

    @And("select the user and click on activate button")
    public void select_the_user_and_click_on_activate_button() throws Throwable {
        String loginId = resolveLoginId();
        log.info("Activating user with loginId: {}", loginId);
        searchAndSelectUser(loginId);
        wLib.scrollToTop(Hook.base().page);
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserStatusDropdownBtn(), "status dropdown");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserActivateLinkBtn(), "activate link");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getUserPage().getUserActiveConfirmBtn(), "activate confirm");
    }

    @Then("validate user activated successfully")
    public void validate_user_activated_successfully() throws Throwable {
        String loginId = resolveLoginId();
        searchUser(loginId);
        Locator userStatus = Pages.getUserPage().getUserStatusByLoginId(loginId);
        wLib.waitForElementVisibility(Hook.base().page, userStatus);
        Assert.assertEquals(userStatus.textContent().trim(), "Active");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC7 — Change Password ─────────────────────────────────────────────────

    @And("select the user and click on change password button")
    public void select_the_user_and_click_on_change_password_button() throws Throwable {
        String loginId = resolveLoginId();
        log.info("Changing password for loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getChangePasswordBtn(), "change password button");
    }

    @And("update the password and click on apply password button")
    public void update_the_password_and_click_on_apply_password_button() throws Throwable {
        var u = TestDataManager.getUserData();
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        Hook.base().shDriver.fill(Pages.getUserPage().getChangepasswordTxt(), u.get("Password"), "new password");
        Hook.base().shDriver.fill(Pages.getUserPage().getChangeConfirmpasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");
        Hook.base().shDriver.click(Pages.getUserPage().getApplyPasswordBtn(), "apply password button");
    }

    @Then("validate password changed successfully")
    public void validate_password_changed_successfully() {
        Locator successMsg = Pages.getUserPage().getPasswordUpdatedconfirmMsg();
        wLib.waitForElementVisibility(Hook.base().page, successMsg);
        Assert.assertTrue(successMsg.isVisible(), "Password updated message should be visible");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC8 — Add User to Team ────────────────────────────────────────────────

     @And("select the user and click on add to teams button")
     public void select_the_user_and_click_on_add_to_teams_button() throws Throwable {
         String loginId = resolveLoginId();
         log.info("Adding TC1 user to team, loginId: {}", loginId);
         searchAndSelectUser(loginId);
         Hook.base().shDriver.click(Pages.getUserPage().getAddUserIntoTeamBtn(), "add to teams button");
     }

     @And("select a team and click on add button")
     public void select_a_team_and_click_on_add_button() throws Throwable {
         String teamName = TestDataManager.getUserData().get("TeamName");

         Hook.base().page.waitForLoadState();
         WaitUtils.pause(1500);
         wLib.scrollToTop(Hook.base().page);
         WaitUtils.pause(WaitUtils.SHORT);

         Locator select2Container = Hook.base().page.locator("#customTeamContainer .select2-selection");
         wLib.waitForElementVisibility(Hook.base().page, select2Container);
         select2Container.click();
         WaitUtils.pause(1500);

         Locator searchField = Hook.base().page.locator(".select2-search__field");
         wLib.waitForElementVisibility(Hook.base().page, searchField);
         searchField.fill(teamName);
         WaitUtils.pause(1500);

         Locator teamOption = Hook.base().page.locator(".select2-results__option")
                 .filter(new Locator.FilterOptions().setHasText(teamName));
         wLib.waitForElementVisibility(Hook.base().page, teamOption);
         teamOption.click();
         WaitUtils.pause(WaitUtils.MEDIUM);

         Hook.base().shDriver.click(Pages.getAddToTeamModalPage().getAddButton(), "add button");
     }

    @Then("validate user added to team successfully")
    public void validate_user_added_to_team_successfully() {
        Locator successMsg = Pages.getUserPage().getUseraddedIntoTeamsuccessMsg();
        wLib.waitForElementVisibility(Hook.base().page, successMsg);
        Assert.assertEquals(successMsg.textContent().trim(), "Success");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC9 — View User Details ───────────────────────────────────────────────

    @And("select the user and click on view details")
    public void select_the_user_and_click_on_view_details() throws Throwable {
        String loginId = resolveLoginId();
        log.info("Viewing details for loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserViewBtn(), "view details button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @Then("validate the user details are displayed correctly")
    public void validate_the_user_details_are_displayed_correctly() {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        // View navigates to a full "User Details" page (title = "User Details | Nuvepro")
        String pageTitle = Hook.base().page.title();
        Assert.assertTrue(pageTitle.contains("User Details"),
                "Expected page title to contain 'User Details' but was: " + pageTitle);
        log.info("User details page displayed — title: {}", pageTitle);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC3 — Edit User ───────────────────────────────────────────────────────

    @And("enter user creation details for edit test")
    public void enter_user_creation_details_for_edit_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        tc3LoginId = loginId;
        log.info("Creating TC3 user for edit, loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(),
                jLib.getRandomNumber() + u.get("EmailId"), "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 4, 13, loginId);
            log.info("TC3 loginId '{}' written to Excel (row 4, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC3 loginId to Excel: {}", e.getMessage());
        }
    }

    @And("select the user and click on edit button")
    public void select_the_user_and_click_on_edit_button() throws Throwable {
        String loginId = resolveTc3LoginId();
        log.info("Editing user with loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserEditBtn(), "edit button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("edit the required inputs")
    public void edit_the_required_inputs() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);

        for (String id : new String[]{"#first_name", "#last_name", "#email"}) {
            Hook.base().page.locator(id).click();
            Hook.base().page.locator(id).press("Control+a");
            Hook.base().page.locator(id).press("Delete");
        }

        var u = TestDataManager.getUserData();
        Hook.base().page.locator("#first_name").fill(u.get("UpdateFirstName") + jLib.getRandomNumber());
        Hook.base().page.locator("#last_name").fill(u.get("UpdateLastName") + jLib.getRandomNumber());
        Hook.base().page.locator("#email").fill(jLib.getRandomNumber() + u.get("UpdateEmail"));
        Hook.base().page.locator("#employee_id").fill(u.get("UpdateEmployeeId") + jLib.getRandomNumber());
        log.info("Edit form filled with TC3 update values from row 4");
    }

    @When("click on save button")
    public void click_on_save_button() {
        Hook.base().shDriver.click(Pages.getEditUserPage().getSaveBtn(), "save button");
    }

    @Then("validate the user details are updated")
    public void validate_the_user_details_are_updated() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        // After save, app redirects back to the users list with the search filter still active.
        // No success toast — validate by checking the updated first name is visible in the row.
        String loginId = resolveTc3LoginId();
        String expectedFirstName = TestDataManager.getUserData().get("UpdateFirstName");
        Locator firstNameCell = Hook.base().page.locator(
                "//table[@id='usersListTable']//tr[td[normalize-space()='" + loginId + "']]//td[5]");
        wLib.waitForElementVisibility(Hook.base().page, firstNameCell);
        Assert.assertTrue(firstNameCell.textContent().contains(expectedFirstName),
                "First name should contain '" + expectedFirstName + "' but was: " + firstNameCell.textContent());
        log.info("TC3 edit validated — first name '{}' contains '{}'", firstNameCell.textContent(), expectedFirstName);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC4 — Remove User ────────────────────────────────────────────────────

    @And("enter user creation details for deletion test")
    public void enter_user_creation_details_for_deletion_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        tc4LoginId = loginId;
        log.info("Creating TC4 user for deletion, loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(),
                jLib.getRandomNumber() + u.get("EmailId"), "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 5, 13, loginId);
            log.info("TC4 loginId '{}' written to Excel (row 5, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC4 loginId to Excel: {}", e.getMessage());
        }
    }

    @When("select the user and click on remove button")
    public void select_the_user_and_click_on_remove_button() throws Throwable {
        String loginId = resolveTc4LoginId();
        log.info("Removing user with loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserRemoveBtn(), "remove button");
    }

    @And("click on delete button to confirm")
    public void click_on_delete_button_to_confirm() {
        Hook.base().shDriver.click(Pages.getUserPage().getUserDeleteBtn(), "delete button");
    }

    @Then("validate the user details are deleted")
    public void validate_the_user_details_are_deleted() {
        Locator deleteSuccessMsg = Pages.getUserPage().getDeleteSuccessMsg();
        wLib.waitForElementVisibility(Hook.base().page, deleteSuccessMsg);
        Assert.assertEquals(deleteSuccessMsg.textContent(), "Success");
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC10 — Change Login ID ────────────────────────────────────────────────

    @And("enter user creation details for login id change test")
    public void enter_user_creation_details_for_login_id_change_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        tc10LoginId = loginId;
        log.info("Creating TC10 user for login id change, loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(),
                jLib.getRandomNumber() + u.get("EmailId"), "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 11, 13, loginId);
            log.info("TC10 loginId '{}' written to Excel (row 11, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC10 loginId to Excel: {}", e.getMessage());
        }
    }

    @And("select the user and click on edit button for login id change")
    public void select_the_user_and_click_on_edit_button_for_login_id_change() throws Throwable {
        String loginId = resolveTc10LoginId();
        log.info("Opening edit page for TC10 user, loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserEditBtn(), "edit button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("clear the old login id and update with new login id")
    public void clear_the_old_login_id_and_update_with_new_login_id() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        String newLoginId = TestDataManager.getUserData().get("Tc10NewLoginId") + jLib.getRandomNumber();
        tc10NewLoginId = newLoginId;
        Locator loginIdField = Hook.base().page.locator("#loginId");
        wLib.waitForElementVisibility(Hook.base().page, loginIdField);
        // Triple-click selects all existing text in the field without triggering
        // page-level keyboard shortcuts (avoids accidental form submission)
        loginIdField.click(new Locator.ClickOptions().setClickCount(3));
        WaitUtils.pause(WaitUtils.SHORT);
        loginIdField.fill(newLoginId);
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Login ID updated to '{}'", newLoginId);
    }

    @Then("validate the login id is changed successfully")
    public void validate_the_login_id_is_changed_successfully() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        String newLoginId = tc10NewLoginId;
        // Search by new loginId — fills the search box (clearing the old value) and executes search
        searchUser(newLoginId);
        // Verify the updated loginId row is present in the table
        Locator loginIdCell = Hook.base().page.locator(
                "//table[@id='usersListTable']//tr[td[normalize-space()='" + newLoginId + "']]//td[3]");
        wLib.waitForElementVisibility(Hook.base().page, loginIdCell);
        Assert.assertTrue(loginIdCell.textContent().trim().equals(newLoginId),
                "Expected loginId '" + newLoginId + "' in table but found: " + loginIdCell.textContent().trim());
        log.info("TC10 login ID change validated — '{}' found in users table", newLoginId);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC13 — Change User Role ───────────────────────────────────────────────

    @And("select the user and click on edit button for role change")
    public void select_the_user_and_click_on_edit_button_for_role_change() throws Throwable {
        String loginId = resolveLoginId();
        log.info("Opening edit page for role change, loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserEditBtn(), "edit button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("update the user role")
    public void update_the_user_role() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        wLib.scrollToTop(Hook.base().page);
        String newRole = TestDataManager.getUserData().get("Tc13NewRole");
        if (newRole != null && !newRole.isBlank()) {
            Hook.base().page.locator("label")
                    .filter(new Locator.FilterOptions().setHasText(newRole))
                    .first().click();
        }
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Role updated to '{}'", newRole);
    }

    @Then("validate the role is changed successfully")
    public void validate_the_role_is_changed_successfully() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        String loginId = resolveLoginId();
        String newRole = TestDataManager.getUserData().get("Tc13NewRole");
        searchUser(loginId);
        Locator roleCell = Pages.getUserPage().getUserRoleByLoginId(loginId);
        wLib.waitForElementVisibility(Hook.base().page, roleCell);
        Assert.assertTrue(roleCell.textContent().trim().contains(newRole.trim()),
                "Expected role '" + newRole.trim() + "' in table but found: " + roleCell.textContent().trim());
        log.info("TC13 role change validated — '{}' found in users table", newRole);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC14 — Change Email ID ────────────────────────────────────────────────

    @And("enter user creation details for email id change test")
    public void enter_user_creation_details_for_email_id_change_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        tc14LoginId = loginId;
        log.info("Creating TC14 user for email id change, loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(),
                jLib.getRandomNumber() + u.get("EmailId"), "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 15, 13, loginId);
            log.info("TC14 loginId '{}' written to Excel (row 15, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC14 loginId to Excel: {}", e.getMessage());
        }
    }

    @And("select the user and click on edit button for email id change")
    public void select_the_user_and_click_on_edit_button_for_email_id_change() throws Throwable {
        String loginId = resolveTc14LoginId();
        log.info("Opening edit page for TC14 user, loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getUserEditBtn(), "edit button");
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("clear the old email id and update with new email id")
    public void clear_the_old_email_id_and_update_with_new_email_id() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        // Random number prepended to email base (e.g. "4832base@gmail.com")
        String newEmailId = jLib.getRandomNumber() + TestDataManager.getUserData().get("Tc14NewEmailId");
        tc14NewEmailId = newEmailId;
        Locator emailField = Hook.base().page.locator("#email");
        wLib.waitForElementVisibility(Hook.base().page, emailField);
        emailField.click(new Locator.ClickOptions().setClickCount(3));
        WaitUtils.pause(WaitUtils.SHORT);
        emailField.fill(newEmailId);
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("Email ID updated to '{}'", newEmailId);
    }

    @Then("validate the email id is changed successfully")
    public void validate_the_email_id_is_changed_successfully() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        String newEmailId = tc14NewEmailId;
        // Search by the unchanged loginId to locate the user row
        searchUser(resolveTc14LoginId());
        // Verify td[4] (email column) contains the newly updated email
        Locator emailCell = Hook.base().page.locator(
                "//table[@id='usersListTable']//tr[td[normalize-space()='" + resolveTc14LoginId() + "']]//td[4]");
        wLib.waitForElementVisibility(Hook.base().page, emailCell);
        Assert.assertTrue(emailCell.textContent().trim().contains(newEmailId),
                "Expected email '" + newEmailId + "' in table but found: " + emailCell.textContent().trim());
        log.info("TC14 email change validated — '{}' found in users table", newEmailId);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC15 / TC16 — Import Users ───────────────────────────────────────────

    @When("navigate to organization and click on Import Users")
    public void navigate_to_organization_and_click_on_import_users() {
        Hook.base().shDriver.click(Pages.getHomePage().getOrganizationDropdown(), "organization dropdown");
        Hook.base().shDriver.click(Pages.getOrganizationDropdownPage().getImportUserTab(), "import users tab");
    }

    @And("click on import button")
    public void click_on_import_button() {
        Hook.base().shDriver.click(Pages.getUserPage().getImportUserBtn(), "import button");
    }

    @And("select the CSV file from local system")
    public void select_the_csv_file_from_local_system() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        java.nio.file.Path csvPath = generateImportCsv();
        Pages.getImportUserPage().getFileToUpload().setInputFiles(csvPath);
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("provide the import details")
    public void provide_the_import_details() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        wLib.scrollToTop(Hook.base().page);
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @And("click on importsubmit button")
    public void click_on_importsubmit_button() {
        Hook.base().shDriver.click(Pages.getImportUserPage().getImportSubmitBtn(), "import submit button");
    }

    @Then("validate the users are imported successfully")
    public void validate_the_users_are_imported_successfully() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.LONG);
        Locator batchTable = Pages.getImportUserPage().getBatchSummaryTable();
        wLib.waitForElementVisibility(Hook.base().page, batchTable);
        int successCount = Hook.base().page
                .locator("#batchSummaryTable tbody tr td:has-text('Success')").count();
        int totalRows = Hook.base().page
                .locator("#batchSummaryTable tbody tr").count();
        Assert.assertTrue(successCount == totalRows && successCount > 0,
                "Expected all rows to show 'Success'. Success: " + successCount + ", Total: " + totalRows);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC17 — Search User by Email ───────────────────────────────────────────

    @And("enter user creation details for email search test")
    public void enter_user_creation_details_for_email_search_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        String email   = jLib.getRandomNumber() + u.get("EmailId");
        tc17LoginId = loginId;
        tc17Email   = email;
        log.info("Creating TC17 user for email search, loginId: {}, email: {}", loginId, email);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(), email, "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 18, 13, loginId);
            log.info("TC17 loginId '{}' written to Excel (row 18, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC17 loginId to Excel: {}", e.getMessage());
        }
    }

    @And("search the user by email")
    public void search_the_user_by_email() throws Throwable {
        Hook.base().shDriver.fill(Pages.getUserPage().getSearchInputBox(), tc17Email, "search input");
        Hook.base().shDriver.click(Pages.getUserPage().getSearchBtn(), "search button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @Then("validate user found by email search")
    public void validate_user_found_by_email_search() throws Throwable {
        Locator emailCell = Hook.base().page.locator(
                "//table[@id='usersListTable']//td[contains(normalize-space(),'" + tc17Email + "')]");
        wLib.waitForElementVisibility(Hook.base().page, emailCell);
        Assert.assertTrue(emailCell.isVisible(),
                "Expected email '" + tc17Email + "' to appear in search results");
        log.info("TC17 email search validated — '{}' found in table", tc17Email);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── TC18 — Add User to All Teams ──────────────────────────────────────────

    @And("select all teams and click on add button")
    public void select_all_teams_and_click_on_add_button() throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        wLib.scrollToTop(Hook.base().page);
        Hook.base().shDriver.click(Pages.getAddToTeamModalPage().getAllTeamsRadioBtn(), "all teams radio");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getAddToTeamModalPage().getAddButton(), "add button");
    }

    // ── TC19 — Add User to Team as Team Admin ─────────────────────────────────

    @And("enter user creation details for team admin test")
    public void enter_user_creation_details_for_team_admin_test() throws Throwable {
        var u = TestDataManager.getUserData();
        String loginId = u.get("LoginId") + jLib.getRandomNumber();
        tc19LoginId = loginId;
        log.info("Creating TC19 user for team admin test, loginId: {}", loginId);

        Hook.base().shDriver.fill(Pages.getCreateUserPage().getFirstNameTxt(),
                u.get("FirstName") + jLib.getRandomNumber(), "first name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLastNameTxt(),
                u.get("LastName") + jLib.getRandomNumber(), "last name");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmailIdTxt(),
                jLib.getRandomNumber() + u.get("EmailId"), "email id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getEmployeeIdTxt(), u.get("EmployeeId"), "employee id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getLoginIdTxt(), loginId, "login id");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getPasswordTxt(), u.get("Password"), "password");
        Hook.base().shDriver.fill(Pages.getCreateUserPage().getConfirmPasswordTxt(), u.get("ConfirmPassword"),
                "confirm password");

        wLib.scrollToTop(Hook.base().page);
        String role = u.get("UserRole");
        Hook.base().shDriver.click(Hook.base().page.locator("//label[contains(text(),'" + role + "')]"), "role");

        try {
            new ExcelUtility().setDataToExcel("User", 20, 13, loginId);
            log.info("TC19 loginId '{}' written to Excel (row 20, col 13)", loginId);
        } catch (Throwable e) {
            log.warn("Could not write TC19 loginId to Excel: {}", e.getMessage());
        }
    }

    @And("select the user and click on add to teams button for team admin")
    public void select_the_user_and_click_on_add_to_teams_button_for_team_admin() throws Throwable {
        String loginId = resolveTc19LoginId();
        log.info("Adding TC19 user to team as admin, loginId: {}", loginId);
        searchAndSelectUser(loginId);
        Hook.base().shDriver.click(Pages.getUserPage().getAddUserIntoTeamBtn(), "add to teams button");
    }

    @And("select a team and add user as team admin")
    public void select_a_team_and_add_user_as_team_admin() throws Throwable {
        String teamName = TestDataManager.getUserData().get("TeamName");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(1500);
        wLib.scrollToTop(Hook.base().page);
        WaitUtils.pause(WaitUtils.SHORT);

        Locator select2Container = Hook.base().page.locator("#customTeamContainer .select2-selection");
        wLib.waitForElementVisibility(Hook.base().page, select2Container);
        select2Container.click();
        WaitUtils.pause(1500);

        Locator searchField = Hook.base().page.locator(".select2-search__field");
        wLib.waitForElementVisibility(Hook.base().page, searchField);
        searchField.fill(teamName);
        WaitUtils.pause(1500);

        Locator teamOption = Hook.base().page.locator(".select2-results__option")
                .filter(new Locator.FilterOptions().setHasText(teamName));
        wLib.waitForElementVisibility(Hook.base().page, teamOption);
        teamOption.click();
        WaitUtils.pause(WaitUtils.SHORT);

        Hook.base().shDriver.click(Pages.getAddToTeamModalPage().getTeamAdminRadioBtn(), "team admin radio");
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(Pages.getAddToTeamModalPage().getAddButton(), "add button");
    }

    // ── TC20 — Login with Created User ────────────────────────────────────────

    @And("login with the created user credentials")
    public void login_with_the_created_user_credentials() throws Throwable {
        String loginId  = resolveLoginId();
        String password = TestDataManager.getUserData().get("Password");
        log.info("Logging in with created user loginId: {}", loginId);
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), loginId, "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), password, "password");
        Hook.base().shDriver.click(Pages.getLoginPage().getSignInBtn(), "sign in button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @Then("validate the home page is displayed for created user")
    public void validate_the_home_page_is_displayed_for_created_user() {
        String currentUrl = Hook.base().page.url();
        Assert.assertFalse(currentUrl.toLowerCase().contains("login"),
                "Expected dashboard page after login but URL is: " + currentUrl);
        log.info("TC20 validated — created user logged in successfully, URL: {}", currentUrl);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    // Search only — used by validation steps to bring the user row into view.
    private void searchUser(String loginId) throws Throwable {
        Hook.base().shDriver.click(Pages.getUserPage().getSearchItemDisplay(), "search item display");
        Hook.base().shDriver.click(Pages.getUserPage().getSearchDropdownOption("name"), "search by name");
        Hook.base().shDriver.fill(Pages.getUserPage().getSearchInputBox(), loginId, "search input");
        Hook.base().shDriver.click(Pages.getUserPage().getSearchBtn(), "search button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    // Search + select checkbox — used by action steps before clicking a toolbar
    // button.
    private void searchAndSelectUser(String loginId) throws Throwable {
        searchUser(loginId);
        Locator checkbox = Pages.getUserPage().getSelectUserCheckBoxByLoginId(loginId);
        wLib.waitForElementVisibility(Hook.base().page, checkbox);
        checkbox.click();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().page.waitForLoadState();
    }

    // Reads the CSV template, appends a timestamp-based suffix to each row's
    // LOGIN ID and E-MAIL username so every run produces unique users.
    // Template columns: FIRST NAME, LAST NAME, E-MAIL, PASSWORD, EXTERNAL ID, LOGIN ID
    private static java.nio.file.Path generateImportCsv() throws Exception {
        // 6-digit suffix unique to this call (last 6 digits of epoch millis)
        String ts = String.valueOf(System.currentTimeMillis());
        String suffix = ts.substring(ts.length() - 6);

        java.nio.file.Path template = java.nio.file.Paths.get(
                System.getProperty("user.dir"), "src/test/resources/users_template(in).csv");
        java.nio.file.Path temp = java.nio.file.Paths.get(
                System.getProperty("user.dir"), "src/test/resources/users_import_temp.csv");

        java.util.List<String> lines = java.nio.file.Files.readAllLines(
                template, java.nio.charset.StandardCharsets.UTF_8);
        java.util.List<String> output = new java.util.ArrayList<>();
        output.add(lines.get(0)); // header row — unchanged

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            String[] cols = line.split(",");
            // col[2] = E-MAIL: append suffix to the username part (before @)
            String email = cols[2].trim();
            int at = email.indexOf("@");
            if (at > 0) {
                cols[2] = email.substring(0, at) + suffix + email.substring(at);
            }
            // col[5] = LOGIN ID: append suffix
            cols[5] = cols[5].trim() + suffix;
            output.add(String.join(",", cols));
        }

        java.nio.file.Files.write(temp, output, java.nio.charset.StandardCharsets.UTF_8);
        log.info("Import CSV generated — suffix '{}', file: {}", suffix, temp);
        return temp;
    }
}
