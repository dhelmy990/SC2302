package utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import inventory.Item;

public class ItemUtils {
    private ItemUtils() {
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

    public static void displayInventory(List<Item> items) {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("\n--- Current Inventory ---");
            for (Item i : items) {
                System.out.println("- " + i.getName() +
                        " | $" + i.getPrice() +
                        " | Prep Time: " + i.getPrepTime() + " mins" +
                        " | Qty: " + i.getQuantity());
            }
        }
    }

    public static void displayMenu(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String label = item.getQuantity() == 0 ? "[OUT OF STOCK]" : "(Qty: " + item.getQuantity() + ")";
            System.out.println((i + 1) + ". " + item.getName() + " ($" + item.getPrice() + ", " + item.getPrepTime() + " mins) " + label);
        }
    }

    
}
