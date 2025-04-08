package userInterface.Menu;

public class WelcomeMenu implements iMainMenu{
    @Override
    public void display() {
        System.out.println("\n=== Welcome to the Canteen System ===");
            System.out.println("1. Log In");
            System.out.println("2. Sign Up");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
    }
}
