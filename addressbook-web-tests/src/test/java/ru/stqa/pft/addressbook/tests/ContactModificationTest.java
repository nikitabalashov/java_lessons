package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactModificationTest extends TestBase{

  @BeforeMethod
  public void ensurePrecondition() {
    if(app.db().groups().size() == 0){
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
    ContactData contact = new ContactData().withFirstname("Nikita").withLastname("Balashov").withNickname("Beliash").withCompany("Dins").
            withAddress("Vitebsk").withMobile("555-5555").withWork("12345").withEmail("nikita.balashov@nordigy.ru")
            .inGroup(new GroupData().withName("testio1"));
      //      .withGroup("testio1");
    //if (! app.contact().isThereAContact()){
    if (app.db().contacts().size() == 0) {
      app.contact().create(contact, true);
    }
  }


  @Test
public void  testContactModification () {
    Contacts before = app.db().contacts();
  //  Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();

    ContactData contact = new ContactData().withId(modifiedContact.getId())
            .withFirstname("Nikita")
            .withLastname("Balashov")
            .withNickname("Beliash")
            .withCompany("Dins").
            withAddress("Vitebsk")
            .withMobile("555-5555")
            .withWork("12345")
            .withEmail("nikita.balashov@nordigy.ru")
         //   .withGroup("testio1");
            .inGroup(new GroupData().withName("test1"));
   // app.contact().modify(contact);
    assertThat(app.contact().count(), equalTo(before.size()));
  //  Contacts after = app.contact().all();
    Contacts after = app.db().contacts();
    assertThat(after.size(), equalTo(before.size()));
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListInUI();


   // before.add(contactModification);

  }
}
