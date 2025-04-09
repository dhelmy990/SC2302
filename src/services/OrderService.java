package services;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import queue.IQueueService;
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
    private final IQueueService queueservice;

    public OrderService(OrderManager orderManager, 
            IQueueService queueservice, TxnManager txnManager,
            IStallService stallService) {
        this.orderManager = orderManager;
        this.txnManager = txnManager;
        this.stallService = stallService;
        this.queueservice = queueservice;
    }
    public int placeOrder(String stallName, Order order) {
        int estTime = queueservice.enqueueOrder(stallName, order);
        orderManager.addOrder(order);

        return estTime;
    }
    public List<Order> getCancellableOrders(String username) {
        return queueservice.getOrdersByUser(username).stream()
            .filter(Order::isPreparing)
            .toList();
    }
    public List<Order> getOrderHistory(String userId) {
        return orderManager.getOrdersByUser(userId);
    }

    public int calculateTotalCost(java.util.List<inventory.Item> items) {
        return items.stream().mapToInt(inventory.Item::getPrice).sum();
    }

    public Order cancelOrder(String username, int orderId) {
        String stallName = queueservice.getStallNameForOrder(orderId);
        if (stallName == null || stallName.isEmpty()) {
            System.out.println("Order not found.");
            return null;
        }

        // Get the queue for the stall
        List<Order> stallQueue = queueservice.getAllOrdersForStall(stallName);

        Iterator<Order> iterator = stallQueue.iterator();
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

                    // Refund inventory items
                    Stall stall = stallService.getAllStalls().stream()
                            .filter(s -> s.getName().equals(stallName))
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

                    // Remove the order from the queue
                    iterator.remove();
                    return o;
                }
            }
        }
        return null;
    }
    
  
    
    
    


}
