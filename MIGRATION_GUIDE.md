# Selenium to Playwright Migration Guide

## Completed Files
- ✅ pom.xml - Updated dependencies
- ✅ Base.java - Changed from WebDriver to Page/Browser/BrowserContext
- ✅ WebDriverUtility.java - All methods converted to Playwright APIs
- ✅ Hook.java - Browser initialization with Playwright
- ✅ Pages.java - Updated to use Page instead of WebDriver
- ✅ LoginPage.java - Converted to Playwright
- ✅ HomePage.java - Converted to Playwright
- ✅ AllLabsPage.java - Converted to Playwright

## Remaining POM Files to Convert (28 files)
All remaining POM files need the same conversion pattern:

### Conversion Pattern for Each POM File:

1. **Replace imports:**
   ```java
   // OLD
   import org.openqa.selenium.WebDriver;
   import org.openqa.selenium.WebElement;
   import org.openqa.selenium.support.FindBy;
   import org.openqa.selenium.support.PageFactory;
   
   // NEW
   import com.microsoft.playwright.Locator;
   import com.microsoft.playwright.Page;
   ```

2. **Replace constructor:**
   ```java
   // OLD
   public ClassName(WebDriver driver) {
       PageFactory.initElements(driver, this);
   }
   
   // NEW
   private Page page;
   
   public ClassName(Page page) {
       this.page = page;
   }
   ```

3. **Replace @FindBy annotations and WebElement fields:**
   ```java
   // OLD
   @FindBy(id="elementId")
   private WebElement elementName;
   
   public WebElement getElementName() {
       return elementName;
   }
   
   // NEW
   public Locator getElementName() {
       return page.locator("#elementId");
   }
   ```

4. **Locator conversion:**
   - `id="value"` → `#value`
   - `xpath="//path"` → `//path`
   - `name="value"` → `[name='value']`
   - `className="value"` → `.value`

## Step Definition Files to Update
After all POM files are converted, update step definition files:

### Changes needed in step definitions:
1. Replace `WebElement` with `Locator`
2. Replace `.sendKeys()` with `.fill()`
3. Replace `base.driver.findElement(By.xpath())` with `base.page.locator()`
4. Replace `wLib.waitForElementVisibility(base.driver, element)` with `element.waitFor()`

## Testing After Migration
1. Run `mvn clean install` to download Playwright dependencies
2. Run `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install"` to install browsers
3. Run your test suite to verify functionality

## Key API Differences:
- `element.click()` - Same in both
- `element.sendKeys(text)` → `element.fill(text)`
- `element.getText()` → `element.textContent()` or `element.innerText()`
- `element.isDisplayed()` → `element.isVisible()`
- `element.isEnabled()` → `element.isEnabled()`
- `driver.get(url)` → `page.navigate(url)`
- `driver.findElement(By.xpath())` → `page.locator(xpath)`
- `driver.close()` → `context.close()` and `browser.close()`
