import java.util.*;

public class TxnManager {

    static List <Transaction> txns; // List of Transaction object

    public static boolean verifyTxn(String storeName, List <Order> orders){
        int success = getPayment();
        if (success == 1){
            Transaction txn = new Transaction(storeName, orders);
            txns.add(txn);
            return true;
        } else{
            return false;
        }
    }

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
        Scanner scn = new Scanner(System.in); 
        int mode = scn.nextInt();
        scn.close();
        return mode;
        
    }

    private static int cardPayment(){
        Scanner scn = new Scanner(System.in); 
        System.out.println("Please enter card number:");
        scn.nextInt();
        System.out.println("Please enter security code:");
        scn.nextInt();
        scn.close();
        return 1;
    }
}
