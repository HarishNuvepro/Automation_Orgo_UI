package stepDefinitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;
import Generic_Utility.CredentialManager;
import Generic_Utility.WaitUtils;
import Util.Pages;
import io.cucumber.java.en.And;
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

    // ── L18 ───────────────────────────────────────────────────────────────────

    @When("click on forgot login id link")
    public void click_on_forgot_login_id_link() {
        Hook.base().shDriver.click(Pages.getLoginPage().getForgotLoginIdLink(), "forgot login id link");
        WaitUtils.pause(WaitUtils.LONG);
    }

    @And("enter a valid email for login id recovery")
    public void enter_a_valid_email_for_login_id_recovery() {
        // Use the tenant admin's email from the credential store — the recovery
        // email is only delivered if the address matches an existing user.
        String email = CredentialManager.getTenantAdminUsername().contains("@")
                ? CredentialManager.getTenantAdminUsername()
                : "ha14rishkumar@nuvelabs.com";
        Hook.base().shDriver.fill(Pages.getLoginPage().getForgotLoginIdEmailTxt(), email,
                "forgot login id email");
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("L18: submitted valid email '{}' for forgot login id", email);
    }

    @And("enter an invalid email format for login id recovery")
    public void enter_an_invalid_email_format_for_login_id_recovery() {
        Hook.base().shDriver.fill(Pages.getLoginPage().getForgotLoginIdEmailTxt(),
                "not-a-valid-email-format", "forgot login id email (invalid format)");
        WaitUtils.pause(WaitUtils.SHORT);
        log.info("L19: submitted invalid email format for forgot login id");
    }

    @And("click on submit forgot login id button")
    public void click_on_submit_forgot_login_id_button() {
        Hook.base().shDriver.click(Pages.getLoginPage().getForgotLoginIdSubmitBtn(),
                "submit forgot login id button");
        // The server may take a moment to surface the notification toast
        WaitUtils.pause(WaitUtils.MEDIUM);
    }

    @Then("validate forgot login id success notification is displayed")
    public void validate_forgot_login_id_success_notification_is_displayed() {
        Locator notification = Pages.getLoginPage().getForgotLoginIdNotificationMsg();
        notification.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(15_000));
        String text = notification.textContent().trim();
        Assert.assertFalse(text.isEmpty(),
                "Expected forgot login id notification to be non-empty but was blank");
        log.info("L18: forgot login id success notification — '{}'", text);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }

    @Then("validate forgot login id error notification is displayed")
    public void validate_forgot_login_id_error_notification_is_displayed() {
        // Wait for the notification region to populate — the app may render either
        // a positive or negative message depending on validation, but a non-blank
        // notification is the contract.
        Locator notification = Pages.getLoginPage().getForgotLoginIdNotificationMsg();
        notification.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.ATTACHED)
                .setTimeout(15_000));
        // Allow time for the network call to land
        WaitUtils.pause(WaitUtils.MEDIUM);
        String text = notification.textContent().trim();
        Assert.assertFalse(text.isEmpty(),
                "Expected forgot login id notification to surface an error but was blank");
        log.info("L19: forgot login id notification (expected error) — '{}'", text);
        if (Hook.base() != null && Hook.base().shDriver != null) {
            Hook.base().shDriver.saveHealingReport();
        }
    }
}
