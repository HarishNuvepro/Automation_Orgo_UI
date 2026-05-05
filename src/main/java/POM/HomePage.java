package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage {

	private Page page;

	public HomePage(Page page) {
		this.page = page;
	}

	// ========== Navigation Menu Dropdowns ==========

	public Locator getHomeDropdown() {
		return page.locator("//a[text()='Home ']");
	}

	public Locator getLabsDropdown() {
		return page.locator("//a[text()='Labs ']");
	}

	public Locator getOrganizationDropdown() {
		return page.locator("//a[text()='Organization ']");
	}

	public Locator getSettingsDropdown() {
		return page.locator("//a[text()='Settings ']");
	}

	public Locator getReportsDropdown() {
		return page.locator("//a[text()='Reports ']");
	}

	public Locator getHelpDropdown() {
		return page.locator("//a[text()='Help ']");
	}

	// ========== Search and Navigation ==========

	public Locator getSearchMenuInput() {
		return page.locator("#master__menu__search");
	}

	public Locator getSearchTxtBox() {
		return page.locator("#select2-master__menu__search-container");
	}

	public Locator getNavHomeLink() {
		return page.locator("#navHome");
	}

	// ========== User Profile and Logout ==========

	public Locator getUserProfileDropdown() {
		return page.locator("//li[@class='dropdown']//a[@class='dropdown-toggle']");
	}

	public Locator getMyAccountLink() {
		return page.locator("//a[@href='https://qalb.cloudloka.com/user/profile']");
	}

	public Locator getChangePasswordLink() {
		return page.locator("//a[@href='https://qalb.cloudloka.com/user/profile/changepassword']");
	}

	public Locator getSignOutLink() {
		return page.locator("//a[@href='https://qalb.cloudloka.com/user/custom/logout']");
	}

	public Locator getSignoutBtn() {
		return page.locator("//a[@role='button']");
	}

	public Locator getLogoutBtn() {
		return page.locator("//a[contains(@href,'logout')]");
	}

	// ========== Company and Team Selection ==========

	public Locator getCompanynameTab() {
		return page.locator(".companyName");
	}

	public Locator getTeamNameTab() {
		return page.locator("//span[text()='Select Team']");
	}

	public Locator getTeamDropdown() {
		return page.locator("#teamDropdown");
	}

	public Locator getTeamDropdownSearchTxt() {
		return page.locator("#teamdropdownsearch");
	}

	// ========== Dashboard Tabs ==========

	public Locator getAdminServicesTab() {
		return page.locator("//a[@href='#AdminServicesTab']");
	}

	public Locator getMyLabsTab() {
		return page.locator("a[href*='MyService'], a[href='#MyServiceTab']");
	}

	public Locator getPowerBIDashboardTab() {
		return page.locator("//a[@href='#PowerbiDashboard']");
	}

	// ========== Dashboard Charts ==========

	public Locator getTeamsChart() {
		return page.locator("#chart-Teams");
	}

	public Locator getUsersChart() {
		return page.locator("#chart-Users");
	}

	public Locator getLabsChart() {
		return page.locator("#chart-Subscriptions");
	}

	public Locator getPlansChart() {
		return page.locator("#chart-Products");
	}

	public Locator getCompaniesChart() {
		return page.locator("#chart-Companies");
	}

	// ========== Recent Labs and Logs ==========

	public Locator getRecentLabsContainer() {
		return page.locator("#recentLabItems");
	}

	public Locator getRecentLogsContainer() {
		return page.locator("#recentLogs");
	}

	// ========== Lab Container ==========

	public Locator getLabContainer() {
		return page.locator("#labContainer");
	}

	public Locator getLabLoadAnimation() {
		return page.locator("#lab-load-anim");
	}

	public Locator getNoLabsMessage() {
		return page.locator("#no_labs_message");
	}

	// ========== Plan Listing ==========

	public Locator getPlanListingContainer() {
		return page.locator("#plan_listing_container");
	}

	public Locator getPlanListTiles() {
		return page.locator("#plan_list_tiles");
	}

	public Locator getPlanSearchInput() {
		return page.locator("#planSearch");
	}

	public Locator getClearSearchBtn() {
		return page.locator("#clearSearch");
	}

	public Locator getPlanOffersCollapseBtn() {
		return page.locator("#plan_offers_collapse");
	}

	// ========== Lab Request Modal ==========

	public Locator getRequestLabModal() {
		return page.locator("#requestLabModal");
	}

	public Locator getLabRequestPlanIdInput() {
		return page.locator("#lab_request_planId");
	}

	public Locator getLabRequestPlanNameInput() {
		return page.locator("#lab_request_planName");
	}

	public Locator getLabRequestRemarksTextarea() {
		return page.locator("#lab_request_remarks");
	}

	public Locator getSubmitLabRequestBtn() {
		return page.locator("#submitLabRequestBtn");
	}

	// ========== Power BI Dashboard ==========

	public Locator getPowerBILaunchBtn() {
		return page.locator("#powerbi_launch_button");
	}

	public Locator getPowerBIUsernameInput() {
		return page.locator("#powerbi_username");
	}

	public Locator getPowerBIPasswordInput() {
		return page.locator("#password");
	}

	public Locator getShowPasswordBtn() {
		return page.locator("#showPass");
	}

	// ========== Global Elements ==========

	public Locator getGlobalTimeZoneInput() {
		return page.locator("#__globalTimeZone");
	}

	public Locator getShowPlanInput() {
		return page.locator("#show_plan");
	}

	public Locator getToTopBtn() {
		return page.locator("#toTop");
	}

	public Locator getBrandLogo() {
		return page.locator(".brand-logo");
	}

	// ========== Notification ==========

	public Locator getNotificationMsg() {
		return page.locator("#msgNotification");
	}

	// ========== Legacy Methods (kept for backward compatibility) ==========

	public Locator getMyservicesBtn() {
		return page.locator("//a[text()='My Services']");
	}

	public Locator getAdminservicesBtn() {
		return page.locator("//a[text()='Admin Services']");
	}

	public Locator getMangeDropdown() {
		return page.locator("//a[text()='Manage']");
	}

	public Locator getMSPSettingBtn() {
		return page.locator("//a[text()='Settings']");
	}

	public Locator getTeamdropdownSerachTxt() {
		return page.locator("#teamdropdownsearch");
	}

	// ========== Dashboard Tabs Navigation ==========

	public Locator getDashboardTabs() {
		return page.locator("#dashboard_tabs");
	}

	// ========== Company Name and Switch ==========

	public Locator getCompanyNameDiv() {
		return page.locator("#commpanyNameDiv");
	}

	public Locator getSwitchBlockDiv() {
		return page.locator("#switch_block");
	}

	// ========== Team List Links ==========

	public Locator getTeamListLink(String teamId) {
		return page.locator("//a[@class=' teamlistlink' and @teamid='" + teamId + "']");
	}

	public Locator getAllTeamListLinks() {
		return page.locator("//a[@class=' teamlistlink']");
	}

	// ========== More Labs Button ==========

	public Locator getMoreLabsBtn() {
		return page.locator("#more_labs");
	}

	// ========== Lab Notes ==========

	public Locator getWithoutLabNote() {
		return page.locator("#without_lab_note");
	}

	public Locator getWithLabNote() {
		return page.locator("#with_lab_note");
	}

	// ========== Plan Search Container ==========

	public Locator getPlanSearchContainer() {
		return page.locator("#planSearchContainer");
	}

	public Locator getPlanListContainer() {
		return page.locator("#plan_list");
	}

	public Locator getPlanCount() {
		return page.locator("#plan_count");
	}

	public Locator getPlanListingContainerHeader() {
		return page.locator("#plan_listing_container_header");
	}

	// ========== Plan Load Animation ==========

	public Locator getPlanLoadAnimation() {
		return page.locator("#plan-load-anim");
	}

	// ========== Request Lab Modal Elements ==========

	public Locator getLabRequestProgress() {
		return page.locator("#lab_request_progress");
	}

	public Locator getRequestLabModalCancelBtn() {
		return page.locator("//div[@id='requestLabModal']//button[text()='Cancel']");
	}

	// ========== Footer ==========

	public Locator getFooterDiv() {
		return page.locator("#footer_div");
	}

	// ========== Navigation Header ==========

	public Locator getHeaderNav() {
		return page.locator("#header");
	}

	public Locator getNavbarCollapse() {
		return page.locator("#navbar");
	}

	// ========== User Dropdown Menu ==========

	public Locator getUserDropdownMenu() {
		return page.locator("//li[@class='dropdown']//a[@class='dropdown-toggle']");
	}

	// ========== Page Container ==========

	public Locator getOrgoPageContainer() {
		return page.locator("#orgoPageContainer");
	}

	// ========== Team Dropdown Items ==========

	public Locator getTeamDropDownItems() {
		return page.locator("#teamDropDownItems");
	}

	public Locator getTeamListLinkByName(String teamName) {
		return page.locator("//a[@class=' teamlistlink' and text()='" + teamName + "']");
	}

}
