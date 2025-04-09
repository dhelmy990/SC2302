package services;

import users.User;

import java.util.List;

public class DuplicateCheckService implements IDuplicateCheckService {

    @Override
    public boolean isUsernameAvailable(String username, User currentUser, List<User> users) {
        for (User u : users) {
            if (!u.equals(currentUser) && u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmailAvailable(String email, User currentUser, List<User> users) {
        for (User u : users) {
            if (!u.equals(currentUser) && u.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }
}