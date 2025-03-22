import java.util.*;

public class OrderManager {

    public int requestOrder(String stallName, List <String> items){

        int numOfItems = items.size();
        List <Order> orders = new ArrayList<Order>();
        for (int i = 0; i < numOfItems; i++){
            Order newOrder = new Order(items.get(i));
            orders.add(newOrder);
        }
        boolean proceed = TxnManager.verifyTxn(stallName, orders);
        if (!proceed){
            return -1;
        }
        // To implement:
        // Pass orders to QueueManager
        // Retrieve est wait time from QueueManager
        // Return est wait time
        return 1; // Placeholder
    }
}