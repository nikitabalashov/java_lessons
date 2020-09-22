package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import static org.testng.Assert.assertTrue;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

public class RegistrationTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }


  @Test
  //public void testRegistration() {
  //  app.registration().start("user", "user1@localhost.localadmin");
  public void testRegistration() throws InterruptedException, IOException {
    long now = System.currentTimeMillis();
    String email = String.format("user%s@localhost.localdomain",now);
    String user =  String.format("iser%s",now);
    String password = "password";
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 100000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password, user);
    assertTrue(app.newSession().login(user, password));

  }



  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}