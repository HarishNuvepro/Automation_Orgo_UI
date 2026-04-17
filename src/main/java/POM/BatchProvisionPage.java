package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BatchProvisionPage {

    private Page page;

    public BatchProvisionPage(Page page) {
        this.page = page;
    }

    public Locator getCreateBtn() {
        return page.locator("#addBtn");
    }

    public Locator getBatchNameInput() {
        return page.locator("#provision_name");
    }

    public Locator getBatchDescriptionInput() {
        return page.locator("#provision_description");
    }

    public Locator getNextBtn() {
        return page.locator(".wizardButton.next_button");
    }

    public Locator getUserSearchInput() {
        return page.locator("#stringSearch");
    }

    public Locator getUserSearchBtn() {
        return page.locator("#doSearchBtn");
    }

    public Locator getUserCheckbox() {
        return page.locator("#usersListTable tbody tr:first-child td.select-checkbox");
    }

    public Locator getPlanSearchInput() {
        return page.locator("#compPlansListTable_filter input");
    }

    public Locator getPlanCheckbox() {
        return page.locator("#compPlansListTable tbody tr:first-child td.select-checkbox");
    }

    public Locator getFinishBtn() {
        return page.locator("button.btn-success.wizardButton[data-target='#confirmProvision']");
    }

    public Locator getConfirmBtn() {
        return page.locator("#confirmProvisionBtn");
    }

    public Locator getUserListedInSummary() {
        return page.locator("#userCount");
    }

    public Locator getStatusInSummary() {
        return page.locator("#provisionStatus");
    }

    public Locator getLabIdInSummary() {
        return page.locator("#labId");
    }

    public Locator getPreviousBtn() {
        return page.locator(".wizardButton.previous");
    }

    public Locator getCancelBtn() {
        return page.locator(".wizardButton.cancel");
    }

    public Locator getPlanRowById(String planId) {
        return page.locator("#compPlansListTable tbody tr td:text('" + planId + "')").first();
    }

    public Locator getPlanRowClick(String planId) {
        return page.locator("#compPlansListTable tbody tr:has(td:text('" + planId + "'))");
    }

    public Locator getUserRowByEmail(String email) {
        return page.locator("#usersListTable tbody tr:has(td:text('" + email + "'))");
    }

    public Locator getBatchSummaryTable() {
        return page.locator("#batchSummaryTable");
    }

    public Locator getBatchSummaryRowByEmail(String email) {
        return page.locator("#batchSummaryTable tbody tr:has(td:text('" + email + "'))");
    }

    public Locator getBatchSummaryStatusByEmail(String email) {
        return page.locator("#batchSummaryTable tbody tr:has(td:text('" + email + "')) td:nth-child(1)");
    }

    public Locator getBatchSummaryLabIdByEmail(String email) {
        return page.locator("#batchSummaryTable tbody tr:has(td:text('" + email + "')) td:nth-child(5)");
    }

    public Locator getBatchSummaryDetailsByEmail(String email) {
        return page.locator("#batchSummaryTable tbody tr:has(td:text('" + email + "')) td:nth-child(6)");
    }

    public Locator getAllBatchSummaryRows() {
        return page.locator("#batchSummaryTable tbody tr");
    }

    public Locator getSearchOptionDropdown() {
        return page.locator("#searchItemDisplay");
    }

    public Locator getSearchOptionByText(String optionText) {
        return page.locator("#searchTypes li a:text('" + optionText + "')");
    }

    public Locator getAllLabsSearchInput() {
        return page.locator("#stringSearch");
    }

    public Locator getAllLabsSearchBtn() {
        return page.locator("#doSearchBtn");
    }

    public Locator getSelectAllCheckbox() {
        return page.locator("#mySubscriptionsTable th.select-checkbox");
    }

    public Locator getSyncLabStatusBtn() {
        return page.locator("#nuvelinkSyncBtn");
    }

    public Locator getLabRowById(String labId) {
        return page.locator("#mySubscriptionsTable tbody tr:has(td:text('" + labId + "'))");
    }

    public Locator getLabLatestActionByLabId(String labId) {
        return page.locator("#mySubscriptionsTable tbody tr:has(td:text('" + labId + "')) td:nth-child(7)");
    }

    public Locator getLabLatestActionStatusByLabId(String labId) {
        return page.locator("#mySubscriptionsTable tbody tr:has(td:text('" + labId + "')) td:nth-child(8) label");
    }

    public Locator getLazyCreateOption() {
        return page.locator("label:has-text('Create on First Start')");
    }
}