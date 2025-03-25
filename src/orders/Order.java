package orders;

import inventory.Item;
import java.util.*;

public class Order {
    private static int count = 1;
    private List<Item> items;
    private int orderID;
    private int waitingTime;
    private String username; // To track the user who placed the order

    public Order(String username, List<Item> items) { // Updated constructor
        this.items = items;
        this.orderID = count;
        this.waitingTime = estWaitingTime();
        this.username = username; // Set the username
        count++;
    }

    // Returns the total waiting time for this specific Order object (which is the sum of individual Item prep times).
    private int estWaitingTime() {
        int waitingTime = 0;
        for (Item item : items) {
            waitingTime += item.getPrepTime();
        }
        return waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getID() {
        return orderID;
    }

    public String getUsername() {
        return username; // Getter for username
    }
}