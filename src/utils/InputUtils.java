package utils;

import java.util.Scanner;

public class InputUtils {

    private InputUtils() {
        // private to prevent instantiation
    }

    public static int getValidIntegerInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static int getPositiveIntegerInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) return value;
                System.out.println("Please enter a non-negative number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
