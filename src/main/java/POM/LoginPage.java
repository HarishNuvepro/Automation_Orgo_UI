package POM;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

   private Page page;

   public LoginPage(Page page) {
      this.page = page;
   }

   // Main login dropdown button in header
   public Locator getLoginDropdownBtn() {
      return page.locator("#loginDrop");
   }

   // Header login form elements (dropdown menu)
   public Locator getHeaderUsernameTxt() {
      return page.locator("#userName");
   }

   public Locator getHeaderPasswordTxt() {
      return page.locator("#password");
   }

   public Locator getHeaderSignInBtn() {
      return page.locator("#com_user_login");
   }

   // Simple login form elements (main page)
   public Locator getUsernameTxt() {
      return page.locator("#sl_userName");
   }

   public Locator getPasswordTxt() {
      return page.locator("#sl_password");
   }

   public Locator getSignInBtn() {
      return page.locator("#user_sign_in_button");
   }

   // Forgot Login ID link
   public Locator getForgotLoginIdLink() {
      return page.locator("#sl_forgetLoginId");
   }

   // Forgot Password link
   public Locator getForgotPasswordLink() {
      return page.locator("#sl_forgetPassword");
   }

   // Forgot Login ID Modal elements
   public Locator getForgotLoginIdEmailTxt() {
      return page.locator("#sl_mail");
   }

   public Locator getForgotLoginIdSubmitBtn() {
      return page.locator("#sl_forgotLoginIdSubmit");
   }

   // Reset Password Modal elements
   public Locator getResetLoginIdTxt() {
      return page.locator("#sl_loginid");
   }

   public Locator getResetCodeTxt() {
      return page.locator("#sl_resetCode");
   }

   public Locator getResetPasswordTxt() {
      return page.locator("#sl_resetpassword");
   }

   public Locator getResetConfirmPasswordTxt() {
      return page.locator("#sl_confirmPassword");
   }

   public Locator getSendResetCodeBtn() {
      return page.locator("#sl_sendResetCode");
   }

   public Locator getResetPasswordBtn() {
      return page.locator("#sl_resetPasswordbtn");
   }

   // Brand logo
   public Locator getBrandLogo() {
      return page.locator("img.brand-logo");
   }

   // Login error message
   public Locator getLoginErrorMsg() {
      return page.locator("#sl_loginError");
   }

   // Notification messages
   public Locator getLoginNotificationMsg() {
      return page.locator("#sl_msgNotification");
   }

   public Locator getResetNotificationMsg() {
      return page.locator("#sl_msgResetNotification");
   }

   public Locator getForgotLoginIdNotificationMsg() {
      return page.locator("#sl_msgResetForgotLoginId");
   }

   // Modal close buttons
   public Locator getModalCloseBtn() {
      return page.locator(".glyphicon-remove-sign");
   }

}
