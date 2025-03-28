package users;
import java.util.*;

import orders.*;

public class Owner extends User{
    private Stall managedStall;
    private static Map<Integer, Owner> owners = new HashMap<>(); // Static map to store all owners by ID
    private int ownerId;

    public Owner(int ownerid, String username, String email, String password, Stall stall) {
        super(username, email, password);
        this.managedStall = stall;
        this.ownerId = ownerId;
        owners.put(ownerId,this) // Add the owner to the static map
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    public static Owner get(int ownerid) {
        return owners.get(ownerid); // Returns Owner object, retrieved using unique ownerid
    }

    public int getOwnerId(){
        return this.owenerId;
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
