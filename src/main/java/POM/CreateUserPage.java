package POM;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class CreateUserPage {
	
	private Page page;
	
	public CreateUserPage(Page page)
	{
	  this.page = page;
	}
	
	
	

	

	

	public Locator getFirstNameTxt() {
		return page.locator("#first_name");
	}

	public Locator getLastNameTxt() {
		return page.locator("#last_name");
	}

	public Locator getEmailIdTxt() {
		return page.locator("#email_id");
	}

	public Locator getEmployeeIdTxt() {
		return page.locator("#employee_id");
	}

	public Locator getLoginIdTxt() {
		return page.locator("#loginId");
	}

	public Locator getPasswordTxt() {
		return page.locator("#password");
	}

	public Locator getConfirmPasswordTxt() {
		return page.locator("#confirm_password");
	}

	

	

	
	
	public Locator getUserCreationSuccessMsgTxt() {
		return page.locator("//span[@data-notify='message' and (text()='Success')]");
	}

	
	
	

	public Locator getCreateSubmitBtn() {
		return page.locator("#create_user_submit");
	}
	
	
	
	
	
	

}
