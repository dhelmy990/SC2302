package services;

import users.*;
import stalls.*;
import java.util.*;

public class AdminService {
    private final UserManagementService userService;
    private final StallManagementService stallService;
    private final UserInputHandler inputHandler;
    private final UserFactory userFactory;

    public AdminService(UserManagementService userService,
                        StallManagementService stallService,
                        UserInputHandler inputHandler,
                        UserFactory userFactory) {
        this.userService = userService;
        this.stallService = stallService;
        this.inputHandler = inputHandler;
        this.userFactory = userFactory;
    }

    public void viewAllUsers() {
        userService.viewAllUsers();
    }

    public void viewAllStalls() {
        stallService.viewAllStalls();
    }

    public void editStallDetails() {
        stallService.viewAllStalls();
        
        try {
            String stallId = inputHandler.getNonEmptyInput("\nEnter Stall ID to edit: ");
            String newName = inputHandler.getNonEmptyInput("Enter new stall name: ");
            
            stallService.updateStallName(stallId, newName);
            System.out.println("Stall name updated.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUser() {
        userService.viewAllUsers();
        
        try {
            String username = inputHandler.getNonEmptyInput("\nEnter username to remove: ");
            userService.removeUser(username);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeStall() {
        stallService.viewAllStalls();
        
        try {
            String stallId = inputHandler.getNonEmptyInput("\nEnter Stall ID to remove: ");
            stallService.removeStall(stallId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewUser() {
        try {
            String username = inputHandler.getNonEmptyInput("Enter username: ");
            String email = inputHandler.getNonEmptyInput("Enter email: ");
            String password = inputHandler.getNonEmptyInput("Enter password: ");
            String role = inputHandler.getNonEmptyInput("Enter role (diner/owner/admin): ");

            User newUser = userFactory.createUser(username, email, password, role);
            userService.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewStall() {
        try {
            String stallName = inputHandler.getNonEmptyInput("Enter stall name: ");
            boolean assignNow = inputHandler.getYesNoInput("Do you want to assign an owner now? (yes/no): ");
            
            String ownerUsername = null;
            Owner matchedOwner = null;
            
            if (assignNow) {
                ownerUsername = inputHandler.getNonEmptyInput("Enter owner username: ");
                matchedOwner = findOwnerByUsername(ownerUsername);
                
                if (matchedOwner == null) {
                    System.out.println("Owner not found. Stall will not be assigned.");
                }
            }
            
            Stall stall = new Stall(stallName, ownerUsername);
            stallService.addStall(stall);
            
            if (matchedOwner != null) {
                matchedOwner.setManagedStall(stall);
                System.out.println("Stall added and assigned to owner: " + ownerUsername);
            } else {
                System.out.println("Stall added without an assigned owner.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private Owner findOwnerByUsername(String username) {
        return userService.findUserByUsername(username) instanceof Owner owner ? owner : null;
    }

    public void editUserDetails() {
        userService.viewAllUsers();
        
        try {
            String targetUsername = inputHandler.getNonEmptyInput("\nEnter username of user to edit: ");
            User user = userService.findUserByUsername(targetUsername);
            
            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            
            System.out.println("Current username: " + user.getUsername());
            System.out.println("Current email: " + user.getEmail());
            
            String newUsername = user.getUsername();
            String newEmail = user.getEmail();
            String newPassword = null;
            
            if (inputHandler.getYesNoInput("Update username? (yes/no): ")) {
                newUsername = inputHandler.getNonEmptyInput("Enter new username: ");
            }
            
            if (inputHandler.getYesNoInput("Update email? (yes/no): ")) {
                newEmail = inputHandler.getNonEmptyInput("Enter new email: ");
            }
            
            if (inputHandler.getYesNoInput("Update password? (yes/no): ")) {
                newPassword = inputHandler.getNonEmptyInput("Enter new password: ");
            }
            
            user.updateUserInfo(newUsername, newEmail, newPassword);
            System.out.println("User details updated.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void reassignStallToNewOwner() {
        stallService.viewAllStalls();
        
        try {
            String stallId = inputHandler.getNonEmptyInput("Enter Stall ID to reassign: ");
            Stall targetStall = stallService.findStallById(stallId);
            
            if (targetStall == null) {
                System.out.println("Stall not found.");
                return;
            }
            
            String newOwnerUsername = inputHandler.getNonEmptyInput("Enter new owner username: ");
            Owner newOwner = findOwnerByUsername(newOwnerUsername);
            
            if (newOwner == null) {
                System.out.println("New owner not found.");
                return;
            }
            
            targetStall.setOwnerUsername(newOwnerUsername);
            newOwner.setManagedStall(targetStall);
            System.out.println("Stall successfully reassigned to " + newOwnerUsername + ".");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}