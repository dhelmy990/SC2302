package services;

import users.*;
import java.util.*;

public class UserManagementService {
    private final List<User> users;
    private final StallManagementService stallService; // Add this dependency

    public UserManagementService(List<User> users, StallManagementService stallService) {
        this.users = users;
        this.stallService = stallService;
    }

    public void viewAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(u -> System.out.println(u.getRole() + ": " + u.getUsername() + " | Email: " + u.getEmail()));
        }
    }

    public void removeUser(String username) {
        User userToRemove = findUserByUsername(username);
        if (userToRemove == null) {
            System.out.println("User not found.");
            return;
        }

        if (userToRemove instanceof Owner) {
            stallService.detachOwnerFromStalls(username);
        }

        users.remove(userToRemove);
        System.out.println("User removed. Any linked stalls are now unassigned.");
    }

    public User findUserByUsername(String username) {
        return users.stream()
            .filter(u -> u.getUsername().equalsIgnoreCase(username))
            .findFirst()
            .orElse(null);
    }

    public void addUser(User user) {
        if (users.stream().anyMatch(u -> 
            u.getUsername().equalsIgnoreCase(user.getUsername()) || 
            u.getEmail().equalsIgnoreCase(user.getEmail()))) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        users.add(user);
    }
}