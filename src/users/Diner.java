package users;
import java.util.*;

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
        return orderManager.requestOrder(stallName, items);
    }

    // View order summary after payment
    public void viewOrderSummary(OrderManager orderManager, String username) {
        orderManager.displayOrderSummaryForUser(username);
    }

    // View past order history (for logged-in users)
    public void viewOrderHistory(OrderManager orderManager, String username) {
        orderManager.displayOrderHistoryForUser(username);
    }
}
