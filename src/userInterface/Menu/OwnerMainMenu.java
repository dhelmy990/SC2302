package userInterface.Menu;

public class OwnerMainMenu implements iMainMenu{
    @Override
    public void display(){
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item to Inventory");
        System.out.println("3. Update Item in Inventory");
        System.out.println("4. Delete Item from Inventory"); 
        System.out.println("5. View Current Orders");
        System.out.println("6. Mark Order As Completed");
        System.out.println("7. View Transaction History");
        System.out.println("8. View/Update Account Details");
        System.out.println("9. Logout");
    }
}
