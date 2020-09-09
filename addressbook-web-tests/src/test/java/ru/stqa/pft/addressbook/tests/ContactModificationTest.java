package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase{
@Test
public void  testContactModification () {

  app.getNavigationHelper().gotoHomePage();
  if (! app.getContactHelper().isThereAContact()){
    app.getContactHelper().createContact(new ContactData("Sasha2", "Firsenkova2", "Prigazhunya2", "SPBU2", "Bransk2", "666-66662", "sasha.firsenkova@ya.ru", "testio1"), true);
  }
  app.getContactHelper().chooseContact();
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillFieldsContact(new ContactData("Sasha", "Firsenkova", "Prigazhunya", "SPBU", "Bransk", "666-6666", "sasha.firsenkova@ya.ru", null), false);
  app.getContactHelper().submitContactModification();
  app.getContactHelper().gotoHomePage();

}

}
