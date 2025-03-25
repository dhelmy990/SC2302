package stalls;

import inventory.Inventory;
import java.util.Objects;

public class Stall {
    private String stallId;
    private String name;
    private String ownerUsername; // Username of the stall owner
    private Inventory inventory;

    public Stall(String stallId, String name, String ownerUsername) {
        this.stallId = stallId;
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.inventory = new Inventory();
    }
    public void viewMenu(){
        System.out.println("Menu for stall: " + name);
        inventory.displayItems();
    }

    public String getStallId() {
        return stallId;
    }

    public String getName() {
        return name;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stall stall = (Stall) o;
        return Objects.equals(stallId, stall.stallId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stallId);
    }
}