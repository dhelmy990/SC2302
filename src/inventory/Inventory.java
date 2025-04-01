package inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getAllItems() {
        return items;
    }

    public Item findItemByName(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public boolean isInStock(String name, int requestedQty) {
        Item item = findItemByName(name);
        return item != null && item.getQuantity() >= requestedQty;
    }

    public void reduceStock(String name, int amount) {
        Item item = findItemByName(name);
        if (item != null && item.getQuantity() >= amount) {
            item.reduceQuantity(amount);
        }
    }
}
