
package ru.stqa.pft.addressbook.tests;

        import org.testng.annotations.BeforeMethod;
        import org.testng.annotations.Test;
        import ru.stqa.pft.addressbook.model.GroupData;
        import ru.stqa.pft.addressbook.model.Groups;
        import ru.stqa.pft.addressbook.model.ContactData;
        import ru.stqa.pft.addressbook.model.Contacts;
        import static org.hamcrest.CoreMatchers.equalTo;
        import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTest extends TestBase {
  ContactData contactMod;

  @BeforeMethod
  public void ensurePrecondition() {
    ContactData contact = new ContactData()
            .withFirstname("Nikita")
            .withLastname("Balashov")
            .withNickname("Beliash")
            .withCompany("Dins")
            .withAddress("Vitebsk")
            .withMobile("555-5555")
            .withWork("12345").inGroup(new GroupData().withName("testio1"));
    if (app.db().groups().size() == 0) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("testio1"));
      app.goTo().homePage();
    }
    if (app.db().contacts().size() == 0) {
      app.contact().create(contact, true);
    }
    contactMod = app.db().contacts().iterator().next();
    if (app.db().groups().size() == contactMod.getGroups().size()) {
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("NewTest1"));
      app.goTo().homePage();
    }
    app.contact().selectContactById(contactMod.getId());
  }

  @Test
  public void testAddContactToGroup() {
    int idGroupForAdd = getNameForAdd(contactMod).iterator().next().getId();
    Contacts before = contactsInGroups(idGroupForAdd);
    app.contact().addContactToGroup(idGroupForAdd);
    Contacts after = contactsInGroups(idGroupForAdd);;

    assertThat(after.size(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(contactMod.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  public Groups getNameForAdd(ContactData contactMod) {
    Groups groups = app.db().groups();
    Groups contactGroups = contactMod.getGroups();
    groups.removeAll(contactGroups);
    return groups;
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
}