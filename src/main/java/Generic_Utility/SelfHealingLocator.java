package Generic_Utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Position;

public class SelfHealingLocator {
    
    private static final int MAX_RETRIES = 2;
    private static final int ELEMENT_TIMEOUT = 10000;
    private static final int FALLBACK_TIMEOUT = 5000;
    
    private Page page;
    private String geminiApiKey;
    private List<HealingRecord> healingRecords;
    private HealingReportLogger reportLogger;
    
    public SelfHealingLocator(Page page, String geminiApiKey, String reportFolder) {
        this.page = page;
        this.geminiApiKey = geminiApiKey;
        this.healingRecords = new ArrayList<>();
        this.reportLogger = new HealingReportLogger();
        if (reportFolder != null && !reportFolder.isEmpty()) {
            this.reportLogger.setReportFolder(reportFolder);
        }
    }
    
    public SelfHealingLocator(Page page, String geminiApiKey) {
        this(page, geminiApiKey, null);
    }
    
    public SelfHealingLocator(Page page) {
        this(page, ConstantFilePath.geminiApiKey, null);
    }
    
    public Locator click(Locator locator, String stepDescription) {
        return performActionWithHealing(locator, stepDescription, ActionType.CLICK, null);
    }
    
    public Locator fill(Locator locator, String value, String stepDescription) {
        return performActionWithHealing(locator, stepDescription, ActionType.FILL, value);
    }
    
    public Locator getText(Locator locator, String stepDescription) {
        return performActionWithHealing(locator, stepDescription, ActionType.GET_TEXT, null);
    }
    
    public Locator isVisible(Locator locator, String stepDescription) {
        return performActionWithHealing(locator, stepDescription, ActionType.IS_VISIBLE, null);
    }
    
    public void hover(Locator locator, String stepDescription) {
        performActionWithHealing(locator, stepDescription, ActionType.HOVER, null);
    }
    
    public void selectOption(Locator locator, String value, String stepDescription) {
        performActionWithHealing(locator, stepDescription, ActionType.SELECT_OPTION, value);
    }
    
    public void clear(Locator locator, String stepDescription) {
        performActionWithHealing(locator, stepDescription, ActionType.CLEAR, null);
    }
    
    private Locator performActionWithHealing(Locator locator, String stepDescription, ActionType actionType, String value) {
        String originalLocator = getLocatorString(locator);
        int retryCount = 0;
        String healingMethod = "none";
        String healedLocatorStr = originalLocator;
        Locator currentLocator = locator;
        
        while (retryCount <= MAX_RETRIES) {
            try {
                switch (actionType) {
                    case CLICK:
                        currentLocator.click(new Locator.ClickOptions().setTimeout(ELEMENT_TIMEOUT));
                        break;
                    case FILL:
                        currentLocator.fill(value);
                        break;
                    case CLEAR:
                        currentLocator.click();
                        currentLocator.press("Control+a");
                        currentLocator.press("Delete");
                        break;
                    case GET_TEXT:
                        currentLocator.textContent();
                        break;
                    case IS_VISIBLE:
                        currentLocator.isVisible();
                        break;
                    case HOVER:
                        currentLocator.hover();
                        break;
                    case SELECT_OPTION:
                        currentLocator.selectOption(value);
                        break;
                }
                
                if (retryCount > 0) {
                    HealingRecord record = new HealingRecord();
                    record.originalLocator = originalLocator;
                    record.healedLocator = healedLocatorStr;
                    record.healingMethod = healingMethod;
                    record.retryCount = retryCount;
                    record.finalStatus = "SUCCESS";
                    record.stepDescription = stepDescription;
                    record.timestamp = System.currentTimeMillis();
                    healingRecords.add(record);
                    System.out.println("[Self-Healing] SUCCESS - Locator healed after " + retryCount + " attempt(s)");
                    System.out.println("[Self-Healing] Original: " + originalLocator + " -> Healed: " + healedLocatorStr + " (Method: " + healingMethod + ")");
                }
                
                return currentLocator;
                
            } catch (Exception e) {
                retryCount++;
                String errorType = e.getClass().getSimpleName();
                System.out.println("[Self-Healing] Attempt " + retryCount + " FAILED (" + errorType + "): " + e.getMessage());
                
                if (retryCount <= MAX_RETRIES) {
                    if (retryCount == 1) {
                        if (actionType != ActionType.CLEAR) {
                            System.out.println("[Self-Healing] Trying fallback locators...");
                            List<String> fallbackLocators = generateFallbackLocators(locator, stepDescription);
                            for (String fallback : fallbackLocators) {
                                try {
                                    currentLocator = page.locator(fallback);
                                    currentLocator.first().waitFor(new Locator.WaitForOptions().setTimeout(FALLBACK_TIMEOUT));
                                    healedLocatorStr = fallback;
                                    healingMethod = "fallback";
                                    System.out.println("[Self-Healing] Fallback locator FOUND: " + fallback);
                                    break;
                                } catch (Exception ex) {
                                    System.out.println("[Self-Healing] Fallback locator failed: " + fallback);
                                }
                            }
                        }
                    } else if (retryCount == 2) {
                        if (actionType != ActionType.CLEAR && geminiApiKey != null && !geminiApiKey.isEmpty()) {
                            System.out.println("[Self-Healing] Trying AI-generated locator...");
                            try {
                                String aiLocator = GeminiLocatorFinder.findElementLocator(
                                    page.content(), 
                                    stepDescription, 
                                    geminiApiKey
                                );
                                if (aiLocator != null && !aiLocator.isEmpty()) {
                                    currentLocator = page.locator(aiLocator);
                                    currentLocator.first().waitFor(new Locator.WaitForOptions().setTimeout(FALLBACK_TIMEOUT));
                                    healedLocatorStr = aiLocator;
                                    healingMethod = "AI";
                                    System.out.println("[Self-Healing] AI-generated locator FOUND: " + aiLocator);
                                }
                            } catch (Exception ex) {
                                System.out.println("[Self-Healing] AI locator failed: " + ex.getMessage());
                            }
                        }
                    }
                }
            }
        }
        
        HealingRecord record = new HealingRecord();
        record.originalLocator = originalLocator;
        record.healedLocator = healedLocatorStr;
        record.healingMethod = healingMethod;
        record.retryCount = retryCount;
        record.finalStatus = "FAILED";
        record.stepDescription = stepDescription;
        record.timestamp = System.currentTimeMillis();
        healingRecords.add(record);
        
        System.out.println("[Self-Healing] FAILED - All healing attempts exhausted for: " + stepDescription);
        System.out.println("[Self-Healing] Original: " + originalLocator + ", Tried: " + healedLocatorStr + ", Method: " + healingMethod);
        
        reportLogger.saveHealingReport(healingRecords);
        takeFailureScreenshot(stepDescription);
        
        throw new RuntimeException("Self-healing failed after " + MAX_RETRIES + " retries. Original: " + 
            originalLocator + ", Healed: " + healedLocatorStr + ", Method: " + healingMethod);
    }
    
    private List<String> generateFallbackLocators(Locator locator, String stepDescription) {
        List<String> fallbacks = new ArrayList<>();
        String text = extractTextFromLocator(locator);
        String lowerDesc = stepDescription.toLowerCase();
        
        if (!text.isEmpty()) {
            fallbacks.add("text=" + text);
            fallbacks.add("//*[contains(text(),'" + text + "')]");
            fallbacks.add("//button[contains(text(),'" + text + "')]");
            fallbacks.add("//a[contains(text(),'" + text + "')]");
            fallbacks.add("//span[contains(text(),'" + text + "')]");
            fallbacks.add("//div[contains(text(),'" + text + "')]");
        }
        
        if (lowerDesc.contains("username") || lowerDesc.contains("user name") || lowerDesc.contains("user id") || lowerDesc.contains("login")) {
            fallbacks.add("#sl_userName");
            fallbacks.add("#userName");
            fallbacks.add("input[id*='username']");
            fallbacks.add("input[name*='username']");
            fallbacks.add("input[id*='user']");
            fallbacks.add("input[name*='user']");
            fallbacks.add("input[id*='login']");
            fallbacks.add("input[name*='login']");
            fallbacks.add("input[id*='email']");
            fallbacks.add("input[name*='email']");
            fallbacks.add("input[placeholder*='username']");
            fallbacks.add("input[placeholder*='Username']");
            fallbacks.add("input[placeholder*='email']");
            fallbacks.add("input[placeholder*='Email']");
        }
        
        if (lowerDesc.contains("password")) {
            fallbacks.add("#sl_password");
            fallbacks.add("#s_password");
            fallbacks.add("#password");
            fallbacks.add("input[id*='password']");
            fallbacks.add("input[name*='password']");
            fallbacks.add("input[placeholder*='password']");
            fallbacks.add("input[placeholder*='Password']");
        }
        
        if (lowerDesc.contains("sign in") || lowerDesc.contains("signin") || lowerDesc.contains("login") || lowerDesc.contains("submit")) {
            fallbacks.add("#user_sign_in_button");
            fallbacks.add("#signIn");
            fallbacks.add("#sl_signIn");
            fallbacks.add("button:has-text('Sign In')");
            fallbacks.add("button:has-text('Sign In')");
            fallbacks.add("button:has-text('Login')");
            fallbacks.add("button:has-text('Sign in')");
            fallbacks.add("button:has-text('Login')");
            fallbacks.add("input[type='submit']");
            fallbacks.add("button[type='submit']");
        }
        
        if (lowerDesc.contains("user profile") || lowerDesc.contains("profile dropdown") || lowerDesc.contains("user dropdown")) {
            fallbacks.add("//li[@class='dropdown']//a[@class='dropdown-toggle']");
            fallbacks.add(".dropdown-toggle");
            fallbacks.add("//a[contains(@class,'dropdown-toggle')]");
            fallbacks.add("#navbar li.dropdown a");
            fallbacks.add("li.dropdown > a");
            fallbacks.add("//span[@class='username']");
            fallbacks.add(".username");
        }
        
        if (lowerDesc.contains("logout") || lowerDesc.contains("sign out")) {
            fallbacks.add("//a[contains(text(),'Logout')]");
            fallbacks.add("//a[contains(text(),'Sign Out')]");
            fallbacks.add("//a[contains(@href,'logout')]");
            fallbacks.add("a[href*='logout']");
            fallbacks.add("//li[@class='dropdown open']//a[contains(text(),'Logout')]");
            fallbacks.add(".dropdown-menu a:text('Logout')");
        }
        
        String fieldName = extractFieldNameFromDescription(stepDescription);
        if (!fieldName.isEmpty() && !fieldName.equals("username")) {
            fallbacks.add("//input[@id='" + fieldName + "']");
            fallbacks.add("//input[@name='" + fieldName + "']");
            fallbacks.add("//input[contains(@id,'" + fieldName + "')]");
            fallbacks.add("//input[contains(@name,'" + fieldName + "')]");
            fallbacks.add("//textarea[@id='" + fieldName + "']");
            fallbacks.add("//textarea[@name='" + fieldName + "']");
        }
        
        String role = extractRoleFromDescription(stepDescription);
        if (!role.isEmpty()) {
            fallbacks.add("button:has-text(\"" + role + "\")");
            fallbacks.add("input[role=\"" + role + "\"]");
        }
        
        String placeholder = extractPlaceholderFromDescription(stepDescription);
        if (!placeholder.isEmpty()) {
            fallbacks.add("[placeholder=\"" + placeholder + "\"]");
            fallbacks.add("input[placeholder*=\"" + placeholder + "\"]");
        }
        
        String label = extractLabelFromDescription(stepDescription);
        if (!label.isEmpty()) {
            fallbacks.add("//label[contains(text(),'" + label + "')]/following-sibling::input");
            fallbacks.add("//label[contains(text(),'" + label + "')]/..//input");
        }
        
        fallbacks.add("//*[contains(@class,'btn') and contains(text(),'" + stepDescription + "')]");
        fallbacks.add("//*[contains(@class,'button')]");
        fallbacks.add("//*[contains(@id,'submit')]");
        
        return fallbacks;
    }
    
    private String extractFieldNameFromDescription(String description) {
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("first name") || lowerDesc.contains("firstname")) return "first_name";
        if (lowerDesc.contains("last name") || lowerDesc.contains("lastname")) return "last_name";
        if (lowerDesc.contains("email")) return "email";
        if (lowerDesc.contains("username") || lowerDesc.contains("user name") || lowerDesc.contains("user id")) return "username";
        if (lowerDesc.contains("login") || lowerDesc.contains("user id")) return "username";
        if (lowerDesc.contains("password")) return "password";
        if (lowerDesc.contains("employee") || lowerDesc.contains("employee id")) return "employee_id";
        if (lowerDesc.contains("phone")) return "phone";
        if (lowerDesc.contains("address")) return "address";
        if (lowerDesc.contains("name")) return "name";
        return "";
    }
    
    private String extractTextFromLocator(Locator locator) {
        try {
            return locator.textContent(new Locator.TextContentOptions().setTimeout(2000)).trim();
        } catch (Exception e) {
            return "";
        }
    }
    
    private String extractRoleFromDescription(String description) {
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("button")) return "button";
        if (lowerDesc.contains("checkbox")) return "checkbox";
        if (lowerDesc.contains("radio")) return "radio";
        if (lowerDesc.contains("textbox") || lowerDesc.contains("input")) return "textbox";
        if (lowerDesc.contains("link")) return "link";
        return "";
    }
    
    private String extractPlaceholderFromDescription(String description) {
        return "";
    }
    
    private String extractLabelFromDescription(String description) {
        return "";
    }
    
    private String getLocatorString(Locator locator) {
        try {
            return locator.toString();
        } catch (Exception e) {
            return "unknown";
        }
    }
    
    private void takeFailureScreenshot(String scenarioName) {
        try {
            WebDriverUtility util = new WebDriverUtility();
            util.takeScreenshot(page, "healing_failed_" + scenarioName);
        } catch (Exception e) {
            System.out.println("[Self-Healing] Failed to take screenshot: " + e.getMessage());
        }
    }
    
    public List<HealingRecord> getHealingRecords() {
        return healingRecords;
    }
    
    public void saveReport() {
        reportLogger.saveHealingReport(healingRecords);
    }
    
    public static class HealingRecord {
        public String originalLocator;
        public String healedLocator;
        public String healingMethod;
        public int retryCount;
        public String finalStatus;
        public String stepDescription;
        public long timestamp;
    }
    
    private enum ActionType {
        CLICK, FILL, CLEAR, GET_TEXT, IS_VISIBLE, HOVER, SELECT_OPTION
    }
}
