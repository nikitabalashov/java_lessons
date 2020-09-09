package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactDeletionTests extends TestBase {




  @Test
  public void testContactDeletion() throws Exception {
    app.getContactHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Nikita", "Balashov", "Beliash", "Dins", "Vitebsk", "555-5555", "nikita.balashov@nordigy.ru", "testio1"), true);
    }
    app.getContactHelper().chooseContact();
    app.getContactHelper().deleteContact();

  }


}


