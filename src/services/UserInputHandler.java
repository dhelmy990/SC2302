package services;

import java.util.Scanner;

public class UserInputHandler {
    
    private final Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNonEmptyInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Input cannot be empty.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error occurred. Please try again.");
            }
        }
    }

    public String getInput(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Error reading input. Returning empty string.");
            return "";
        }
    }

    public boolean getYesNoInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + " (yes/no): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("yes")) {
                    return true;
                } else if (input.equals("no")) {
                    return false;
                } else {
                    System.out.println("Please enter 'yes' or 'no'.");
                }
            } catch (Exception e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
    }

    public int getValidIntegerInput(String prompt, int min, int max) {
        while (true) {
            try {
                if (!prompt.isEmpty()) { // Only display the prompt if it's not empty
                    System.out.print(prompt);
                }
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
