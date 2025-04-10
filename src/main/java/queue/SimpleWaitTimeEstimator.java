package queue;

import java.util.Queue;
import orders.Order;

public class SimpleWaitTimeEstimator implements IWaitTimeEstimator {

    @Override
    public int estimateWaitTime(Queue<Order> queue) {
        return queue.stream()
                .mapToInt(order -> order.getItems().stream().mapToInt(item -> item.getPrepTime()).sum())
                .sum();
    }
}
