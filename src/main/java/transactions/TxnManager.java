package transactions;

import java.util.ArrayList;
import java.util.List;

import orders.Order;
import utils.TransactionUtils;

public class TxnManager implements ITransactionManager {
    private final List<Transaction> txns = new ArrayList<>();

    public void recordTransaction(String stallName, Order order) {
        Transaction txn = new Transaction(stallName, order, order.getPaymentMethod());
        txns.add(txn);
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
        return new ArrayList<>(txns); // prevent external mutation
    }

    public void displayAllTransactions() {
        if (txns.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            txns.forEach(TransactionUtils::display);
        }
    }

    public void updateStatusForOrder(int orderId, String newStatus) {
        for (Transaction txn : txns) {
            if (txn.getOrderId() == orderId) {
                if ("Completed".equalsIgnoreCase(newStatus)) {
                    txn.markCompleted();
                } else if ("Cancelled".equalsIgnoreCase(newStatus)) {
                    txn.markCancelled();
                }
                break;
            }
        }
    }
}
