package userInterface.Flow;
import users.*;
import java.util.*;

import dependencies.DependencyContainer;
import inventory.Item;
import userInterface.Menu.*;
import orders.Order;
import queue.IQueueService;
import queue.QueueManager;
import stalls.Stall;
import transactions.Transaction;
import utils.*;
import services.ItemUpdateService;
import services.ItemDeleteService;
import queue.CompletionService;

public class OwnerFlow extends Flow{

    OwnerMainMenu ownerMainMenu = new OwnerMainMenu();
    private final IQueueService queueService;
    private final CompletionService completionService;

    public OwnerFlow(DependencyContainer dependencies, IQueueService queueService,
            CompletionService completionService) {
        super(dependencies);
        this.queueService = queueService;
        this.completionService = completionService;
    }

    @Override
    public void run(User user){
        Owner owner = (Owner) user;
        while (true) {
            ownerMainMenu.display();
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            Stall stall = owner.getManagedStall(); // Access stall from owner
            if (stall == null) {
                System.out.println("No stall assigned to this owner yet. Please contact admin.");
                return;
            }

            switch (choice) {
                case 1 -> ItemUtils.displayInventory(stall.getInventory().getAllItems());
                case 2 -> {
                    Item newItem = ItemInputUtils.createItemFromInput(scanner);
                    stall.getInventory().addItem(newItem);
                    System.out.println("Item added.");
                }
                case 3 -> new ItemUpdateService(scanner).update(stall.getInventory());
                case 4 -> new ItemDeleteService(scanner).delete(stall.getInventory());
                case 5 -> {
                    List<Order> orders = queueService.getAllOrdersForStall(stall.getName());
                    if (orders.isEmpty()) {
                        System.out.println("No current orders.");
                    } else {
                        boolean hasActive = false;
                        for (Order o : orders) {
                            if (o.isPreparing() || o.isCooking()) {
                                hasActive = true;
                                OrderUtils.displayOrderSummary(o, false, false);
                            }
                        }
                        if (!hasActive) {
                            System.out.println("No preparing or cooking orders.");
                        }
                    }
                }
                case 6 -> {
                    List<Order> orders = queueService.getAllOrdersForStall(stall.getName());
                    List<Order> cookingOrders = new ArrayList<>();

                    for (Order o : orders) {
                        if (o.isCooking()) {
                            cookingOrders.add(o);
                        }
                    }

                    if (cookingOrders.isEmpty()) {
                        System.out.println("No Cooking orders to mark as completed.");
                        break;
                    }

                    System.out.println("\n--- Cooking Orders ---");
                    for (Order o : cookingOrders) {
                        OrderUtils.displayOrderSummary(o, false, false);
                    }

                    while (true) {
                        System.out.print("Enter Order ID to mark as completed or type exit to quit: ");
                        String input = scanner.nextLine();
                        if (input.equalsIgnoreCase("exit")) {
                            System.out.println("Marking order aborted.");
                            break;
                        }
                        try {
                            int orderId = Integer.parseInt(input);

                            boolean found = cookingOrders.stream().anyMatch(o -> o.getID() == orderId);
                            if (!found) {
                                System.out.println("Invalid Order ID. Please choose from the list above.");
                                continue;
                            }

                            // Use CompletionService to mark the order as completed
                            boolean updated = completionService.markOrderCompleted(stall.getName(), orderId);
                            if (updated) {
                                orderService.getTxnManagerInstance().updateStatusForOrder(orderId, "Completed");
                                System.out.println("Order marked as completed.");
                            } else {
                                System.out.println("Order could not be updated.");
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }
                }
                case 7 -> showTransactionsForStall(stall.getName());
                case 8 -> UserUtils.handleAccountUpdate(owner, scanner);
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void showTransactionsForStall(String stallName) {
        List<Transaction> transactions = orderService.getTxnManagerInstance().getAllTransactions();
        boolean found = false;
        for (Transaction txn : transactions) {
            if (txn.getStallName().equals(stallName)) {
                TransactionUtils.display(txn); 
                found = true;
            }
        }
        if (!found) {
            System.out.println("No transactions found for your stall.");
        }
    }
}
