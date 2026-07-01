package stepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import Generic_Utility.JavaUtility;
import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class Roles {

    private static final Logger log = LoggerFactory.getLogger(Roles.class);
    private final JavaUtility jLib = new JavaUtility();

    private static final ThreadLocal<String> createdRoleName = new ThreadLocal<>();

    // ── Navigation ─────────────────────────────────────────────────────────────

    @When("navigate to organization and click on roles tab")
    public void navigate_to_organization_and_click_on_roles_tab() {
        openOrgDropdownAndClick(Pages.getOrganizationDropdownPage().getRoleTab(), "roles tab");
        WaitUtils.waitForNetworkIdle(Hook.base().page);
    }

    // ── TC1 — Create Role ───────────────────────────────────────────────────────

    @And("click on create role button")
    public void click_on_create_role_button() {
        WaitUtils.waitForVisible(Pages.getRolesPage().getCreateBtn(), WaitUtils.TWENTY_SECONDS);
        Hook.base().shDriver.click(Pages.getRolesPage().getCreateBtn(), "create role button");
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @And("enter the required role details")
    public void enter_the_required_role_details() {
        String roleName = "AutoRole_" + jLib.getRandomNumber();
        createdRoleName.set(roleName);
        log.info("Creating role: {}", roleName);
        Hook.base().shDriver.fill(Pages.getRolesPage().getRoleNameInput(), roleName, "role name");
        Hook.base().shDriver.fill(Pages.getRolesPage().getRoleDescriptionInput(), "Automated test role", "role description");
        Pages.getRolesPage().getRoleScopeDropdown().selectOption("company");
        WaitUtils.pause(WaitUtils.MEDIUM);
        // Select at least one permission — required for form submission
        Hook.base().shDriver.click(Pages.getRolesPage().getPermUsersView(), "permission: users view");
        WaitUtils.pause(WaitUtils.SHORT);
    }

    @When("click on create role submit button")
    public void click_on_create_role_submit_button() {
        Locator btn = Pages.getRolesPage().getCreateRoleSubmitBtn();
        btn.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(WaitUtils.TEN_SECONDS));
        btn.click(new Locator.ClickOptions().setNoWaitAfter(true));
    }

    @Then("validate role created successfully")
    public void validate_role_created_successfully() {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        String url = Hook.base().page.url();
        boolean redirectedToList = !url.contains("/create");
        if (!redirectedToList) {
            boolean successOnPage = (boolean) Hook.base().page.evaluate(
                    "() => document.body.innerText.toLowerCase().includes('success')");
            Assert.assertTrue(successOnPage,
                    "Role creation: expected redirect to /roles list or success message on page. URL: " + url);
        }
        log.info("Role '{}' created successfully. URL: {}", createdRoleName.get(), url);
        if (Hook.base().shDriver != null) Hook.base().shDriver.saveHealingReport();
    }

    // ── Shared helper ───────────────────────────────────────────────────────────

    private void openOrgDropdownAndClick(Locator menuItem, String label) {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.MEDIUM);
        for (int attempt = 1; attempt <= 3; attempt++) {
            Pages.getHomePage().getOrganizationDropdown().click(
                    new Locator.ClickOptions().setNoWaitAfter(true));
            try {
                menuItem.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5_000));
                break;
            } catch (Exception e) {
                if (attempt == 3) throw e;
                log.warn("Org dropdown did not reveal '{}' on attempt {}, retrying", label, attempt);
                WaitUtils.pause(WaitUtils.MEDIUM);
            }
        }
        menuItem.click();
        Hook.base().page.waitForLoadState();
    }
}
