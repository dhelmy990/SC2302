package services;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import queue.QueueManager;
import inventory.Item;
import orders.*;
import stalls.IStallService;
import stalls.Stall;
import transactions.TxnManager;
import utils.OrderUtils;


public class OrderService {
    private final OrderManager orderManager;
    private final TxnManager txnManager;
    private final IStallService stallService;
    private final QueueManager queueManager;

    public OrderService(OrderManager orderManager, QueueManager queueManager2, TxnManager txnManager, IStallService stallService) {
        this.orderManager = orderManager;
        this.txnManager = txnManager;
        this.stallService = stallService;
        this.queueManager = queueManager2;
    }

    public int placeOrder(String stallName, Order order) {
        int estTime = queueManager.enqueueOrder(stallName, order);
        orderManager.addOrder(order);

        return estTime;
    }

    public List<Order> getOrderHistory(String userId) {
        return orderManager.getOrdersByUser(userId);
    }

    public int calculateTotalCost(java.util.List<inventory.Item> items) {
        return items.stream().mapToInt(inventory.Item::getPrice).sum();
    }
    public Order cancelOrder(String username, int orderId) {
        String stallName = queueManager.getStallNameForOrder(orderId);
        Queue<Order> queue = queueManager.getAllQueues().getOrDefault(stallName, new LinkedList<>());
    
        Iterator<Order> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            if (o.getID() == orderId && o.getUsername().equals(username)) {
                if (o.isCooking()) {
                    System.out.println("Order is already being cooked and cannot be cancelled.");
                    return null;
                }
                if (o.isPreparing()) {
                    o.markCancelled();
                    txnManager.updateStatusForOrder(orderId, "Cancelled");
                    Stall stall = stallService.getAllStalls().stream()
                        .filter(s -> s.getName().trim().equalsIgnoreCase(stallName.trim()))
                        .findFirst()
                        .orElse(null);
                    if (stall != null) {
                        for (Item item : o.getItems()) {
                            Item inventoryItem = stall.getInventory().findItemByName(item.getName());
                            if (inventoryItem != null) {
                                inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
                }
                    }
            }

            iterator.remove();
            return o;
        }

            }
            
        }
        return null;
    }
    
    public boolean handleOrderCancellationFlow(String username, Scanner scanner) {
        List<Order> orders = queueManager.getOrdersByUser(username);
        List<Order> cancellable = new ArrayList<>();
    
        for (Order o : orders) {
            if (o.isPreparing()) {
                cancellable.add(o);
            }
        }
    
        if (cancellable.isEmpty()) {
            System.out.println("No cancellable orders found.");
            return false;
        }
    
        System.out.println("\nYour current preparing orders:");
        for (Order o : cancellable) {
            OrderUtils.displayOrderSummary(o, true, username.startsWith("guest_"));
        }
    
        while (true) {
            System.out.print("Enter Order ID to cancel or type exit to quit: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Cancellation aborted.");
                break;
            }
            try {
                int id = Integer.parseInt(input);
    
                boolean isValid = cancellable.stream().anyMatch(o -> o.getID() == id);
                if (!isValid) {
                    System.out.println("Invalid Order ID. Please choose from the list above.");
                    continue;
                }
    
                Order cancelledOrder = cancelOrder(username, id);
                if (cancelledOrder != null) {
                    int refundAmount = cancelledOrder.getItems().stream().mapToInt(Item::getPrice).sum();
                    System.out.println("Order cancelled successfully. $" + refundAmount + " has been refunded.");
                    return true;
                } else {
                    System.out.println("Order not found or cannot be cancelled.");
                    return false;
                }
    
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    
        return false;
    }
    
    
    


}
