package transactions;
import java.time.LocalDateTime;
import orders.Order;

public class Transaction {
    static int count = 1;
    private String userName;
    private int txnID;
    private String stallName;
    private LocalDateTime dateTime;
    private double totalPrice;
    private Order order;

    Transaction(String userName, String stallName, Order order){
        this.userName = userName;
        this.txnID = count++;
        this.dateTime = LocalDateTime.now();
        this.stallName = stallName;
        this.order = order;
        this.totalPrice = order.getTotalPrice();
    }

    public int getTxnID(){
        return this.txnID;
    }

    public String getUsername(){
        return userName;
    }

    public void display(){
        System.out.println("Stall: " + this.stallName);
        System.out.println("Date and time: " + this.dateTime);
        System.out.println("Payment received: $" + this.totalPrice);
        this.order.display(); 
    }

    public int getOrderID(){
        return order.getID(); //???
    }
    
    public Order getOrder(){
        return order;
    }

    public String getStallName(){
        return stallName;
    }

}
