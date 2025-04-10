package utils;

import inventory.Item;
import services.INumericInputHandler;
import services.ITextInputHandler;


public class ItemInputUtils {
    private ItemInputUtils() {
    }

    public static Item createItemFromInput(ITextInputHandler textInputHandler, INumericInputHandler numericInputHandler) {
        String name = prompt(textInputHandler, "Enter item name: ");
        double price = numericInputHandler.getValidDoubleInput("Enter price: ", 0, Double.MAX_VALUE);
        int prep = numericInputHandler.getValidIntegerInput("Enter preparation time (in minutes): ", 0, Integer.MAX_VALUE);
        int qty = numericInputHandler.getValidIntegerInput("Enter quantity: ", 0, Integer.MAX_VALUE);
        return new Item(name, price, prep, qty);
    }

    public static Item updateItemFromInput(ITextInputHandler textInputHandler, INumericInputHandler numericInputHandler, Item item) {
        double price = numericInputHandler.getValidDoubleInput("Enter new price (current: $" + item.getPrice() + "): ", 0, Double.MAX_VALUE);
        int prep = numericInputHandler.getValidIntegerInput("Enter new prep time (current: " + item.getPrepTime() + " mins): ", 0, Integer.MAX_VALUE);
        int qty = numericInputHandler.getValidIntegerInput("Enter new quantity (current: " + item.getQuantity() + "): ", 0, Integer.MAX_VALUE);
        return new Item(item.getName(), price, prep, qty);
    }

    private static String prompt(ITextInputHandler textInputHandler, String message) {
        return textInputHandler.getNonEmptyInput(message);
    }
}
