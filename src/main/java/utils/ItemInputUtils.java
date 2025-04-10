package utils;

import inventory.Item;
import java.util.Scanner;

public class ItemInputUtils {
    private ItemInputUtils() {
    }

    public static Item createItemFromInput(Scanner scanner) {
        String name = prompt(scanner, "Enter item name: ");
        int price = readInt(scanner, "Enter price: ");
        int prep = readInt(scanner, "Enter preparation time (in minutes): ");
        int qty = readInt(scanner, "Enter quantity: ");
        return new Item(name, price, prep, qty);
    }

    public static Item updateItemFromInput(Scanner scanner, Item item) {
        int price = readInt(scanner, "Enter new price (current: $" + item.getPrice() + "): ");
        int prep = readInt(scanner, "Enter new prep time (current: " + item.getPrepTime() + " mins): ");
        int qty = readInt(scanner, "Enter new quantity (current: " + item.getQuantity() + "): ");
        return new Item(item.getName(), price, prep, qty);
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static String prompt(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
