package users;

public abstract class User {
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public boolean login(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();

    public void updateUserInfo(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public void displayUserInfo() {
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
    }
    public String getPassword() {
        return password;
    }

}
