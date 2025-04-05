import inventory.Item;
import orders.Order;
import orders.OrderManager;
import orders.QueueManager;
import payments.*;
import services.*;
import simulated.SimulatedData;
import stalls.CanteenManager;
import stalls.IStallService;
import stalls.Stall;
import transactions.Transaction;
import transactions.TxnManager;
import users.*;
import utils.*;

import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final IStallService canteenManager = new CanteenManager();
    private static final List<User> users = SimulatedData.getSampleUsers(canteenManager);
    private static final List<Stall> stalls = SimulatedData.getSampleStalls();
    private static final TxnManager txnManager = new TxnManager();
    private static final OrderManager orderManager = new OrderManager();
    private static final OrderService orderService = new OrderService(orderManager, txnManager, canteenManager);
    private static final AdminService adminService = new AdminService(users, stalls);


   


    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Welcome to the Canteen System ===");
            System.out.println("1. Log In");
            System.out.println("2. Sign Up");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> signUp();
                case 3 -> guestFlow();
                case 4 -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.login(username, password)) {
                System.out.println("Login successful as " + user.getRole());
                switch (user.getRole()) {
                    case "Diner" -> dinerMenu((Diner) user);
                    case "Owner" -> ownerMenu((Owner) user);
                    case "Admin" -> adminMenu((Admin) user);
                }
                return;
            }
        }
        System.out.println("Login failed. Incorrect credentials.");
    }

    private static void signUp() {
        System.out.print("Choose role (diner/owner): ");
        String role = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
    
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists.");
                return;
            }
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Email already in use.");
                return;
            }
        }
    
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
    
        switch (role.toLowerCase()) {
            case "diner" -> users.add(new Diner(username, email, password, canteenManager));
            case "owner" -> users.add(new Owner(username, email, password));
            default -> {
                System.out.println("Invalid role.");
                return;
            }
        }
    
        System.out.println("Account created successfully. You may now log in.");
    }
    
    private static void guestFlow() {
        String guestId = "guest_" + UUID.randomUUID().toString();
        System.out.println("\nContinuing as Guest Diner...");
        System.out.println("Your Guest ID (for tracking): " + guestId);

        while (true) {
            System.out.println("\n--- Guest Menu ---");
            System.out.println("1. Order Food");
            System.out.println("2. Track My Orders");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewStallsAndOrder(guestId);
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

    private static void dinerMenu(Diner diner) {
        while (true) {
            System.out.println("\n--- Diner Menu ---");
            System.out.println("1. Order Food");
            System.out.println("2. View Order History");
            System.out.println("3. Cancel an Order"); 
            System.out.println("4. View/Update Account Details");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewStallsAndOrder(diner.getUsername());
                case 2 -> {
                    List<Order> history = orderService.getOrderHistory(diner.getUsername());
                    if (history.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        for (Order order : history) {
                            OrderUtils.displayOrderSummary(order, true, false); // 👈 isDiner = true
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



private static void ownerMenu(Owner owner) {
    while (true) {
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Inventory");
        System.out.println("3. Update Item in Inventory");
        System.out.println("4. Delete Item from Inventory"); 
        System.out.println("5. View Current Orders");
        System.out.println("6. Mark Order As Completed");
        System.out.println("7. View Transaction History");
        System.out.println("8. View/Update Account Details");
        System.out.println("9. Logout");

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

            case 3 -> InventoryUtils.updateItemFlow(stall.getInventory(), scanner);

            case 4 -> InventoryUtils.deleteItemFlow(stall.getInventory(), scanner); 
            case 5 -> {
                List<Order> orders = QueueManager.getInstance().getAllOrdersForStall(stall.getName());
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
                List<Order> orders = QueueManager.getInstance().getAllOrdersForStall(stall.getName());
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
            
                        boolean updated = QueueManager.getInstance().markOrderCompleted(stall.getName(), orderId);
                        if (updated) {
                            txnManager.updateStatusForOrder(orderId, "Completed");
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

private static void showTransactionsForStall(String stallName) {
    List<Transaction> transactions = txnManager.getAllTransactions();
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

private static void adminMenu(Admin admin) {
    while (true) {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. Add New User");
        System.out.println("2. Add New Stall");
        System.out.println("3. View All Users");
        System.out.println("4. View All Stalls");
        System.out.println("5. Edit User Details");
        System.out.println("6. Edit Stall Name");
        System.out.println("7. Reassign Stall to New Owner"); 
        System.out.println("8. Remove User");
        System.out.println("9. Remove Stall");
        System.out.println("10. View All Transactions");
        System.out.println("11. View/Update Account Details");
        System.out.println("12. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> adminService.addNewUser(scanner, canteenManager);
            case 2 -> adminService.addNewStall(scanner);
            case 3 -> adminService.viewAllUsers();
            case 4 -> adminService.viewAllStalls();
            case 5 -> adminService.editUserDetails(scanner);
            case 6 -> adminService.editStallDetails(scanner);
            case 7 -> adminService.reassignStallToNewOwner(scanner, users);
            case 8 -> adminService.removeUser(scanner);
            case 9 -> adminService.removeStall(scanner);
            case 10 -> txnManager.displayAllTransactions();
            case 11 -> UserUtils.handleAccountUpdate(admin, scanner);
            case 12 -> {
                System.out.println("Logging out...");
                return;
            }
        }
    }
}


private static boolean isRegisteredDiner(String username) {
    for (User user : users) {
        if (user.getUsername().equals(username)) {
            return user instanceof Diner;
        }
    }
    return false; // Not found => probably a guest
}

private static void viewStallsAndOrder(String username) {
    System.out.println("\nAvailable Stalls:");
    for (int i = 0; i < stalls.size(); i++) {
        System.out.println((i + 1) + ". " + stalls.get(i).getName());
    }

    int stallChoice = InputUtils.getValidIntegerInput(scanner, "Select stall number: ", 1, stalls.size());
    Stall selectedStall = stalls.get(stallChoice - 1);
    List<Item> allItems = selectedStall.getInventory().getAllItems();

    List<Item> selectedItems = new ArrayList<>();
    Map<Item, Integer> itemCounts = new HashMap<>();

    while (true) {
        System.out.println("\n--- Menu ---");
        ItemUtils.displayMenu(allItems);

        int itemNum = InputUtils.getValidIntegerInput(scanner, "Enter item number to add to order (0 to finish): ", 0, allItems.size());
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

    int paymentChoice = InputUtils.getValidIntegerInput(scanner, """
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

}