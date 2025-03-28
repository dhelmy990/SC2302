package transactions;

import orders.Order;
import java.util.List;

public interface ITransactionManager {
    boolean verifyTxn(String stallName, Order order);
    Transaction getTxn(int txnID);
    List<Transaction> getAllTransactions();
}