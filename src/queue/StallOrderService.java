package queue;

import java.util.Map;
import java.util.Queue;

import orders.Order;

public class StallOrderService implements IStallOrderService {
    private final IQueueService queueService;

    public StallOrderService(IQueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public String getStallNameForOrder(int orderId) {
        for (Map.Entry<String, Queue<Order>> entry : queueService.getStallQueues().entrySet()) {
            for (Order o : entry.getValue()) {
                if (o.getID() == orderId) {
                    return entry.getKey();
                }
            }
        }
        return "";
    }
}