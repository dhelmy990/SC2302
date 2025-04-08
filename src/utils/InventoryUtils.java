// utils/InventoryUtils.java
package utils;


import inventory.Item;
import inventory.Inventory;
import java.util.List;
import java.util.Scanner;

public class InventoryUtils {
    private InventoryUtils() {
        // Prevent instantiation
    }
    public static void updateItemFlow(Inventory inventory, Scanner scanner) {
        List<Item> items = inventory.getAllItems();
        ItemUtils.displayInventory(items);

        System.out.print("\nEnter item name to update: ");
        String name = scanner.nextLine();
        Item existingItem = inventory.findItemByName(name);

        if (existingItem != null) {
            inventory.getAllItems().remove(existingItem);
            Item updatedItem = ItemInputUtils.updateItemFromInput(scanner, existingItem);
            inventory.addItem(updatedItem);
            System.out.println("Item updated.");
        } else {
            System.out.println("Item not found.");
        }
    }
    public static void deleteItemFlow(Inventory inventory, Scanner scanner) {
        List<Item> items = inventory.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("--- Current Inventory ---");
        ItemUtils.displayInventory(items);

        System.out.print("Enter item name to delete: ");
        String name = scanner.nextLine();
        Item item = inventory.findItemByName(name);

        if (item != null) {
            items.remove(item);
            System.out.println("Item '" + name + "' deleted successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }
}
