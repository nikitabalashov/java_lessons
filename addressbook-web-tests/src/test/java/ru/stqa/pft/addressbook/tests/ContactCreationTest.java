package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactCreationTest extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactFromXml() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/contacts.xml")));
    String xml = "";
    String line = reader.readLine();
    while (line != null) {
      xml += line;
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
    return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }


  @DataProvider
  public Iterator<Object[]> validContactFromJson() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/contacts.json")));
    String json = "";
    String line = reader.readLine();
    while (line != null) {
      json += line;
      line = reader.readLine();
    }
    Gson gson = new Gson();
    List<ContactData> contact = gson.fromJson(json, new TypeToken<List<ContactData>>() {
    }.getType());
    return contact.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
  }









  @Test(dataProvider = "validContactFromJson")
  public void testNewContactCreation(ContactData contact) throws Exception {
    Contacts before = app.contact().all();
    app.contact().create(contact, true);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }



  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all();
    File photo = new File("src/test/java/ru/stqa/pft/addressbook/resources/Photo.png");
    ContactData contact = new ContactData().
            withFirstname("Nikita").
            withLastname("Balashov").
            withNickname("Beliash").
            withCompany("Dins").
            withAddress("Vitebsk").
            withMobile("555-5555").
            withWork("12345").
            withPhoto(photo).
            withEmail("nikita.balashov@nordigy.ru").
            withGroup("Test1");
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


