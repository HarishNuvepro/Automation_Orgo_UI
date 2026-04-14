package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class OrganizationDropdownPage {

	private Page page;

	public OrganizationDropdownPage(Page page) {
		this.page = page;
	}

	// ========== Users Section ==========

	public Locator getUserBtn() {
		return page.locator("//a[@href='/users' and @class='dropdown-header']");
	}

	public Locator getCreateUserTab() {
		return page.locator("//a[@href='/users/create']");
	}

	public Locator getImportUserTab() {
		return page.locator("//a[@href='/users/import']");
	}

	// ========== Roles Section ==========

	public Locator getRoleTab() {
		return page.locator("//a[@href='/roles' and @class='dropdown-header']");
	}

	public Locator getCreateRoleTab() {
		return page.locator("//a[@href='/roles/create']");
	}

	// ========== Teams Section ==========

	public Locator getTeamsTab() {
		return page.locator("//a[@href='/teams' and @class='dropdown-header']");
	}

	public Locator getCreateTeamTab() {
		return page.locator("//a[@href='/teams/create']");
	}

	// ========== Labs Section ==========

	public Locator getLabsTab() {
		return page.locator("//a[@class='dropdown-toggle' and contains(text(),'Labs')]");
	}

	public Locator getRequestNewLabTab() {
		return page.locator("//a[@href='/store']").first();
	}

	// ========== More Section ==========

	public Locator getDefineApprovalProcessLink() {
		return page.locator("//a[@href='/approvals/processes']");
	}

	public Locator getRebrandingLink() {
		return page.locator("//a[@href='/settings/rebranding']");
	}

	// ========== Legacy Methods (for backward compatibility) ==========

	public Locator getUserBtnLegacy() {
		return page.locator("//a[text()='Users']");
	}

	public Locator getCreateUserTabLegacy() {
		return page.locator("//a[text()='Create new User']");
	}

	public Locator getImportUserTabLegacy() {
		return page.locator("//a[text()='Import Users']");
	}

	public Locator getRoleTabLegacy() {
		return page.locator("//a[text()='Roles']");
	}

	public Locator getCreateRoleTabLegacy() {
		return page.locator("//a[text()='Create new Role']");
	}

	public Locator getTeamsTabLegacy() {
		return page.locator("//a[text()='Teams']");
	}

	public Locator getCreateTeamTabLegacy() {
		return page.locator("//a[text()='Create new Team']");
	}

}
