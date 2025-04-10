package inventory;

public class Item {
    private final String name;
    private final double  price;
    private final int prepTime;
    private int quantity; 

    public Item(String name, double price, int prepTime, int quantity) {
        this.name = name;
        this.price = price;
        this.prepTime = prepTime;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }
    public void addQuantity(int amount) {
        this.quantity += amount;
    }
}
