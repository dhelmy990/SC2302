package services;

import inventory.Inventory;
import inventory.Item;
import utils.ItemInputUtils;
import utils.ItemUtils;

import java.util.List;
import java.util.Scanner;

public class ItemUpdateService {
    private final Scanner scanner;

    public ItemUpdateService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void update(Inventory inventory) {
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
}