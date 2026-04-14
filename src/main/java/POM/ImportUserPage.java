package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class ImportUserPage {

	private Page page;

	public ImportUserPage(Page page) {
		this.page = page;
	}

	public Locator getFileToUpload() {
		return page.locator("#fileToUpload");
	}

	public Locator getOrgRadio() {
		return page.locator("#orgRadio");
	}

	public Locator getAllTeamRadio() {
		return page.locator("#allTeamRadio");
	}

	public Locator getCustomTeamRadio() {
		return page.locator("#customTeamRadio");
	}

	public Locator getActivateUsersCheckBox() {
		return page.locator("#activate_users");
	}

	public Locator getAddIntoTeamCheckBox() {
		return page.locator("#add_into_team");
	}

	public Locator getNotifyUsersCheckBox() {
		return page.locator("#notify_users");
	}

	public Locator getPwdForceResetCheckBox() {
		return page.locator("#pwd_force_reset");
	}

	public Locator getIgnorePwdPolicyCheckBox() {
		return page.locator("#ignore_pwd_policy");
	}

	public Locator getCompanyScopeRoleDropdown() {
		return page.locator("#companyScopeRoleId");
	}

	public Locator getImportSubmitBtn() {
		return page.locator("#userImportSubmit");
	}

	public Locator getCancelBtn() {
		return page.locator("//a[text()='Cancel']");
	}

	public Locator getImportSuccessMsg() {
		return page.locator("//span[text()='Success']");
	}

	public Locator getImportErrorMsg() {
		return page.locator("//div[contains(@class,'alert')]");
	}

	public Locator getBatchSummaryTable() {
		return page.locator("#batchSummaryTable");
	}

	public Locator getAllImportedUsersStatus() {
		return page.locator("#batchSummaryTable tbody tr td:nth-child(1)");
	}

	public Locator getAllImportedUsersDetails() {
		return page.locator("#batchSummaryTable tbody tr td:last-child");
	}

	public Locator getImportResultStatus(String status) {
		return page.locator("#batchSummaryTable tbody tr td[contains(text(),'" + status + "')]");
	}

	public Locator getImportResultDetailsEmpty() {
		return page.locator("#batchSummaryTable tbody tr td:last-child[text()='']");
	}

}