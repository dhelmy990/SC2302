package queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Iterator;

import orders.Order;

public class CompletionService implements ICompletionService{
    private final Map<String, List<Order>> completedOrders = new HashMap<>();
    private final QueueService queueService;

    public CompletionService() {
        this.queueService = QueueManager.getInstance().getQueueService();
    }

    @Override
    public boolean markOrderCompleted(String stallName, int orderId) {
        Queue<Order> queue = ((QueueService) queueService).getStallQueues().get(stallName);
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
        Queue<Order> queue = ((QueueService) queueService).getStallQueues().get(stallName);
        if (queue != null && !queue.isEmpty()) {
            Order first = queue.peek();
            if (first.isPreparing()) {
                first.markCooking();
            }
        }
    }
}
