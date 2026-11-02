package org.rkov;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main() {
        // 1. Создайте список заказов с разными продуктами и их стоимостями.
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        //2. Группируйте заказы по продуктам.
        Map<String, List<Order>> groupedOrders = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));

        //3. Для каждого продукта найдите общую стоимость всех заказов.
        var totalCosts = groupedOrders.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .mapToDouble(Order::getCost)
                                .sum())
                );

        // 4. Отсортируйте продукты по убыванию общей стоимости
        var sortedProducts = totalCosts.entrySet()
                .stream()
                .sorted((cost1, cost2) -> Double.compare(cost2.getValue(), cost1.getValue()))
                .toList();

        // 5. Выберите три самых дорогих продукта
        var topThreeProducts = groupedOrders.values()
                .stream()
                .map(orderList -> {
                    Order result = orderList.getFirst();

                    for (Order order : orderList) {
                        if (order.getCost() > result.getCost()) {
                            result = order;
                        }
                    }

                    return result;
                })
                .sorted((cost1, cost2) -> Double.compare(cost2.getCost(), cost1.getCost()))
                .limit(3)
                .toList();

        // 6. Выведите результат: список трех самых дорогих продуктов и их общая стоимость.

        System.out.println("список трех самых дорогих продуктов и их общая стоимость:");
        topThreeProducts.forEach(order -> {
            System.out.println(order.getProduct() + ": " + order.getCost() + ". Общая стоимость: " + totalCosts.get(order.getProduct()));
        });
    }
}
