package ru.stqa.pft.addressbook.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;
import org.testng.Assert;



public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    ContactData contact = new ContactData("Alexandra2",
            "Firsenkova2", "Prigazhunya2", "SPBU2", "Bransk2", "666-66662", "sasha.firsenkova@ya.ru", "testio1");
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(contact, true);
    }
   List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().chooseContact(before.size() - 1);
    app.getContactHelper().removeContactForMainPage();
    app.getNavigationHelper().acceptAlert();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }
}


