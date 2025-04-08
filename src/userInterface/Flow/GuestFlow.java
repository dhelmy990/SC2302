package userInterface.Flow;

import java.util.List;
import dependencies.DependencyContainer;
import orders.Order;
import users.User;
import utils.OrderUtils;

public class GuestFlow extends Flow {

    OrderFlow orderFlow;

    public GuestFlow(DependencyContainer dependencies){
        super(dependencies);
        orderFlow = new OrderFlow(dependencies);
    }

    @Override
    public void run(User user){

            while (true) {
                System.out.println("\n--- Guest Menu ---");
                System.out.println("1. Order Food");
                System.out.println("2. Track My Orders");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> orderFlow.run(user);
                    case 2 -> {
                        System.out.print("Enter your Guest ID: ");
                        String guestIdInput = scanner.nextLine();
                        List<Order> guestOrders = orderManager.getOrdersByUser(guestIdInput);
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
