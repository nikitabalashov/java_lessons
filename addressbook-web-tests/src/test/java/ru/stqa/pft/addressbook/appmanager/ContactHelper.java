
package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.assertTrue;



public class ContactHelper extends HelperBase {

  public boolean acceptNextAlert = true;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void submitContact() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void selectContactById(int id) {

    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }
  public void selectContactByIdForMod(int id) {

    wd.findElement(By.xpath( ".//input[@value='" + id + "']/..//following-sibling::td[7]/a")).click();
  }


  public void fillFieldsContact(ContactData contactData , boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
  //  typeForFile(By.name("photo"),contactData.getPhoto());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());
    type(By.name("work"), contactData.getWork());

    if (creation) {
      try {

      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } catch (NoSuchElementException e) {
        System.out.println("Group named " + "\'" + contactData.getGroup() + "\' was not found, the contact was created without a group");
      }

    } else{
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

 /*public boolean isElementPresent(By locator) {
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex){
    return false;
    }
  }
*/

  public void type(By locator, String text) {
    click(locator);
    wd.findElement(locator).clear();
    wd.findElement(locator).sendKeys(text);
  }

  public void gotoAddNewContact() {
    click(By.linkText("add new"));
  }

  public void deleteContact() {
    acceptNextAlert = true;
    click(By.xpath("//input[@value='Delete']"));
    assertTrue(closeAlertAndGetItsText().matches("^Delete 1 addresses[\\s\\S]$"));
  }
  //public void chooseContact() {
  //  click(By.name("selected[]"));
 // }

  public void chooseContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public String closeAlertAndGetItsText() {
    try {
      Alert alert = wd.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
    //click(By.xpath("Edit"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }
  public void gotoHomePage() {
    click(By.linkText("home"));
  }
  public void create(ContactData contact, boolean b) {
    gotoAddNewContact();
    fillFieldsContact(contact, b);
    submitContact();
    contactCache = null;
    gotoHomePage();
  }




  NavigationHelper nav = new NavigationHelper(wd);

  public void removeContact() {
    click(By.xpath("//form[2]//input[2]"));
  }

  public void modify(ContactData contactMod) {
    selectContactByIdForMod(contactMod.getId());
    fillFieldsContact(contactMod, false);
    submitContactModification();
    contactCache = null;
    gotoHomePage();
  }

  public void deleteContactFromMainPage(ContactData deletedContact) {
    selectContactById(deletedContact.getId());
    removeContactForMainPage();
    nav.acceptAlert();
    contactCache = null;
    gotoHomePage();
  }

  public void deleteContactFromModPage(ContactData deletedContact) {
    selectContactByIdForMod(deletedContact.getId());
    removeContact();
    contactCache = null;
    gotoHomePage();
  }



  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }




  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void removeContactForMainPage() {
    click(By.xpath("//div[2]//input[1]"));
  }



    public List<ContactData> getContactList() {
      List<ContactData> contacts = new ArrayList<>();
      List<WebElement> lastNames = wd.findElements(By.xpath("//tr[@name=\"entry\"]/td[3]"));
      List<WebElement> firstNames = wd.findElements(By.xpath("//tr[@name=\"entry\"]/td[2]"));
      List<WebElement> ids = wd.findElements(By.xpath("//tr[@name=\"entry\"]/td[1]/input"));
      for(int i=0; i<lastNames.size(); i++){
        String lname = lastNames.get(i).getText();
        String fname = firstNames.get(i).getText();
        int id = Integer.parseInt(ids.get(i).getAttribute("value"));
        ContactData contact = new ContactData().withId(id).withFirstname(fname).withLastname(lname);
        contacts.add(contact);
      }
      return contacts;
    }

    private Contacts contactCache = null;


  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    Contacts contactCache = new Contacts();
    List<WebElement> lastNames = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[3]"));
    List<WebElement> firstNames = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[2]"));
    List<WebElement> phones = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[6]"));
    List<WebElement> emails = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[5]"));
    List<WebElement> addresses = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[4]"));
    List<WebElement> ids = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[1]/input"));
    for(int i=0; i<lastNames.size(); i++){
      String lname = lastNames.get(i).getText();
      String fname = firstNames.get(i).getText();
      String allphones = phones.get(i).getText();
      String address = addresses.get(i).getText();
      String allEmails = emails.get(i).getText();
     // String[] phone = phones.get(i).getText().split("\n");
      int id = Integer.parseInt(ids.get(i).getAttribute("value"));
      contactCache.add(new ContactData().withId(id).withFirstname(lname).withLastname(fname).withAllPhones(allphones).withAddress(address).withAllEmails(allEmails));
//      if (phone.length == 2) {
 //       contactCache.add(new ContactData().withId(id).withFirstname(lname).withLastname(fname).withMobile(phone[0]).withWork(phone[1]));
  //    } else {
   //     contactCache.add(new ContactData().withId(id).withFirstname(lname).withLastname(fname).withMobile(phone[0]));
   //   }

      contactCache.add(new ContactData().withId(id).withLastname(fname).withFirstname(lname));
    }
    return new Contacts(contactCache);
  }
 /* public List<ContactData> getContactList() {

    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      String firstname = cells.get(2).getText();
      String lastname = cells.get(1).getText();

      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname));
    }
    return contacts;
  }
*/
 public ContactData infoFromEditForm(ContactData user) {
   selectContactByIdForMod(user.getId());
   String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
   String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
   String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
   String work = wd.findElement(By.name("work")).getAttribute("value");

   String email1 = wd.findElement(By.name("email")).getAttribute("value");
   String email2 = wd.findElement(By.name("email2")).getAttribute("value");
   String email3 = wd.findElement(By.name("email3")).getAttribute("value");
   String address = wd.findElement(By.name("address")).getAttribute("value");

   wd.navigate().back();
   return new ContactData().withFirstname(firstname).withLastname(lastname).withMobile(mobile).withWork(work).withAddress(address).withEmail(email1).withEmail2(email2).withEmail3(email3);
 }



}