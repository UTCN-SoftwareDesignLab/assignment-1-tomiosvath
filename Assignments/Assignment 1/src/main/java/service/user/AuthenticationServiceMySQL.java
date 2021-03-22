package service.user;

import database.JDBConnectionWrapper;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.role.RoleRepository;
import repository.role.RoleRepositoryMySQL;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import javax.swing.*;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Schemas.PRODUCTION;


public class AuthenticationServiceMySQL implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role customerRole = roleRepository.findRoleByName(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user, customerRole.getName()));
        }
        return userRegisterNotification;
    }

    public Notification<Boolean> registerAdmin(String username, String password) {
        Role customerRole = roleRepository.findRoleByName(ADMINISTRATOR);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(password));
            userRegisterNotification.setResult(userRepository.save(user, customerRole.getName()));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        return userRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public Notification<Boolean> update(String username, String password) {
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userUpdateNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userUpdateNotification::addError);
            userUpdateNotification.setResult(Boolean.FALSE);
        } else {
            userRepository.update(username, encodePassword(password));
            userUpdateNotification.setResult(Boolean.TRUE);
        }
        return userUpdateNotification;
    }

    //private String encodePassword(String password) {
    public static String encodePassword(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean delete(User user) {
        return userRepository.delete(user);
    }

    public void removeAll(){
        userRepository.removeAll();
    }

    public static void main(String[] args) {
        /*User user = new UserBuilder().setUsername("admin").setPassword(encodePassword("password")).build();

        JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(PRODUCTION);
        RoleRepository roleRepository = new RoleRepositoryMySQL(jdbConnectionWrapper.getConnection());
        UserRepositoryMySQL userRepositoryMySQL = new UserRepositoryMySQL(jdbConnectionWrapper.getConnection(), roleRepository);

        userRepositoryMySQL.save(user, ADMINISTRATOR);*/

        /*JDBConnectionWrapper jdbConnectionWrapper = new JDBConnectionWrapper(PRODUCTION);
        RoleRepository roleRepository = new RoleRepositoryMySQL(jdbConnectionWrapper.getConnection());
        UserRepository userRepositoryMySQL = new UserRepositoryMySQL(jdbConnectionWrapper.getConnection(), roleRepository);
        AuthenticationServiceMySQL authenticationServiceMySQL = new AuthenticationServiceMySQL(userRepositoryMySQL, roleRepository);

        Notification<Boolean> notification = null;
        notification = authenticationServiceMySQL.registerAdmin("admin@yahoo.com", "password1!");
        if (notification != null) {
            if (notification.hasErrors()) {
                System.out.println(notification.getFormattedErrors());
            } else {
                System.out.println("OK");
            }
        }
*/
    }
}
