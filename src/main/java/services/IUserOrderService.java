package services;

import java.util.List;

import orders.Order;

public interface IUserOrderService {

    

     List<Order> getOrdersByUser(String username);
}
