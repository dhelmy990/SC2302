package payments;

import services.ITextInputHandler;

public class QRPaymentProcessor implements IPaymentProcessor {
    private final ITextInputHandler textInputHandler;

    public QRPaymentProcessor(ITextInputHandler textInputHandler) {
        this.textInputHandler = textInputHandler;
    }

    @Override
    public boolean processPayment() {
        System.out.println("Scan this QR code: [### FAKE-QR-CODE ###]");
        System.out.print("Press Enter once payment is complete...");
        textInputHandler.getInput("");
        System.out.println("QR payment received.");
        return true;
    }
}
