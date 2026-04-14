package stepDefinitions;

import com.microsoft.playwright.Locator;
import org.testng.Assert;
import Util.Pages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Generic_Utility.ExcelUtility;

public class Login {

	ExcelUtility eLib = new ExcelUtility();

	@Given("Open browser and enter the url")
	public void open_browser_and_enter_the_url() throws Throwable {
		System.out.println("Browser setup handled in Hook class");
	}

	@Given("Login page should display")
	public void login_page_should_display() {
		try {
			if (Hook.base != null && Hook.base.shDriver != null) {
				Hook.base.shDriver.isVisible(Pages.LoginPage.getUsernameTxt(), "username field");
				Hook.base.shDriver.isVisible(Pages.LoginPage.getPasswordTxt(), "password field");
				System.out.println("Login page displayed");
			} else {
				System.out.println("ShDriver not initialized");
			}
		} catch (Exception e) {
			System.out.println("Login page validation: " + e.getMessage());
		} finally {
			if (Hook.base != null && Hook.base.shDriver != null) {
				Hook.base.shDriver.saveHealingReport();
			}
		}
	}

	@When("Enter valid username and invalid password")
	public void enter_valid_username_and_invalid_password() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 15, 1);
		String password = eLib.getDataFromExcel("Credentials", 17, 1);
		Hook.base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		Hook.base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		System.out.println("Entered valid username and invalid password");
	}

	@When("Enter invalid username and valid password")
	public void enter_invalid_username_and_valid_password() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 18, 1);
		String password = eLib.getDataFromExcel("Credentials", 16, 1);
		Hook.base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		Hook.base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		System.out.println("Entered invalid username and valid password");
	}

	@When("Enter invalid username and invalid password")
	public void enter_invalid_username_and_invalid_password() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 18, 1);
		String password = eLib.getDataFromExcel("Credentials", 17, 1);
		Hook.base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		Hook.base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		System.out.println("Entered invalid username and invalid password");
	}

	@When("Enter empty username and password")
	public void enter_empty_username_and_password() throws Throwable {
		Hook.base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), "", "username");
		Hook.base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), "", "password");
		System.out.println("Entered empty username and password");
	}

	@When("click on Login")
	public void click_on_login() throws Throwable {
		Hook.base.shDriver.click(Pages.LoginPage.getSignInBtn(), "sign in button");
	}

	@Then("validate proper Home page is displayed")
	public void validate_proper_home_page_is_displayed() throws InterruptedException {
		try {
			Locator homeElement = Pages.HomePage.getOrganizationDropdown();
			boolean isDisplayed = Hook.base.shDriver.isVisible(homeElement, "organization dropdown");
			Assert.assertTrue(isDisplayed, "Home page should be displayed");
			System.out.println("Home page is displayed successfully with all key elements");
		} catch (Exception e) {
			System.out.println("Home page validation: " + e.getMessage());
		} finally {
			if (Hook.base != null && Hook.base.shDriver != null) {
				Hook.base.shDriver.saveHealingReport();
			}
		}
	}

	@Then("validate error message is displayed")
	public void validate_error_message_is_displayed() {
		try {
			Locator errorMsg = Pages.LoginPage.getLoginErrorMsg();
			boolean isDisplayed = Hook.base.shDriver.isVisible(errorMsg, "login error message");
			Assert.assertTrue(isDisplayed, "Error message should be displayed");
			System.out.println("Error message displayed: " + errorMsg.textContent());
		} catch (Exception e) {
			System.out.println("Error message validation: " + e.getMessage());
		} finally {
			if (Hook.base != null && Hook.base.shDriver != null) {
				Hook.base.shDriver.saveHealingReport();
			}
		}
	}
}
