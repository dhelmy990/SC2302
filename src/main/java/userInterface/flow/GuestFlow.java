package userInterface.flow;

import java.util.List;
import dependencies.DependencyContainer;
import orders.Order;
import queue.UserOrderService;
import services.INumericInputHandler;
import services.ITextInputHandler;
import users.User;
import utils.OrderUtils;

public class GuestFlow extends Flow {

    OrderFlow orderFlow;
    UserOrderService userOrderService;

    public GuestFlow(DependencyContainer dependencies){
        super(dependencies);
        orderFlow = new OrderFlow(dependencies);
        this.userOrderService = dependencies.getUserOrderService();
    }

    @Override
    public void run(User user){
    INumericInputHandler numericInputHandler = getNumericInputHandler();
        ITextInputHandler textInputHandler = getTextInputHandler();
            while (true) {
                System.out.println("\n--- Guest Menu ---");
                System.out.println("1. Order Food");
                System.out.println("2. Track My Orders");
                System.out.println("3. Exit");
                int choice = numericInputHandler.getValidIntegerInput("Choose an option: ", 1, 3);

                switch (choice) {
                    case 1 -> orderFlow.run(user);
                    case 2 -> {
                        String guestIdInput = textInputHandler.getNonEmptyInput("Enter your Guest ID: ");
                        List<Order> guestOrders = userOrderService.getOrdersByUser(guestIdInput);
                        if (guestOrders.isEmpty()) {
                            System.out.println("No orders found for guest ID: " + guestIdInput);
                        } else {
                            for (Order order : guestOrders) {
                                OrderUtils.displayOrderSummary(order, false, true); // guest = same as diner
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("Thank you for visiting!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }
