package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{


  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().gotoAddNewContact();
    app.getContactHelper().createContact(new ContactData("Nikita", "Balashov", "Beliash", "Dins", "Vitebsk", "555-5555", "nikita.balashov@nordigy.ru", "testio1"), true);
    //
    //app.getContactHelper().fillFieldsContact(new ContactData("Nikita", "Balashov", "Beliash", "Dins", "Vitebsk", "555-5555", "nikita.balashov@nordigy.ru", "testio1"), true);
    //app.getContactHelper().submitContact();
   // app.getContactHelper().gotoHomePage();
   // app.logout();

  }
}


