package userInterface;

import dependencies.DependencyContainer;
import services.AuthenticationService;
import services.GuestUserService;
import services.INumericInputHandler;
import userInterface.flow.*;
import userInterface.menu.WelcomeMenu;
import users.*;

public class Runner {

    private static final DependencyContainer dependencies = new DependencyContainer();
    private static final INumericInputHandler numericInputHandler = dependencies.getNumericInputHandler();
    private static final AuthenticationService authService = dependencies.getAuthenticationService();
    private static final GuestUserService guestUserService = new GuestUserService();
    private static final DinerFlow dinerFlow = new DinerFlow(dependencies);
    private static final OwnerFlow ownerFlow = new OwnerFlow(dependencies);
    private static final GuestFlow guestFlow = new GuestFlow(dependencies);
    private static final AdminFlow adminFlow = new AdminFlow(dependencies);

    public static void main(String[] args) {
        while (true) {
            WelcomeMenu welcomeMenu = new WelcomeMenu();
            welcomeMenu.display();
            int choice = numericInputHandler.getValidIntegerInput("Enter your choice: ", 1, 4);

            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> authService.signUp();
                case 3 -> handleGuestFlow();
                case 4 -> {
                    System.out.println("Exiting. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void handleLogin() {
        User user = authService.login();
        if (user != null) {
            switch (user.getRole()) {
                case "Diner" -> dinerFlow.run(user);
                case "Owner" -> ownerFlow.run(user);
                case "Admin" -> adminFlow.run(user);
            }
        }
    }

    private static void handleGuestFlow() {
        Diner dummyUser = guestUserService.createGuestUser();
        guestFlow.run(dummyUser);
    }
}