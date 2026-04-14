package Generic_Utility;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SelfHealingWebDriver extends WebDriverUtility {

    private SelfHealingLocator selfHealing;
    private Page page;

    public SelfHealingWebDriver(Page page) {
        this.page = page;
        this.selfHealing = new SelfHealingLocator(page, ConstantFilePath.geminiApiKey);
    }

    public SelfHealingWebDriver(Page page, String geminiApiKey) {
        this.page = page;
        this.selfHealing = new SelfHealingLocator(page, geminiApiKey);
    }
    
    public SelfHealingWebDriver(Page page, String geminiApiKey, String reportFolder) {
        this.page = page;
        this.selfHealing = new SelfHealingLocator(page, geminiApiKey, reportFolder);
    }

    public void click(Locator locator, String stepDescription) {
        selfHealing.click(locator, stepDescription);
    }

    public void fill(Locator locator, String value, String stepDescription) {
        selfHealing.fill(locator, value, stepDescription);
    }
    
    public void clear(Locator locator, String stepDescription) {
        selfHealing.clear(locator, stepDescription);
    }

    public String getText(Locator locator, String stepDescription) {
        Locator result = selfHealing.getText(locator, stepDescription);
        try {
            return result.textContent();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isVisible(Locator locator, String stepDescription) {
        try {
            selfHealing.isVisible(locator, stepDescription);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void hover(Locator locator, String stepDescription) {
        selfHealing.hover(locator, stepDescription);
    }

    public void selectOption(Locator locator, String value, String stepDescription) {
        selfHealing.selectOption(locator, value, stepDescription);
    }

    public void saveHealingReport() {
        selfHealing.saveReport();
    }
}