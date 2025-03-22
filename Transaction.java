import java.util.*;
import java.time.LocalDateTime;

public class Transaction {
    static int count = 1;
    private int txnID;
    private String storeName;
    private LocalDateTime dateTime;
    private int totalPrice;
    private List <Order> orders;

    Transaction(String storeName, List <Order> orders){
        this.txnID = count;
        this.dateTime = LocalDateTime.now();
        this.storeName = storeName;
        this.orders = orders;
        count++;
    }

    public int getTxnID(){
        return this.txnID;
    }

    public void display(){
        System.out.println("Store: " + this.storeName);
        System.out.println("Date and time: " + this.dateTime);
        System.out.println("Payment made: " + this.totalPrice);
        System.out.println("Orders: " + this.orders); // To implement
    }

}
