package services;

import inventory.Inventory;
import inventory.Item;
import utils.ItemUtils;

import java.util.List;

public class ItemDeleteService {
    private final ITextInputHandler textInputHandler;

    public ItemDeleteService(ITextInputHandler textInputHandler) {
        this.textInputHandler = textInputHandler;
    }

    public void delete(Inventory inventory) {
        List<Item> items = inventory.getAllItems();
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        ItemUtils.displayInventory(items);
        String name = textInputHandler.getNonEmptyInput("Enter item name to delete: ");
        Item item = inventory.findItemByName(name);

        if (item != null) {
            items.remove(item);
            System.out.println("Item '" + name + "' deleted successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }
}
