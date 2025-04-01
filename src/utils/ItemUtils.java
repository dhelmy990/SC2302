package utils;

import inventory.Item;
import java.util.*;

public class ItemUtils {
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
}
