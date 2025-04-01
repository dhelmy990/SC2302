import inventory.Item;
import orders.Order;
import orders.OrderManager;
import orders.QueueManager;
import payments.CardPaymentProcessor;
import payments.IPaymentProcessor;
import services.OrderService;
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
    private static final IPaymentProcessor paymentProcessor = new CardPaymentProcessor();
    private static final TxnManager txnManager = new TxnManager(paymentProcessor);
    private static final OrderManager orderManager = new OrderManager();
    private static final OrderService orderService = new OrderService(orderManager, txnManager);
   


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

        for (User u : users) {
            if (u.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        switch (role.toLowerCase()) {
            case "diner" -> users.add(new Diner(username, email, password,canteenManager));
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
            System.out.println("4. Logout");
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

                case 3 -> cancelOrder(diner.getUsername());
                case 4 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void cancelOrder(String username) {
        List<Order> orders = QueueManager.getInstance().getOrdersByUser(username);

        List<Order> cancellable = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus().equals("Preparing")) {
                cancellable.add(o);
            }
        }

        if (cancellable.isEmpty()) {
            System.out.println("No cancellable orders found.");
            return;
        }

        System.out.println("Your current preparing orders:");
        for (Order o : cancellable) {
            System.out.println("Order ID: " + o.getID() + " | Items: " +
                    o.getItems().stream().map(Item::getName).toList());
        }

        System.out.print("Enter Order ID to cancel: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean cancelled = false;
        Queue<Order> queue = QueueManager.getInstance().getAllQueues().getOrDefault(
                getStallNameForOrder(id), new LinkedList<>());

        Iterator<Order> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            if (o.getID() == id && o.getUsername().equals(username) && o.getStatus().equals("Preparing")) {
                o.markCancelled();
                txnManager.updateStatusForOrder(o.getID(), "Cancelled");
                iterator.remove();
                System.out.println("Order cancelled successfully.");
                cancelled = true;
                break;
            }
        }

        if (!cancelled) {
            System.out.println("Order not found or cannot be cancelled.");
        }
    }

    private static String getStallNameForOrder(int orderId) {
        for (Map.Entry<String, Queue<Order>> entry : QueueManager.getInstance().getAllQueues().entrySet()) {
            for (Order o : entry.getValue()) {
                if (o.getID() == orderId) {
                    return entry.getKey();
                }
            }
        }
        return "";
    }

private static void ownerMenu(Owner owner) {
    while (true) {
        System.out.println("\n--- Owner Menu ---");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Inventory");
        System.out.println("3. Update Item in Inventory");
        System.out.println("4. View Current Orders");
        System.out.println("5. Mark Order As Completed");
        System.out.println("6. View Transaction History");
        System.out.println("7. Logout");

        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Stall stall = owner.getManagedStall(); // Access stall from owner
        if (stall == null) {
            System.out.println("No stall assigned to this owner yet. Please contact admin.");
            return;
        }

        switch (choice) {
            case 1 -> {
                List<Item> items = stall.getInventory().getAllItems();
                if (items.isEmpty()) {
                    System.out.println("Inventory is empty.");
                } else {
                    for (Item i : items) {
                        System.out.println(i.getName() + " - $" + i.getPrice() + " - " + i.getPrepTime() + " mins");
                    }
                }
            }

            case 2 -> {
                Item newItem = createItem();
                stall.getInventory().addItem(newItem);
                System.out.println("Item added.");
            }

            case 3 -> {
                System.out.print("Enter item name to update: ");
                String name = scanner.nextLine();
                List<Item> items = stall.getInventory().getAllItems();
                boolean found = false;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getName().equalsIgnoreCase(name)) {
                        items.remove(i);
                        Item updatedItem = createItem();
                        stall.getInventory().addItem(updatedItem);
                        System.out.println("Item updated.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Item not found.");
                }
            }

            case 4 -> {
                List<Order> orders = QueueManager.getInstance().getAllOrdersForStall(stall.getName());
                if (orders.isEmpty()) {
                    System.out.println("No current orders.");
                } else {
                    boolean hasPreparing = false;
                    for (Order o : orders) {
                        if (o.getStatus().equals("Preparing")) {
                            hasPreparing = true;
                            OrderUtils.displayOrderSummary(o, false, false); // 👈 isDiner=false, isGuest=false

                        }
                    }
                    if (!hasPreparing) {
                        System.out.println("No preparing orders.");
                    }
                }
            }

            case 5 -> {
                System.out.print("Enter Order ID to mark as completed: ");
                int orderId = scanner.nextInt();
                scanner.nextLine();
                boolean updated = QueueManager.getInstance().markOrderCompleted(stall.getName(), orderId);
                if (updated) {
                    txnManager.updateStatusForOrder(orderId, "Completed");
                    System.out.println("Order marked as completed.");
                } else {
                    System.out.println("Order ID not found.");
                }
            }

            case 6 -> showTransactionsForStall(stall.getName());
            case 7 -> {
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
            txn.display();
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
        System.out.println("6. Edit Stall Details");
        System.out.println("7. Remove User");
        System.out.println("8. Remove Stall");
        System.out.println("9. View All Transactions");
        System.out.println("10. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> addNewUser();
            case 2 -> addNewStall();
            case 3 -> viewAllUsers();
            case 4 -> viewAllStalls();
            case 5 -> editUserDetails();
            case 6 -> editStallDetails();
            case 7 -> removeUser();
            case 8 -> removeStall();
            case 9 -> txnManager.getAllTransactions().forEach(Transaction::display);
            case 10 -> {
                System.out.println("Logging out...");
                return;
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}

private static void viewAllUsers() {
    if (users.isEmpty()) {
        System.out.println("No users found.");
    } else {
        users.forEach(u -> System.out.println(u.getRole() + ": " + u.getUsername() + " | Email: " + u.getEmail()));
    }
}

private static void viewAllStalls() {
    if (stalls.isEmpty()) {
        System.out.println("No stalls available.");
    } else {
        stalls.forEach(s -> System.out
                .println("ID: " + s.getId() + " | Name: " + s.getName() + " | Owner: " + s.getOwnerUsername()));
    }
}

private static void editStallDetails() {
    System.out.print("Enter Stall ID to edit: ");
    String stallId = scanner.nextLine();

    for (Stall stall : stalls) {
        if (stall.getStallId().equalsIgnoreCase(stallId)) {
            System.out.print("Enter new stall name: ");
            String newName = scanner.nextLine();
            stall.setName(newName);
            System.out.println("Stall name updated.");
            return;
        }
    }
    System.out.println("Stall not found.");
}

private static void removeUser() {
    System.out.print("Enter username to remove: ");
    String username = scanner.nextLine();

    Iterator<User> iterator = users.iterator();
    while (iterator.hasNext()) {
        User u = iterator.next();
        if (u.getUsername().equalsIgnoreCase(username)) {
            iterator.remove();
            System.out.println("User removed.");
            return;
        }
    }
    System.out.println("User not found.");
}

private static void removeStall() {
    System.out.print("Enter Stall ID to remove: ");
    String stallId = scanner.nextLine();

    Iterator<Stall> iterator = stalls.iterator();
    while (iterator.hasNext()) {
        Stall s = iterator.next();
        if (s.getId().equalsIgnoreCase(stallId)) {
            iterator.remove();
            System.out.println("Stall removed.");
            return;
        }
    }
    System.out.println("Stall not found.");
}

private static void addNewUser() {
    System.out.print("Enter username: ");
    String username = scanner.nextLine();
    System.out.print("Enter email: ");
    String email = scanner.nextLine();
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    System.out.print("Enter role (diner / owner / admin): ");
    String role = scanner.nextLine().toLowerCase();

    // Check for duplicate username or email
    for (User user : users) {
        if (user.getUsername().equals(username) || user.getEmail().equals(email)) {
            System.out.println("Username or email already exists.");
            return;
        }
    }

    switch (role) {
        case "diner" -> users.add(new Diner(username, email, password, canteenManager));
        case "owner" -> users.add(new Owner(username, email, password)); // Stall assigned later
        case "admin" -> users.add(new Admin(username, email, password));
        default -> {
            System.out.println("Invalid role.");
            return;
        }
    }

    System.out.println("User added successfully.");
}

private static void addNewStall() {
    System.out.print("Enter stall name: ");
    String stallName = scanner.nextLine();
    System.out.print("Enter owner username: ");
    String ownerUsername = scanner.nextLine();

    // Validate that the owner exists
    Owner matchedOwner = null;
    for (User u : users) {
        if (u.getUsername().equals(ownerUsername) && u instanceof Owner owner) {
            matchedOwner = owner;
            break;
        }
    }

    if (matchedOwner == null) {
        System.out.println("Owner not found.");
        return;
    }

    // Create and assign the new stall with an auto-generated ID
    Stall stall = new Stall(stallName, ownerUsername);
    matchedOwner.setManagedStall(stall);
    stalls.add(stall);
    System.out.println("Stall added and assigned to owner.");
}

private static void editUserDetails() {
    System.out.print("Enter username of user to edit: ");
    String targetUsername = scanner.nextLine();

    for (User user : users) {
        if (user.getUsername().equals(targetUsername)) {
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            user.updateUserInfo(user.getUsername(), newEmail, newPassword);
            System.out.println("User details updated.");
            return;
        }
    }
    System.out.println("User not found.");
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
    System.out.print("Select stall number: ");
    int choice = scanner.nextInt();
    scanner.nextLine();

    Stall selectedStall = stalls.get(choice - 1);
    System.out.println("\nMenu:");
    List<Item> allItems = selectedStall.getInventory().getAllItems();
    for (int i = 0; i < allItems.size(); i++) {
        Item item = allItems.get(i);
        String label = item.getQuantity() == 0 ? "[OUT OF STOCK]" : "(Qty: " + item.getQuantity() + ")";
        System.out.println((i + 1) + ". " + item.getName() +
                " ($" + item.getPrice() + ", " + item.getPrepTime() + " mins) " + label);
    }

    List<Item> selectedItems = new ArrayList<>();
    while (true) {
        System.out.println("\n--- Menu ---");
        for (int i = 0; i < allItems.size(); i++) {
            Item item = allItems.get(i);
            String label = item.getQuantity() == 0 ? "[OUT OF STOCK]" : "(Qty: " + item.getQuantity() + ")";
            System.out.println((i + 1) + ". " + item.getName() +
                    " ($" + item.getPrice() + ", " + item.getPrepTime() + " mins) " + label);
        }

        System.out.print("Enter item number to add to order (0 to finish): ");
        int itemNum = scanner.nextInt();
        scanner.nextLine();
        if (itemNum == 0)
            break;

        if (itemNum < 1 || itemNum > allItems.size()) {
            System.out.println("Invalid selection.");
            continue;
        }

        Item chosenItem = allItems.get(itemNum - 1);

        if (chosenItem.getQuantity() == 0) {
            System.out.println("Sorry, that item is out of stock.");
            continue;
        }

        selectedItems.add(chosenItem);
        chosenItem.reduceQuantity(1); // Update quantity immediately
        System.out.println(chosenItem.getName() + " added to your order.");
    }

    if (selectedItems.isEmpty()) {
        System.out.println("No items selected. Order cancelled.");
        return;
    }

    int total = orderService.calculateTotalCost(selectedItems);

    System.out.println("\nOrder Summary:");
    selectedItems.forEach(i -> System.out.println("- " + i.getName() + " ($" + i.getPrice() + ")"));
    System.out.println("Total Cost: $" + total);

    System.out.println("Choose Payment Method:");
    System.out.println("1. Card");
    System.out.println("2. Cash");
    System.out.println("3. QR / PayNow");
    System.out.print("Enter payment option: ");
    int paymentChoice = scanner.nextInt();
    scanner.nextLine();

    String paymentMethod = switch (paymentChoice) {
        case 1 -> "Card";
        case 2 -> "Cash";
        case 3 -> "QR / PayNow";
        default -> "Unknown";
    };

    System.out.print("Enter payment details (simulated): ");
    String paymentDetails = scanner.nextLine(); 

   
    Order order = new Order(username, selectedItems, selectedStall.getName());
    order.setPaymentMethod(paymentMethod);

  
    int waitTime = orderService.placeOrder(selectedStall.getName(), order);

    if (waitTime >= 0) {
        System.out.println("Payment successful.");
        System.out.println("Order placed. Estimated wait time: " + waitTime + " minutes.");

        if (username.startsWith("guest") && !isRegisteredDiner(username)) {
            ReceiptUtils.printGuestReceipt(order, total, paymentMethod);
        }

    } else {
        System.out.println("Payment failed. Order not placed.");
    }

}

    private static Item createItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        int price = scanner.nextInt();
        System.out.print("Enter preparation time (in minutes): ");
        int prep = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        return new Item(name, price, prep,qty);
    }
}