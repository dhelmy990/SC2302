package userInterface.flow;
import users.*;
import java.util.*;

import dependencies.DependencyContainer;
import orders.Order;
import services.INumericInputHandler;
import services.ITextInputHandler;
import userInterface.menu.DinerMainMenu;
import utils.*;

public class DinerFlow extends Flow{

    DinerMainMenu dinerMainMenu = new DinerMainMenu();

    OrderProcessor orderFlow;

    public DinerFlow(DependencyContainer dependencies){
        super(dependencies);
        orderFlow = new OrderProcessor(dependencies);
    }

    @Override
    public void run(User user){
        Diner diner = (Diner) user;
        INumericInputHandler numericInputHandler = getNumericInputHandler();
        ITextInputHandler textInputHandler = getTextInputHandler();
        while (true) {
            dinerMainMenu.display();
            int choice = numericInputHandler.getValidIntegerInput("Choose an option: ", 1, 5);

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

                       

                        while (true) {
                            System.out.print("> ");
                            String input = textInputHandler.getInput("\nEnter an Order ID to view full details or press Enter to skip: ");

                            if (input.isEmpty()) {
                                System.out.println("Returning to menu...");
                                break;
                            }

                            try {
                                int selectedId = Integer.parseInt(input);
                                Order selectedOrder = history.stream()
                                        .filter(o -> o.getID() == selectedId)
                                        .findFirst()
                                        .orElse(null);

                                if (selectedOrder != null) {
                                    System.out.println("\n--- Order Details ---");
                                    OrderUtils.displayDetailedOrder(selectedOrder);
                                    break; // Done viewing one, return to menu
                                } else {
                                    System.out.println("Order ID not found. Try again or press Enter to go back.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(
                                        "Invalid input. Please enter a valid Order ID or press Enter to skip.");
                            }
                        }
                    }
                }

                case 3 -> {
                    if (!orderFlow.handleOrderCancellationFlow(diner.getUsername())) {
                        System.out.println("Order not found or cannot be cancelled.");
                    }
                }
                case 4 -> accountUpdateService.updateAccount(user, users);

                
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
