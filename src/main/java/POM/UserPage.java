package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import Generic_Utility.ExcelUtility;

public class UserPage {

	private Page page;

	public UserPage(Page page) {
		this.page = page;
	}

	public Locator getCreateUserBtn() {
		return page.locator("#addBtn");
	}

	public Locator getUserViewBtn() {
		return page.locator("#viewBtn");
	}

	public Locator getUserEditBtn() {
		return page.locator("#editBtn");
	}

	public Locator getUserRemoveBtn() {
		return page.locator("#removeBtn");
	}

	public Locator getUserImportBtn() {
		return page.locator("#importBtn");
	}

	public Locator getAddUserIntoTeamBtn() {
		return page.locator("#addUserIntoTeamBtn");
	}

	public Locator getChangePasswordBtn() {
		return page.locator("#changePasswordBtn");
	}

	public Locator getActiveBtn() {
		return page.locator("#activateBtn");
	}

	public Locator getDeActiveBtn() {
		return page.locator("#deActivateBtn");
	}

	public Locator getShowEntriesDropdown() {
		return page.locator("//select[@name='usersListTable_length']");
	}

	public Locator getSelectUserCheckBox() {
		return page.locator("//td[contains(@class,'select-checkbox') and contains(@class,'noVis')]");
	}

	/**
	 * "Select all" header cell in the users table. The th with class
	 * {@code select-checkbox} is the DataTables Select extension's header
	 * for bulk-row selection. The cell is initially empty in the HTML and
	 * the input checkbox is added at runtime by the Select extension.
	 *
	 * <p>HTML:
	 * <pre>
	 * &lt;th class="sorting_disabled select-checkbox noVis" rowspan="1" colspan="1" style="width: 16px;"&gt;
	 *   &lt;input type="checkbox"&gt;  &lt;-- added by DataTables Select at runtime
	 * &lt;/th&gt;
	 * </pre>
	 *
	 * <p>Locator points at the th itself (not the input inside) because:
	 * <ol>
	 *   <li>The input is not guaranteed to exist in the DOM when the test runs</li>
	 *   <li>The th is the element the DataTables Select extension actually
	 *       listens to for click events (via event delegation)</li>
	 *   <li>Force-clicking the th works whether or not the inner input has
	 *       been injected yet</li>
	 * </ol>
	 */
	public Locator getSelectAllCheckbox() {
		return page.locator("th.select-checkbox");
	}

	public Locator getSelectUserCheckBoxByLoginId(String loginId) {
		return page.locator("//table[@id='usersListTable']//tr[td[normalize-space()='" + loginId
				+ "']]//td[contains(@class,'select-checkbox')]").first();
	}

	public Locator getUserDeleteBtn() {
		return page.locator("#userDeleteButton");
	}

	public Locator getUserTableRefreshBtn() {
		return page.locator("#tableRefreshBtn");
	}

	public Locator getUserDeactiveBtn() {
		return page.locator("#userDeActiveButton");
	}

	public Locator getUserDeactivateLinkBtn() {
		return page.locator("#deActivateBtn");
	}

	public Locator getUserDeactiveConfirmBtn() {
		return page.locator("#deActivateUser button:has-text('Deactivate')");
	}

	public Locator getUserDeactiveCancelBtn() {
		return page.locator("#deActivateUser button:has-text('Cancel')");
	}

	public Locator getUserDeleteCancelBtn() {
		// Matches: <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
		// This is the same generic Cancel pattern used by every Bootstrap modal in this app
		// (change-password, edit-user, etc.). The remove-user dialog does NOT have a unique
		// parent ID, so we rely on .first() in the step def to pick the only Cancel button
		// visible when the remove dialog is open.
		return page.locator("button.btn.btn-default[data-dismiss='modal']:has-text('Cancel')");
	}

	public Locator getUserActiveBtn() {
		return page.locator("#userActiveButton");
	}

	public Locator getUserStatusDropdownBtn() {
		return page.locator("#userStatusBtnContainer");
	}

	public Locator getUserActivateLinkBtn() {
		return page.locator("#activateBtn");
	}

	public Locator getUserActiveConfirmBtn() {
		return page.locator("#activateUser button:has-text('Activate')");
	}

	public Locator getChangeConfirmpasswordTxt() {
		return page.locator("#confirm_password");
	}

	public Locator getApplyPasswordBtn() {
		return page.locator("#changePawsswordButton");
	}

	public Locator getNotifyUserCheckBox() {
		return page.locator("#pwd_update_notify_users");
	}

	public Locator getPasswordUpdatedconfirmMsg() {
		return page.locator("//span[text()='Password Updated Successfully']");
	}

	public Locator getChangepasswordTxt() {
		return page.locator("#password");
	}

	public Locator getChooseTeamRadioBtn() {
		return page.locator("#customTeamRadio");
	}

	public Locator getAllTeamsRadioBtn() {
		return page.locator("#allTeamRadio");
	}

	public Locator getSelectTeamSearchBox() {
		return page.locator("//input[@class='select2-search__field']");
	}

	public Locator getSelectTeamDropdown() {
		return page.locator("#select2-teamId-results");
	}

	public Locator getTeamAdminRadioBtn() {
		return page.locator("//label[text()='TeamAdmin']");
	}

	public Locator getAdduserIntoTeamModalBtn() {
		return page.locator("#addUserIntoTeamModalBtn");
	}

	public Locator getTeamMemberRadioBtn() {
		return page.locator("//label[text()='TeamMember']");
	}

	public Locator getUseraddedIntoTeamsuccessMsg() {
		return page.locator("//span[text()='Success']").first();
	}

	public Locator getDeleteSuccessMsg() {
		return page.locator("//span[@data-notify='message' and (text()='Success')]").first();
	}

	public Locator getSearchItemDisplay() {
		return page.locator("#searchItemDisplay");
	}

	public Locator getSearchTypesDropdown() {
		return page.locator("#searchTypes");
	}

	public Locator getSearchDropdownOption(String dataValue) {
		return page.locator("#searchTypes a[data-value='" + dataValue + "']");
	}

	public Locator getSearchInputBox() {
		return page.locator("#stringSearch");
	}

	public Locator getSearchBtn() {
		return page.locator("#doSearchBtn");
	}

	public Locator getUserStatusByLoginId(String loginId) {
		return page.locator("//table[@id='usersListTable']//tr[td[text()='" + loginId
				+ "']]//td[contains(@class,'cell-align-center')]");
	}

	public Locator getUserRoleByLoginId(String loginId) {
		return page.locator("//table[@id='usersListTable']//tr[td[text()='" + loginId + "']]//td[7]");
	}

	public Locator getUserLoginIdByUserId(String userId) {
		return page.locator("//table[@id='usersListTable']//tr[td[text()='" + userId + "']]//td[3]");
	}

	public Locator getUserLoginIdByPartialLoginId(String partialLoginId) {
		return page.locator("//table[@id='usersListTable']//tr[td[contains(text(),'" + partialLoginId + "')]]//td[3]");
	}

	public Locator getUserEmailIdByEmailId(String emailId) {
		return page.locator("//table[@id='usersListTable']//tr[td[text()='" + emailId + "']]//td[4]");
	}

	public Locator getUserEmailIdByEmailPartial(String partialEmail) {
		return page.locator("//table[@id='usersListTable']//tr[td[contains(text(),'" + partialEmail + "')]]//td[4]");
	}

	public Locator getAdvancedSearchBtn() {
		return page.locator("#advancedSearchLaunchBtn");
	}

	public Locator getExactSearchCheckbox() {
		return page.locator("#exactSearch");
	}

	public Locator getAdvanceSearchAllOption() {
		return page.locator("#stateFilter option[value='*']");
	}

	public Locator getAdvanceSearchSubmitBtn() {
		return page.locator("#advancedSearchDlgExecuteBtn");
	}

	public Locator getSearchResultCount() {
		return page.locator("#usersListTable_info");
	}

	public Locator getImportUserBtn() {
		return page.locator("#importBtn");
	}

	public Locator getChooseFileBtn() {
		return page.locator("#fileInput");
	}

	public Locator getImportDetailsSubmitBtn() {
		return page.locator("#importSubmitBtn");
	}

	public Locator getImportSuccessMsg() {
		return page.locator("//span[text()='Success']");
	}

	public Locator getExportBtn() {
		return page.locator("button.buttons-collection:has-text('Export')");
	}

	public Locator getExportCsvOption() {
		return page.locator("li.buttons-csv");
	}

	public Locator getExportExcelOption() {
		return page.locator("li.buttons-excel");
	}

	public Locator getExportPdfOption() {
		return page.locator("li.buttons-pdf");
	}

	public Locator getExportCopyOption() {
		return page.locator("li.buttons-copy");
	}

}
