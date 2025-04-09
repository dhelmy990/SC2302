package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import orders.Order;

public class UserOrderService implements IUserOrderService {
    private final IQueueService queueService;

    public UserOrderService(IQueueService queueService) {
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