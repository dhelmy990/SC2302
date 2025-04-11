package services;

import java.util.List;


import orders.Order;
public interface ICompletionService {


     boolean markOrderCompleted(String stallName, int orderId);

    List<Order> getCompletedOrdersForStall(String stallName);
}
