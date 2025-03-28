import java.util.*;
import java.time.LocalDateTime;

public class Transaction {
    static int count = 1;
    private String userName;
    private int txnID;
    private String storeName;
    private LocalDateTime dateTime;
<<<<<<< Updated upstream:Transaction.java
    private int totalPrice;
    private List <Order> orders;

    Transaction(String storeName, List <Order> orders){
        this.txnID = count;
        this.dateTime = LocalDateTime.now();
        this.storeName = storeName;
        this.orders = orders;
        count++;
=======
    private double totalPrice;
    private Order order;

    Transaction(String userName, String stallName, Order order){
        this.userName = userName;
        this.txnID = count++;
        this.dateTime = LocalDateTime.now();
        this.stallName = stallName;
        this.order = order;
        this.totalPrice = order.getTotalPrice();
>>>>>>> Stashed changes:src/transactions/Transaction.java
    }

    public int getTxnID(){
        return this.txnID;
    }

    public String getUsername(){
        return userName;
    }

    public void display(){
        System.out.println("Store: " + this.storeName);
        System.out.println("Date and time: " + this.dateTime);
<<<<<<< Updated upstream:Transaction.java
        System.out.println("Payment made: " + this.totalPrice);
        System.out.println("Orders: " + this.orders); // To implement
=======
        System.out.println("Payment received: $" + this.totalPrice);
        this.order.display(); 
    }

    public int getOrderID(){
        return order.getID(); //is this 
    }

    public String getStallName(){
        return stallName;
>>>>>>> Stashed changes:src/transactions/Transaction.java
    }

}
