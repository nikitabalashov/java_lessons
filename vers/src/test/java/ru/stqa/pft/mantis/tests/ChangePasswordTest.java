package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTest extends TestBase {

  @Test
  public void testChangePassword() throws IOException, MessagingException {
    String loginAdmin = app.getProperty("web.adminLogin");
    String passwordAdmin = app.getProperty("web.adminPassword");
    app.registration().registrationAdminFromUI(loginAdmin,passwordAdmin);
    app.workWithUser().openUsersManagePage();
    UserData userForResetPassword = app.db().users().stream().iterator().next();
    String nameUserWithResetPassword = userForResetPassword.getName();
    String emailUserWithResetPassword = userForResetPassword.getEmail();
    String passwordForUserMail = "password";
    app.james().createUser(nameUserWithResetPassword, passwordForUserMail);
    app.workWithUser().openUserPageByName(nameUserWithResetPassword);
    app.workWithUser().resetUserPassword();
    List<MailMessage> mailMessages = app.james().waitForMail(nameUserWithResetPassword, passwordForUserMail, 60000);
    String confirmationLink = app.mail().findConfirmationLink(mailMessages, emailUserWithResetPassword);
    String newPassword = "new_password";
    app.registration().finishChangePasswordForUser(confirmationLink, newPassword, "userBeliash");
    assertTrue(app.newSession().login(nameUserWithResetPassword, newPassword));
  }
}