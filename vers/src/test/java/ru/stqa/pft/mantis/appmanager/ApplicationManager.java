package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {

  private String browser;
  private final Properties properties;
  private WebDriver wd;

   public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException
  {
   if(browser.equals(BrowserType.FIREFOX))  {
      wd = new FirefoxDriver();
    } else if (browser.equals(BrowserType.CHROME))   {
      wd = new ChromeDriver();
    } else if (browser.equals(BrowserType.IE))   {
      wd = new InternetExplorerDriver();
    }
    wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
  }

  public void stop() {
    wd.findElement(By.linkText("Logout")).click();
    wd.quit();
  }


}
