package payments;

import services.ITextInputHandler;

public class CardPaymentProcessor implements IPaymentProcessor {
    private final ITextInputHandler textInputHandler;

    public CardPaymentProcessor(ITextInputHandler textInputHandler) {
        this.textInputHandler = textInputHandler;
    }

    @Override
    public boolean processPayment() {
        String card = textInputHandler.getNumericStringInput("Enter card number: ");
        String expiry = textInputHandler.getNumericStringInput("Enter expiry date (MM/YY): ");
        String cvv = textInputHandler.getNumericStringInput("Enter CVV: ");

        // Simulate validation (or just accept any input for now)
        if (card.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            System.out.println("Invalid card details.");
            return false;
        }

        System.out.println("Card processed successfully.");
        return true;
    }
}
