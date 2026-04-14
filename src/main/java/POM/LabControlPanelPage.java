package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LabControlPanelPage {

	private Page page;

	public LabControlPanelPage(Page page) {
		this.page = page;
	}

	public Locator getLabControlPanelHeader() {
		return page.locator("//h3[contains(text(),'Lab Control Panel')]");
	}

	public Locator getPlanLogo() {
		return page.locator("//img[@id='planDetailsLogo']");
	}

	public Locator getPlanName() {
		return page.locator("//span[contains(@class,'forceWrap')]");
	}

	public Locator getStopBtn() {
		return page.locator("//button[@id='leftActionBtn' and contains(@class,'Stop')]");
	}

	public Locator getLatestStatusText() {
		return page.locator("//span[@id='deploymentStatus']");
	}

	public Locator getRefreshStatusBtn() {
		return page.locator("//a[@id='refreshStatusMain']");
	}

	public Locator getAccessDetailsTable() {
		return page.locator("//div[@id='labAccessLegacy']//table");
	}

	public Locator getLoginIdValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='loginId']/following-sibling::td");
	}

	public Locator getLoginPasswordField() {
		return page.locator("//input[@id='copy-target-loginpassword']");
	}

	public Locator getViewPasswordBtn() {
		return page.locator("//button[@id='view-target-loginpassword']");
	}

	public Locator getCopyPasswordBtn() {
		return page.locator("//button[@id='copybtn-target-loginpassword']");
	}

	public Locator getIsReadyToUseValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='isReadyToUse']/following-sibling::td");
	}

	public Locator getUserNameValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='userName']/following-sibling::td");
	}

	public Locator getRegisteredMailIdValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='registeredMailId']/following-sibling::td");
	}

	public Locator getUserIdValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='userId']/following-sibling::td");
	}

	public Locator getParentIdValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='parentId']/following-sibling::td");
	}

	public Locator getAccessIdValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='accessId']/following-sibling::td");
	}

	public Locator getAccessKeyValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='accessKey']/following-sibling::td");
	}

	public Locator getStateValue() {
		return page.locator("//table[contains(@class,'table-condensed')]//td[text()='state']/following-sibling::td");
	}

	public Locator getActionResponseTextarea() {
		return page.locator("//textarea[@id='actionResponseContent']");
	}

	public Locator getTotalCost() {
		return page.locator("//span[@id='totalCost']");
	}

	public Locator getUsageLastUpdateDate() {
		return page.locator("//span[@id='usageLastUpdateDate']");
	}

	public Locator getUsageCostLink() {
		return page.locator("//a[contains(@href,'subcriptions/usage')]");
	}

	public Locator getOnDemandCostBtn() {
		return page.locator("//button[@id='on-demand-cost-btn']");
	}

	public Locator getStartDateInput() {
		return page.locator("//input[@id='fromDate']");
	}

	public Locator getEndDateInput() {
		return page.locator("//input[@id='toDate']");
	}

	public Locator getBillingDatesSection() {
		return page.locator("//div[@id='billing_dates']");
	}

	public Locator getStartBtn() {
		return page.locator("//button[@id='leftActionBtn' and contains(@class,'Start')]");
	}

	public Locator getLabControlPanelContainer() {
		return page.locator("//div[@id='leftPaneMaster']");
	}

	public Locator getAccessDetailsPanel() {
		return page.locator("//div[@id='labAccessLegacy']");
	}

	public Locator getUsageCostPanel() {
		return page.locator("//div[contains(@class,'account-usage')]");
	}
}