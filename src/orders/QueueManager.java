// === FILE: QueueManager.java ===
package orders;

import java.util.*;

public class QueueManager {
    private static QueueManager instance;
    private final Map<String, Queue<Order>> stallQueues;

    private QueueManager() {
        stallQueues = new HashMap<>();
    }

    public static QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

    public int enqueueOrder(String stallName, Order order) {
        Queue<Order> queue = stallQueues.computeIfAbsent(stallName, k -> new LinkedList<>());
        queue.offer(order);
        return estimateWaitTime(queue);
    }

    public Order dequeueOrder(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        return (queue != null && !queue.isEmpty()) ? queue.poll() : null;
    }

    public int estimateWaitTime(Queue<Order> queue) {
        int totalTime = 0;
        for (Order o : queue) {
            totalTime += o.getWaitingTime();
        }
        return totalTime;
    }

    public int getOrderPosition(String stallName, int orderId) {
        Queue<Order> queue = stallQueues.get(stallName);
        if (queue == null)
            return -1;
        int position = 1;
        for (Order o : queue) {
            if (o.getID() == orderId)
                return position;
            position++;
        }
        return -1; // Not found
    }

    public List<Order> getAllOrdersForStall(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        return queue != null ? new ArrayList<>(queue) : Collections.emptyList();
    }

    public Map<String, Queue<Order>> getAllQueues() {
        return stallQueues;
    }
    
    public List<Order> getOrdersByUser(String username) {
        List<Order> result = new ArrayList<>();
        for (Queue<Order> queue : stallQueues.values()) {
            for (Order o : queue) {
                if (o.getUsername().equals(username)) {
                    result.add(o);
                }
            }
        }
        return result;
    }

    public boolean markOrderCompleted(String stallName, int orderId) {
        Queue<Order> queue = stallQueues.get(stallName);
        if (queue != null) {
            for (Order order : queue) {
                if (order.getID() == orderId) {
                    order.markCompleted();
                    return true;
                }
            }
        }
        return false;
    }

}
