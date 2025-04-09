package utils;

import inventory.Inventory;
import inventory.Item;

public class InventoryUtils {
    private InventoryUtils() {
    }

    public static boolean deleteItemByName(Inventory inventory, String name) {
        Item item = inventory.findItemByName(name);
        if (item != null) {
            return inventory.removeItem(item);
        }
        return false;
    }

    public static boolean updateItem(Inventory inventory, Item updatedItem) {
        Item existing = inventory.findItemByName(updatedItem.getName());
        if (existing != null) {
            inventory.removeItem(existing);
            inventory.addItem(updatedItem);
            return true;
        }
        return false;
    }
}
