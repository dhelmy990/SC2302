package orders;

import inventory.Item;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private final List<Order> orders = new ArrayList<>();

    public void displayOrderHistoryForUser(String userId) {
        boolean found = false;
        for (Order order : orders) {
            if (order.getUsername().equals(userId)) {
                found = true;
                int total = order.getItems().stream().mapToInt(Item::getPrice).sum();
                System.out.println("Order ID: " + order.getID()
                        + " | Status: " + order.getStatus()
                        + " | Total: $" + total
                        + " | Waiting Time: " + order.getWaitingTime() + " mins");
            }
        }
        if (!found) {
            System.out.println("No orders found for user: " + userId);
        }
    }

    // ✅ NEW METHOD
    public void addOrder(Order order) {
        orders.add(order);
    }
}
