package stepDefinitions;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import Generic_Utility.CredentialManager;
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
}
