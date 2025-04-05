package services;

import users.*;
import stalls.*;
import java.util.*;

public class AdminService {
    private final List<User> users;
    private final List<Stall> stalls;

    public AdminService(List<User> users, List<Stall> stalls) {
        this.users = users;
        this.stalls = stalls;
    }

    public void viewAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            users.forEach(u -> System.out.println(u.getRole() + ": " + u.getUsername() + " | Email: " + u.getEmail()));
        }
    }

    public void viewAllStalls() {
        if (stalls.isEmpty()) {
            System.out.println("No stalls available.");
        } else {
            stalls.forEach(s -> System.out
                    .println("ID: " + s.getId() + " | Name: " + s.getName() + " | Owner: " + s.getOwnerUsername()));
        }
    }

    public void editStallDetails(Scanner scanner) {
        viewAllStalls(); // Show all stalls first
    
        System.out.print("\nEnter Stall ID to edit: ");
        String stallId = scanner.nextLine().trim();
    
        if (stallId.isEmpty()) {
            System.out.println("Stall ID cannot be empty.");
            return;
        }
    
        for (Stall stall : stalls) {
            if (stall.getStallId().equalsIgnoreCase(stallId)) {
                System.out.println("Current Name: " + stall.getName());
    
                System.out.print("Enter new stall name: ");
                String newName = scanner.nextLine().trim();
    
                if (newName.isEmpty()) {
                    System.out.println("Stall name cannot be empty.");
                    return;
                }
    
                stall.setName(newName);
                System.out.println("Stall name updated.");
                return;
            }
        }
        System.out.println("Stall not found.");
    }
    
    public void removeUser(Scanner scanner) {
        viewAllUsers();
        System.out.print("\nEnter username to remove: ");
        String username = scanner.nextLine().trim();
    
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
    
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equalsIgnoreCase(username)) {
                iterator.remove();
    
                // Remove ownership from any stalls
                for (Stall stall : stalls) {
                    if (username.equalsIgnoreCase(stall.getOwnerUsername())) {
                        stall.setOwnerUsername(null);
                    }
                }
    
                System.out.println("User removed. Any linked stalls are now unassigned.");
                return;
            }
        }
        System.out.println("User not found.");
    }
       
    public void removeStall(Scanner scanner) {
        viewAllStalls(); // Show all stalls first
    
        System.out.print("\nEnter Stall ID to remove: ");
        String stallId = scanner.nextLine().trim();
    
        if (stallId.isEmpty()) {
            System.out.println("Stall ID cannot be empty.");
            return;
        }
    
        Iterator<Stall> iterator = stalls.iterator();
        while (iterator.hasNext()) {
            Stall s = iterator.next();
            if (s.getId().equalsIgnoreCase(stallId)) {
                iterator.remove();
                System.out.println("Stall removed.");
                return;
            }
        }
        System.out.println("Stall not found.");
    }
    
    public void addNewUser(Scanner scanner, IStallService canteenManager) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (diner / owner / admin): ");
        String role = scanner.nextLine().toLowerCase();

        for (User user : users) {
            if(user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Username or email already exists.");
                return;
            }
        }

        switch (role) {
            case "diner" -> users.add(new Diner(username, email, password, canteenManager));
            case "owner" -> users.add(new Owner(username, email, password));
            case "admin" -> users.add(new Admin(username, email, password));
            default -> {
                System.out.println("Invalid role.");
                return;
            }
        }

        System.out.println("User added successfully.");
    }

    public void addNewStall(Scanner scanner) {
        System.out.print("Enter stall name: ");
        String stallName = scanner.nextLine().trim();
    
        if (stallName.isEmpty()) {
            System.out.println("Stall name cannot be empty.");
            return;
        }
    
        System.out.print("Do you want to assign an owner now? (yes/no): ");
        String assignNow = scanner.nextLine().trim();
    
        String ownerUsername = null;
        Owner matchedOwner = null;
    
        if (assignNow.equalsIgnoreCase("yes")) {
            System.out.print("Enter owner username: ");
            ownerUsername = scanner.nextLine().trim();
    
            for (User u : users) {
                if (u instanceof Owner owner && u.getUsername().equalsIgnoreCase(ownerUsername)) {
                    matchedOwner = owner;
                    break;
                }
            }
    
            if (matchedOwner == null) {
                System.out.println("Owner not found. Stall will not be assigned.");
                ownerUsername = null; // fallback to unassigned
            }
        }
    
        Stall stall = new Stall(stallName, ownerUsername); // allow null
        stalls.add(stall);
    
        if (matchedOwner != null) {
            matchedOwner.setManagedStall(stall);
            System.out.println("Stall added and assigned to owner: " + ownerUsername);
        } else {
            System.out.println("Stall added without an assigned owner.");
        }
    }
    
    public void editUserDetails(Scanner scanner) {
        viewAllUsers(); // Show users
    
        System.out.print("\nEnter username of user to edit: ");
        String targetUsername = scanner.nextLine().trim();
    
        if (targetUsername.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
    
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(targetUsername)) {
                System.out.println("Current username: " + user.getUsername());
                System.out.println("Current email: " + user.getEmail());
    
                String newUsername = user.getUsername();
                String newEmail = user.getEmail();
                String newPassword = null;
    
                System.out.print("Update username? (yes/no): ");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Enter new username: ");
                    newUsername = scanner.nextLine().trim();
                }
    
                System.out.print("Update email? (yes/no): ");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Enter new email: ");
                    newEmail = scanner.nextLine().trim();
                }
    
                System.out.print("Update password? (yes/no): ");
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Enter new password: ");
                    newPassword = scanner.nextLine().trim();
                }
    
                if (newPassword == null || newPassword.isEmpty()) {
                    System.out.println("Password is required to proceed.");
                    return;
                }
    
                user.updateUserInfo(newUsername, newEmail, newPassword);
                System.out.println("User details updated.");
                return;
            }
        }
    
        System.out.println("User not found.");
    }
    public void reassignStallToNewOwner(Scanner scanner, List<User> users) {
        viewAllStalls();
    
        System.out.print("Enter Stall ID to reassign: ");
        String stallId = scanner.nextLine().trim();
    
        if (stallId.isEmpty()) {
            System.out.println("Stall ID cannot be empty.");
            return;
        }
    
        Stall targetStall = stalls.stream()
            .filter(s -> s.getStallId().equalsIgnoreCase(stallId))
            .findFirst().orElse(null);
    
        if (targetStall == null) {
            System.out.println("Stall not found.");
            return;
        }
    
        System.out.print("Enter new owner username: ");
        String newOwnerUsername = scanner.nextLine().trim();
    
        Owner newOwner = null;
        for (User user : users) {
            if (user instanceof Owner owner && user.getUsername().equalsIgnoreCase(newOwnerUsername)) {
                newOwner = owner;
                break;
            }
        }
    
        if (newOwner == null) {
            System.out.println("New owner not found.");
            return;
        }
    
        targetStall.setOwnerUsername(newOwnerUsername);
        newOwner.setManagedStall(targetStall);
        System.out.println("Stall successfully reassigned to " + newOwnerUsername + ".");
    }
    
}