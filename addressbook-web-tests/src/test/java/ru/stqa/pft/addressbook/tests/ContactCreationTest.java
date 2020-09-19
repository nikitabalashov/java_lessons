package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;

public class ContactCreationTest extends TestBase{


  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    File photo = new File("src/test/java/ru/stqa/pft/addressbook/resources/Photo.png");
    ContactData contact = new ContactData().withFirstname("Nikita").withLastname("Balashov").withNickname("Beliash").withCompany("Dins").
            withAddress("Vitebsk").withMobile("555-5555").withWork("12345").withPhoto(photo).withEmail("nikita.balashov@nordigy.ru").withGroup("Test1");
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();

   // assertThat(after.size(), equalTo(before.size()+1));
    assertThat(after,equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));

/*
    app.getContactHelper().create(contact, true);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(after, before);

 */
  }
}


