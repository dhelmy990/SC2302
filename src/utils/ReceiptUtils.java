package utils;

import orders.Order;

public class ReceiptUtils {

    public static void printGuestReceipt(Order order, int total, String paymentMethod) {
        System.out.println("\n====== Guest Order Receipt ======");
        System.out.println("Your Guest ID: " + order.getUsername());
        System.out.println("Order ID: " + order.getID());
        System.out.println("Stall: " + order.getStallName());
        OrderUtils.displayGroupedItems(order.getItems());
        System.out.println("Total: $" + total);
        System.out.println("Paid via: " + paymentMethod);
        System.out.println("Ordered At: " + DateUtils.format(order.getOrderTime()));
        System.out.println(
                "Est. Collection Time: " + DateUtils.format(order.getOrderTime().plusMinutes(order.getWaitingTime())));
        System.out.println("Keep your Order ID for pickup.");
        System.out.println("=================================");
    }
}
