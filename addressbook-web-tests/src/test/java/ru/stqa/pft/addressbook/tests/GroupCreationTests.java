package ru.stqa.pft.addressbook.tests;


import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import ru.stqa.pft.addressbook.model.GroupData;
import org.testng.Assert;
import java.util.List;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Comparator;
import java.util.Set;

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
    assertThat(after.size(), equalTo(before.size() + 1));


    //groupData.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
    //before.add(groupData);

    assertThat(after, equalTo(
            before.withAdded(groupData.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

  }
}
