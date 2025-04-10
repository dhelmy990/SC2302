package users;

public class Diner extends User {

    public Diner(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getRole() {
        return "Diner";
    }
}