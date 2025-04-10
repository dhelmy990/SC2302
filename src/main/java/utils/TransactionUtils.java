package utils;

import transactions.Transaction;

public class TransactionUtils {
    private TransactionUtils() {
        // Prevent instantiation
    }
    public static void display(Transaction txn) {
        System.out.println(
                "\nTxn ID: " + txn.getTxnID() + ", Stall: " + txn.getStallName() + ", Diner: " + txn.getDinerName());
        System.out.println("Time: " + DateUtils.format(txn.getDateTime()) + ", Status: " + txn.getStatus());
        System.out.println("Items:");
        ItemUtils.displayGroupedItems(txn.getItems());
        System.out.println("Total: $" + txn.getTotalCost() + ", Paid via: " + txn.getPaymentMethod());
    }
}
