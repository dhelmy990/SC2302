import java.util.*;

public class OrderManager {
    
    // RequestOrder takes in 2 parameters: 
    // 1: String stallName -- NAME of STALL
    // 2: List <String> items -- list of ITEM NAMES
    // ------------------------------------------------------------
    // RequestOrder returns:
    // Int Estimated waiting time in MINUTES
    // Int -1 if TxnManager FAILS
    // ------------------------------------------------------------
    public int requestOrder(String stallName, List <String> items){
        int numOfItems = items.size();
        List <Order> orders = new ArrayList<Order>(); // Create empty array to contain Order objects
        for (int i = 0; i < numOfItems; i++){
            Order newOrder = new Order(items.get(i)); // Add new Order objects to array
            orders.add(newOrder);
        }
        boolean proceed = TxnManager.verifyTxn(stallName, orders); // Attempt to process transaction
        if (!proceed){
            return -1; // Return -1 if transaction fails
        }
        // To implement:
        // Pass orders to QueueManager
        // Retrieve est wait time from QueueManager
        return 1; // Placeholder -- To return est wait time in MINUTES
    }
}