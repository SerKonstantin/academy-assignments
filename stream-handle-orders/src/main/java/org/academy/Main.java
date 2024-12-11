package org.academy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Создайте список заказов с разными продуктами и их стоимостями.
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );


        // Группируйте заказы по продуктам.
        var result1 = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));
        System.out.println("Группируйте заказы по продуктам: " + result1 + "\n");

        // Для каждого продукта найдите общую стоимость всех заказов.
         var result2 = orders.stream()
                 .collect(Collectors.groupingBy(
                         Order::getProduct,
                         Collectors.summingDouble(Order::getCost)
                 ));
        System.out.println("Для каждого продукта найдите общую стоимость всех заказов: " + result2 + "\n");

        /*
        Понять что можно использовать Collectors внутри Collectors для result2 было сложно, потратил кучу времени.
        Сделал бы намного быстрее через for-each и добавлением в пустую мапу.
        */

        // Отсортируйте продукты по убыванию общей стоимости.
        var result3 = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .toList();
        System.out.println("Отсортируйте продукты по убыванию общей стоимости: " + result3 + "\n");

        // Выберите три самых дорогих продукта.
        var result4 = result3.stream().limit(3).toList();
        System.out.println("Выберите три самых дорогих продукта: " + result4 + "\n");

        // Выведите результат: список трех самых дорогих продуктов и их общая стоимость.
        var result5 = result4.stream()
                .mapToDouble(Order::getCost)
                .reduce(0, Double::sum);
        System.out.println("Выведите результат: список трех самых дорогих продуктов -> " + result4);
        System.out.println("и их общая стоимость -> " + result5);

    }
}