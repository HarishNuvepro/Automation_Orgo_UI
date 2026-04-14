package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class SubscribePlanPage {

	private Page page;

	public SubscribePlanPage(Page page) {
		this.page = page;
	}

	public Locator getScriptPathInput() {
		return page.locator("//input[@id='UID_SCRIPT_PATH']");
	}

	public Locator getScriptArgumentsInput() {
		return page.locator("//input[@id='UID_SCRIPT_ARGS']");
	}

	public Locator getScriptTypeInput() {
		return page.locator("//input[@id='UID_SCRIPT_REPO_TYPE']");
	}

	public Locator getRepositoryUserInput() {
		return page.locator("//input[@id='UID_SCRIPT_REPO_USER']");
	}

	public Locator getRepositoryPasswordInput() {
		return page.locator("//input[@id='UID_SCRIPT_REPO_CONF']");
	}

	public Locator getRegionDropdown() {
		return page.locator("//select[@id='UID_SCRIPT_REGION']");
	}

	public Locator getRegionOptionByValue(String value) {
		return page.locator("//select[@id='UID_SCRIPT_REGION']//option[@value='" + value + "']");
	}

	public Locator getProvisionForSelfRadio() {
		return page.locator("//input[@value='labForSelf' and @name='labForRadio']");
	}

	public Locator getProvisionForOthersRadio() {
		return page.locator("//input[@value='labForOthers' and @name='labForRadio']");
	}

	public Locator getUserDropdown() {
		return page.locator("//select[@id='username']");
	}

	public Locator getNextButton() {
		return page.locator("//button[@id='subCatalogDataSubmitBtn']");
	}

	public Locator getSubscribeButton() {
		return page.locator("//input[@id='catInputSubmitBtn']");
	}

	public Locator getCancelButton() {
		return page.locator("//input[@id='catInputCancelBtn']");
	}

	public Locator getHiddenPlanId() {
		return page.locator("//input[@id='plan_id']");
	}

	public Locator getHiddenPlanDisplayName() {
		return page.locator("//input[@id='planDisplayName']");
	}

	public Locator getHiddenCatalogOfferType() {
		return page.locator("//input[@id='catalog_offer_type']");
	}

	public Locator getNoInputRequiredMessage() {
		return page.locator("//h6[@id='noUIInput']");
	}
}