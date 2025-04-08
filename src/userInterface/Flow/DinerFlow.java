package userInterface.Flow;
import users.*;
import java.util.*;

import dependencies.DependencyContainer;
import userInterface.Menu.DinerMainMenu;
import orders.Order;
import utils.*;

public class DinerFlow extends Flow{

    DinerMainMenu dinerMainMenu = new DinerMainMenu();

    OrderFlow orderFlow;

    public DinerFlow(DependencyContainer dependencies){
        super(dependencies);
        orderFlow = new OrderFlow(dependencies);
    }

    @Override
    public void run(User user){
        Diner diner = (Diner) user;
        while (true) {
            dinerMainMenu.display();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> orderFlow.run(diner);
                case 2 -> {
                    List<Order> history = orderService.getOrderHistory(diner.getUsername());
                    if (history.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order order : history) {
                            OrderUtils.displayOrderSummary(order, true, false);
                        }
                    }
                }

                case 3 -> {
                    if (!orderService.handleOrderCancellationFlow(diner.getUsername(), scanner)) {
                        System.out.println("Order not found or cannot be cancelled.");
                    }
                }
                case 4 -> UserUtils.handleAccountUpdate(diner, scanner);

                
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
