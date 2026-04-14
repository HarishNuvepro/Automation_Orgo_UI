package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LabStorePage {

	private Page page;

	public LabStorePage(Page page) {
		this.page = page;
	}

	public Locator getPlanTileByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]");
	}

	public Locator getPlanContainerByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']");
	}

	public Locator getPlanSubscribeButtonByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']//div[@class='btn btn-primary btn-xs roundedButton']");
	}

	public Locator getPlanTitleByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']//div[@class='coursePanelTitle']");
	}

	public Locator getAllPlanTiles() {
		return page.locator("//div[@class='panel coursePanel']");
	}

	public Locator getPlanDescriptionByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']//div[@class='coursePanelDesc']");
	}

	public Locator getPlanToolsByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']//div[@class='coursePanelTools']");
	}

	public Locator getPlanAllottedByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::div[@class='panel-body']//div[@class='coursePanelDuration']");
	}

	public Locator getPlanLinkByPlanId(String planId) {
		return page.locator("//span[contains(text(),'#" + planId + "')]/ancestor::a[@href]");
	}
}