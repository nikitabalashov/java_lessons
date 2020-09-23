package ru.stqa.pft.rest.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class TestBase {

  public boolean isIssueOpen(int issueId) throws IOException {
    String s = getExecutor().execute(Request.Get(String.format("https://bugify.stqa.ru/api/issues/%s.json",issueId)))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(s);
    JsonElement issues =  parsed.getAsJsonObject().get("issues");
    Set<Issue> issue = new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
    Issue issue1 = issue.iterator().next();
    if(issue1.getState_name().equalsIgnoreCase("open")){
      return true;
    }
    return false;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public int createIssue(Issue newIssue) throws IOException {
    String s = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(s);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public Set<Issue> getIssues() throws IOException {
    String s = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = new JsonParser().parse(s);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  private Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

}