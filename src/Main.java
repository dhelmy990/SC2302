import inventory.Item;
import java.util.*;
import orders.OrderManager;
import stalls.CanteenManager;
import stalls.IStallService;
import stalls.Stall;
import transactions.ITransactionManager;
import transactions.Transaction;
import transactions.TxnManager;
import users.Admin;
import users.Diner;
import users.Owner;
import users.User;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static IStallService canteenManager = new CanteenManager();
    private static User currentUser;

    public static void main(String[] args) {
        // Initialize some sample data for testing (optional)
        initializeSampleData();

        while (true) {
            System.out.println("\n=== Canteen Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeSampleData() {
        // Add sample users
        Admin admin = new Admin("admin", "admin@example.com", "password");
        Owner owner = new Owner("owner1", "owner1@example.com", "password", null);
        Diner diner = new Diner("diner1", "diner1@example.com", "password", canteenManager);

        users.add(admin);
        users.add(owner);
        users.add(diner);

        // Add sample stall
        Stall stall = new Stall("S1", "Chicken Rice Stall", "owner1");
        ((CanteenManager) canteenManager).addStall(stall);

        // Add sample items to stall's inventory
        Item item1 = new Item("Chicken Rice", 5, 10);
        Item item2 = new Item("Fried Rice", 6, 12);
        stall.getInventory().addItem(item1);
        stall.getInventory().addItem(item2);
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.login(username, password)) {
                currentUser = user;
                System.out.println("Login successful as " + currentUser.getRole());
                showRoleMenu();
                return;
            }
        }
        System.out.println("Invalid credentials. Please try again.");
    }

    private static void signUp() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (diner/owner/admin): ");
        String role = scanner.nextLine();

        User newUser;
        switch (role.toLowerCase()) {
            case "diner":
                newUser = new Diner(username, email, password, canteenManager);
                break;
            case "owner":
                newUser = new Owner(username, email, password, null);
                break;
            case "admin":
                newUser = new Admin(username, email, password);
                break;
            default:
                System.out.println("Invalid role.");
                return;
        }

        users.add(newUser);
        System.out.println("User created successfully.");
    }

    private static void showRoleMenu() {
        while (true) {
            System.out.println("\n=== " + currentUser.getRole() + " Menu ===");
            if (currentUser instanceof Diner) {
                dinerMenu((Diner) currentUser);
            } else if (currentUser instanceof Owner) {
                ownerMenu((Owner) currentUser);
            } else if (currentUser instanceof Admin) {
                adminMenu((Admin) currentUser);
            }
        }
    }

    private static void dinerMenu(Diner diner) {
        System.out.println("1. View Stalls");
        System.out.println("2. Place Order");
        System.out.println("3. View Order History");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                diner.viewStalls();
                break;
            case 2:
                placeOrder(diner);
                break;
            case 3:
                viewOrderHistory(diner);
                break;
            case 4:
                currentUser = null;
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void placeOrder(Diner diner) {
        diner.viewStalls();
        System.out.print("Enter Stall ID: ");
        String stallId = scanner.nextLine();

        Stall selectedStall = ((CanteenManager) canteenManager).getStallById(stallId);
        if (selectedStall == null) {
            System.out.println("Invalid stall ID.");
            return;
        }
        //Display menu
        selectedStall.viewMenu();
        System.out.println("Enter item names (comma-separated): ");
        String[] itemNames = scanner.nextLine().split(",");
        List<Item> selectedItems = new ArrayList<>();

        for (String itemName : itemNames) {
            Item item = selectedStall.getInventory().getItemByName(itemName.trim());
            if (item != null) {
                selectedItems.add(item);
            } else {
                System.out.println("Item not found: " + itemName);
            }
        }

        if (!selectedItems.isEmpty()) {
            OrderManager orderManager = new OrderManager();
            int estWaitTime = diner.placeOrder(orderManager, selectedStall.getName(), selectedItems);
            System.out.println("Order placed successfully. Estimated wait time: " + estWaitTime + " minutes.");
        } else {
            System.out.println("No valid items selected. Order not placed.");
        }
    }

    private static void viewOrderHistory(Diner diner) {
        System.out.println("Order history viewing logic to be implemented.");
    }

    private static void ownerMenu(Owner owner) {
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Inventory");
        System.out.println("3. Update Item in Inventory");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                owner.viewInventory();
                break;
            case 2:
                addItemToInventory(owner);
                break;
            case 3:
                updateItemInInventory(owner);
                break;
            case 4:
                currentUser = null;
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static Item createItemFromInput(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
    
        System.out.print("Enter price: ");
        int price = validatePositiveInteger(scanner);
    
        System.out.print("Enter preparation time (minutes): ");
        int prepTime = validatePositiveInteger(scanner);
    
        return new Item(name, price, prepTime);
    }
    
    private static int validatePositiveInteger(Scanner scanner) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Value must be positive. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static void addItemToInventory(Owner owner) {
       addItemToInventory(owner, scanner); 
    }

    private static void updateItemInInventory(Owner owner) {
        updateItemInInventory(owner, scanner); 
     }

    private static void addItemToInventory(Owner owner, Scanner scanner) {
        System.out.println("Adding a new item to inventory:");
        Item newItem = createItemFromInput(scanner);
        owner.addItemToInventory(newItem);
        System.out.println("Item added successfully: " + newItem.getName());
    }

    private static void updateItemInInventory(Owner owner, Scanner scanner) {
        System.out.println("Updating an existing item in inventory:");
        System.out.print("Enter item name to update: ");
        String itemName = scanner.nextLine();
    
        // Check if the item exists in the inventory
        Item existingItem = owner.getManagedStall().getInventory().getItemByName(itemName);
        if (existingItem == null) {
            System.out.println("Item not found: " + itemName);
            return;
        }
    
        System.out.println("Enter new details for the item:");
        Item updatedItem = createItemFromInput(scanner);
        owner.updateItemInInventory(updatedItem);
        System.out.println("Item updated successfully: " + updatedItem.getName());
    }
    

    private static void adminMenu(Admin admin) {
        System.out.println("1. Add User");
        System.out.println("2. Add Stall");
        System.out.println("3. View Transactions");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addUser(admin);
                break;
            case 2:
                addStall(admin);
                break;
            case 3:
                ITransactionManager txnManager = new TxnManager();
                viewTransactions(txnManager);
                break;
            case 4:
                currentUser = null;
                return;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void addUser(Admin admin) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (diner/owner/admin): ");
        String role = scanner.nextLine();

        User newUser;
        switch (role.toLowerCase()) {
            case "diner":
                newUser = new Diner(username, email, password,canteenManager);
                break;
            case "owner":
                newUser = new Owner(username, email, password, null);
                break;
            case "admin":
                newUser = new Admin(username, email, password);
                break;
            default:
                System.out.println("Invalid role.");
                return;
        }

        admin.addUser(users, newUser);
    }

    private static void addStall(Admin admin) {
        System.out.print("Enter Stall ID: ");
        String stallId = scanner.nextLine();
        System.out.print("Enter Stall Name: ");
        String stallName = scanner.nextLine();
        System.out.print("Enter Owner Username: ");
        String ownerUsername = scanner.nextLine();

        Stall newStall = new Stall(stallId, stallName, ownerUsername);
        
        ((CanteenManager) canteenManager).addStall(newStall);
    }

    private static void viewTransactions(ITransactionManager txnManager) {
        List<Transaction> transactions = txnManager.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        for (Transaction txn : transactions) {
            txn.display();
        }
    }

    private static Stall findStallById(String stallId) {
    // Use CanteenManager to find the stall
    return ((CanteenManager) canteenManager).getStallById(stallId);
}
}