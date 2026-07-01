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
        Generic_Utility.ScenarioBufferAppender.startScenario();
        Generic_Utility.RunSummaryWriter.startScenario();
        Generic_Utility.HealingCache.startScenario();
        ExecutionContext.initialize();
        String reportFolder = ExecutionContext.getReportFolder();

        // Initialize test data once per suite run (AtomicBoolean guard inside)
        TestDataManager.initialize(new ExcelUtility());

        log.info("Run folder: {} | Thread: {}", ExecutionContext.getRunFolder(), Thread.currentThread().getName());

        Base b = new Base();

        String browser = CredentialManager.getBrowser();
        boolean headless = Boolean.parseBoolean(System.getProperty("test.headless", "true"));

        b.playwright = Playwright.create();

        if (browser.equalsIgnoreCase("chrome")) {
            java.util.List<String> args = headless
                    ? java.util.Arrays.asList("--window-size=1920,1080")
                    : java.util.Arrays.asList("--start-maximized");
            b.browser = b.playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(headless)
                            .setArgs(args));
        } else if (browser.equalsIgnoreCase("firefox")) {
            b.browser = b.playwright.firefox().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(headless));
        } else {
            log.warn("Unknown browser type: {}", browser);
        }

        // Headed: null viewport — Playwright follows the maximized window size
        // Headless: fixed 1920x1080 — consistent CI rendering
        Browser.NewContextOptions ctxOptions = new Browser.NewContextOptions();
        if (headless) ctxOptions.setViewportSize(1920, 1080);
        else          ctxOptions.setViewportSize((com.microsoft.playwright.options.ViewportSize) null);
        b.context = b.browser.newContext(ctxOptions);
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

    @Given("login as GCP tenant admin")
    public void login_as_gcp_tenant_admin() throws Throwable {
        performLogin(CredentialManager.getGcpTenantAdminUsername(), CredentialManager.getGcpTenantAdminPassword());
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
            // Wait for the button to be clickable before proceeding — under parallel load
            // the page may still be initialising when fill completes
            Pages.getLoginPage().getSignInBtn().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(15_000));
            // Direct click — bypasses self-healing's 10s navigation-wait timeout.
            // waitForLoadState() below handles post-login navigation.
            Pages.getLoginPage().getSignInBtn().click(
                    new com.microsoft.playwright.Locator.ClickOptions().setNoWaitAfter(true));
        } else {
            // Home page — close current browser and launch a fresh one
            log.info("Home page detected — relaunching browser for clean login");
            if (base().context    != null) base().context.close();
            if (base().browser    != null) base().browser.close();
            if (base().playwright != null) base().playwright.close();

            Playwright pw = Playwright.create();
            base().playwright = pw;
            String browserName = CredentialManager.getBrowser();
            boolean headless = Boolean.parseBoolean(System.getProperty("test.headless", "true"));
            if (browserName.equalsIgnoreCase("firefox")) {
                base().browser = pw.firefox().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless));
            } else {
                java.util.List<String> relaunchArgs = headless
                        ? java.util.Arrays.asList("--window-size=1920,1080")
                        : java.util.Arrays.asList("--start-maximized");
                base().browser = pw.chromium().launch(
                        new BrowserType.LaunchOptions()
                                .setHeadless(headless)
                                .setArgs(relaunchArgs));
            }
            Browser.NewContextOptions relaunchCtx = new Browser.NewContextOptions();
            if (headless) relaunchCtx.setViewportSize(1920, 1080);
            else          relaunchCtx.setViewportSize((com.microsoft.playwright.options.ViewportSize) null);
            base().context = base().browser.newContext(relaunchCtx);
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
            Pages.getLoginPage().getSignInBtn().click(
                    new com.microsoft.playwright.Locator.ClickOptions().setNoWaitAfter(true));
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
            base().page.waitForLoadState();
            base().shDriver.click(Pages.getHomePage().getUserProfileDropdown(), "user profile dropdown");
            Generic_Utility.WaitUtils.pause(Generic_Utility.WaitUtils.SHORT);
            Pages.getHomePage().getLogoutBtn().waitFor(
                    new com.microsoft.playwright.Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(8_000));
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

        if (scenario.isFailed()) {
            Generic_Utility.HealingCache.getInstance().clearScenarioHeals();
        }

        if (b != null && scenario.isFailed()) {
            String screenshotName = "FAILED_" + scenario.getName().replace(" ", "_");
            takeScreenshot(b.page, ExecutionContext.getScreenshotFolder() + "/" + screenshotName);
            byte[] imageBytes = b.page.screenshot();
            scenario.attach(imageBytes, "img/png", scenario.getName());
        }

        if (b != null) {
            if (b.context    != null) b.context.close();
            if (b.browser    != null) b.browser.close();
            if (b.playwright != null) b.playwright.close();
        }

        Generic_Utility.RunSummaryWriter.recordScenario(scenario);
        Generic_Utility.ScenarioBufferAppender.flushScenario(
                scenario.getName(), !scenario.isFailed());

        Pages.remove();
        TL_BASE.remove();
    }
}
