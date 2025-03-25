package inventory;
public class Item {
    private final String name;
    private final int price;
    private int prepTime;

    public Item(String name, int price, int prepTime){
        this.name = name;
        this.price = price;
        this.prepTime = prepTime;
    }

    public int getPrepTime(){
        return prepTime;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrepTime(int prepTime){
        this.prepTime = prepTime;
    }
}
