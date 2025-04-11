package userInterface.flow;

import dependencies.DependencyContainer;
import services.INumericInputHandler;
import users.*;

public class AdminFlow extends Flow {

    public AdminFlow(DependencyContainer dependencies){
        super(dependencies);
    }
    
    @Override
    public void run(User user){
        INumericInputHandler numericInputHandler = getNumericInputHandler();
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add New User");
            System.out.println("2. Add New Stall");
            System.out.println("3. View All Users");
            System.out.println("4. View All Stalls");
            System.out.println("5. Edit User Details");
            System.out.println("6. Edit Stall Name");
            System.out.println("7. Reassign Stall to New Owner"); 
            System.out.println("8. Remove User");
            System.out.println("9. Remove Stall");
            System.out.println("10. View All Transactions");
            System.out.println("11. View/Update Account Details");
            System.out.println("12. Logout");
            System.out.print("Choose an option: ");
            int choice = numericInputHandler.getValidIntegerInput("Choose an option: ", 1, 12);

            switch (choice) {
                case 1 -> adminService.addNewUser();
                case 2 -> adminService.addNewStall();
                case 3 -> adminService.viewAllUsers();
                case 4 -> adminService.viewAllStalls();
                case 5 -> adminService.editUserDetails();
                case 6 -> adminService.editStallDetails();
                case 7 -> adminService.reassignStallToNewOwner();
                case 8 -> adminService.removeUser();
                case 9 -> adminService.removeStall();
                case 10 -> orderService.getTxnManagerInstance().displayAllTransactions();
                case 11 -> accountUpdateService.updateAccount(user, users);
                case 12 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}