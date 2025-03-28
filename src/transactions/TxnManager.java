package transactions;

import orders.Order;
import payments.IPaymentProcessor;

import java.util.ArrayList;
import java.util.List;

public class TxnManager implements ITransactionManager {
    private final List<Transaction> txns;
    private final IPaymentProcessor paymentProcessor;

    public TxnManager(IPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
        this.txns = new ArrayList<>();
    }

    @Override
    public boolean verifyTxn(String stallName, Order order) {
        if (getPayment()) {
            Transaction txn = new Transaction(stallName, order, order.getPaymentMethod());

            txns.add(txn);
            return true;
        }
        return false;
    }

    private boolean getPayment() {
        return paymentProcessor.processPayment();
    }

    @Override
    public Transaction getTxn(int txnID) {
        for (Transaction txn : txns) {
            if (txn.getTxnID() == txnID) {
                return txn;
            }
        }
        return null;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(txns); // Prevent external modification
    }
    
    public void updateStatusForOrder(int orderId, String newStatus) {
        for (Transaction txn : txns) {
            if (txn.getOrderId() == orderId) {
                if (newStatus.equalsIgnoreCase("Completed"))
                    txn.markCompleted();
                if (newStatus.equalsIgnoreCase("Cancelled"))
                    txn.markCancelled();
                break;
            }
        }
    }
}
