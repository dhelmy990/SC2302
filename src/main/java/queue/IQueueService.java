package queue;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import orders.Order;
public interface IQueueService {
    int enqueueOrder(String stallName, Order order);

    Order dequeueOrder(String stallName);

    List<Order> getAllOrdersForStall(String stallName);

    int getOrderPosition(String stallName, int orderId);

    Map<String, Queue<Order>> getStallQueues();

}


