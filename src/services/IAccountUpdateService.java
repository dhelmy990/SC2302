package services;

import users.User;
import java.util.List;

public interface IAccountUpdateService {
    void updateAccount(User user, List<User> users);
}