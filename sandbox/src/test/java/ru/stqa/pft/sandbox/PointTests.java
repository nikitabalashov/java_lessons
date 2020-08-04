package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class PointTests {
  public void testDistance() {
    Point p1 = new Point(4.0, 6.0);
    Point p2 = new Point(1.0, 2.0);
    Assert.assertEquals( p2.distance(p1) , 5.0 );
  }
}
