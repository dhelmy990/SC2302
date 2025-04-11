package services;

import java.util.Queue;
import orders.Order;

public interface IWaitTimeEstimator {
    int estimateWaitTime(Queue<Order> queue);
}
