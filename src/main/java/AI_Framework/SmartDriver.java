package AI_Framework;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Position;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmartDriver {

    private static final Logger log = LoggerFactory.getLogger(SmartDriver.class);

    private static final int MAX_RETRIES = 2;
    private static final int ELEMENT_TIMEOUT = 10000;

    private Page page;
    private GeminiClient geminiClient;
    private ReportManager reportManager;
    private RetryUtil retryUtil;

    public SmartDriver(Page page) {
        this.page = page;
        this.geminiClient = new GeminiClient();
        this.reportManager = new ReportManager();
        this.retryUtil = new RetryUtil(MAX_RETRIES, 1000);
    }

    public SmartDriver(Page page, String apiKey) {
        this.page = page;
        this.geminiClient = new GeminiClient(apiKey);
        this.reportManager = new ReportManager();
        this.retryUtil = new RetryUtil(MAX_RETRIES, 1000);
    }

    public Locator findElement(String selector, String elementName) {
        Locator locator = page.locator(selector);
        String workingLocator = selector;
        boolean success = false;

        for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                if (attempt == 0) {
                    locator.first().waitFor(new Locator.WaitForOptions().setTimeout(ELEMENT_TIMEOUT));
                } else {
                    locator.first().waitFor(new Locator.WaitForOptions().setTimeout(5000));
                }
                success = true;
                break;
            } catch (Exception e) {
                log.warn("Attempt {} failed for '{}': {}", attempt + 1, elementName, e.getMessage());

                if (attempt < MAX_RETRIES) {
                    if (attempt == MAX_RETRIES - 1) {
                        String aiLocator = callAIForLocator(selector, elementName);
                        if (aiLocator != null) {
                            locator = page.locator(aiLocator);
                            workingLocator = aiLocator;
                            log.info("AI suggested new locator: {}", aiLocator);
                        }
                    }
                }
            }
        }

        if (!success) {
            takeScreenshot("healing_failed_" + elementName);
            throw new RuntimeException("[SmartDriver] Element '" + elementName + "' not found after " + (MAX_RETRIES + 1) + " attempts. Original: " + selector + ", Last tried: " + workingLocator);
        }

        log.debug("Element '{}' found with locator: {}", elementName, workingLocator);
        return locator;
    }

    private String callAIForLocator(String failedLocator, String elementName) {
        try {
            String html = page.content();
            String aiLocator = geminiClient.getSmartLocator(html, failedLocator, elementName);

            if (aiLocator != null) {
                reportManager.logSuggestion(elementName, failedLocator, aiLocator);
            }

            return aiLocator;
        } catch (Exception e) {
            log.error("AI call failed", e);
            return null;
        }
    }

    public void click(Locator locator, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.click();
    }

    public void fill(Locator locator, String value, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.fill(value);
    }

    public void clear(Locator locator, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.click();
        smartLocator.press("Control+a");
        smartLocator.press("Delete");
    }

    public String getText(Locator locator, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        return smartLocator.textContent();
    }

    public boolean isVisible(Locator locator, String elementName) {
        try {
            Locator smartLocator = findElement(getLocatorString(locator), elementName);
            return smartLocator.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public void hover(Locator locator, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.hover();
    }

    public void selectOption(Locator locator, String value, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.selectOption(value);
    }

    public void dragAndDrop(Locator source, Locator target, String sourceName, String targetName) {
        Locator src = findElement(getLocatorString(source), sourceName);
        Locator tgt = findElement(getLocatorString(target), targetName);
        src.dragTo(tgt);
    }

    public void scrollIntoView(Locator locator, String elementName) {
        Locator smartLocator = findElement(getLocatorString(locator), elementName);
        smartLocator.scrollIntoViewIfNeeded();
    }

    private void takeScreenshot(String scenarioName) {
        try {
            String screenshotPath = ".\\src\\Screenshot\\" + scenarioName + "_" + sysDate() + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            log.info("Screenshot saved: {}", screenshotPath);
        } catch (Exception e) {
            log.error("Failed to take screenshot", e);
        }
    }

    private String sysDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss");
        return dateFormat.format(new Date());
    }

    private String getLocatorString(Locator locator) {
        try {
            return locator.toString().replace("Locator@", "");
        } catch (Exception e) {
            return "";
        }
    }

    public Page getPage() {
        return page;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public GeminiClient getGeminiClient() {
        return geminiClient;
    }
}