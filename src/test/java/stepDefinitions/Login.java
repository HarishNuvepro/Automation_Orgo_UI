package stepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import Generic_Utility.CredentialManager;
import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login {

    private static final Logger log = LoggerFactory.getLogger(Login.class);

    @Given("Open browser and enter the url")
    public void open_browser_and_enter_the_url() {
        log.debug("Browser setup handled in Hook");
    }

    @Given("Login page should display")
    public void login_page_should_display() {
        try {
            Hook.base().shDriver.isVisible(Pages.getLoginPage().getUsernameTxt(), "username field");
            Hook.base().shDriver.isVisible(Pages.getLoginPage().getPasswordTxt(), "password field");
        } catch (Exception e) {
            log.warn("Login page validation: {}", e.getMessage());
        } finally {
            if (Hook.base() != null && Hook.base().shDriver != null) {
                Hook.base().shDriver.saveHealingReport();
            }
        }
    }

    @When("Enter valid username and invalid password")
    public void enter_valid_username_and_invalid_password() throws Throwable {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), CredentialManager.getTenantAdminUsername(), "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), CredentialManager.getInvalidPassword(), "password");
    }

    @When("Enter invalid username and valid password")
    public void enter_invalid_username_and_valid_password() throws Throwable {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), CredentialManager.getInvalidUsername(), "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), CredentialManager.getTenantAdminPassword(), "password");
    }

    @When("Enter invalid username and invalid password")
    public void enter_invalid_username_and_invalid_password() throws Throwable {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), CredentialManager.getInvalidUsername(), "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), CredentialManager.getInvalidPassword(), "password");
    }

    @When("Enter empty username and password")
    public void enter_empty_username_and_password() {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), "", "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), "", "password");
    }

    @When("Enter empty username and valid password")
    public void enter_empty_username_and_valid_password() {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), "", "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), CredentialManager.getTenantAdminPassword(), "password");
    }

    @When("Enter valid username and empty password")
    public void enter_valid_username_and_empty_password() {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), CredentialManager.getTenantAdminUsername(), "username");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), "", "password");
    }

    @When("click on Login")
    public void click_on_login() {
        Hook.base().shDriver.click(Pages.getLoginPage().getSignInBtn(), "sign in button");
    }

    @Then("validate proper Home page is displayed")
    public void validate_proper_home_page_is_displayed() {
        try {
            Locator homeElement = Pages.getHomePage().getOrganizationDropdown();
            boolean isDisplayed = Hook.base().shDriver.isVisible(homeElement, "organization dropdown");
            Assert.assertTrue(isDisplayed, "Home page should be displayed");
        } catch (Exception e) {
            log.warn("Home page validation: {}", e.getMessage());
        } finally {
            if (Hook.base() != null && Hook.base().shDriver != null) {
                Hook.base().shDriver.saveHealingReport();
            }
        }
    }

    @Then("validate error message is displayed")
    public void validate_error_message_is_displayed() {
        try {
            Locator errorMsg = Pages.getLoginPage().getLoginErrorMsg();
            boolean isDisplayed = Hook.base().shDriver.isVisible(errorMsg, "login error message");
            Assert.assertTrue(isDisplayed, "Error message should be displayed");
            log.info("Error message displayed: {}", errorMsg.textContent());
        } catch (Exception e) {
            log.warn("Error message validation: {}", e.getMessage());
        } finally {
            if (Hook.base() != null && Hook.base().shDriver != null) {
                Hook.base().shDriver.saveHealingReport();
            }
        }
    }

    // ── L1 ────────────────────────────────────────────────────────────────────

    @When("enter credentials with special characters")
    public void enter_credentials_with_special_characters() throws Throwable {
        Hook.base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), "test@user! name#123", "username with special chars");
        Hook.base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), "pass@word! #$%^&*()", "password with special chars");
    }

    // ── L4 ────────────────────────────────────────────────────────────────────

    @Then("validate forgot password link is visible on login page")
    public void validate_forgot_password_link_is_visible_on_login_page() {
        boolean isVisible = Hook.base().shDriver.isVisible(
                Pages.getLoginPage().getForgotPasswordLink(), "forgot password link");
        Assert.assertTrue(isVisible, "Forgot password link should be visible on the login page");
    }

    @When("click on forgot password link")
    public void click_on_forgot_password_link() {
        Hook.base().shDriver.click(Pages.getLoginPage().getForgotPasswordLink(), "forgot password link");
        WaitUtils.pause(WaitUtils.LONG);
    }

    @Then("validate forgot password form is displayed")
    public void validate_forgot_password_form_is_displayed() {
        try {
            boolean formVisible = Hook.base().shDriver.isVisible(
                    Pages.getLoginPage().getResetLoginIdTxt(), "reset login ID field");
            Assert.assertTrue(formVisible, "Forgot password form should be displayed with login ID field");
            log.info("Forgot password form displayed successfully");
        } catch (Exception e) {
            log.warn("Forgot password form validation: {}", e.getMessage());
        } finally {
            if (Hook.base() != null && Hook.base().shDriver != null) {
                Hook.base().shDriver.saveHealingReport();
            }
        }
    }

    // ── L8 ────────────────────────────────────────────────────────────────────

    @Given("open browser and navigate to login page with redirect url param")
    public void open_browser_and_navigate_to_login_page_with_redirect_url_param() {
        String baseUrl = CredentialManager.getBaseUrl();
        String redirectUrl = baseUrl + (baseUrl.contains("?") ? "&" : "?") + "redirect=/home";
        try {
            Hook.base().page.navigate(redirectUrl, new Page.NavigateOptions().setTimeout(60000));
        } catch (Exception e) {
            log.warn("Redirect URL navigation retry: {}", e.getMessage());
            Hook.base().page.navigate(redirectUrl, new Page.NavigateOptions().setTimeout(60000));
        }
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
    }
}
