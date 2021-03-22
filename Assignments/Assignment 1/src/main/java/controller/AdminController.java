package controller;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.user.AuthenticationException;
import service.activity.ActivityService;
import service.user.AuthenticationService;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminController {
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final ActivityService activityService;

    public AdminController(AdminView adminView, AuthenticationService authenticationService, ActivityService activityService) {
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.activityService = activityService;
        adminView.setDeleteButtonListener(new DeleteButtonListener());
        adminView.setUpdateButtonListener(new UpdateButtonListener());
        adminView.setGenerateButtonListener(new GenerateButtonListener());
    }

    public AdminView getAdminView() {
        return adminView;
    }

    private class DeleteButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    User user = new UserBuilder().setUsername(username).build();
                    authenticationService.delete(user);
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Delete successful!");
                }
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            String newPassword = adminView.getNewPassword();

            Notification<User> loginNotification = null;
            Notification<Boolean> updateNotification = null;

            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    updateNotification = authenticationService.update(username, newPassword);
                    if (updateNotification != null){
                        if (updateNotification.hasErrors()){
                            JOptionPane.showMessageDialog(adminView.getContentPane(), updateNotification.getFormattedErrors());
                        }
                        else{
                            JOptionPane.showMessageDialog(adminView.getContentPane(), "Update successful!");
                        }
                    }
                }
            }
        }
    }

    private class GenerateButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Date date1, date2;
            try{
                date1 = new SimpleDateFormat("dd-MM-yyyy").parse(adminView.getDate1());
                date2 = new SimpleDateFormat("dd-MM-yyyy").parse(adminView.getDate2());

                java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime());
                java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime());

                Notification<Boolean> notification = activityService.report(adminView.getUsername(), sqlDate1, sqlDate2);

                if (notification != null){
                    if (notification.hasErrors()){
                        JOptionPane.showMessageDialog(adminView.getContentPane(), notification.getFormattedErrors());
                    }
                    else{
                        JOptionPane.showMessageDialog(adminView.getContentPane(), "Report generated");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "An error occured");
                }

            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Incorrect date format. Date should be day-month-year");
                parseException.printStackTrace();
            }
        }
    }




}
