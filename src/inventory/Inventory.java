package inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
        System.out.println("Item added to inventory: " + item.getName());
    }

    public void updateItem(Item updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(updatedItem.getName())) {
                items.set(i, updatedItem);
                System.out.println("Item updated in inventory: " + updatedItem.getName());
                return;
            }
        }
        System.out.println("Item not found in inventory.");
    }

    public Item getItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getAllItems() {
        return items;
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("No items available in inventory.");
            return;
        }
        System.out.println("Available Items:");
        for (Item item : items) {
            System.out.println("Name: " + item.getName() +
                    ", Price: $" + item.getPrice() +
                    ", Prep Time: " + item.getPrepTime() + " mins");
        }
    }
}