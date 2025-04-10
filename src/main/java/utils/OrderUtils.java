package utils;

import inventory.Item;
import orders.Order;

import java.util.*;

public class OrderUtils {

    private OrderUtils() {
        // Prevent instantiation
    }
    public static void displayGroupedItems(List<Item> items) {
        Map<String, Integer> grouped = new LinkedHashMap<>();
        Map<String, Double> prices = new HashMap<>();

        for (Item item : items) {
            grouped.put(item.getName(), grouped.getOrDefault(item.getName(), 0) + 1);
            prices.put(item.getName(), item.getPrice());
        }

        for (String name : grouped.keySet()) {
            System.out.println("- " + name + " x" + grouped.get(name) + " ($" + prices.get(name) + ")");
        }
    }

    public static void displayOrderSummary(Order order, boolean isDiner, boolean isGuest) {
        String prefix = "\nOrder ID: " + order.getID();
        if (isDiner || isGuest) {
            prefix += " | Stall: " + order.getStallName();
        } else {
            prefix += " | User: " + order.getUsername();
        }

        double total = order.getItems().stream().mapToDouble(Item::getPrice).sum();
        System.out.println(prefix + " | Status: " + order.getStatus() + " | Total: $" + total);

        System.out.println("Order Time: " + DateUtils.format(order.getOrderTime()));
        System.out.println(
                "Est. Collection Time: " + DateUtils.format(order.getOrderTime().plusMinutes(order.getWaitingTime())));

        System.out.println("Items:");
        displayGroupedItems(order.getItems());
    }

    public static void displayDetailedOrder(Order order) {
        System.out.println("Order ID: " + order.getID());
        System.out.println("Stall: " + order.getStallName());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Order Time: " + DateUtils.format(order.getOrderTime()));
        System.out.println(
                "Est. Collection Time: " + DateUtils.format(order.getOrderTime().plusMinutes(order.getWaitingTime())));
        System.out.println("Paid via: " + order.getPaymentMethod());

        System.out.println("Items Breakdown:");
        Map<String, Integer> itemCounts = new LinkedHashMap<>();
        Map<String, Double> itemPrices = new LinkedHashMap<>();

        for (Item item : order.getItems()) {
            itemCounts.put(item.getName(), itemCounts.getOrDefault(item.getName(), 0) + 1);
            itemPrices.put(item.getName(), item.getPrice());
        }

        int total = 0;
        for (String name : itemCounts.keySet()) {
            int qty = itemCounts.get(name);
            double price = itemPrices.get(name);
            double subtotal = qty * price;
            total += subtotal;
            System.out.println("- " + name + " x" + qty + " @ $" + price + " = $" + subtotal);
        }

        System.out.println("Total Paid: $" + total);
    }

}
