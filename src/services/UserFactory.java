package services;

import stalls.IStallService;
import users.*;

public class UserFactory {
    private final IStallService canteenManager;

    public UserFactory(IStallService canteenManager) {
        this.canteenManager = canteenManager;
    }

    public User createUser(String username, String email, String password, String role) {
        return switch (role.toLowerCase()) {
            case "diner" -> new Diner(username, email, password, canteenManager);
            case "owner" -> new Owner(username, email, password);
            case "admin" -> new Admin(username, email, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    }
}
