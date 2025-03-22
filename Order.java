public class Order {
    static int count = 1;
    String itemName;
    int orderID;
    int estWaitingTime;

    Order(String itemName){
        this.itemName = itemName;
        this.orderID = count;
        count++;
    }
    
    public void setEstWaitingTime(int estWaitingTime) {
        this.estWaitingTime = estWaitingTime;
    }

    public int getEstWaitingTime() {
        return estWaitingTime;
    }
}