package inventory;
public class Item {
    String name;
    int price;
    int prepTime;

    Item(String name, int price, int prepTime){
        this.name = name;
        this.price = price;
        this.prepTime = prepTime;
    }

    public int getPrepTime(){
        return prepTime;
    }

    public void setPrepTime(int prepTime){
        this.prepTime = prepTime;
    }
}
