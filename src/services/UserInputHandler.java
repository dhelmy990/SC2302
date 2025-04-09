package services;

import java.util.Scanner;

public class UserInputHandler {
    private final Scanner scanner;

    public UserInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getNonEmptyInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }
        return input;
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean getYesNoInput(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.equalsIgnoreCase("yes");
    }
}