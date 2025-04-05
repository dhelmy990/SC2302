package orders;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrdersByUser(String userId) {
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUsername().equals(userId)) {
                result.add(order);
            }
        }
        return result;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
    //Order cancelllation should be here instead of somewhr else
}
