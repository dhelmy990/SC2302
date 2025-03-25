package users;

import inventory.Item;
import orders.OrderManager;
import stalls.IStallService;
import stalls.Stall;

import java.util.List;

public class Diner extends User {
    private final IStallService stallService;

    public Diner(String username, String email, String password, IStallService stallService) {
        super(username, email, password);
        this.stallService = stallService;
    }

    @Override
    public String getRole() {
        return "Diner";
    }

    // View all available stalls
    public void viewStalls() {
        List<Stall> stalls = stallService.getAllStalls();
        if (stalls.isEmpty()) {
            System.out.println("No stalls available.");
            return;
        }
        System.out.println("Available Stalls:");
        for (Stall stall : stalls) {
            System.out.println("Stall ID: " + stall.getStallId() + ", Name: " + stall.getName());
        }
    }

    // Place order via OrderManager
    public int placeOrder(OrderManager orderManager, String stallName, List<Item> items) {
        return orderManager.requestOrder(stallName, items);
    }

    // View past order history (for logged-in users)
    public void viewOrderHistory(OrderManager orderManager, String username) {
        orderManager.displayOrderHistoryForUser(username);
    }
}