package queue;

import java.util.*;
import java.util.stream.Collectors;
import orders.Order;
public class QueueService implements IQueueService {
    private final Map<String, Queue<Order>> stallQueues = new HashMap<>();



    @Override
    public int enqueueOrder(String stallName, Order order) {
        Queue<Order> queue = stallQueues.computeIfAbsent(stallName, k -> new LinkedList<>());

        int waitTimeBeforeThisOrder = estimateWaitTime(queue);
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

    private int estimateWaitTime(Queue<Order> queue) {
        return queue.stream()
                .mapToInt(o -> o.getItems().stream().mapToInt(i -> i.getPrepTime()).sum())
                .sum();
    }

    @Override
    public Map<String, Queue<Order>> getStallQueues() {
        return Collections.unmodifiableMap(stallQueues); // Return an unmodifiable view for safety
    }

    @Override
    public String getStallNameForOrder(int orderId) {
        for (Map.Entry<String, Queue<Order>> entry : stallQueues.entrySet()) {
            for (Order o : entry.getValue()) {
                if (o.getID() == orderId) {
                    return entry.getKey();
                }
            }
        }
        return ""; // Return empty string if order is not found
    }

    @Override
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
}