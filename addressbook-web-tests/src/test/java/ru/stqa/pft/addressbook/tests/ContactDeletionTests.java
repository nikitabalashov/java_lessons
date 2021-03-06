package ru.stqa.pft.addressbook.tests;

import java.util.List;
import ru.stqa.pft.addressbook.model.GroupData;
import org.hamcrest.CoreMatchers;
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
    if(app.db().groups().size() == 0){
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
    ContactData contact = new ContactData().withFirstname("Nikita").withLastname("Balashov").withNickname("Beliash").withCompany("Dins").
            withAddress("Vitebsk").withMobile("555-5555").withWork("12345").withEmail("nikita.balashov@nordigy.ru")
            .inGroup(new GroupData().withName("test1"));
            //.withGroup("testio1");
    //if (! app.contact().isThereAContact()){
    if (app.db().contacts().size() == 0) {
      app.contact().create(contact, true);
    }
  }





  @Test
  public void testContactDeletion() throws Exception {
    Contacts before = app.db().contacts();
    //Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().deleteContactFromMainPage(deletedContact);
    assertThat(app.contact().count(), CoreMatchers.equalTo(before.size() - 1));
  //  Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
   // assertEquals(after.size(), before.size() - 1);
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

 */verifyContactListInUI();
  }
}


