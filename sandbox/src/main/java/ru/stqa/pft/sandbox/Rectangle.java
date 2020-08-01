package ru.stqa.pft.sandbox;

public class Rectangle {
  public double x;
  public double y;

  public Rectangle(double x, double y){
    this.x = x;
    this.y = y;
  }

  public double area(){
    return this.x * this.y;
  }


}
