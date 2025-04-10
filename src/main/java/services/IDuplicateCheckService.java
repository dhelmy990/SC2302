package services;

import users.User;

import java.util.List;

public interface IDuplicateCheckService {
    boolean isUsernameAvailable(String username, User currentUser, List<User> users);

    boolean isEmailAvailable(String email, User currentUser, List<User> users);
}