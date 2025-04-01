// utils/OrderUtils.java
package utils;

import inventory.Item;
import orders.Order;

import java.util.*;

public class OrderUtils {

    public static void displayGroupedItems(List<Item> items) {
        Map<String, Integer> grouped = new LinkedHashMap<>();
        Map<String, Integer> prices = new HashMap<>();

        for (Item item : items) {
            grouped.put(item.getName(), grouped.getOrDefault(item.getName(), 0) + 1);
            prices.put(item.getName(), item.getPrice());
        }

        for (String name : grouped.keySet()) {
            System.out.println("- " + name + " x" + grouped.get(name) + " ($" + prices.get(name) + ")");
        }
    }

    // 🎯 Unified display logic for different user roles
    public static void displayOrderSummary(Order order, boolean isDiner, boolean isGuest) {
        String prefix = "\nOrder ID: " + order.getID();
        if (isDiner || isGuest) {
            prefix += " | Stall: " + order.getStallName();
        } else {
            prefix += " | User: " + order.getUsername();
        }

        int total = order.getItems().stream().mapToInt(Item::getPrice).sum();
        System.out.println(prefix + " | Status: " + order.getStatus() + " | Total: $" + total);
        System.out.println("Items:");
        displayGroupedItems(order.getItems());
    }
}
