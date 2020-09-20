package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


import java.util.concurrent.TimeUnit;

public class ApplicationManager {
 private final Properties properties;
  private WebDriver wd;

  private NavigationHelper navigationHelper;
  private GroupHelper groupHelper;
  private SessionHelper sessionHelper;
  private ContactHelper contactHelper;
  private String browser;
  private DbHelper dpHelper;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() //throws IOException
  {
  //  String target = System.getProperty("target", "local");
  //  properties.load(new FileReader(new File(String.format("/Users/nikita.balashov/Documents/GitHub/java_lessons/addressbook-web-tests/src/test/java/ru/stqa/pft/addressbook/resources/%s.properties", target))));
    if(browser.equals(BrowserType.FIREFOX))  {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME))   {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE))   {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
  //  wd.get(properties.getProperty("web.baseUrl"));
    groupHelper = new GroupHelper(wd);
    navigationHelper = new NavigationHelper(wd);
    sessionHelper = new SessionHelper(wd);
    sessionHelper.login("admin", "secret");
 //   sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    contactHelper = new ContactHelper(wd);
  }



  public void stop() {
    wd.findElement(By.linkText("Logout")).click();
    wd.quit();
  }

  public boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public GroupHelper group() {
    return groupHelper;
  }

  public NavigationHelper goTo() {
    return navigationHelper;
  }

  //public SessionHelper getSessionHelper() {return sessionHelper; }

  public ContactHelper contact() {return contactHelper;}

  public DbHelper db() {
    return dpHelper;
  }

}
