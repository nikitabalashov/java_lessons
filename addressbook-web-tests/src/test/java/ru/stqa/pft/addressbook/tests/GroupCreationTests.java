package ru.stqa.pft.addressbook.tests;

import ru.stqa.pft.addressbook.model.GroupData;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.testng.annotations.DataProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;
import com.thoughtworks.xstream.XStream;
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class GroupCreationTests extends TestBase {


  @DataProvider
  public Iterator<Object[]> validGroupFromXml() throws IOException {
    //List<Object[]> list = new ArrayList<Object[]>();
    //BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/groups.csv")));

    try (BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/groups.csv")))) {
      //   BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        //String[] split = line.split(";");
        //list.add(new Object[]{new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(GroupData.class);
      List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);

      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }//return list.iterator();
  }


  @DataProvider
  public Iterator<Object[]> validGroupFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/groups.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
      }.getType());
      return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }



  @Test(dataProvider = "validGroupFromJson")
  public void testGroupCreation(GroupData group) throws Exception {
    app.goTo().GroupPage();
    Groups before = app.group().all();
    //GroupData groupData = new GroupData().withName("test2");
    app.group().create(group);
    Groups after = app.group().all();
    assertThat(app.group().count(), equalTo(before.size() + 1));
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
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
