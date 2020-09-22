package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class WorkWithUserHelper extends HelperBase {

  public WorkWithUserHelper(ApplicationManager app) {
    super(app);
  }

  public void openUsersManagePage() {
    click(By.xpath("//li[6]//a[1]"));
    click(By.xpath("//div[@class='main-content']//li[2]//a[1]"));
  }

  public void openUserPageByName (String name) {
    click(By.xpath(String.format("//a[contains(text(),'%s')]",name)));
  }

  public void resetUserPassword() {
    click(By.xpath("//form[@id='manage-user-reset-form']//input[@class='btn btn-primary btn-white btn-round']"));
  }
}