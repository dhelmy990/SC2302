package services;

import inventory.Item;
import orders.Order;
import orders.OrderManager;
import orders.QueueManager;
import transactions.TxnManager;

import java.util.List;

public class OrderService {
    private final OrderManager orderManager;
    private final TxnManager txnManager;

    public OrderService(OrderManager orderManager, TxnManager txnManager) {
        this.orderManager = orderManager;
        this.txnManager = txnManager;
    }

    public int placeOrder(String stallName, String username, List<Item> items) {
        Order order = new Order(username, items);
        boolean isPaid = txnManager.verifyTxn(stallName, order);

        if (!isPaid)
            return -1;

        int estTime = QueueManager.getInstance().enqueueOrder(stallName, order);
        orderManager.addOrder(order);

        return estTime;
    }

    public void showOrderHistory(String userId) {
        orderManager.displayOrderHistoryForUser(userId);
    }
    
    public int calculateTotalCost(List<Item> items) {
        return items.stream().mapToInt(Item::getPrice).sum();
    }

}