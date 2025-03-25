package stalls;

import java.util.List;

public interface IStallService {
    List<Stall> getAllStalls();
    Stall getStallById(String stallId);
}