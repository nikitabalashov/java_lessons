package ru.stqa.pft.sandbox;

public class MyFirstJava {

	public static void main(String[] args) {
	//	hello("world");
	//	hello("Nikita");

	//	Sqare s = new Sqare(5);
	//	Rectangle z = new Rectangle(4, 88);

		Point p1 = new Point(3, 5);
		Point p2 = new Point(4, 13);

		p1.xx = 1;
		p1.yy = 2;
		p2.xx = 3;
		p2.yy = 4;

		System.out.println("расстояние от точки X с координатами (" + p1.xx + " , " + p1.yy + ") до точки Y с координатами (" + p2.xx + " , " + p2.yy + ") равна " + distance(p1, p2));
//		  System.out.println( p.distance() );

	//	System.out.println("Площадь квадрата со стороной " + s.l + " равна " + s.area());
	//	System.out.println("Площадь прямоугольника со сторонами " + z.x + " и " + z.y + " равна " + z.area());
	}

//	public static void hello(String somebody){
//		System.out.println("Hello, " + somebody + "!");
//	}

//	public static double area(Rectangle z){
//		return z.x * z.y;
//	}


	public static double distance(Point p1, Point p2){
		return Math.sqrt( (p2.xx - p1.xx) * (p2.xx - p1.xx) + (p2.yy - p1.yy) * (p2.yy - p1.yy) );
	}


}