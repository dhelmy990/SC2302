package userInterface.menu;

public class DinerMainMenu implements iMainMenu{
   
    @Override
    public void display() {
        System.out.println("\n--- Diner Menu ---");
        System.out.println("1. Order Food");
        System.out.println("2. View Order History");
        System.out.println("3. Cancel an Order"); 
        System.out.println("4. View/Update Account Details");
        System.out.println("5. Logout");
    }
}
