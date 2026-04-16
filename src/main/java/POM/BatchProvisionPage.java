package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BatchProvisionPage {

    private Page page;

    public BatchProvisionPage(Page page) {
        this.page = page;
    }

    public Locator getCreateBtn() {
        return page.locator("#createBatchProvisionBtn");
    }

    public Locator getBatchNameInput() {
        return page.locator("#batchName");
    }

    public Locator getBatchDescriptionInput() {
        return page.locator("#batchDescription");
    }

    public Locator getNextBtn() {
        return page.locator("//button[text()='Next']");
    }

    public Locator getUserSearchInput() {
        return page.locator("#userSearch");
    }

    public Locator getUserSearchBtn() {
        return page.locator("#userSearchBtn");
    }

    public Locator getUserCheckbox() {
        return page.locator(".user-checkbox:first-of-type");
    }

    public Locator getPlanSearchInput() {
        return page.locator("#planSearch");
    }

    public Locator getPlanCheckbox() {
        return page.locator(".plan-checkbox:first-of-type");
    }

    public Locator getFinishBtn() {
        return page.locator("//button[text()='Finish']");
    }

    public Locator getConfirmBtn() {
        return page.locator("//button[text()='Confirm']");
    }

    public Locator getUserListedInSummary() {
        return page.locator("#summaryUser");
    }

    public Locator getStatusInSummary() {
        return page.locator("#summaryStatus");
    }

    public Locator getLabIdInSummary() {
        return page.locator("#summaryLabId");
    }
}