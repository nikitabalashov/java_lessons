package ru.stqa.pft.sandbox;

public class Point {
  public double xx;
  public  double yy;


  public Point(double xx, double yy){
    this.xx = xx;
    this.yy = yy;
  }

 public double distance(Point pj){
    //double nwxx = this.xx - pj.xx;
    //double nwyy = this.yy - pj.yy;


    return Math.sqrt( (this.xx - pj.xx) * (this.xx - pj.xx) + (this.yy - pj.yy) * (this.yy - pj.yy) );
  }
}
