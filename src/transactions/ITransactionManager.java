package transactions;

import java.util.List;
import orders.Order; // Ensure this package and class exist, or replace with the correct package path.
import stalls.Stall;

public interface ITransactionManager {
    boolean verifyTxn(String stallName, Order order);
    Transaction getTxn(int txnID);
    List<Transaction> getAllTransactions();
}