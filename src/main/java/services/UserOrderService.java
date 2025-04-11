package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import orders.Order;
import queue.IQueueManager;

public class UserOrderService implements IUserOrderService {
    private final IQueueManager queueService;

    public UserOrderService(IQueueManager queueService) {
        this.queueService = queueService;
    }

    @Override
    public List<Order> getOrdersByUser(String username) {
        List<Order> result = new ArrayList<>();
        for (Queue<Order> queue : queueService.getStallQueues().values()) {
            for (Order o : queue) {
                if (o.getUsername().equals(username)) {
                    result.add(o);
                }
            }
        }
        return result;
    }
}