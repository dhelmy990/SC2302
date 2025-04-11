package services;

import java.util.*;
import orders.Order;
import queue.IQueueManager;

public class CompletionService implements ICompletionService {
    private final Map<String, List<Order>> completedOrders = new HashMap<>();
    private final IQueueManager queueService;

    public CompletionService(IQueueManager queueService) {
        this.queueService = queueService;
    }

    @Override
    public boolean markOrderCompleted(String stallName, int orderId) {
        Queue<Order> queue = queueService.getStallQueues().get(stallName);
        if (queue != null) {
            Iterator<Order> it = queue.iterator();
            while (it.hasNext()) {
                Order order = it.next();
                if (order.getID() == orderId) {
                    order.markCompleted();
                    it.remove();
                    completedOrders.computeIfAbsent(stallName, k -> new ArrayList<>()).add(order);
                    updateCookingStatusForStall(stallName);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Order> getCompletedOrdersForStall(String stallName) {
        return completedOrders.getOrDefault(stallName, Collections.emptyList());
    }

    private void updateCookingStatusForStall(String stallName) {
        Queue<Order> queue = queueService.getStallQueues().get(stallName);
        if (queue != null && !queue.isEmpty()) {
            Order first = queue.peek();
            if (first.isPreparing()) {
                first.markCooking();
            }
        }
    }
}