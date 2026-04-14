package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class AddToTeamModalPage {

	private Page page;

	public AddToTeamModalPage(Page page) {
		this.page = page;
	}

	public Locator getChooseTeamRadioBtn() {
		return page.locator("#customTeamRadio");
	}

	public Locator getAllTeamsRadioBtn() {
		return page.locator("#allTeamRadio");
	}

	public Locator getTeamDropdown() {
		return page.locator("#teamId");
	}

	public Locator getTeamSearchField() {
		return page.locator(".select2-search__field");
	}

	public Locator getTeamOption(String teamName) {
		return page.locator(".select2-results__option").filter(new Locator.FilterOptions().setHasText(teamName));
	}

	public Locator getTeamAdminRadioBtn() {
		return page.locator("input[name='roleName'][value='9']");
	}

	public Locator getTeamMemberRadioBtn() {
		return page.locator("input[name='roleName'][value='10']");
	}

	public Locator getAddButton() {
		return page.locator("#addUserIntoTeamModalBtn");
	}

	public Locator getCancelButton() {
		return page.locator(".btn.btn-default");
	}
}