package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactDeletionTests extends TestBase {




  @Test
  public void testContactDeletion() throws Exception {
    app.getContactHelper().gotoHomePage();
    app.getContactHelper().chooseContact();
    app.getContactHelper().deleteContact();

  }


}


