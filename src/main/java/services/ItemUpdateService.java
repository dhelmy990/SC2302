package services;

import inventory.Inventory;
import inventory.Item;
import utils.ItemInputUtils;
import utils.ItemUtils;

import java.util.List;

public class ItemUpdateService {
    private final ITextInputHandler textInputHandler;
    private final INumericInputHandler numericInputHandler;

    public ItemUpdateService(ITextInputHandler textInputHandler,INumericInputHandler numericInputHandler) {
        this.textInputHandler = textInputHandler;
        this.numericInputHandler =numericInputHandler;
    }

    public void update(Inventory inventory) {
        List<Item> items = inventory.getAllItems();
        ItemUtils.displayInventory(items);

        String name = textInputHandler.getNonEmptyInput("\nEnter item name to update: ");
       
        Item existingItem = inventory.findItemByName(name);

        if (existingItem != null) {
            inventory.getAllItems().remove(existingItem);
            Item updatedItem = ItemInputUtils.updateItemFromInput(textInputHandler, numericInputHandler, existingItem);
            inventory.addItem(updatedItem);
            System.out.println("Item updated.");
        } else {
            System.out.println("Item not found.");
        }
    }
}