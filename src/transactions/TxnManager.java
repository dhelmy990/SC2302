package transactions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import orders.Order;

public class TxnManager implements ITransactionManager {
    private final List<Transaction> txns = new ArrayList<>();

    @Override
    public boolean verifyTxn(String stallName, Order order) {
        int success = getPayment();
        if (success == 1) {
            Transaction txn = new Transaction(stallName, order);
            txns.add(txn);
            return true;
        }
        return false;
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
        return new ArrayList<>(txns); // Return a copy to prevent external modification
    }

    private int getPayment() {
        int paymentMode = getPaymentMode();
        int paymentStatus = 0;
        switch (paymentMode) {
            case 1:
                paymentStatus = cardPayment();
                break;
            default:
                paymentStatus = 0;
        }
        return paymentStatus;
    }

    private int getPaymentMode() {
        System.out.println("Please select payment mode:");
        System.out.println("(1) Card Payment");
        System.out.println("(2) Cancel");
        Scanner scn = new Scanner(System.in);
        int mode = scn.nextInt();
        // Do not close the scanner here to avoid closing System.in
        return mode;
    }

    private int cardPayment() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please enter card number:");
        scn.nextInt();
        System.out.println("Please enter security code:");
        scn.nextInt();
        // Do not close the scanner here to avoid closing System.in
        return 1;
    }
}