package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().GroupPage();
    if (app.group().all().size() == 0){
      app.group().create(new GroupData().withName("testio1"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {

    Groups before = app.group().all();
    GroupData deletionGroup = before.iterator().next();
    app.group().delete(deletionGroup);
    assertThat(app.group().count(), equalTo(before.size() - 1));
    Groups after = app.group().all();

    assertThat(after, equalTo(before.without(deletionGroup)));
  }


}
