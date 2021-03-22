package repository.user;

import model.User;
import model.validation.Notification;

import java.util.List;


public interface UserRepository {

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    boolean save(User user, String roleName);

    boolean delete(User user);

    boolean update(String username, String password);

    void removeAll();
}
