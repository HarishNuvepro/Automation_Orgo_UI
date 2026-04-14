package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class EditUserPage {

	private Page page;

	public EditUserPage(Page page) {
		this.page = page;
	}

	public Locator getFirstNameTxt() {
		return page.locator("first_name");
	}

	public Locator getLastNameTxt() {
		return page.locator("last_name");
	}

	public Locator getEmailTxt() {
		return page.locator("email");
	}

	public Locator getEmployeeId() {
		return page.locator("employee_id");
	}

	public Locator getLoginIdTxt() {
		return page.locator("loginId");
	}

	public Locator getUpdatePasswordCheckBox() {
		return page.locator("#updatePassword");
	}

	public Locator getSaveBtn() {
		return page.locator("#update_user_submit");
	}

	public Locator getCancelBtn() {
		return page.locator("//input[@class='btn btn-default']");
	}

}
