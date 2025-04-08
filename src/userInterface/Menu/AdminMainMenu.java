package userInterface.Menu;

public class AdminMainMenu implements iMainMenu {
    @Override
    public void display(){
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
    }
}
