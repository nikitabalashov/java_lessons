package ru.stqa.pft.addressbook.tests;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class ContactPhoneTests extends TestBase {
  @Test
  public void testContactPhone() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

 //   assertThat(contact.getMobile(), equalTo(cleaned(contactInfoFromEditForm.getMobile())));
 //   assertThat(contact.getWork(), equalTo(cleaned(contactInfoFromEditForm.getWork())));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getMobile(), contact.getWork())
            .stream()
            .filter((s) -> !s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }
    private String mergeEmails(ContactData contact) {
      return Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
              .stream()
              .filter((s)-> !s.equals(""))
              .map(ContactPhoneTests::cleaned)
              .collect(Collectors.joining("\n"));
    }




  public static String cleaned(String phones){
      return phones.replaceAll("\\s","").replaceAll("[-()]","");
    }

}

