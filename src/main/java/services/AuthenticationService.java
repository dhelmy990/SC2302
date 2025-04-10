package services;

import users.*;
import java.util.List;

public class AuthenticationService {
    private final List<User> users;
    private final ITextInputHandler textInputHandler;
    private final IDuplicateCheckService duplicateCheckService;

    public AuthenticationService(List<User> users, 
            ITextInputHandler textInputHandler,
            IDuplicateCheckService duplicateCheckService) {
        this.users = users;
        this.textInputHandler = textInputHandler;
        this.duplicateCheckService = duplicateCheckService;
    }

    public User login() {
        String username = textInputHandler.getNonEmptyInput("Enter username: ");
        String password = textInputHandler.getNonEmptyInput("Enter password: ");

        for (User user : users) {
            if (user.login(username, password)) {
                System.out.println("Login successful as " + user.getRole());
                return user;
            }
        }
        System.out.println("Login failed. Incorrect credentials.");
        return null;
    }

    public void signUp() {
        String role;
        while (true) {
            role = textInputHandler.getNonEmptyInput("Choose role (diner/owner): ").toLowerCase();
            if (role.equals("diner") || role.equals("owner")) {
                break; // Exit the loop if the role is valid
            }
            System.out.println("Invalid role. Please enter either 'diner' or 'owner'.");
        }

        String username = getUniqueInput("Enter username: ",
                input -> duplicateCheckService.isUsernameAvailable(input, null, users));
        String email = getUniqueInput("Enter email: ",
                input -> duplicateCheckService.isEmailAvailable(input, null, users));
        String password = textInputHandler.getNonEmptyInput("Enter password: ");

        // Create the user based on the role
        User newUser;
        switch (role) {
            case "diner" -> newUser = new Diner(username, email, password);
            case "owner" -> newUser = new Owner(username, email, password);
            default -> throw new IllegalStateException("Unexpected role: " + role); // probably never happen due to validation above
        }

        users.add(newUser);
        System.out.println("Account created successfully. You may now log in.");
    }

    private String getUniqueInput(String prompt, IFieldValidator validator) {
        while (true) {
            String input = textInputHandler.getNonEmptyInput(prompt);
            if (validator.validate(input)) {
                return input;
            }
            System.out.println("Input is not valid. Please try again.");
        }
    }
}