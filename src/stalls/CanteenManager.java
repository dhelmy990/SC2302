package stalls;

import java.util.ArrayList;
import java.util.List;

public class CanteenManager implements IStallService {
    private final List<Stall> stalls = new ArrayList<>();

    public void addStall(Stall stall) {
        stalls.add(stall);
    }

    @Override
    public List<Stall> getAllStalls() {
        return stalls;
    }

    @Override
    public Stall getStallById(String stallId) {
        for (Stall stall : stalls) {
            if (stall.getStallId().equals(stallId)) {
                return stall;
            }
        }
        return null;
    }
}
