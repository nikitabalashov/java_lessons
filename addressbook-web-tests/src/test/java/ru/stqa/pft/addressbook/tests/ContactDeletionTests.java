package ru.stqa.pft.addressbook.tests;

import java.util.List;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    ContactData contact = new ContactData().withFirstname("Nikita").withLastname("Balashov").withNickname("Beliash").withCompany("Dins").
            withAddress("Vitebsk").withMobile("555-5555").withEmail("nikita.balashov@nordigy.ru").withGroup("testio1");
    if (! app.contact().isThereAContact()){
      app.contact().create(contact, true);
    }
  }





  @Test
  public void testContactDeletion() throws Exception {

    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().deleteContactFromMainPage(deletedContact);
    Contacts after = app.contact().all();

    assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(deletedContact)));

/*
   List<ContactData> before = app.contact().getContactList();
    app.contact().chooseContact(before.size() - 1);
    app.contact().removeContactForMainPage();
    app.goTo().acceptAlert();
    app.goTo().gotoHomePage();
    List<ContactData> after = app.contact().getContactList();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);

 */
  }
}


