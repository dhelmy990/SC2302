package utils;

import orders.Order;

public class ReceiptUtils {

    public static void printReceipt(Order order, int total, String paymentMethod, boolean isGuest) {
        System.out.println("\n====== " + (isGuest ? "Guest" : "Diner") + " Order Receipt ======");

        if (isGuest) {
            System.out.println("Your Guest ID: " + order.getUsername());
        } else {
            System.out.println("Your Order ID: " + order.getID());
        }

        System.out.println("Order ID: " + order.getID());
        System.out.println("Stall: " + order.getStallName());
        OrderUtils.displayGroupedItems(order.getItems());
        System.out.println("Total: $" + total);
        System.out.println("Paid via: " + paymentMethod);
        System.out.println("Ordered At: " + DateUtils.format(order.getOrderTime()));

        int waitTime = order.getWaitingTime();
        System.out.println("Est. Waiting Time: " + waitTime + " mins");
        System.out.println("Est. Collection Time: " + DateUtils.format(order.getOrderTime().plusMinutes(waitTime)));

        System.out.println("Keep your " + (isGuest ? "Guest ID" : "Order ID") + " for pickup.");
        System.out.println("=================================");
    }
}
