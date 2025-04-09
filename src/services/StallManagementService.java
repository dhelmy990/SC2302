package services;

import stalls.*;
import users.*;
import java.util.*;

public class StallManagementService {
    private final List<Stall> stalls;

    public StallManagementService(List<Stall> stalls) {
        this.stalls = stalls;
    }

    public void viewAllStalls() {
        if (stalls.isEmpty()) {
            System.out.println("No stalls available.");
        } else {
            stalls.forEach(s -> System.out.println(
                "ID: " + s.getId() + " | Name: " + s.getName() + " | Owner: " + s.getOwnerUsername()));
        }
    }

    public void addStall(Stall stall) {
        stalls.add(stall);
    }

    public void removeStall(String stallId) {
        Iterator<Stall> iterator = stalls.iterator();
        while (iterator.hasNext()) {
            Stall s = iterator.next();
            if (s.getId().equalsIgnoreCase(stallId)) {
                iterator.remove();
                System.out.println("Stall removed.");
                return;
            }
        }
        System.out.println("Stall not found.");
    }

    public Stall findStallById(String stallId) {
        return stalls.stream()
            .filter(s -> s.getId().equalsIgnoreCase(stallId))
            .findFirst()
            .orElse(null);
    }

    public void updateStallName(String stallId, String newName) {
        Stall stall = findStallById(stallId);
        if (stall != null) {
            stall.setName(newName);
        } else {
            throw new IllegalArgumentException("Stall not found");
        }
    }

    public void detachOwnerFromStalls(String ownerUsername) {
        stalls.stream()
            .filter(stall -> ownerUsername.equalsIgnoreCase(stall.getOwnerUsername()))
            .forEach(stall -> stall.setOwnerUsername(null));
    }
}