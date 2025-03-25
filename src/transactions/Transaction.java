package transactions;
import java.time.LocalDateTime;

import orders.Order;

public class Transaction {
    static int count = 1;
    private int txnID;
    private String stallName;
    private LocalDateTime dateTime;
    private int totalPrice;
    private Order order;

    Transaction(String stallName, Order order){
        this.txnID = count;
        this.dateTime = LocalDateTime.now();
        this.stallName = stallName;
        this.order = order;
        count++;
    }

    public int getTxnID(){
        return this.txnID;
    }

    public void display(){
        System.out.println("Store: " + this.stallName);
        System.out.println("Date and time: " + this.dateTime);
        System.out.println("Payment made: " + this.totalPrice);
        System.out.println("Order: " + this.order); // To implement
    }

}
