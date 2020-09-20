


package ru.stqa.pft.addressbook.tests;

        import org.testng.annotations.BeforeMethod;
        import org.testng.annotations.Test;
        import ru.stqa.pft.addressbook.model.Contacts;
        import ru.stqa.pft.addressbook.model.GroupData;
        import ru.stqa.pft.addressbook.model.Groups;
        import ru.stqa.pft.addressbook.model.ContactData;

        import static org.hamcrest.CoreMatchers.equalTo;
        import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteContactFromGroup extends TestBase {
  private ContactData contactForDel;
  private GroupData groupForDeleteContact;
  @BeforeMethod
  public void ensurePrecondition() {
    groupForDeleteContact = app.db().groups().stream().iterator().next();
    ContactData user = new ContactData()
            .withFirstname("Nikita")
            .withLastname("Balashov")
            .withNickname("Beliash")
            .withCompany("Dins")
            .withAddress("Vitebsk")
            .withMobile("555-5555")
            .withWork("12345").inGroup(new GroupData().withName("testio1"));
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
    if (app.db().contacts().size() == 0) {
      app.contact().create(user, true);
    }

    contactForDel = app.db().contacts().iterator().next();
    if (contactForDel.getGroups().size() == 0) {
      app.contact().selectContactById(contactForDel.getId());
      groupForDeleteContact = app.db().groups().stream().iterator().next();
      app.contact().addContactToGroup(groupForDeleteContact.getId());
      app.goTo().homePage();
    } else if(app.db().groups().size() == contactForDel.getGroups().size()) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("NewTest2"));
      app.goTo().homePage();
      groupForDeleteContact = getNameForDelete(contactForDel).stream().iterator().next();
      app.contact().selectContactById(contactForDel.getId());
      app.contact().addContactToGroup(groupForDeleteContact.getId());
      app.goTo().homePage();
    } else {
      groupForDeleteContact = getNameForDelete(contactForDel).stream().iterator().next();
      app.contact().selectContactById(contactForDel.getId());
      app.contact().addContactToGroup(groupForDeleteContact.getId());
      app.goTo().homePage();
    }
  }

  @Test
  public void testRemoveUserFromGroup() {
    app.goTo().homePage();
    app.contact().selectGroupForImaging(groupForDeleteContact.getId());
    Contacts before = contactsInGroups(groupForDeleteContact.getId());
    System.out.println(before);
    app.contact().removeUserFromSelectedGroup(contactForDel.getId());
    Contacts after = contactsInGroups(groupForDeleteContact.getId());

    assertThat(after.size(), equalTo(before.size() - 1));
    assertThat(after, equalTo(before.without(contactForDel)));
  }

  private Contacts contactsInGroups(int groupID){
    Contacts result = new Contacts();
    app.db().groups().forEach((b) -> {
      if (b.getId() == groupID) {
        if (b.getContacts().size() > 0) {
          result.addAll(b.getContacts());
        }
      }
    });
    return result;
  }

  public Groups getNameForDelete(ContactData contact) {
    Groups groups = app.db().groups();
    Groups contactGroups = contact.getGroups();
    groups.removeAll(contactGroups);
    return groups;
  }
}