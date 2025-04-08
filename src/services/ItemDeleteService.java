package services;

import inventory.Inventory;
import inventory.Item;
import utils.ItemUtils;

import java.util.List;
import java.util.Scanner;

public class ItemDeleteService {
    private final Scanner scanner;

    public ItemDeleteService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void delete(Inventory inventory) {
        List<Item> items = inventory.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

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
