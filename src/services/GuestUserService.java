package services;

import users.Diner;

import java.util.UUID;

public class GuestUserService {

    public Diner createGuestUser() {
        String guestId = "guest_" + UUID.randomUUID().toString();
        System.out.println("\nContinuing as Guest Diner...");
        System.out.println("Your Guest ID (for tracking): " + guestId);
        return new Diner(guestId, "None", "None");
    }
}