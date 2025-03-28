package transactions;

import orders.Order;
import inventory.Item;
import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private static int counter = 1;
    private final int txnID;
    private final String stallName;
    private final String dinerName;
    private final LocalDateTime dateTime;
    private final List<Item> items;
    private final int totalCost;
    private final String paymentMethod;
    private String status;
    private final int orderId;



    public Transaction(String stallName, Order order, String paymentMethod) {
        this.txnID = counter++;
        this.stallName = stallName;
        this.dinerName = order.getUsername();
        this.items = order.getItems();
        this.totalCost = items.stream().mapToInt(Item::getPrice).sum();
        this.dateTime = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.status = order.getStatus(); // Initially "Preparing"
        this.orderId = order.getID();   
    }

    public void markCompleted() {
        this.status = "Completed";
    }

    public void markCancelled() {
        this.status = "Cancelled";
    }

    public void display() {
        System.out.println("\nTxn ID: " + txnID + ", Stall: " + stallName + ", Diner: " + dinerName);
        System.out.println("Time: " + dateTime + ", Status: " + status);
        System.out.println("Items:");
        for (Item item : items) {
            System.out.println("- " + item.getName() + " ($" + item.getPrice() + ")");
        }
        System.out.println("Total: $" + totalCost + ", Paid via: " + paymentMethod);
    }

    public String getStatus() {
        return status;
    }

    public String getDinerName() {
        return dinerName;
    }

    public String getStallName() {
        return stallName;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public int getTxnID() {
        return txnID;
    }
}
