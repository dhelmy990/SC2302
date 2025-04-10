package services;

import users.*;

import java.util.List;

import stalls.*;

public class AdminService {
    private final List<User> users;
    private final UserManagementService userService;
    private final StallManagementService stallService;
    private final ITextInputHandler textInputHandler;
    private final IBooleanInputHandler booleanInputHandler;
    private final IAccountUpdateService accountUpdateService;

public AdminService(UserManagementService userManagementService, StallManagementService stallManagementService,
ITextInputHandler textInputHandler, IBooleanInputHandler booleanInputHandler,IAccountUpdateService accountUpdateService, List<User> users) {
    this.userService = userManagementService;
    this.stallService = stallManagementService;
    this.textInputHandler = textInputHandler;
    this.booleanInputHandler = booleanInputHandler;
    this.accountUpdateService = accountUpdateService; 
    this.users = users;
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
            String stallId = textInputHandler.getNonEmptyInput("\nEnter Stall ID to edit: ");
            String newName = textInputHandler.getNonEmptyInput("Enter new stall name: ");
            
            stallService.updateStallName(stallId, newName);
            System.out.println("Stall name updated.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUser() {
        userService.viewAllUsers();
        
        try {
            String username = textInputHandler.getNonEmptyInput("\nEnter username to remove: ");
            userService.removeUser(username);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeStall() {
        stallService.viewAllStalls();
        
        try {
            String stallId = textInputHandler.getNonEmptyInput("\nEnter Stall ID to remove: ");
            stallService.removeStall(stallId);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewUser() {
        try {
            String username = textInputHandler.getNonEmptyInput("Enter username: ");
            String email = textInputHandler.getNonEmptyInput("Enter email: ");
            String password = textInputHandler.getNonEmptyInput("Enter password: ");
            String role = textInputHandler.getNonEmptyInput("Enter role (diner/owner/admin): ");

            User newUser = UserFactory.createUser(username, email, password, role);
            userService.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addNewStall() {
        try {
            String stallName = textInputHandler.getNonEmptyInput("Enter stall name: ");
            boolean assignNow = booleanInputHandler.getYesNoInput("Do you want to assign an owner now? (yes/no): ");
            
            String ownerUsername = null;
            Owner matchedOwner = null;
            
            if (assignNow) {
                ownerUsername = textInputHandler.getNonEmptyInput("Enter owner username: ");
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
            String targetUsername = textInputHandler.getNonEmptyInput("\nEnter username of user to edit: ");
            User user = userService.findUserByUsername(targetUsername);

            if (user == null) {
                System.out.println("User not found.");
                return;
            }

            System.out.println("Current username: " + user.getUsername());
            System.out.println("Current email: " + user.getEmail());

            // Use AccountUpdateService to handle the update process
            accountUpdateService.updateAccount(user, users);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void reassignStallToNewOwner() {
        stallService.viewAllStalls();
        
        try {
            String stallId = textInputHandler.getNonEmptyInput("Enter Stall ID to reassign: ");
            Stall targetStall = stallService.findStallById(stallId);
            
            if (targetStall == null) {
                System.out.println("Stall not found.");
                return;
            }
            
            String newOwnerUsername = textInputHandler.getNonEmptyInput("Enter new owner username: ");
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