
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
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());

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
    gotoHomePage();
  }

  public void deleteContactFromMainPage(ContactData deletedContact) {
    selectContactById(deletedContact.getId());
    removeContactForMainPage();
    nav.acceptAlert();
    gotoHomePage();
  }

  public void deleteContactFromModPage(ContactData deletedContact) {
    selectContactByIdForMod(deletedContact.getId());
    removeContact();
    gotoHomePage();
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

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> lastNames = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[3]"));
    List<WebElement> firstNames = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[2]"));
    List<WebElement> ids = wd.findElements(By.xpath(".//tr[@name=\"entry\"]/td[1]/input"));
    for(int i=0; i<lastNames.size(); i++){
      String lname = lastNames.get(i).getText();
      String fname = firstNames.get(i).getText();
      int id = Integer.parseInt(ids.get(i).getAttribute("value"));
      contacts.add(new ContactData().withId(id).withLastname(fname).withFirstname(lname));
    }
    return contacts;
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



}