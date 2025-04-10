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
    private final double  totalCost;
    private final String paymentMethod;
    private String status;
    private final int orderId;

    public Transaction(String stallName, Order order, String paymentMethod) {
        this.txnID = counter++;
        this.stallName = stallName;
        this.dinerName = order.getUsername();
        this.items = order.getItems();
        this.totalCost = items.stream().mapToDouble(Item::getPrice).sum();
        this.dateTime = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
        this.status = order.getStatus(); 
        this.orderId = order.getID();
    }

    public void markCompleted() {
        this.status = "Completed";
    }

    public void markCancelled() {
        this.status = "Cancelled";
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
