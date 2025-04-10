package orders;
import java.util.*;

import inventory.Item;

public class Order {
    private static int count = 1;
    private List <Item> items;
    private int orderID;
    private int waitingTime;

    Order(List <Item> items){
        this.items = items; // Contains an array of Item Objects
        this.orderID = count;
        this.waitingTime = estWaitingTime();
        count++;
    }

    // Returns the total waiting time for this specific Order object (which is the sum of individual Item prep times).
    private int estWaitingTime() {
        int waitingTime = 0;
        int numOfItems = items.size();
        for (int i = 0; i<numOfItems; i++){
            waitingTime += items.get(i).getPrepTime();
        }
        return waitingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getID() {
        return orderID;
    }
}