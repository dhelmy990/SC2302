package payments;

import services.INumericInputHandler;

public class CashPaymentProcessor implements IPaymentProcessor {
    private final INumericInputHandler numericInputHandler;
    private final double total;

    public CashPaymentProcessor(INumericInputHandler numericInputHandler, double total) {
        this.numericInputHandler = numericInputHandler;
        this.total = total;
    }

    @Override
    public boolean processPayment() {
        // Use the numericInputHandler to validate and retrieve the cash amount
        double amount = numericInputHandler.getValidDoubleInput("Enter cash amount given: $");

        if (amount < total) {
            System.out.println("Insufficient amount. Payment failed.");
            return false;
        }

        double change = amount - total;
        System.out.printf("Payment accepted. Change: $%.2f\n", change);
        return true;
    }
}

