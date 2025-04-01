package simulated;

import inventory.Item;
import stalls.IStallService;
import stalls.Stall;
import stalls.CanteenManager;
import users.*;

import java.util.ArrayList;
import java.util.List;

public class SimulatedData {

    private static final List<Stall> globalStalls = new ArrayList<>(); // Shared between methods

    public static List<User> getSampleUsers(IStallService stallService) {
        List<User> users = new ArrayList<>();

        // === Owner 1 ===
        Owner owner1 = new Owner("owner1", "owner1@email.com", "1234");
        Stall s1 = new Stall("Chicken Rice", owner1.getUsername());
        s1.getInventory().addItem(new Item("Chicken Rice", 5, 10,2));
        s1.getInventory().addItem(new Item("Fried Chicken", 6, 8,100));
        owner1.setManagedStall(s1);
        globalStalls.add(s1);

        // === Owner 2 ===
        Owner owner2 = new Owner("owner2", "owner2@email.com", "5678");
        Stall s2 = new Stall("Western Delights", owner2.getUsername());
        s2.getInventory().addItem(new Item("Spaghetti", 7, 12,20));
        s2.getInventory().addItem(new Item("Chicken Chop", 9, 15,20));
        owner2.setManagedStall(s2);
        globalStalls.add(s2);

        // Optional global stall registration (e.g., used by IStallService)
        if (stallService instanceof CanteenManager cm) {
            globalStalls.forEach(cm::addStall);
        }

        // Add users
        users.add(new Diner("diner1", "diner1@email.com", "1234", stallService));
        users.add(owner1);
        users.add(owner2);
        users.add(new Admin("admin1", "admin1@email.com", "adminpass"));

        return users;
    }

    public static List<Stall> getSampleStalls() {
        return globalStalls;
    }
}
