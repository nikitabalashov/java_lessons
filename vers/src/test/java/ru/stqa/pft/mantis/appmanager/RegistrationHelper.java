package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

//  private ApplicationManager app;
//  private WebDriver wd;

  public RegistrationHelper(ApplicationManager app) {
    //this.app = app;
    //wd = app.getDriver();
    super(app);
  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.xpath("//input[@class='width-40 pull-right btn btn-success btn-inverse bigger-110']"));
  }

  public void finish(String confirmationLink, String password, String username) {
    wd.get(confirmationLink);
    type(By.xpath("//input[@id = 'realname']"),username);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.xpath("//button[@type = 'submit']"));
  }

  public void finishChangePasswordForUser(String confirmationLink, String password, String username) {
    wd.get(confirmationLink);
    type(By.xpath("//input[@id = 'realname']"),username);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.xpath("//button[@type = 'submit']"));
  }

  public void registrationAdminFromUI(String username, String password) {
    wd.get(app.getProperty("web.baseUrl"));
    type(By.name("username"), username);
    click(By.xpath("//input[@type = 'submit']"));
    type(By.name("password"), password);
    click(By.xpath("//input[@type = 'submit']"));
  }
}