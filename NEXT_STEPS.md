# Next Steps After Selenium to Playwright Migration

## ✅ Completed
Your framework has been successfully migrated from Selenium to Playwright! All core framework files and 31 POM files have been converted.

## 🔧 Manual Updates Required

### Step 1: Update Step Definition Files

The following files in `src/test/java/stepDefinitions/` need manual updates:

1. **AwsAccountLabAction.java**
2. **CspAccountLabActions.java**
3. **Login.java**
4. **MSPAdmin.java**
5. **Roles.java**
6. **Teams.java**
7. **User.java**

### Common Changes Needed:

#### 1. Remove Selenium Imports
```java
// DELETE these lines
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
```

#### 2. Replace WebElement with Locator
```java
// OLD
WebElement element = Pages.SomePage.getElement();

// NEW
Locator element = Pages.SomePage.getElement();
```

#### 3. Update Element Interactions
```java
// OLD
element.sendKeys("text");

// NEW
element.fill("text");
```

#### 4. Replace driver.findElement() calls
```java
// OLD
base.driver.findElement(By.xpath("//path")).click();

// NEW
base.page.locator("//path").click();
```

#### 5. Update Wait Methods
```java
// OLD
wLib.waitForElementVisibility(base.driver, element);

// NEW
wLib.waitForElementVisibility(base.page, element);
```

## 🚀 Installation & Setup

### Step 2: Install Playwright Browsers
Run this command in your terminal:
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

This will download and install Chromium, Firefox, and WebKit browsers.

### Step 3: Build the Project
```bash
mvn clean install
```

### Step 4: Run Tests
```bash
mvn test
```

## 📝 Quick Reference

### Playwright API Cheat Sheet

| Action | Playwright Code |
|--------|----------------|
| Navigate to URL | `page.navigate("https://example.com")` |
| Click element | `element.click()` |
| Fill text | `element.fill("text")` |
| Get text | `element.textContent()` |
| Check visibility | `element.isVisible()` |
| Wait for element | `element.waitFor()` |
| Select dropdown | `element.selectOption("value")` |
| Take screenshot | `page.screenshot()` |
| Hover | `element.hover()` |
| Double click | `element.dblclick()` |

### Locator Syntax

| Type | Playwright Syntax |
|------|------------------|
| ID | `#elementId` or `[id='elementId']` |
| Class | `.className` |
| XPath | `//div[@class='example']` |
| Text | `text=Click me` |
| CSS | `div.class > span` |
| Attribute | `[data-test='value']` |

## 🐛 Troubleshooting

### Issue: "Cannot resolve symbol 'Locator'"
**Solution:** Make sure you've run `mvn clean install` to download Playwright dependencies.

### Issue: "Browser not found"
**Solution:** Run the browser installation command:
```bash
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"
```

### Issue: "Timeout waiting for element"
**Solution:** Increase timeout in your test or use explicit waits:
```java
element.waitFor(new Locator.WaitForOptions().setTimeout(30000));
```

### Issue: Compilation errors in step definitions
**Solution:** Update the step definition files as described in Step 1 above.

## 📚 Documentation

- **Full Migration Summary:** See [`SELENIUM_TO_PLAYWRIGHT_MIGRATION_SUMMARY.md`](SELENIUM_TO_PLAYWRIGHT_MIGRATION_SUMMARY.md)
- **Detailed Migration Guide:** See [`MIGRATION_GUIDE.md`](MIGRATION_GUIDE.md)
- **Playwright Java Docs:** https://playwright.dev/java/
- **Playwright API Reference:** https://playwright.dev/java/docs/api/class-playwright

## ✨ Benefits You'll Experience

1. **Faster Test Execution** - Playwright is generally faster than Selenium
2. **Auto-waiting** - No more explicit waits for most scenarios
3. **Better Debugging** - Built-in debugging tools and traces
4. **Modern API** - Cleaner, more intuitive code
5. **Network Control** - Can intercept and mock network requests
6. **Parallel Execution** - Better support for parallel test execution

## 🎯 Testing Checklist

After completing manual updates:

- [ ] All step definition files updated
- [ ] Project builds successfully (`mvn clean install`)
- [ ] Playwright browsers installed
- [ ] Login test passes
- [ ] User management tests pass
- [ ] Role management tests pass
- [ ] Team management tests pass
- [ ] Lab management tests pass
- [ ] Company management tests pass
- [ ] All feature files execute successfully

## 💡 Tips

1. **Start Small:** Test one feature file at a time
2. **Use Playwright Inspector:** Run with `PWDEBUG=1` environment variable for debugging
3. **Check Timeouts:** Adjust `page.setDefaultTimeout()` if needed
4. **Use Locator Best Practices:** Prefer user-facing attributes (text, role) over XPath when possible
5. **Leverage Auto-waiting:** Playwright waits automatically, so remove unnecessary explicit waits

## 🆘 Need Help?

If you encounter issues:
1. Check the error message carefully
2. Refer to the migration guide
3. Check Playwright documentation
4. Verify all imports are correct
5. Ensure browsers are installed

Good luck with your migration! 🚀
