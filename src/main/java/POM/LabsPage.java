package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LabsPage {

	private Page page;

	public LabsPage(Page page) {
		this.page = page;
	}

	public Locator getRequestLabBtn() {
		return page.locator("//button[@id='marketplace']");
	}

	public Locator getViewBtn() {
		return page.locator("//button[@id='viewLabControlPanelBtn']");
	}

	public Locator getAccessLabBtn() {
		return page.locator("//button[@id='launchLabControlPanelBtn']");
	}

	public Locator getActionsDropdown() {
		return page.locator("//button[@id='labActionBtnContainer']");
	}

	public Locator getStartLink() {
		return page.locator("//a[@id='startBtn']");
	}

	public Locator getStopLink() {
		return page.locator("//a[@id='stopBtn']");
	}

	public Locator getDeleteLink() {
		return page.locator("//a[@id='deProvBtn']");
	}

	public Locator getPoliciesDropdown() {
		return page.locator("//button[@id='policyActionBtnContainer']");
	}

	public Locator getAssignPolicyLink() {
		return page.locator("//a[@id='attachPolicyModelBtn']");
	}

	public Locator getRemovePolicyLink() {
		return page.locator("//a[@id='detachPolicyModelBtn']");
	}

	public Locator getUsageTrendsBtn() {
		return page.locator("//button[@id='viewDeploymentUsageBtn']");
	}

	public Locator getEventsBtn() {
		return page.locator("//button[@id='viewDeploymentEventsBtn']");
	}

	public Locator getAssistBtn() {
		return page.locator("//button[@id='monitorLabBtn']");
	}

	public Locator getSyncLabStatusBtn() {
		return page.locator("//button[@id='nuvelinkSyncBtn']");
	}

	public Locator getStateFilterDropdown() {
		return page.locator("//select[@id='stateFilter']");
	}

	public Locator getRefreshBtn() {
		return page.locator("//button[@id='tableRefreshBtn']");
	}

	public Locator getAllOptionInStateFilter() {
		return page.locator("//select[@id='stateFilter']//option[@value='all']");
	}

	public Locator getActiveOptionInStateFilter() {
		return page.locator("//select[@id='stateFilter']//option[@value='Active']");
	}

	public Locator getDeletedOptionInStateFilter() {
		return page.locator("//select[@id='stateFilter']//option[@value='Deleted']");
	}

	public Locator getSubscriptionsTable() {
		return page.locator("//table[@id='mySubscriptionsTable']");
	}

	public Locator getTableRowByLabId(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/ancestor::tr");
	}

	public Locator getTableRowByLabName(String labName) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[contains(text(),'" + labName + "')]/ancestor::tr");
	}

	public Locator getLabNameColumnById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[contains(@class,'colHighlightStyle')]");
	}

	public Locator getLabTypeById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[1]");
	}

	public Locator getLabGuidById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[2]");
	}

	public Locator getLabCreatedOnById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[3]");
	}

	public Locator getLabTeamById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[4]");
	}

	public Locator getLabEmailById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[5]");
	}

	public Locator getLabLatestActionById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[6]");
	}

	public Locator getLabLatestActionStatusById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[7]//label");
	}

	public Locator getViewLabLinkById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[8]//a[contains(@href,'subscriptions/view')]");
	}

	public Locator getAccessLabLinkById(String labId) {
		return page.locator("//table[@id='mySubscriptionsTable']//td[text()='" + labId + "']/following-sibling::td[8]//a[contains(@href,'subscriptions/launch')]");
	}

	public Locator getAllTableRows() {
		return page.locator("//table[@id='mySubscriptionsTable']//tbody//tr");
	}

	public Locator getTableRowCount() {
		return page.locator("//table[@id='mySubscriptionsTable']//tbody//tr");
	}
}