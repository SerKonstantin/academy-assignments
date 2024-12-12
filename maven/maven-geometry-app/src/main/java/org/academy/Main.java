package org.academy;

public class Main {
    public static void main(String[] args) {
        var circle = new Circle(new Point(0,0), 10.0);
        System.out.println("Площадь круга: " + circle.getArea());

        var rectangle = new Rectangle(100.0, 100.000005);
        System.out.println("Проверка на квадрат: " + rectangle.isSquare());
        System.out.println("Площадь прямоугольника: " + rectangle.getArea());

        System.out.println("Совпадение по площади:" + Util.compareAreas(circle, rectangle));

    }
}