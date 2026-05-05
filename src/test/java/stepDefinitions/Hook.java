package stepDefinitions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import Generic_Utility.Base;
import Generic_Utility.CredentialManager;
import Generic_Utility.ExecutionContext;
import Generic_Utility.ExcelUtility;
import Generic_Utility.SelfHealingWebDriver;
import Generic_Utility.TestDataManager;
import Generic_Utility.WebDriverUtility;
import Util.Pages;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hook extends WebDriverUtility {

    private static final Logger log = LoggerFactory.getLogger(Hook.class);

    // One Base per thread — no cross-thread sharing
    private static final ThreadLocal<Base> TL_BASE = new ThreadLocal<>();

    /** Returns the Base instance for the running thread. */
    public static Base base() {
        return TL_BASE.get();
    }

    @Before
    public void beforeScenario(Scenario scenario) throws Throwable {
        ExecutionContext.initialize();
        String reportFolder = ExecutionContext.getReportFolder();

        // Initialize test data once per suite run (AtomicBoolean guard inside)
        TestDataManager.initialize(new ExcelUtility());

        log.info("Run folder: {} | Thread: {}", ExecutionContext.getRunFolder(), Thread.currentThread().getName());

        Base b = new Base();

        String browser = CredentialManager.getBrowser();

        b.playwright = Playwright.create();

        if (browser.equalsIgnoreCase("chrome")) {
            b.browser = b.playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(java.util.Arrays.asList("--start-maximized")));
        } else if (browser.equalsIgnoreCase("firefox")) {
            b.browser = b.playwright.firefox().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(java.util.Arrays.asList("--start-maximized")));
        } else {
            log.warn("Unknown browser type: {}", browser);
        }

        b.context = b.browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        b.page    = b.context.newPage();
        b.page.setDefaultTimeout(60000);

        Pages.loadPages(b.page);

        String url = CredentialManager.getBaseUrl();
        try {
            b.page.navigate(url, new Page.NavigateOptions().setTimeout(60000));
        } catch (Exception e) {
            log.warn("Navigation retry: {}", e.getMessage());
            b.page.navigate(url, new Page.NavigateOptions().setTimeout(60000));
        }

        b.page.evaluate("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");

        b.shDriver = new SelfHealingWebDriver(b.page, CredentialManager.getGeminiApiKey(), reportFolder);

        TL_BASE.set(b);

        log.info("Browser ready | Thread: {}", Thread.currentThread().getName());
    }

    @Given("login as tenant admin")
    public void login_as_tenant_admin() throws Throwable {
        performLogin(CredentialManager.getTenantAdminUsername(), CredentialManager.getTenantAdminPassword());
    }

    @Given("login as mspadmin")
    public void login_as_mspadmin() throws Throwable {
        performLogin(CredentialManager.getMspAdminUsername(), CredentialManager.getMspAdminPassword());
    }

    @Given("login as sysadmin")
    public void login_as_sysadmin() throws Throwable {
        performLogin(CredentialManager.getSysAdminUsername(), CredentialManager.getSysAdminPassword());
    }

    @Given("login as user")
    public void login_as_user() throws Throwable {
        performLogin(CredentialManager.getUserUsername(), CredentialManager.getUserPassword());
    }

    // Handles both tenant behaviours:
    //   1. URL loads direct sign-in page  → use simple form (#sl_userName / #sl_password)
    //   2. URL loads home page            → click LOGIN link, wait for popup, type into #loginform fields
    private void performLogin(String username, String password) throws Throwable {
        base().page.waitForLoadState();
        Generic_Utility.WaitUtils.pause(Generic_Utility.WaitUtils.SHORT);
        if (Pages.getLoginPage().getUsernameTxt().isVisible()) {
            // Direct sign-in page
            base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), username, "username");
            base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), password, "password");
            base().shDriver.click(Pages.getLoginPage().getSignInBtn(), "sign in button");
        } else {
            // Home page — close current browser and launch a fresh one
            log.info("Home page detected — relaunching browser for clean login");
            if (base().context    != null) base().context.close();
            if (base().browser    != null) base().browser.close();
            if (base().playwright != null) base().playwright.close();

            Playwright pw = Playwright.create();
            base().playwright = pw;
            String browserName = CredentialManager.getBrowser();
            if (browserName.equalsIgnoreCase("firefox")) {
                base().browser = pw.firefox().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(false)
                                .setArgs(java.util.Arrays.asList("--start-maximized")));
            } else {
                base().browser = pw.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(false)
                                .setArgs(java.util.Arrays.asList("--start-maximized")));
            }
            base().context = base().browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
            Page newPage = base().context.newPage();
            newPage.setDefaultTimeout(60000);
            String url = CredentialManager.getBaseUrl();
            try {
                newPage.navigate(url, new Page.NavigateOptions().setTimeout(60000));
            } catch (Exception e) {
                log.warn("Fresh browser navigation retry: {}", e.getMessage());
                newPage.navigate(url, new Page.NavigateOptions().setTimeout(60000));
            }
            newPage.waitForLoadState();
            newPage.evaluate("window.moveTo(0, 0); window.resizeTo(screen.width, screen.height);");
            base().page = newPage;
            base().shDriver = new SelfHealingWebDriver(newPage, CredentialManager.getGeminiApiKey(), ExecutionContext.getReportFolder());
            Pages.loadPages(newPage);
            Generic_Utility.WaitUtils.pause(Generic_Utility.WaitUtils.SHORT);
            base().shDriver.fill(Pages.getLoginPage().getUsernameTxt(), username, "username");
            base().shDriver.fill(Pages.getLoginPage().getPasswordTxt(), password, "password");
            base().shDriver.click(Pages.getLoginPage().getSignInBtn(), "sign in button");
        }
        base().page.waitForLoadState();
    }

    @And("click on sysadmin logout")
    public void click_on_sysadmin_logout() {
        base().shDriver.click(Pages.getLoginPage().getSysAdminLogoutLink(), "sysadmin logout link");
        base().page.waitForLoadState();
    }

    @And("click on logout")
    public void click_on_logout() {
        try {
            base().shDriver.click(Pages.getHomePage().getUserProfileDropdown(), "user profile dropdown");
            base().shDriver.click(Pages.getHomePage().getLogoutBtn(), "logout button");
        } catch (Exception e) {
            log.warn("Logout failed: {}", e.getMessage());
        }
    }

    @Then("validate login page is displayed")
    public void validate_login_page_is_displayed() {
        try {
            base().page.waitForLoadState();
            Generic_Utility.WaitUtils.pause(Generic_Utility.WaitUtils.SHORT);
            if (!Pages.getLoginPage().getUsernameTxt().isVisible()) {
                // Tenant redirected to home page after logout — click LOGIN link to open sign-in form
                Locator loginLink = Pages.getLoginPage().getLoginDropdownBtn();
                if (loginLink.isVisible()) {
                    base().shDriver.click(loginLink, "login link");
                    Generic_Utility.WaitUtils.pause(Generic_Utility.WaitUtils.SHORT);
                }
                base().shDriver.isVisible(Pages.getLoginPage().getHeaderUsernameTxt(), "header username field");
            } else {
                // Tenant redirected directly to sign-in page
                base().shDriver.isVisible(Pages.getLoginPage().getUsernameTxt(), "username field");
                base().shDriver.isVisible(Pages.getLoginPage().getPasswordTxt(), "password field");
            }
        } catch (Exception e) {
            log.warn("Login page validation: {}", e.getMessage());
        } finally {
            if (base() != null && base().shDriver != null) {
                base().shDriver.saveHealingReport();
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) throws Throwable {
        Base b = base();

        if (b != null && b.shDriver != null) {
            b.shDriver.saveHealingReport();
        }

        if (b != null && scenario.isFailed()) {
            String screenshotName = scenario.getName().replace(" ", "_")
                    + "_" + Thread.currentThread().getName();
            takeScreenshot(b.page, ExecutionContext.getScreenshotFolder() + "/" + screenshotName);
            byte[] imageBytes = b.page.screenshot();
            scenario.attach(imageBytes, "img/png", scenario.getName());
        }

        if (b != null) {
            if (b.context    != null) b.context.close();
            if (b.browser    != null) b.browser.close();
            if (b.playwright != null) b.playwright.close();
        }

        Pages.remove();
        TL_BASE.remove();
    }
}
