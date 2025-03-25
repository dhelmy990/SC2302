import inventory.Item;
import orders.OrderManager;
import stalls.Stall;
import transactions.TxnManager;
import users.Admin;
import users.Diner;
import users.Owner;
import users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<User> users = new ArrayList<>();
    private static List<Stall> stalls = new ArrayList<>();
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
        Diner diner = new Diner("diner1", "diner1@example.com", "password");

        users.add(admin);
        users.add(owner);
        users.add(diner);

        // Add sample stall
        Stall stall = new Stall("S1", "Chicken Rice Stall", "owner1");
        stalls.add(stall);

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
                newUser = new Diner(username, email, password);
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
                diner.viewStalls(stalls);
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
        diner.viewStalls(stalls);
        System.out.print("Enter Stall ID: ");
        String stallId = scanner.nextLine();

        Stall selectedStall = findStallById(stallId);
        if (selectedStall == null) {
            System.out.println("Invalid stall ID.");
            return;
        }

        diner.viewMenu(selectedStall);
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
            int estWaitTime = diner.placeOrder(orderManager, selectedStall, selectedItems);
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

    private static void addItemToInventory(Owner owner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        int price = scanner.nextInt();
        System.out.print("Enter preparation time (minutes): ");
        int prepTime = scanner.nextInt();
        scanner.nextLine();

        Item newItem = new Item(name, price, prepTime);
        owner.addItemToInventory(newItem);
    }

    private static void updateItemInInventory(Owner owner) {
        System.out.print("Enter item name to update: ");
        String name = scanner.nextLine();
        System.out.print("Enter new price: ");
        int price = scanner.nextInt();
        System.out.print("Enter new preparation time (minutes): ");
        int prepTime = scanner.nextInt();
        scanner.nextLine();

        Item updatedItem = new Item(name, price, prepTime);
        owner.updateItemInInventory(updatedItem);
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
                viewTransactions(admin);
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
                newUser = new Diner(username, email, password);
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
        admin.addStall(stalls, newStall);
    }

    private static void viewTransactions(Admin admin) {
        TxnManager txnManager = TxnManager.getInstance();
        List<Transaction> transactions = txnManager.getAllTransactions();
        for (Transaction txn : transactions) {
            txn.display();
        }
    }

    private static Stall findStallById(String stallId) {
        for (Stall stall : stalls) {
            if (stall.getStallId().equals(stallId)) {
                return stall;
            }
        }
        return null;
    }
}