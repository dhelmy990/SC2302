package users;
import transactions.TxnManager;
import java.util.*;
import inventory.*;
import orders.*;

public class Diner extends User{
    public Diner(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "Diner";
    }

    // Place order via OrderManager
    public int placeOrder(OrderManager orderManager, String stallName, List<Item> items) {
        return orderManager.requestOrder(username, stallName, items);
    }

    // View order summary after payment
    public void viewOrderSummary(OrderManager orderManager, String username) {
        TxnManager.displayOrderSummaryForUser(username);
    }

    // View past order history (for logged-in users)
    public void viewOrderHistory(OrderManager orderManager, String username) {
        TxnManager.displayOrderHistoryForUser(username);
    }
}
