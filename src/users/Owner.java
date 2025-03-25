package users;

import java.util.*;
import stalls.Stall;
import orders.*;
import inventory.Item;

public class Owner extends User{
    private Stall managedStall;

    public Owner(String username, String email, String password, Stall stall) {
        super(username, email, password);
        this.managedStall = stall;
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    // Stall knows its own Inventory. Inventory can display can update items
    public void viewInventory() {
        System.out.println("Inventory for: " + managedStall.getName());
        managedStall.getInventory().displayItems();
    }

    public void updateInventory(List<Item> newItems) {
        managedStall.getInventory().setItems(newItems);
    }

    // Call method in Order class
    public void markOrderAsComplete(Order order) {
        order.markCompleted();
    }
}
