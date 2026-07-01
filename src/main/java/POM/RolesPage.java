package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RolesPage {

    private Page page;

    public RolesPage(Page page) {
        this.page = page;
    }

    // ── Navigation ─────────────────────────────────────────────────────────────

    public Locator getCreateNewRoleLink() {
        return page.locator("a[href='/roles/create']");
    }

    // ── Action Buttons (Roles List Page) ───────────────────────────────────────

    public Locator getCreateBtn() {
        return page.locator("#addBtn");
    }

    public Locator getViewBtn() {
        return page.locator("#viewBtn");
    }

    public Locator getEditBtn() {
        return page.locator("#editBtn");
    }

    public Locator getRemoveBtn() {
        return page.locator("#removeBtn");
    }

    public Locator getCloneBtn() {
        return page.locator("#copyBtn");
    }

    // ── Remove Role Modal ──────────────────────────────────────────────────────

    public Locator getRemoveRoleModal() {
        return page.locator("#removeRole");
    }

    public Locator getConfirmRemoveBtn() {
        return page.locator("#removeRole .btn-danger, #removeRole button[type='submit']").first();
    }

    public Locator getCancelRemoveBtn() {
        return page.locator("#removeRole button[data-dismiss='modal']").first();
    }

    // ── Clone Role Modal ───────────────────────────────────────────────────────

    public Locator getCloneRoleModal() {
        return page.locator("#copyRole");
    }

    public Locator getConfirmCloneBtn() {
        return page.locator("#copyRole .btn-primary, #copyRole button[type='submit']").first();
    }

    public Locator getCancelCloneBtn() {
        return page.locator("#copyRole button[data-dismiss='modal']").first();
    }

    // ── Roles Table ────────────────────────────────────────────────────────────

    public Locator getRolesTable() {
        return page.locator("table#rolesTable");
    }

    public Locator getRoleRowByName(String roleName) {
        return page.locator("//table[@id='rolesTable']//tr[td[contains(text(),'" + roleName + "')]]");
    }

    public Locator getRoleCheckboxByName(String roleName) {
        return page.locator("//table[@id='rolesTable']//tr[td[contains(text(),'" + roleName + "')]]//input[@type='checkbox']");
    }

    // ── Create / Edit Role Form ────────────────────────────────────────────────

    public Locator getRoleNameInput() {
        return page.locator("#roleName");
    }

    public Locator getRoleDescriptionInput() {
        return page.locator("#roleDesc");
    }

    public Locator getRoleScopeDropdown() {
        return page.locator("#roleScope");
    }

    public Locator getCreateRoleSubmitBtn() {
        return page.locator("#createRoleSubmit");
    }

    public Locator getCancelRoleBtn() {
        return page.locator("#cancel_role_submit");
    }

    // ── Permissions Section ────────────────────────────────────────────────────

    // Select any individual permission checkbox by its value attribute
    // e.g. getPermissionCheckbox("approval_process_create")
    public Locator getPermissionCheckbox(String permValue) {
        return page.locator("input[name='perms[]'][value='" + permValue + "']");
    }

    // Select the category (group-level) checkbox by group label text
    // e.g. getCategoryCheckboxByGroup("Approval")
    public Locator getCategoryCheckboxByGroup(String groupName) {
        return page.locator("//label[@class='groupTitle' and text()='" + groupName + "']/preceding-sibling::input[@class='category']");
    }

    // Permission container divs
    public Locator getCompanyPermissionsDiv() {
        return page.locator("#companyPermSetDiv");
    }

    public Locator getTeamPermissionsDiv() {
        return page.locator("#teamPermSetDiv");
    }

    public Locator getMspPermissionsDiv() {
        return page.locator("#mspPermSetDiv");
    }

    // ── Permission Values — Company Scope ──────────────────────────────────────
    // Approval
    public Locator getPermApprovalCreate()   { return getPermissionCheckbox("approval_process_create"); }
    public Locator getPermApprovalView()     { return getPermissionCheckbox("approval_process_read"); }
    public Locator getPermApprovalEdit()     { return getPermissionCheckbox("approval_process_update"); }
    public Locator getPermApprovalDelete()   { return getPermissionCheckbox("approval_process_delete"); }
    public Locator getPermApprovalApprove()  { return getPermissionCheckbox("approval_process_approver"); }

    // Batch Operations
    public Locator getPermBatchCreate()          { return getPermissionCheckbox("batch_operations_create"); }
    public Locator getPermBatchView()            { return getPermissionCheckbox("batch_operations_view"); }
    public Locator getPermBatchDelete()          { return getPermissionCheckbox("batch_operations_delete"); }
    public Locator getPermBatchAddUsers()        { return getPermissionCheckbox("batch_operations_add_users"); }
    public Locator getPermBatchModifyDuration()  { return getPermissionCheckbox("batch_operations_modify_duration"); }

    // Billing
    public Locator getPermBillingCloudCost() { return getPermissionCheckbox("nl_cloud_cost"); }
    public Locator getPermBillingMargins()   { return getPermissionCheckbox("nl_margins"); }

    // Cloud Platform
    public Locator getPermCloudAccessCredentials() { return getPermissionCheckbox("cloud_access_credentials"); }
    public Locator getPermCloudPools()             { return getPermissionCheckbox("cloud_pools"); }

    // Cloud Storage
    public Locator getPermCloudStorageCreate() { return getPermissionCheckbox("cloud_storage_create"); }
    public Locator getPermCloudStorageView()   { return getPermissionCheckbox("cloud_storage_view"); }
    public Locator getPermCloudStorageEdit()   { return getPermissionCheckbox("cloud_storage_edit"); }
    public Locator getPermCloudStorageDelete() { return getPermissionCheckbox("cloud_storage_delete"); }

    // Help
    public Locator getPermHelpApiDoc()   { return getPermissionCheckbox("api_doc"); }
    public Locator getPermHelpLogs()     { return getPermissionCheckbox("all_audit_logs"); }

    // Labs (Company)
    public Locator getPermLabsFeedbacks()      { return getPermissionCheckbox("feedbacks"); }
    public Locator getPermLabsView()           { return getPermissionCheckbox("team_service_read"); }
    public Locator getPermLabsModifyDuration() { return getPermissionCheckbox("extend_duration"); }
    public Locator getPermLabsApplyPolicy()    { return getPermissionCheckbox("apply_policy"); }
    public Locator getPermLabsRemovePolicy()   { return getPermissionCheckbox("remove_policy"); }
    public Locator getPermLabsDelete()         { return getPermissionCheckbox("team_service_delete"); }
    public Locator getPermLabsControlPanel()   { return getPermissionCheckbox("team_service_control_panel"); }
    public Locator getPermLabsPerformActions() { return getPermissionCheckbox("perform_actions"); }
    public Locator getPermLabsMonitor()        { return getPermissionCheckbox("lab_monitor"); }
    public Locator getPermLabsBillingCategory(){ return getPermissionCheckbox("billing_categories"); }

    // Own Services
    public Locator getPermOwnDeleteLab()       { return getPermissionCheckbox("service_delete"); }
    public Locator getPermOwnControlPanel()    { return getPermissionCheckbox("own_service_control_panel"); }
    public Locator getPermOwnViewLogs()        { return getPermissionCheckbox("my_audit_logs"); }
    public Locator getPermOwnLabs()            { return getPermissionCheckbox("service_read"); }
    public Locator getPermOwnEditProfile()     { return getPermissionCheckbox("own_profile_edit"); }
    public Locator getPermOwnLabViewDetails()  { return getPermissionCheckbox("own_subscription_view"); }

    // Plan Category
    public Locator getPermPlanCategoryView()   { return getPermissionCheckbox("plan_cateogy_view"); }
    public Locator getPermPlanCategoryCreate() { return getPermissionCheckbox("plan_cateogy_create"); }
    public Locator getPermPlanCategoryEdit()   { return getPermissionCheckbox("plan_cateogy_edit"); }
    public Locator getPermPlanCategoryDelete() { return getPermissionCheckbox("plan_cateogy_delete"); }

    // Plans
    public Locator getPermPlansDelete()       { return getPermissionCheckbox("mng_plans_delete"); }
    public Locator getPermPlansCreate()       { return getPermissionCheckbox("mng_plans_create"); }
    public Locator getPermPlansViewCatalogs() { return getPermissionCheckbox("nl_view_catalogs"); }
    public Locator getPermPlansEdit()         { return getPermissionCheckbox("mng_plans_update"); }
    public Locator getPermPlansView()         { return getPermissionCheckbox("mng_plans_read"); }

    // Policies
    public Locator getPermPoliciesCreate() { return getPermissionCheckbox("mng_policy_create"); }
    public Locator getPermPoliciesDelete() { return getPermissionCheckbox("mng_policy_delete"); }
    public Locator getPermPoliciesEdit()   { return getPermissionCheckbox("mng_policy_update"); }
    public Locator getPermPoliciesView()   { return getPermissionCheckbox("mng_policy_read"); }

    // Reports
    public Locator getPermReportsSchedule()    { return getPermissionCheckbox("reports_schedule"); }
    public Locator getPermReportsDownload()    { return getPermissionCheckbox("reports_download"); }
    public Locator getPermReportsPowerBI()     { return getPermissionCheckbox("powerbi_report"); }
    public Locator getPermReportsApiAccess()   { return getPermissionCheckbox("api_automation_access"); }

    // Roles
    public Locator getPermRolesCreate() { return getPermissionCheckbox("mng_roles_create"); }
    public Locator getPermRolesEdit()   { return getPermissionCheckbox("mng_roles_update"); }
    public Locator getPermRolesView()   { return getPermissionCheckbox("mng_roles_read"); }
    public Locator getPermRolesDelete() { return getPermissionCheckbox("mng_roles_delete"); }

    // Schedules
    public Locator getPermSchedulesEdit()   { return getPermissionCheckbox("mng_schedule_update"); }
    public Locator getPermSchedulesView()   { return getPermissionCheckbox("mng_schedule_read"); }
    public Locator getPermSchedulesDelete() { return getPermissionCheckbox("mng_schedule_delete"); }
    public Locator getPermSchedulesCreate() { return getPermissionCheckbox("mng_schedule_create"); }

    // Settings
    public Locator getPermSettingsGeneral()       { return getPermissionCheckbox("general_settings"); }
    public Locator getPermSettingsNotifications() { return getPermissionCheckbox("notify_configs"); }
    public Locator getPermSettingsRebrand()       { return getPermissionCheckbox("comany_rebrand"); }

    // Teams
    public Locator getPermTeamsSwitch() { return getPermissionCheckbox("switch_teams"); }
    public Locator getPermTeamsView()   { return getPermissionCheckbox("mng_team_read"); }
    public Locator getPermTeamsEdit()   { return getPermissionCheckbox("mng_team_update"); }
    public Locator getPermTeamsCreate() { return getPermissionCheckbox("mng_team_create"); }
    public Locator getPermTeamsDelete() { return getPermissionCheckbox("mng_team_delete"); }

    // Tickets
    public Locator getPermTicketsView()    { return getPermissionCheckbox("tickets_view"); }
    public Locator getPermTicketsProcess() { return getPermissionCheckbox("tickets_process"); }

    // Users
    public Locator getPermUsersDelete() { return getPermissionCheckbox("mng_users_delete"); }
    public Locator getPermUsersEdit()   { return getPermissionCheckbox("mng_users_update"); }
    public Locator getPermUsersImport() { return getPermissionCheckbox("mng_users_import"); }
    public Locator getPermUsersCreate() { return getPermissionCheckbox("mng_users_create"); }
    public Locator getPermUsersView()   { return getPermissionCheckbox("mng_users_read"); }

    // ── Permission Values — Team Scope Only ────────────────────────────────────

    public Locator getPermLabStoreView()     { return getPermissionCheckbox("market_place"); }
    public Locator getPermOwnCreateLab()     { return getPermissionCheckbox("service_create"); }
    public Locator getPermTeamsAddUsers()    { return getPermissionCheckbox("mng_users_add"); }
    public Locator getPermLabsCreate()       { return getPermissionCheckbox("team_service_create"); }

}
