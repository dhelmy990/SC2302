package orders;
import java.util.*;

import inventory.Item;
import transactions.TxnManager;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();

    public void displayOrderHistoryForUser(String userId) {
        boolean found = false;
        for (Order order : orders) {
            if (order.getUsername().equals(userId)) {
                found = true;
                System.out.println("Order ID: " + order.getID() + ", Waiting Time: " + order.getWaitingTime() + " minutes");
            }
        }
        if (!found) {
            System.out.println("No orders found for user: " + userId);
        }
    }
    // RequestOrder takes in 2 parameters: 
    // 1: String stallName -- NAME of STALL
    // 2: List <String> items -- list of ITEM NAMES
    // ------------------------------------------------------------
    // RequestOrder returns:
    // Int Estimated waiting time in MINUTES
    // Int -1 if TxnManager FAILS
    // ------------------------------------------------------------



    public int requestOrder(String stallName, String username, List<Item> items) {
        Order newOrder = new Order(username, items); // Create new Order object with username
        boolean proceed = TxnManager.getInstance().verifyTxn(stallName, newOrder); // Use instance of TxnManager
        if (!proceed) {
            return -1; // Return -1 if transaction fails
        }

        // Pass order to QueueManager and retrieve estimated waiting time
        QueueManager queueManager = QueueManager.getInstance();
        int estWaitTime = queueManager.enqueueOrder(stallName, newOrder);

        // Add the order to the list of orders
        orders.add(newOrder);

        return estWaitTime; // Return the estimated waiting time
    
}
}