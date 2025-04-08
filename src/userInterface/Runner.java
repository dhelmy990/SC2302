package userInterface;
import dependencies.*;
import orders.OrderManager;
import services.AdminService;
import services.OrderService;
import java.util.*;
import userInterface.Flow.*;
import stalls.*;
import transactions.TxnManager;
import userInterface.Menu.*;
import users.*;

public class Runner {

    private static final DependencyContainer dependencies = new DependencyContainer();
    public static final Scanner scanner = dependencies.scanner;
    public static final List<Stall> stalls = dependencies.stalls;
    public static final OrderManager orderManager = dependencies.orderManager;
    public static final TxnManager txnManager = dependencies.txnManager;
    public static final IStallService canteenManager = dependencies.canteenManager;
    public static final List<User> users = dependencies.users;
    public static final OrderService orderService = dependencies.orderService;
    public static final AdminService adminService = dependencies.adminService;
    public static final WelcomeMenu welcomeMenu = dependencies.welcomeMenu;

    public static final DinerFlow dinerFlow = new DinerFlow(dependencies);
    public static final OwnerFlow ownerFlow = new OwnerFlow(dependencies);
    public static final AdminFlow adminFlow = new AdminFlow(dependencies);
    public static final GuestFlow guestFlow = new GuestFlow(dependencies);
    
    public static void main(String[] args) {
        while (true) {
            welcomeMenu.display();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> signUp();
                case 3 -> {
                    String guestId = "guest_" + UUID.randomUUID().toString();
                    System.out.println("\nContinuing as Guest Diner...");
                    System.out.println("Your Guest ID (for tracking): " + guestId);
                    Diner dummyUser = new Diner(guestId, "None", "None", canteenManager);
                    guestFlow.run(dummyUser);
                }
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
                    case "Diner" -> dinerFlow.run(user);
                    case "Owner" -> ownerFlow.run(user);
                    case "Admin" -> adminFlow.run(user);
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
}