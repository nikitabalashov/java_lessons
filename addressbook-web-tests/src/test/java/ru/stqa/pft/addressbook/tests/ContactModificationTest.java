package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import org.testng.Assert;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTest extends TestBase{
@Test
public void  testContactModification () {

  //app.getNavigationHelper().gotoHomePage();
  ContactData contact = new ContactData("Nikita2",
            "Balashov2", "Prigazhunya2", "SPBU2", "Bransk2", "666-66662", "sasha.firsenkova@ya.ru", "testio1");
  ContactData contactModification = new ContactData("Nikita",
          "Balashov", "Prigazhunya2", "SPBU2", "Bransk2", "666-66662", "sasha.firsenkova@ya.ru", "testio1");
  if (! app.getContactHelper().isThereAContact()){
    app.getContactHelper().createContact(contact, true);
  }
  List<ContactData> before = app.getContactHelper().getContactList();
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillFieldsContact(contactModification, false);
  app.getContactHelper().submitContactModification();
  app.getContactHelper().gotoHomePage();
  List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());
    before.remove(0);
    before.add(contactModification);
  Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }
}
