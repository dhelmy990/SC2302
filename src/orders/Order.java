package orders;

import inventory.Item;
import java.util.List;

public class Order {
    private static int counter = 1;
    private final int id;
    private final String username;
    private final List<Item> items;
    private String status = "Preparing";
    private String paymentMethod;
    private final String stallName;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStallName() {
        return stallName;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Order(String username, List<Item> items, String stallName) {
        this.id = counter++;
        this.username = username;
        this.items = items;
        this.stallName = stallName;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getWaitingTime() {
        return items.stream().mapToInt(Item::getPrepTime).sum();
    }

    public String getStatus() {
        return status;
    }

    public void markCompleted() {
        this.status = "Completed";
    }
    
    public void markCancelled() {
        this.status = "Cancelled";
    }

    // ✅ Add this
    public List<Item> getItems() {
        return items;
    }
}
