package ru.stqa.pft.addressbook.tests;


import ru.stqa.pft.addressbook.model.GroupData;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().GroupPage();
    Groups before = app.group().all();
    GroupData groupData = new GroupData().withName("test2");
    app.group().create(groupData);

    Groups after = app.group().all();
    assertThat(app.group().count(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(groupData.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }



  @Test
  public void testBadGroupCreation() throws Exception {
    app.goTo().GroupPage();
    Groups before = app.group().all();
    GroupData groupData = new GroupData().withName("test'`");
    app.group().create(groupData);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();

    assertThat(after, equalTo(before));

  }





}
