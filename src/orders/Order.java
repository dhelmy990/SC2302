package orders;

import inventory.Item;
import java.util.List;
import java.time.LocalDateTime;

public class Order {
    private static int counter = 1;
    private final int id;
    private final String username;
    private final List<Item> items;
    private String status = "Preparing";
    private String paymentMethod;
    private final String stallName;
    private final LocalDateTime orderTime;
    private int waitingTime;

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    

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
        this.orderTime = LocalDateTime.now();
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getWaitingTime() {
        return waitingTime;
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
    public void markCooking() {
        this.status = "Cooking";
    }
    public List<Item> getItems() {
        return items;
    }

    public boolean isCooking() {
        return "Cooking".equals(status);
    }
    
    public boolean isPreparing() {
        return "Preparing".equals(status);
    }
    
    
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
}
