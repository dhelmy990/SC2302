package stall;
import java.util.*;

public class Stall {
    private String stallName;
    private Owner owner;
    private String cuisineName;

    public Stall(String stallName, Owner owner, String cuisineName) {
        this.stallName = stallName;
        this.owner = owner;
        this.cuisineName = cuisineName;
    }

    @Override
    public String toString() {
        return "Stall{" +
                "stallName='" + stallName + '\'' +
                ", owner=" + owner +
                ", cuisineName='" + cuisineName + '\'' +
                '}';
    }
}