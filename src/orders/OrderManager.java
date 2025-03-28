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
                System.out.println("\nOrder ID: " + order.getID() +
                        " | Stall: " + order.getStallName() +
                        " | Status: " + order.getStatus() +
                        " | Total: $" + order.getItems().stream().mapToInt(Item::getPrice).sum());

                System.out.println("Items:");
                for (Item i : order.getItems()) {
                    System.out.println("- " + i.getName() + " ($" + i.getPrice() + ")");
                }
            }
        }
        if (!found) {
            System.out.println("No orders found for user: " + userId);
        }
    }



  
    public void addOrder(Order order) {
        orders.add(order);
    }
}
