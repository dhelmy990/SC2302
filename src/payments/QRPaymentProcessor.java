package payments;

import java.util.Scanner;

public class QRPaymentProcessor implements IPaymentProcessor {
    private final Scanner scanner;

    public QRPaymentProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public boolean processPayment() {
        System.out.println("Scan this QR code: [### FAKE-QR-CODE ###]");
        System.out.print("Press Enter once payment is complete...");
        scanner.nextLine(); // Simulate confirmation
        System.out.println("QR payment received.");
        return true;
    }
}
