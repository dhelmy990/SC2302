package users;
import java.util.*;
import stalls.*;

import transactions.*;

public class Admin extends User{
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    // Adds a new user to the system
    public void addUser(List<User> users, User newUser) {
        users.add(newUser);
        System.out.println("User added: " + newUser.getUsername());
    }

    // Adds a new stall to the system, get stall name
    public void addStall(List<Stall> stalls, Stall newStall) {
        stalls.add(newStall);
        System.out.println("Stall added: " + newStall.getStall());
    }

    // Updates stall details
    public void editStall(Stall stall, String newName) {
        stall.setName(newName);
    }

    // Displays all transactions recorded in the system
    public void viewAllTransactions(List<Transaction> transactions) {
        for (Transaction txn : transactions) {
            txn.display();
        }
    }
}