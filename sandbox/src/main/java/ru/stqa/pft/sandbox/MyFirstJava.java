package ru.stqa.pft.sandbox;

public class MyFirstJava {

	public static void main(String[] args) {
		hello("world");
		hello("Nikita");

		double len = 5;
		double x = 4;
		double y = 88;
		System.out.println("Площадь квадрата со стороной " + len + "равна" + area(len));
		System.out.println("Площадь прямоугольника со сторонами " + x + " и " + y + " равна " + area(x , y));



	}

	public static void hello(String somebody){
		System.out.println("Hello, " + somebody + "!");
	}
	public static double area(double l){
		return l * l;
	}
	public static double area(double a , double b ){
		return a * b;
	}
}