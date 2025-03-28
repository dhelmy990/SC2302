package orders;
import java.util.*;

import inventory.Item;
import transactions.TxnManager;

public class OrderManager {
    
    // RequestOrder takes in 2 parameters: 
    // 1: String stallName -- NAME of STALL
    // 2: List <String> items -- list of ITEM NAMES
    // ------------------------------------------------------------
    // RequestOrder returns:
    // Int Estimated waiting time in MINUTES
    // Int -1 if TxnManager FAILS
    // ------------------------------------------------------------
    public int requestOrder(String stallName, List <Item> items){
        Order newOrder = new Order(items); // Create new Order object
        boolean proceed = TxnManager.verifyTxn(stallName, newOrder); // Attempt to process transaction
        if (!proceed){
            return -1; // Return -1 if transaction fails
        }
        // To implement:
        // Pass order to QueueManager
        // Retrieve total est wait time from QueueManager

        return 1; // Placeholder -- To return est wait time in MINUTES
    }
}