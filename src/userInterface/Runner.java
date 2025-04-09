package userInterface;
import dependencies.*;
import java.util.*;
import orders.OrderManager;
import services.AdminService;
import services.OrderService;
import services.UserInputHandler;
import stalls.*;
import transactions.TxnManager;
import userInterface.Flow.AdminFlow;
import userInterface.Flow.DinerFlow;
import userInterface.Flow.GuestFlow;
import userInterface.Flow.OwnerFlow;
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
    public static final GuestFlow guestFlow = new GuestFlow(dependencies);
    public static final AdminFlow adminFlow = new AdminFlow(dependencies);
    public static void main(String[] args) {
        UserInputHandler inputHandler = dependencies.userInputHandler; // Use UserInputHandler

        while (true) {
            welcomeMenu.display();
            int choice = inputHandler.getValidIntegerInput("Choose an option: ", 1, 4); // Use the new method
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
        UserInputHandler inputHandler = dependencies.userInputHandler; // Use UserInputHandler

        for (int attempt = 1; attempt <= 2; attempt++) { // Allow up to 2 attempts
            String username = inputHandler.getNonEmptyInput("Enter username: ");
            String password = inputHandler.getNonEmptyInput("Enter password: ");

            for (User user : users) {
                if (user.login(username, password)) {
                    System.out.println("Login successful as " + user.getRole());
                    switch (user.getRole()) {
                        case "Diner" -> dinerFlow.run(user);
                        case "Owner" -> ownerFlow.run(user);
                        case "Admin" -> adminFlow.run(user);
                    }
                    return; // Exit the method after successful login
                }
            }

            if (attempt == 1) {
                System.out.println("Login failed. Try again.");
            } else {
                System.out.println("Login failed. Returning to the main menu.");
            }
        }
    }

    private static void signUp() {
        UserInputHandler inputHandler = dependencies.userInputHandler; // Use UserInputHandler

        for (int attempt = 1; attempt <= 2; attempt++) { // Allow up to 2 attempts
            String role = inputHandler.getNonEmptyInput("Choose role (diner/owner): ");
            String username = inputHandler.getNonEmptyInput("Enter username: ");
            String email = inputHandler.getNonEmptyInput("Enter email: ");

            boolean duplicateFound = false;
            for (User u : users) {
                if (u.getUsername().equalsIgnoreCase(username)) {
                    System.out.println("Username already exists.");
                    duplicateFound = true;
                    break;
                }
                if (u.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("Email already in use.");
                    duplicateFound = true;
                    break;
                }
            }

            if (duplicateFound) {
                if (attempt == 1) {
                    System.out.println("Try again.");
                    continue; // Retry the operation
                } else {
                    System.out.println("Sign-up failed. Returning to the main menu.");
                    return; // Exit after the second failed attempt
                }
            }

            String password = inputHandler.getNonEmptyInput("Enter password: ");

            switch (role.toLowerCase()) {
                case "diner" -> users.add(new Diner(username, email, password, canteenManager));
                case "owner" -> users.add(new Owner(username, email, password));
                default -> {
                    System.out.println("Invalid role.");
                    if (attempt == 1) {
                        System.out.println("Try again.");
                        continue; // Retry the operation
                    } else {
                        System.out.println("Sign-up failed. Returning to the main menu.");
                        return; // Exit after the second failed attempt
                    }
                }
            }

            System.out.println("Account created successfully. You may now log in.");
            return; // Exit the method after successful sign-up
        }
    }
}