package transactions;

import java.util.List;

import orders.Order;

public interface ITransactionManager {
    void recordTransaction(String stallName, Order order); // replaces verifyTxn
    Transaction getTxn(int txnID);
    List<Transaction> getAllTransactions();
    void updateStatusForOrder(int orderId, String newStatus);
}
