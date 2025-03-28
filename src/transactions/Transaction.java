package transactions;

import orders.Order;
import java.time.LocalDateTime;

public class Transaction {
    private static int counter = 1;
    private final int txnID;
    private final String stallName;
    private final LocalDateTime dateTime;
    private final Order order;

    public Transaction(String stallName, Order order) {
        this.txnID = counter++;
        this.stallName = stallName;
        this.order = order;
        this.dateTime = LocalDateTime.now();
    }

    public int getTxnID() {
        return txnID;
    }

    public void display() {
        System.out.println("Txn ID: " + txnID + ", Stall: " + stallName + ", Time: " + dateTime);
    }
}