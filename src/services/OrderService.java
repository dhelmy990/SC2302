package services;

import java.util.List;

import orders.Order;
import orders.OrderManager;
import orders.QueueManager;
import transactions.TxnManager;

public class OrderService {
    private final OrderManager orderManager;
    private final TxnManager txnManager;

    public OrderService(OrderManager orderManager, TxnManager txnManager) {
        this.orderManager = orderManager;
        this.txnManager = txnManager;
    }

    // ✅ Updated to accept Order object
    public int placeOrder(String stallName, Order order) {
        boolean isPaid = txnManager.verifyTxn(stallName, order);

        if (!isPaid)
            return -1;

        int estTime = QueueManager.getInstance().enqueueOrder(stallName, order);
        orderManager.addOrder(order);

        return estTime;
    }

public List<Order> getOrderHistory(String userId) {
    return orderManager.getOrdersByUser(userId);
}


    public int calculateTotalCost(java.util.List<inventory.Item> items) {
        return items.stream().mapToInt(inventory.Item::getPrice).sum();
    }
}
