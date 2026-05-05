package Util;

import com.microsoft.playwright.Page;

import POM.AddToTeamModalPage;
import POM.BatchProvisionPage;
import POM.CreateUserPage;
import POM.EditUserPage;
import POM.HomeDropdownPage;
import POM.HomePage;
import POM.ImportUserPage;
import POM.LabControlPanelPage;
import POM.LabStorePage;
import POM.LabsDropdownPage;
import POM.LabsPage;
import POM.LoginPage;
import POM.OrganizationDropdownPage;
import POM.SubscribePlanPage;
import POM.UserPage;

public class Pages {

    private static final ThreadLocal<Pages> TL = new ThreadLocal<>();

    // Playwright page reference — used to create POM objects on first access
    private final Page page;

    // Lazily initialized — created only when first accessed by a test
    private LoginPage               loginPage;
    private HomePage                homePage;
    private CreateUserPage          createUserPage;
    private EditUserPage            editUserPage;
    private HomeDropdownPage        homeDropdownPage;
    private OrganizationDropdownPage organizationDropdownPage;
    private UserPage                userPage;
    private AddToTeamModalPage      addToTeamModalPage;
    private ImportUserPage          importUserPage;
    private LabsDropdownPage        labsDropdownPage;
    private LabStorePage            labStorePage;
    private LabsPage                labsPage;
    private SubscribePlanPage       subscribePlanPage;
    private LabControlPanelPage     labControlPanelPage;
    private BatchProvisionPage      batchProvisionPage;

    private Pages(Page page) {
        this.page = page;
        // No POM objects created here — instantiated on first use per getter
    }

    public static void loadPages(Page page) {
        TL.set(new Pages(page));
    }

    public static void remove() {
        TL.remove();
    }

    // ── Lazy getters — thread-safe because each thread owns its own Pages ─────

    public static LoginPage getLoginPage() {
        Pages p = TL.get();
        if (p.loginPage == null) p.loginPage = new LoginPage(p.page);
        return p.loginPage;
    }

    public static HomePage getHomePage() {
        Pages p = TL.get();
        if (p.homePage == null) p.homePage = new HomePage(p.page);
        return p.homePage;
    }

    public static CreateUserPage getCreateUserPage() {
        Pages p = TL.get();
        if (p.createUserPage == null) p.createUserPage = new CreateUserPage(p.page);
        return p.createUserPage;
    }

    public static EditUserPage getEditUserPage() {
        Pages p = TL.get();
        if (p.editUserPage == null) p.editUserPage = new EditUserPage(p.page);
        return p.editUserPage;
    }

    public static HomeDropdownPage getHomeDropdownPage() {
        Pages p = TL.get();
        if (p.homeDropdownPage == null) p.homeDropdownPage = new HomeDropdownPage(p.page);
        return p.homeDropdownPage;
    }

    public static OrganizationDropdownPage getOrganizationDropdownPage() {
        Pages p = TL.get();
        if (p.organizationDropdownPage == null)
            p.organizationDropdownPage = new OrganizationDropdownPage(p.page);
        return p.organizationDropdownPage;
    }

    public static UserPage getUserPage() {
        Pages p = TL.get();
        if (p.userPage == null) p.userPage = new UserPage(p.page);
        return p.userPage;
    }

    public static AddToTeamModalPage getAddToTeamModalPage() {
        Pages p = TL.get();
        if (p.addToTeamModalPage == null) p.addToTeamModalPage = new AddToTeamModalPage(p.page);
        return p.addToTeamModalPage;
    }

    public static ImportUserPage getImportUserPage() {
        Pages p = TL.get();
        if (p.importUserPage == null) p.importUserPage = new ImportUserPage(p.page);
        return p.importUserPage;
    }

    public static LabsDropdownPage getLabsDropdownPage() {
        Pages p = TL.get();
        if (p.labsDropdownPage == null) p.labsDropdownPage = new LabsDropdownPage(p.page);
        return p.labsDropdownPage;
    }

    public static LabStorePage getLabStorePage() {
        Pages p = TL.get();
        if (p.labStorePage == null) p.labStorePage = new LabStorePage(p.page);
        return p.labStorePage;
    }

    public static LabsPage getLabsPage() {
        Pages p = TL.get();
        if (p.labsPage == null) p.labsPage = new LabsPage(p.page);
        return p.labsPage;
    }

    public static SubscribePlanPage getSubscribePlanPage() {
        Pages p = TL.get();
        if (p.subscribePlanPage == null) p.subscribePlanPage = new SubscribePlanPage(p.page);
        return p.subscribePlanPage;
    }

    public static LabControlPanelPage getLabControlPanelPage() {
        Pages p = TL.get();
        if (p.labControlPanelPage == null) p.labControlPanelPage = new LabControlPanelPage(p.page);
        return p.labControlPanelPage;
    }

    public static BatchProvisionPage getBatchProvisionPage() {
        Pages p = TL.get();
        if (p.batchProvisionPage == null) p.batchProvisionPage = new BatchProvisionPage(p.page);
        return p.batchProvisionPage;
    }
}
