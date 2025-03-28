package canteen;
import java.util.*;

public class Canteen{
    private List <Stall> stalls;

    Canteen(){
        this.stalls = new ArrayList<>();
    }

    // would this be a composition rls between Canteen and Stall?
    public void add_stall(String stallName, Owner owner, String cuisineName){
        Stall newStall = new Stall(stallName, owner, cuisineName); // Create a new Stall object
        this.stalls.add(newStall); // Add the Stall object to the list
    }


}