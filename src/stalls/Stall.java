package stalls;

import inventory.Inventory;

public class Stall {
    private static int stallCounter = 1;
    private final String stallId;
    private String name; 
    private String owner;
    private final Inventory inventory;

    public Stall(String name, String owner) {
        this.stallId = generateUniqueStallId(); // Automatically generate ID
        this.name = name;
        this.owner = owner;
        this.inventory = new Inventory();
    }

    public String getStallId() {
        return stallId;
    }

    public String getId() {
        return stallId; // for admin UI use
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getOwnerUsername() {
        return owner != null ? owner : "[Unassigned]";
    }

    public void setOwnerUsername(String newOwner) {
        this.owner = newOwner;
    }


    public Inventory getInventory() {
        return inventory;
    }
    
    private static String generateUniqueStallId() {
        return "S" + String.format("%03d", stallCounter++);
    }
}
