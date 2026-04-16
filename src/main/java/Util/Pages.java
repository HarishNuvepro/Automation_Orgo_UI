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

	public static LoginPage LoginPage;
	public static HomePage HomePage;
	public static CreateUserPage CreateUserPage;
	public static EditUserPage EditUserPage;
	public static HomeDropdownPage HomeDropdownPage;
	public static OrganizationDropdownPage OrganizationDropdownPage;
	public static UserPage UserPage;
	public static AddToTeamModalPage AddToTeamModalPage;
	public static ImportUserPage ImportUserPage;
	public static LabsDropdownPage LabsDropdownPage;
	public static LabStorePage LabStorePage;
	public static LabsPage LabsPage;
	public static SubscribePlanPage SubscribePlanPage;
	public static LabControlPanelPage LabControlPanelPage;
	public static BatchProvisionPage BatchProvisionPage;

	public static void loadPages(Page page) {
		LoginPage = new LoginPage(page);
		HomePage = new HomePage(page);
		CreateUserPage = new CreateUserPage(page);
		EditUserPage = new EditUserPage(page);
		HomeDropdownPage = new HomeDropdownPage(page);
		OrganizationDropdownPage = new OrganizationDropdownPage(page);
		UserPage = new UserPage(page);
		AddToTeamModalPage = new AddToTeamModalPage(page);
		ImportUserPage = new ImportUserPage(page);
		LabsDropdownPage = new LabsDropdownPage(page);
		LabStorePage = new LabStorePage(page);
		LabsPage = new LabsPage(page);
		SubscribePlanPage = new SubscribePlanPage(page);
		LabControlPanelPage = new LabControlPanelPage(page);
		BatchProvisionPage = new BatchProvisionPage(page);

	}

}
