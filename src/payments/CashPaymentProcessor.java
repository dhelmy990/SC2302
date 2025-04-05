package payments;

import java.util.Scanner;

public class CashPaymentProcessor implements IPaymentProcessor {
    private final Scanner scanner;
    private final int total;

    public CashPaymentProcessor(Scanner scanner, int total) {
        this.scanner = scanner;
        this.total = total;
    }

    @Override
    public boolean processPayment() {
        System.out.print("Enter cash amount given: ");
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            if (amount < total) {
                System.out.println("Insufficient amount. Payment failed.");
                return false;
            }
            double change = amount - total;
            System.out.printf("Payment accepted. Change: $%.2f\n", change);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");
            return false;
        }
    }
}
