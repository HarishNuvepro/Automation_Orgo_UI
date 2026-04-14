package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class HomeDropdownPage {
	
	private Page page;
	
	public HomeDropdownPage(Page page)
	{
	  this.page = page;
	}
	
	public Locator getMylabsTab() {
		return page.locator("//a[text()='My Labs']");
	}

	public Locator getMylogsTab() {
		return page.locator("//a[text()='My Logs']");
	}

	public Locator getDasboardTab() {
		return page.locator("//a[text()='Dashboard']");
	}

	
	

	
	
    
    
  
    
    

}
