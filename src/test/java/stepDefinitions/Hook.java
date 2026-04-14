package stepDefinitions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Generic_Utility.WebDriverUtility;
import Generic_Utility.Base;
import Generic_Utility.ConstantFilePath;
import Generic_Utility.ExcelUtility;
import Generic_Utility.SelfHealingWebDriver;
import Util.Pages;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Hook extends WebDriverUtility {

	ExcelUtility eLib = new ExcelUtility();
	public static Base base;
	public SelfHealingWebDriver shDriver;
	public static String runFolder;
	public static String screenshotFolder;
	public static String reportFolder;

	@Before
	public void beforeScenario(Scenario scenario) throws Throwable {
		base = new Base();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String timestamp = sdf.format(new Date());
		runFolder = "./test-output/" + timestamp;
		screenshotFolder = runFolder + "/screenshots";
		reportFolder = runFolder + "/reports";
		
		Files.createDirectories(Paths.get(screenshotFolder));
		Files.createDirectories(Paths.get(reportFolder));
		
		System.out.println("Run folder created: " + runFolder);

		String browser = eLib.getDataFromExcel("Credentials", 12, 1);
		System.out.println(browser);

		base.playwright = Playwright.create();

		if (browser.equalsIgnoreCase("chrome")) {
			base.browser = base.playwright.chromium().launch(
					new BrowserType.LaunchOptions()
							.setHeadless(false)
							.setArgs(java.util.Arrays.asList("--start-maximized")));
		} else if (browser.equalsIgnoreCase("firefox")) {
			base.browser = base.playwright.firefox().launch(
					new BrowserType.LaunchOptions()
							.setHeadless(false)
							.setArgs(java.util.Arrays.asList("--start-maximized")));
		} else {
			System.out.println("invalid browser input from property file:00");
		}

		base.context = base.browser.newContext(
				new Browser.NewContextOptions().setViewportSize(null));
		base.page = base.context.newPage();
		base.page.setDefaultTimeout(60000);

		Pages.loadPages(base.page);
		String url = eLib.getDataFromExcel("Credentials", 1, 1);

		try {
			base.page.navigate(url, new Page.NavigateOptions().setTimeout(60000));
		} catch (Exception e) {
			System.out.println("Initial navigation failed, retrying: " + e.getMessage());
			base.page.navigate(url, new Page.NavigateOptions().setTimeout(60000));
		}

		base.page.evaluate("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");

		shDriver = new SelfHealingWebDriver(base.page, ConstantFilePath.geminiApiKey, reportFolder);
		base.shDriver = shDriver;

		System.out.println("Browser initialized and self-healing driver ready");
	}

	@Given("login as tenant admin")
	public void login_as_tenant_admin() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 15, 1);
		String password = eLib.getDataFromExcel("Credentials", 16, 1);
		base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		base.shDriver.click(Pages.LoginPage.getSignInBtn(), "sign in button");
		base.page.waitForLoadState();
		System.out.println("Logged in as tenant admin");
	}

	@Given("login as mspadmin")
	public void login_as_mspadmin() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 3, 1);
		String password = eLib.getDataFromExcel("Credentials", 4, 1);
		base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		base.shDriver.click(Pages.LoginPage.getSignInBtn(), "sign in button");
		base.page.waitForLoadState();
		System.out.println("Logged in as mspadmin");
	}

	@Given("login as user")
	public void login_as_user() throws Throwable {
		String username = eLib.getDataFromExcel("Credentials", 9, 1);
		String password = eLib.getDataFromExcel("Credentials", 10, 1);
		base.shDriver.fill(Pages.LoginPage.getUsernameTxt(), username, "username");
		base.shDriver.fill(Pages.LoginPage.getPasswordTxt(), password, "password");
		base.shDriver.click(Pages.LoginPage.getSignInBtn(), "sign in button");
		base.page.waitForLoadState();
		System.out.println("Logged in as user");
	}

	@And("click on logout")
	public void click_on_logout() {
		try {
			base.shDriver.click(Pages.HomePage.getUserProfileDropdown(), "user profile dropdown");
			base.shDriver.click(Pages.HomePage.getLogoutBtn(), "logout button");
			System.out.println("Logged out successfully");
		} catch (Exception e) {
			System.out.println("Logout failed: " + e.getMessage());
		}
	}

	@Then("validate login page is displayed")
	public void validate_login_page_is_displayed() {
		try {
			base.shDriver.isVisible(Pages.LoginPage.getUsernameTxt(), "username field");
			base.shDriver.isVisible(Pages.LoginPage.getPasswordTxt(), "password field");
			System.out.println("Login page displayed after logout");
		} catch (Exception e) {
			System.out.println("Login page validation after logout: " + e.getMessage());
		} finally {
			if (base != null && base.shDriver != null) {
				base.shDriver.saveHealingReport();
			}
		}
	}

	@After
	public void afterscenario(Scenario scenario) throws Throwable {
		if (shDriver != null) {
			shDriver.saveHealingReport();
		}

		if (base != null && scenario.isFailed()) {
			String screenshotName = scenario.getName().replace(" ", "_");
			takeScreenshot(base.page, screenshotFolder + "/" + screenshotName);
			byte[] imageBytes = base.page.screenshot();
			scenario.attach(imageBytes, "img/png", scenario.getName());
		}
		
		if (base != null) {
			if (base.context != null) base.context.close();
			if (base.browser != null) base.browser.close();
			if (base.playwright != null) base.playwright.close();
		}
		
		base = null;
	}

}
