package utils;
import users.User;
import java.util.*;
public class UserUtils {
    public static void handleAccountUpdate(User user, Scanner scanner) {
        System.out.println("\n--- My Account ---");
        user.displayUserInfo();

        System.out.print("Do you want to update your info? (yes/no): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("yes")) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            user.updateUserInfo(newUsername, newEmail, newPassword);
            System.out.println("Your information has been updated.");
        } else {
            System.out.println("No changes made.");
        }
    }
}
