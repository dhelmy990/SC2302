package queue;

import java.util.*;

import orders.Order;

public class QueueManager {
    private static QueueManager instance;
    private static final Map<String, Queue<Order>> stallQueues = new HashMap<>();
    private final Map<String, List<Order>> completedOrders = new HashMap<>();


    public static QueueManager getInstance() {
        if (instance == null) {
            instance = new QueueManager();
        }
        return instance;
    }

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

    public Order dequeueOrder(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        return (queue != null && !queue.isEmpty()) ? queue.poll() : null;
    }

    public int estimateWaitTime(Queue<Order> queue) {
        int totalTime = 0;
        for (Order o : queue) {
            totalTime += o.getItems().stream().mapToInt(i -> i.getPrepTime()).sum();
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
    public String getStallNameForOrder(int orderId) {
        for (Map.Entry<String, Queue<Order>> entry : QueueManager.getInstance().getAllQueues().entrySet()) {
            for (Order o : entry.getValue()) {
                if (o.getID() == orderId) {
                    return entry.getKey();
                }
            }
        }
        return "";
    }

    public boolean markOrderCompleted(String stallName, int orderId) {
        Queue<Order> queue = stallQueues.get(stallName);
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
    

    public List<Order> getCompletedOrdersForStall(String stallName) {
        return completedOrders.getOrDefault(stallName, Collections.emptyList());
    }

    public void updateCookingStatusForStall(String stallName) {
        Queue<Order> queue = stallQueues.get(stallName);
        if (queue != null && !queue.isEmpty()) {
            Order first = queue.peek();
            if (first.isPreparing()) {
                first.markCooking(); // only promote Preparing -> Cooking
            }
        }
    }
    

}
