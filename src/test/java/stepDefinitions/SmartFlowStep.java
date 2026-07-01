package stepDefinitions;

import Generic_Utility.WaitUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * Generic step definitions used by SmartFlow-generated feature files.
 *
 * These steps complement the existing step definitions without replacing them.
 * They handle common actions (click button by text, verify text, navigate to tab)
 * that Gemini generates for new modules.
 *
 * ─── HOW TO REMOVE SMART FLOW ──────────────────────────────────────────────
 * Delete the following and nothing else changes:
 *   src/main/java/SmartFlow/
 *   src/main/resources/SmartFlow/
 *   src/test/java/stepDefinitions/SmartFlowStep.java   ← this file
 *   src/test/java/FeatureFiles/generated/
 *   smart-flow/
 *   SMART_FLOW_GUIDE.md
 * ────────────────────────────────────────────────────────────────────────────
 */
public class SmartFlowStep {

    private static final Logger log = LoggerFactory.getLogger(SmartFlowStep.class);

    // ── Informational marker ───────────────────────────────────────────────────

    /** Emitted by Gemini at the top of each generated scenario for traceability. */
    @Given("SmartFlow generated scenario for module {string}")
    public void smartflow_generated_scenario(String module) {
        log.info("[SmartFlow] Running generated scenario — module: {}", module);
    }

    // ── Generic actions ────────────────────────────────────────────────────────

    /**
     * Clicks any button, anchor, or submit input whose visible text matches.
     * Example step:  When click on "Create Role" button
     */
    @When("click on {string} button")
    public void click_on_named_button(String buttonText) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(
                Hook.base().page.locator(
                        "button:has-text('" + buttonText + "'), " +
                        "input[value='" + buttonText + "'], " +
                        "a:has-text('" + buttonText + "')").first(),
                buttonText + " button");
        log.info("[SmartFlow] Clicked button: {}", buttonText);
    }

    /**
     * Clicks an anchor whose visible text matches.
     * Example step:  And click on "Import Users" link
     */
    @And("click on {string} link")
    public void click_on_named_link(String linkText) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(
                Hook.base().page.locator("a:has-text('" + linkText + "')").first(),
                linkText + " link");
        log.info("[SmartFlow] Clicked link: {}", linkText);
    }

    /**
     * Navigates to a named tab or menu item.
     * Example step:  When navigate to "Roles" tab
     */
    @When("navigate to {string} tab")
    public void navigate_to_named_tab(String tabName) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        Hook.base().shDriver.click(
                Hook.base().page.locator(
                        "a:has-text('" + tabName + "'), " +
                        "li:has-text('" + tabName + "') > a, " +
                        "[role='tab']:has-text('" + tabName + "')").first(),
                tabName + " tab");
        log.info("[SmartFlow] Navigated to tab: {}", tabName);
    }

    // ── Generic validations ────────────────────────────────────────────────────

    /**
     * Asserts that a piece of visible text is present anywhere on the page.
     * Example step:  Then validate "Role created successfully" message is displayed
     */
    @Then("validate {string} message is displayed")
    public void validate_message_is_displayed(String expectedText) throws Throwable {
        Hook.base().page.waitForLoadState();
        WaitUtils.pause(WaitUtils.SHORT);
        boolean present = Hook.base().page.content().contains(expectedText);
        Assert.assertTrue(present,
                "[SmartFlow] Expected text not found on page: \"" + expectedText + "\"");
        log.info("[SmartFlow] Validated text present: {}", expectedText);
    }

    /**
     * Asserts that the current page URL does NOT contain "login" — i.e., user is
     * logged in and on a post-login page.
     * Example step:  Then validate the home page is displayed
     */
    @Then("validate the home page is displayed")
    public void validate_home_page_is_displayed() {
        String currentUrl = Hook.base().page.url();
        Assert.assertFalse(currentUrl.toLowerCase().contains("login"),
                "[SmartFlow] Expected home page but URL contains 'login': " + currentUrl);
        log.info("[SmartFlow] Home page validated — URL: {}", currentUrl);
    }
}
