package services;

import users.*;
import java.util.List;
import java.util.Scanner;

public class AuthenticationService {
    private final List<User> users;
    private final Scanner scanner;

    public AuthenticationService(List<User> users, Scanner scanner) {
        this.users = users;
        this.scanner = scanner;
    }

    public User login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

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
            case "diner" -> users.add(new Diner(username, email, password, null)); // Pass required dependencies here
            case "owner" -> users.add(new Owner(username, email, password));
            default -> {
                System.out.println("Invalid role.");
                return; 
            }
        }

        System.out.println("Account created successfully. You may now log in.");
    }
}