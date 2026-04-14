package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class LabsDropdownPage {

	private Page page;

	public LabsDropdownPage(Page page) {
		this.page = page;
	}

	public Locator getLabsDropdownMenu() {
		return page.locator("//ul[@class='dropdown-menu mega-dropdown-menu']");
	}

	public Locator getLabsLink() {
		return page.locator("//a[@href='/subscriptions?type=all']");
	}

	public Locator getApproveLabRequestsLink() {
		return page.locator("//a[@href='/my/approvals']");
	}

	public Locator getBatchProvisioningLink() {
		return page.locator("//a[@href='/batch/provisioning']");
	}

	public Locator getCreateNewBatchLink() {
		return page.locator("//a[@href='/batch/subscriptions/create']");
	}

	public Locator getTicketsLink() {
		return page.locator("//a[@href='/tickets']");
	}

	public Locator getPlansLink() {
		return page.locator("//a[@href='/plans']");
	}

	public Locator getCreateNewPlanLink() {
		return page.locator("//a[@href='/plans/create']");
	}

	public Locator getPlanCategoriesLink() {
		return page.locator("//a[@href='/categories']");
	}

	public Locator getCreateNewPlanCategoryLink() {
		return page.locator("//a[@href='/category/create']");
	}

	public Locator getCompositionsLink() {
		return page.locator("//a[@href='/compositions']");
	}

	public Locator getCreateCompositionLink() {
		return page.locator("//a[@href='/compositions/create']");
	}

	public Locator getPoliciesLink() {
		return page.locator("//a[@href='/policies']");
	}

	public Locator getCreateNewPolicyLink() {
		return page.locator("//a[@href='/policies/create']");
	}

	public Locator getSchedulesLink() {
		return page.locator("//a[@href='/schedules']");
	}

	public Locator getCreateNewScheduleLink() {
		return page.locator("//a[@href='/schedules/create']");
	}

	public Locator getCatalogsLink() {
		return page.locator("//a[@href='/catalogs']");
	}

	public Locator getRequestNewLabLink() {
		return page.locator("//a[text()='Request new Lab']");
	}
}