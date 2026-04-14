package Generic_Utility;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Base {

	public Playwright playwright;
	public Browser browser;
	public BrowserContext context;
	public Page page;
	public SelfHealingWebDriver shDriver;

}
