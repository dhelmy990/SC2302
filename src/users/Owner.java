package users;

import stalls.Stall;
import orders.Order;
import inventory.Item;

public class Owner extends User {
    private Stall managedStall;

    public Owner(String username, String email, String password, Stall stall) {
        super(username, email, password);
        this.managedStall = stall;
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    // View inventory of the managed stall
    public void viewInventory() {
        System.out.println("Inventory for: " + managedStall.getName());
        managedStall.getInventory().displayItems();
    }

    // Add a single item to the inventory
    public void addItemToInventory(Item newItem) {
        managedStall.getInventory().addItem(newItem);
        System.out.println("Item added to inventory: " + newItem.getName());
    }

    // Update an existing item in the inventory
    public void updateItemInInventory(Item updatedItem) {
        managedStall.getInventory().updateItem(updatedItem);
        System.out.println("Item updated in inventory: " + updatedItem.getName());
    }

    // Mark an order as complete
    public void markOrderAsComplete(Order order) {
        order.markCompleted();
        System.out.println("Order marked as completed: " + order.getID());
    }

    // Get the managed stall (for external access)
    public Stall getManagedStall() {
        return managedStall;
    }
}