// === FILE: CardPaymentProcessor.java ===
package payments;

import java.util.Scanner;

public class CardPaymentProcessor implements IPaymentProcessor {
    @Override
    public boolean processPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card number:");
        scanner.nextLine();
        System.out.println("Enter CVV:");
        scanner.nextLine();
        return true; // Simulate success
    }
}
