package utils;

import inventory.Item;
import java.util.Scanner;

public class ItemInputUtils {
    public static Item createItemFromInput(Scanner scanner) {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        int price = readInt(scanner, "Enter price: ");
        int prep = readInt(scanner, "Enter preparation time (in minutes): ");
        int qty = readInt(scanner, "Enter quantity: ");

        return new Item(name, price, prep, qty);
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    private static int getIntInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter a valid integer: ");
            }
        }
    }
    

    public static Item updateItemFromInput(Scanner scanner, Item existingItem) {
        System.out.println("Updating item: " + existingItem.getName());
    
        System.out.print("Enter new price (current: $" + existingItem.getPrice() + "): ");
        int price = getIntInput(scanner);
    
        System.out.print("Enter new preparation time (current: " + existingItem.getPrepTime() + " mins): ");
        int prep = getIntInput(scanner);
    
        System.out.print("Enter new quantity (current: " + existingItem.getQuantity() + "): ");
        int qty = getIntInput(scanner);
    
        return new Item(existingItem.getName(), price, prep, qty);
    }
    
}
