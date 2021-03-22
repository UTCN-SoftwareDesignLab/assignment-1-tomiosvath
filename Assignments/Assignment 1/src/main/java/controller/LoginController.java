package controller;

import model.Role;
import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;

    private AdminController adminController;
    private UserController userController;

    public LoginController(LoginView loginView, AdminController adminController, UserController userController, AuthenticationService authenticationService) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;
        this.adminController = adminController;
        this.userController = userController;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
        loginView.setRegisterAdminButtonListener(new RegisterAdminButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    Role role = loginNotification.getResult().getRole();
                    if (role.getName().equals(ADMINISTRATOR)) {
                        adminController.getAdminView().setVisible(true);
                        loginView.setVisible(false);
                    }
                    else if (role.getName().equals(EMPLOYEE)){
                        userController.getUserView().setEmployeeName(username);
                        userController.getUserView().setVisible(true);
                        loginView.setVisible(false);
                    }
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    private class RegisterAdminButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.registerAdmin(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration of admin successful!");
                }
            }
        }
    }
}
