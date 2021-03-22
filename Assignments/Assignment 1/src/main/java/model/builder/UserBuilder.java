package model.builder;

import model.Role;
import model.User;


public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRole(Role role) {
        user.setRole(role);
        return this;
    }

    public User build() {
        return user;
    }


}