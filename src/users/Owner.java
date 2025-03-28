package users;

import stalls.Stall;

public class Owner extends User {
    private Stall managedStall;

    public Owner(String username, String email, String password) {
        super(username, email, password);
        this.managedStall = null; // default until assigned
    }

    @Override
    public String getRole() {
        return "Owner";
    }

    public Stall getManagedStall() {
        return managedStall;
    }

    public void setManagedStall(Stall managedStall) {
        this.managedStall = managedStall;
    }
}
