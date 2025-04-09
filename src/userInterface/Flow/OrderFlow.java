package userInterface.Flow;

import dependencies.DependencyContainer;
import inventory.Item;
import java.util.*;
import orders.Order;
import payments.*;
import services.PaymentService;
import services.UserInputHandler;
import stalls.Stall;
import users.Diner;
import users.User;
import utils.*;

public class OrderFlow extends Flow {

    private final UserInputHandler inputHandler; // Use UserInputHandler

    OrderFlow(DependencyContainer dependencies) {
        super(dependencies);
        this.inputHandler = dependencies.userInputHandler; // Initialize UserInputHandler
    }

    public void run(User user) {
        String username = user.getUsername();
        System.out.println("\nAvailable Stalls:");
        for (int i = 0; i < stalls.size(); i++) {
            System.out.println((i + 1) + ". " + stalls.get(i).getName());
        }

        
        int stallChoice = inputHandler.getValidIntegerInput("Select stall number: ", 1, stalls.size());
        Stall selectedStall = stalls.get(stallChoice - 1);
        List<Item> allItems = selectedStall.getInventory().getAllItems();

        List<Item> selectedItems = new ArrayList<>();
        Map<Item, Integer> itemCounts = new HashMap<>();

        while (true) {
            System.out.println("\n--- Menu ---");
            ItemUtils.displayMenu(allItems);

            
            int itemNum = inputHandler.getValidIntegerInput("Enter item number to add to order (0 to finish): ", 0, allItems.size());
            if (itemNum == 0) break;

            Item chosenItem = allItems.get(itemNum - 1);

            if (chosenItem.getQuantity() <= 0) {
                System.out.println("Sorry, that item is out of stock.");
                continue;
            }

            chosenItem.reduceQuantity(1); // Temporarily reserve stock
            selectedItems.add(chosenItem);
            itemCounts.put(chosenItem, itemCounts.getOrDefault(chosenItem, 0) + 1);
            System.out.println(chosenItem.getName() + " added to your order.");
        }

        if (selectedItems.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        int total = orderService.calculateTotalCost(selectedItems);

        System.out.println("\nOrder Summary:");
        itemCounts.forEach((item, qty) ->
            System.out.println("- " + item.getName() + " x" + qty + " ($" + item.getPrice() + " each)")
        );
        System.out.println("Total Cost: $" + total);

        
        int paymentChoice = inputHandler.getValidIntegerInput("""
            Choose Payment Method:
            1. Card
            2. Cash
            3. QR / PayNow
            Enter payment option: """, 1, 3);

        IPaymentProcessor paymentProcessor;
        String paymentMethod;
        switch (paymentChoice) {
            case 1 -> {
                paymentProcessor = new CardPaymentProcessor(scanner);
                paymentMethod = "Card";
            }
            case 2 -> {
                paymentProcessor = new CashPaymentProcessor(scanner, total);
                paymentMethod = "Cash";
            }
            case 3 -> {
                paymentProcessor = new QRPaymentProcessor(scanner);
                paymentMethod = "QR / PayNow";
            }
            default -> throw new IllegalStateException("Unexpected value: " + paymentChoice);
        }

        PaymentService paymentService = new PaymentService(paymentProcessor);
        if (!paymentService.makePayment()) {
            System.out.println("Payment failed. Order not placed.");
            restoreItemQuantities(itemCounts); // Restore reserved quantities
            return;
        }

        Order order = new Order(username, selectedItems, selectedStall.getName());
        order.setPaymentMethod(paymentMethod);

        txnManager.recordTransaction(selectedStall.getName(), order);
        int waitTime = orderService.placeOrder(selectedStall.getName(), order);

        if (waitTime >= 0) {
            System.out.println("Payment successful.");
            boolean isGuest = username.startsWith("guest") && !isRegisteredDiner(username);
            ReceiptUtils.printReceipt(order, total, paymentMethod, isGuest);
        } else {
            System.out.println("Order failed during processing.");
            restoreItemQuantities(itemCounts); 
        }
    }

    private static void restoreItemQuantities(Map<Item, Integer> itemCounts) {
        for (Map.Entry<Item, Integer> entry : itemCounts.entrySet()) {
            entry.getKey().addQuantity(entry.getValue());
        }
    }

    private boolean isRegisteredDiner(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user instanceof Diner;
            }
        }
        return false; // Not found => probably a guest
    }
}
