package orders;

import java.util.*;
import transactions.*;

public class QueueManager {
    private static QueueManager instance;
    private final Map<String, Deque<Order>> queues = new HashMap<>();

    private QueueManager() {}

    public static QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

    public int enqueueOrder(String stallName, Order order) {
        queues.computeIfAbsent(stallName, k -> new LinkedList<>()).add(order);
        return calculateEstimatedCompletionTime(stallName, order.getWaitingTime());
    }

    private int calculateEstimatedCompletionTime(String stallName, int orderWaitTime) {
        Deque<Order> queue = queues.get(stallName);
        if (queue.isEmpty()) {
            return orderWaitTime;
        } else {
            Order lastOrder = queue.peekLast();
            return lastOrder.getWaitingTime() + orderWaitTime;
        }
    }

    public Order dequeueOrder(String stallName) {
        return queues.getOrDefault(stallName, new LinkedList<>()).poll();
    }
}