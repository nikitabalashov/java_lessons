package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import org.testng.Assert;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTest extends TestBase{


  @Test
  public void testContactCreation() throws Exception {
    //app.getContactHelper().gotoAddNewContact();
    //app.getContactHelper().createContact(new ContactData("Nikita",
    List<ContactData> before = app.getContactHelper().getContactList();
    ContactData contact = new ContactData("Nikita",
            "Balashov", "Beliash", "Dins", "Vitebsk", "555-5555", "nikita.balashov@nordigy.ru", "testio1");
    app.getContactHelper().createContact(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);
  }
}


