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

	// Clicks this to open the Select2 dropdown and reveal the search field
	public Locator getTeamSelect2Container() {
		return page.locator("#select2-teamId-container");
	}

	public Locator getTeamSearchField() {
		return page.locator(".select2-search__field");
	}

	// Exact-text match — selects only the option whose full text equals teamName
	public Locator getTeamOption(String teamName) {
		return page.locator("//li[contains(@class,'select2-results__option') and normalize-space()='" + teamName + "']");
	}

	public Locator getTeamAdminRadioBtn() {
		return page.locator("input[name='roleName'][value='29']");
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
