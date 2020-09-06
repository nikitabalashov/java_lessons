package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTest extends TestBase{
@Test
public void  testContactModification () {

  app.getNavigationHelper().gotoHomePage();
  app.getContactHelper().chooseContact();
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillFieldsContact(new ContactData("Sasha", "Firsenkova", "Bulba", "SPBU", "Bransk", "666-6666", "sasha.firsenkova@ya.ru"));
  app.getContactHelper().fillFieldsContact(new ContactData("Sasha", "Firsenkova", "Prigazhunya", "SPBU", "Bransk", "666-6666", "sasha.firsenkova@ya.ru"));
  app.getContactHelper().submitContactModification();
  app.getContactHelper().gotoHomePage();

}

}
