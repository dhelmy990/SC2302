// === FILE: PaymentService.java ===
package services;

import payments.IPaymentProcessor;

public class PaymentService {
    private final IPaymentProcessor processor;

    public PaymentService(IPaymentProcessor processor) {
        this.processor = processor;
    }

    public boolean makePayment() {
        return processor.processPayment();
    }
}