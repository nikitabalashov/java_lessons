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
    //int index = before.size()-1;
    app.group().delete(deletionGroup);
    Groups after = app.group().all();
    assertEquals(after.size(), before.size() - 1);
    //before.remove(deletionGroup);
    assertThat(after, equalTo(before.without(deletionGroup)));
    //Assert.assertEquals(before, after);
  }


}
