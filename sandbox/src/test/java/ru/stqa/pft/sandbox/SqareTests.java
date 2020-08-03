package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class SqareTests {
  public void testArea() {
    Sqare s = new Sqare(5);
    Assert.assertEquals( s.area() , 25.0);
  }
}
