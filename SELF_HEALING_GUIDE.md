# Self-Healing Locator Framework - User Guide

## Overview

This framework provides automatic recovery when element locators fail, using fallback strategies and AI-powered healing via Gemini.

---

## Files Created

| File | Purpose |
|------|---------|
| `SelfHealingLocator.java` | Core wrapper with retry logic and fallback strategies |
| `GeminiLocatorFinder.java` | AI-based locator generation using Gemini API |
| `HealingReportLogger.java` | JSON report logging for healed locators |
| `SelfHealingWebDriver.java` | Easy-to-use wrapper extending WebDriverUtility |

---

## How It Works

### 1. Detection & Retry Flow

```
Primary Locator Fails
        ↓
Try Fallback Locators (text=, role, placeholder, XPath contains)
        ↓ (if fails)
Call Gemini AI with page HTML + step description
        ↓ (if fails)
Mark step as failed + capture screenshot
```

### 2. Retry Strategy

- **Retry 1**: Fallback locators (text selector, role-based, placeholder, label-based, partial XPath)
- **Retry 2**: Gemini AI locator generation

---

## Integration Steps

### Step 1: Add Gson Dependency (Already added to pom.xml)

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

### Step 2: Set Environment Variable

```bash
# Windows
set GEMINI_API_KEY=your_google_ai_studio_api_key

# Linux/Mac
export GEMINI_API_KEY=your_google_ai_studio_api_key
```

### Step 3: Update Hook.java

```java
package stepDefinitions;

import Generic_Utility.WebDriverUtility;
import Generic_Utility.Base;
import Generic_Utility.SelfHealingWebDriver;
import Util.Pages;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hook extends WebDriverUtility {

    private Base base;
    private SelfHealingWebDriver shDriver;  // Add this

    public Hook(Base base) {
        this.base = base;
    }

    @Before
    public void beforeScenario() throws Throwable {
        // ... existing browser setup ...
        
        // Initialize self-healing driver
        String apiKey = System.getenv("GEMINI_API_KEY");
        shDriver = new SelfHealingWebDriver(base.page, apiKey);
    }

    @After
    public void afterScenario(Scenario scenario) throws Throwable {
        // Save healing report before closing
        shDriver.saveHealingReport();
        
        if (scenario.isFailed()) {
            takeScreenshot(base.page, scenario.getName());
        }
        base.context.close();
        base.browser.close();
        base.playwright.close();
    }
}
```

### Step 4: Wrap Element Actions (Non-Breaking)

**Option A: Direct usage in step definitions**

```java
// In your step definitions (User.java, etc.)
@When("click on create user button")
public void click_on_create_user_button() {
    // Old way (still works):
    // Pages.UserPage.getCreateUserBtn().click();
    
    // New way (with self-healing):
    shDriver.click(Pages.UserPage.getCreateUserBtn(), "click create user button");
}

@Given("enter username")
public void enter_username() {
    shDriver.fill(Pages.LoginPage.getUsernameTxt(), "testuser", "fill username");
}
```

**Option B: Extend WebDriverUtility (No code changes needed)**

```java
// Create a new utility that wraps existing methods
public class SelfHealingWebDriverUtility extends WebDriverUtility {
    
    private SelfHealingLocator shLocator;
    
    public SelfHealingWebDriverUtility(Page page) {
        String apiKey = System.getenv("GEMINI_API_KEY");
        this.shLocator = new SelfHealingLocator(page, apiKey);
    }
    
    public void clickWithHealing(Page page, Locator locator, String stepDesc) {
        shLocator.click(locator, stepDesc);
    }
    
    public void fillWithHealing(Page page, Locator locator, String value, String stepDesc) {
        shLocator.fill(locator, value, stepDesc);
    }
}
```

---

## Report Structure

### healing_report.json (New)

```json
[
  {
    "originalLocator": "//button[@id='user-submit']",
    "healedLocator": "text=Submit User",
    "healingMethod": "fallback",
    "retryCount": 1,
    "finalStatus": "SUCCESS",
    "stepDescription": "click on submit button",
    "timestamp": 1712659200000
  }
]
```

### batch_report.json (Appended Fields)

For recovered steps, the following fields are added:
- `healedLocator` - The locator that worked
- `healingMethod` - "fallback" or "AI"
- `retryCount` - Number of attempts
- `status` - Changed to "RECOVERED"

---

## Screenshot Handling

- Screenshots are captured **only** when a step fails after all retries
- Saved to: `src/Screenshot/healing_failed_<scenario_name>.png`
- Attached to the Cucumber report automatically

---

## Configuration Options

| Parameter | Default | Description |
|-----------|---------|-------------|
| `MAX_RETRIES` | 2 | Maximum retry attempts |
| `ELEMENT_TIMEOUT` | 10000ms | Timeout for primary locator |
| `FALLBACK_TIMEOUT` | 5000ms | Timeout for fallback locators |

---

## Fallback Strategies (In Order)

1. **Text selector**: `text="Button Text"`
2. **Role-based**: `button:has-text("Text")`
3. **Placeholder**: `[placeholder="search"]`
4. **Label-based**: `//label[contains(text(),'Label')]/following-sibling::input`
5. **Partial XPath**: `//*[contains(@class,'btn') and contains(text(),'Text')]`
6. **Gemini AI** (last fallback)

---

## Success Criteria Validation

✅ Existing tests run without modification (wrappers are optional)
✅ Failed locator scenarios auto-recovered
✅ JSON report reflects healing data
✅ Screenshots captured only on final failure
✅ No performance impact on passing tests