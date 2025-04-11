package queue;

import java.util.*;
import orders.Order;
import services.IWaitTimeEstimator;

public class QueueManager implements IQueueManager {
    private final Map<String, Queue<Order>> stallQueues = new HashMap<>();
    private final IWaitTimeEstimator waitTimeEstimator;
    
    public QueueManager(IWaitTimeEstimator waitTimeEstimator) {
        this.waitTimeEstimator = waitTimeEstimator;
    }

    @Override
    public int enqueueOrder(String stallName, Order order) {
        Queue<Order> queue = stallQueues.computeIfAbsent(stallName, k -> new LinkedList<>());

        int waitTimeBeforeThisOrder = waitTimeEstimator.estimateWaitTime(queue);
        int thisOrderPrepTime = order.getItems().stream().mapToInt(i -> i.getPrepTime()).sum();

        order.setWaitingTime(waitTimeBeforeThisOrder + thisOrderPrepTime);
        queue.offer(order);

        Order head = queue.peek();
        if (head != null && head.getStatus().equals("Preparing")) {
            head.markCooking();
        }
        return order.getWaitingTime();
    }

    @Override
    public Order dequeueOrder(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        return (queue != null && !queue.isEmpty()) ? queue.poll() : null;
    }

    @Override
    public List<Order> getAllOrdersForStall(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        return queue != null ? new ArrayList<>(queue) : Collections.emptyList();
    }

    @Override
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
        return -1;
    }

    @Override
    public Map<String, Queue<Order>> getStallQueues() {
        return Collections.unmodifiableMap(stallQueues); // Return an unmodifiable view for safety
    }


}