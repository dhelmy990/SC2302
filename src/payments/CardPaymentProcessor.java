package payments;

import java.util.Scanner;

public class CardPaymentProcessor implements IPaymentProcessor {
    private final Scanner scanner;

    public CardPaymentProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean processPayment() {
        System.out.print("Enter card number: ");
        String card = scanner.nextLine();

        System.out.print("Enter expiry date (MM/YY): ");
        String expiry = scanner.nextLine();

        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();

        // Simulate validation (or just accept any input for now)
        if (card.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            System.out.println("Invalid card details.");
            return false;
        }

        System.out.println("Card processed successfully.");
        return true;
    }
}
