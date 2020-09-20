package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Comparator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
   // if (app.group().all().size() == 0){
   //   app.group().create(new GroupData().withName("testio1"));
    if(app.db().groups().size()==0){
      app.goTo().GroupPage();
      app.group().create(new GroupData().withName("test1"));
  }
  }



  @Test
  public void testGroupModification () {
    Groups before = app.db().groups();
   // Groups before = app.group().all();
    GroupData modifyGroup = before.iterator().next();
    GroupData groupData = new GroupData()
            .withId(modifyGroup.getId()).withName("Test1").withHeader("Test2").withFooter("Test3");
    app.goTo().GroupPage();

    app.group().modify(groupData);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifyGroup).withAdded(groupData)));
  }
}
