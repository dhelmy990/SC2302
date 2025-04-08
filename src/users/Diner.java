package users;

import stalls.IStallService;

public class Diner extends User {
    private final IStallService stallService;

    public Diner(String username, String email, String password, IStallService stallService) {
        super(username, email, password);
        this.stallService = stallService;
    }

    @Override
    public String getRole() {
        return "Diner";
    }

    public IStallService getStallService() {
        return stallService;
    }
}
