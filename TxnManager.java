import java.util.*;

public class TxnManager {

<<<<<<< Updated upstream:TxnManager.java
    static List <Transaction> txns; // List of Transaction object

    public static boolean verifyTxn(String storeName, List <Order> orders){
        int success = getPayment();
        if (success == 1){
            Transaction txn = new Transaction(storeName, orders);
=======
    static List <Transaction> txns; // List of Transaction objects
    static Scanner scn = new Scanner(System.in); 

    // Returns true if Txn is successfully processed, false otherwise. Ensure stallName is identical to that in Stall object.
    public static boolean verifyTxn(String username, String stallName, Order order){
        int success = getPayment();
        if (success == 1){
            Transaction txn = new Transaction(username, stallName, order);
>>>>>>> Stashed changes:src/transactions/TxnManager.java
            txns.add(txn);
            return true;
        } else{
            return false;
        }
    }

<<<<<<< Updated upstream:TxnManager.java
=======
    // Returns the Transaction Object associated with the txnID, or null if Transaction is not found.
    public static Transaction getTxn(int txnID){
        int txnCount = txns.size();
        for (int i = 0; i < txnCount; i++){
            if (txns.get(i).getTxnID() == txnID){
                return txns.get(i);
            }
        }
        return null;
    }

    public static void displayOrderHistoryForUser(String username){
        int txnCount = txns.size();
        for (int i = 0; i < txnCount; i++){
            Transaction txn = txns.get(i);
            if (txn.getUsername() == username){
                txn.display();
                System.out.println();
            }
        }
    }

    public static void displayOrderSummaryForUser(String username){
        int txnCount = txns.size();
        for (int i = txnCount-1; i >= 0; i--){
            Transaction txn = txns.get(i);
            if (txn.getUsername() == username){
                txn.display();
                System.out.println();
                return;
            }
        }
    }

    // Returns 1 if payment is successful, 0 otherwise.
>>>>>>> Stashed changes:src/transactions/TxnManager.java
    private static int getPayment(){
        // To implement
        int paymentMode = getPaymentMode();
        int paymentStatus = 0;
        switch (paymentMode){
            case 1: paymentStatus = cardPayment();
                    break;
            default: paymentStatus = 0;
        }
        if (paymentStatus == 0){
            return 0;
        }
        return 1;
    }

    private static int getPaymentMode(){
        System.out.println("Please select payment mode:");
        System.out.println("(1) Card Payment");
        System.out.println("(2) Cancel");
        int mode = scn.nextInt();
        return mode;
        
    }

    private static int cardPayment(){ 
        System.out.println("Please enter card number:");
        scn.nextInt();
        System.out.println("Please enter security code:");
        scn.nextInt();
        return 1;
    }
}
