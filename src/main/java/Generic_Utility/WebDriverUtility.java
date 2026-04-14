package Generic_Utility;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

public class WebDriverUtility {

	public void takeScreenshot(Page page, String screenshotPath) throws IOException {
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath + ".png")));
	}

	public String sysDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss");
		return dateFormat.format(new Date());
	}

	public void acceptAlert(Page page) {
		page.onDialog(dialog -> dialog.accept());
	}

	public void dismissAlert(Page page) {
		page.onDialog(dialog -> dialog.dismiss());
	}

	public void dropdownIndex(Locator element, int index) {
		element.selectOption(new SelectOption().setIndex(index));
	}

	public void dropdownValue(Locator element, String value) {
		element.selectOption(value);
	}

	public void maximizeWindow(Page page) {
		page.setViewportSize(1920, 1080);
	}

	/**
	 * this method wait for 20sec for page loading
	 * 
	 */
	public void waitUntilPageLoad(Page page) {
		page.setDefaultTimeout(20000);
	}

	/**
	 * this method is used for wait until element to be click able
	 */

	public void waitForElementToBeClickable(Page page, Locator element) {
		element.waitFor(new Locator.WaitForOptions().setTimeout(20000));
	}

	/**
	 * this method wait for the element to be visible
	 */
	public void waitForElementVisibility(Page page, Locator element) {
		element.waitFor(new Locator.WaitForOptions().setTimeout(300000));
	}

	public void mouserHover(Page page, Locator element) {
		element.hover();
	}

	/**
	 * this method perform right click operation
	 */

	public void rightClick(Page page, Locator element) {
		element.click(new Locator.ClickOptions().setButton(com.microsoft.playwright.options.MouseButton.RIGHT));
	}

	/**
	 * this method is used to double click action
	 */
	public void doubleClick(Page page, Locator element) {
		element.dblclick();
	}

	/**
	 * this method is used to perform drag and drop actions
	 * 
	 */

	public void dragAndDrop(Page page, Locator src, Locator dest) {
		src.dragTo(dest);
	}

	/**
	 * This method is used to perform scrolling actions in webpage
	 */

	public void scrollToWebElement(Page page, Locator element) {
		element.scrollIntoViewIfNeeded();
	}

	public void scrollToTop(Page page) {
		page.evaluate("window.scrollTo(0,0)");
	}

}
