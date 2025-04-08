package canteen;
import java.util.*;

public class CanteenManager{
    private Canteen canteen; // Canteen is a collection of Stall objects

<<<<<<< Updated upstream
    public CanteenManager(){
        this.canteen = new Canteen()
=======
import stalls.IStallService;
import stalls.Stall;

public class CanteenManager implements IStallService {
    
    private static final List<Stall> stalls = new ArrayList<>();

    public void addStall(Stall stall) {
        stalls.add(stall);
>>>>>>> Stashed changes
    }

    public void createStall(String stallName, int ownerid, String cuisineName){
        Owner owner = Owner.get(ownerid) // Get owner object using ownerid

        if (owner != null) { // Ensure the owner exists
            canteen.add_stall(stallName, owner, cuisineName); // Invoke add_stall from Canteen
            System.out.println("Stall successfully created and added to canteen")
        } else {
            System.out.println("Owner with ID " + ownerid + " not found."); // Handle invalid ownerid
        }    
    }
    
}