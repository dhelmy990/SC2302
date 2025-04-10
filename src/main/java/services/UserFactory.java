package services;

import users.*;

public class UserFactory {

    public static User createUser(String username, String email, String password, String role) {
        return switch (role.toLowerCase()) {
            case "diner" -> new Diner(username, email, password);
            case "owner" -> new Owner(username, email, password);
            case "admin" -> new Admin(username, email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
