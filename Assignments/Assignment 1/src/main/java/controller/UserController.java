package controller;

import model.Account;
import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.client.ClientService;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserController {

    private UserView userView;
    private ClientService clientService;
    private AccountService accountService;
    private ActivityService activityService;

    public UserController(UserView userView, ClientService clientService, AccountService accountService, ActivityService activityService){
        this.userView = userView;
        this.clientService = clientService;
        this.accountService = accountService;
        this.activityService = activityService;

        userView.setShowButtonListener(new ShowButtonListener());
        userView.setDeleteClientButtonListener(new DeleteClientButtonListener());
        userView.setAddClientButtonListener(new AddClientButtonListener());
        userView.setUpdateClientButtonListener(new UpdateClientButtonListener());
        userView.setBalanceUpdateButtonListener(new BalanceUpdateButtonListener());
        userView.setShowAccountButtonListener(new ShowAccountButtonListener());
        userView.setSendButtonListener(new SendButtonListener());
        userView.setProcessUtilitiesListener(new ProcessUtilitiesListener());
    }

    public UserView getUserView() {
        return userView;
    }

    private class ShowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            userView.showClients(clientService.findAll());
        }
    }

    private class DeleteClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> deleteNotification = null;
            try {
                deleteNotification = clientService.delete(userView.getUsername());
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            if (deleteNotification != null){
                if (deleteNotification.hasErrors()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), deleteNotification.getFormattedErrors());
                }
                else{
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Delete successful!");
                    activityService.save(userView.getEmployeeName(), "Deleted client " + userView.getUsername());
                }
            }
        }
    }

    private class AddClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> addClientNotification = null;
            try{
                ClientBuilder clientBuilder = new ClientBuilder();
                Client client = clientBuilder
                        .setName(userView.getNewName())
                        .setNumCode(userView.getNewNumCode())
                        .setAddress(userView.getNewAddress())
                        .setIdCardNo(userView.getNewIdCardNo())
                        .setTelephone(userView.getNewTelephone())
                        .build();
                float balance = userView.getNewBalance();

                addClientNotification = clientService.save(client, balance);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            if (addClientNotification != null){
                if (addClientNotification.hasErrors()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), addClientNotification.getFormattedErrors());
                }
                else{
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Client creation successful!");
                    activityService.save(userView.getEmployeeName(), "Added client " + userView.getNewName());
                }
            }
            else{
                JOptionPane.showMessageDialog(userView.getContentPane(), "An error occured!");
            }
        }
    }

    private class UpdateClientButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification = null;
            try{
                ClientBuilder clientBuilder = new ClientBuilder();
                Client client = clientBuilder
                        .setName(userView.getNewName())
                        .setNumCode(userView.getNewNumCode())
                        .setAddress(userView.getNewAddress())
                        .setIdCardNo(userView.getNewIdCardNo())
                        .setTelephone(userView.getNewTelephone())
                        .build();

                ClientValidator clientValidator = new ClientValidator(client);
                if (clientValidator.validate()){
                    updateNotification = clientService.updateClient(userView.getUsername(), client);

                    if (updateNotification != null) {
                        if (updateNotification.hasErrors()) {
                            JOptionPane.showMessageDialog(userView.getContentPane(), updateNotification.getFormattedErrors());
                        }
                        else{
                            JOptionPane.showMessageDialog(userView.getContentPane(), "Client updated successfully");
                            activityService.save(userView.getEmployeeName(), "Updated client " + userView.getUsername());
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "An error occured");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(userView.getContentPane(), clientValidator.getErrors());
                }
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }
        }
    }

    public class BalanceUpdateButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> updateNotification = null;
            updateNotification = clientService.updateBalance(userView.getUsername(), userView.getNewBalance());

            if (updateNotification == null){
                JOptionPane.showMessageDialog(userView.getContentPane(), "An error occurred");
            }
            else{
                if (updateNotification.hasErrors()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), updateNotification.getFormattedErrors());
                }
                else{
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Update successful");
                    activityService.save(userView.getEmployeeName(), "Updated balance for client " + userView.getUsername());
                }
            }

        }
    }

    public class ShowAccountButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Client> notification = clientService.findByName(userView.getUsername());
            if (notification == null){
                JOptionPane.showMessageDialog(userView.getContentPane(), "An error occured");
            }
            else{
                if (notification.hasErrors()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), notification.getFormattedErrors());
                }
                else{
                    Notification<Account> accountNotification = accountService.findById(notification.getResult().getId());
                    if (accountNotification == null){
                        JOptionPane.showMessageDialog(userView.getContentPane(), "An error occured");
                    }
                    else{
                        if (accountNotification.hasErrors()){
                            JOptionPane.showMessageDialog(userView.getContentPane(), accountNotification.getFormattedErrors());
                        }
                        else{
                            JOptionPane.showMessageDialog(userView.getContentPane(), "The balance is: " + accountNotification.getResult().getBalance());
                        }
                    }
                }
            }
        }
    }

    public class SendButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Client> sender = clientService.findByName(userView.getSender());
            Notification<Client> receiver = clientService.findByName(userView.getReceiver());

            if (sender.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), sender.getFormattedErrors());
            }
            else if (receiver.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), receiver.getFormattedErrors());
            }
            else{
                Notification<Account> senderAccount = accountService.findById(sender.getResult().getId());
                Notification<Account> receiverAccount = accountService.findById(receiver.getResult().getId());

                if (senderAccount.hasErrors() || receiverAccount.hasErrors()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), "An error occurred");
                }
                else if (userView.getAmount() > senderAccount.getResult().getBalance()){
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Insufficient amount");
                }
                else{
                    clientService.updateBalance(userView.getSender(), senderAccount.getResult().getBalance() - userView.getAmount());
                    clientService.updateBalance(userView.getReceiver(), receiverAccount.getResult().getBalance() + userView.getAmount());
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Successful operation");
                    activityService.save(userView.getEmployeeName(), "Transaction between " + userView.getSender() + " and " + userView.getReceiver() + " created");
                }
            }
        }
    }

    public class ProcessUtilitiesListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Client> client = clientService.findByName(userView.getUsername());
            if (client.hasErrors()){
                JOptionPane.showMessageDialog(userView.getContentPane(), client.getFormattedErrors());
            }
            else{
                Notification<Account> clientAccount = accountService.findById(client.getResult().getId());
                if (clientAccount.hasErrors()) {
                    JOptionPane.showMessageDialog(userView.getContentPane(), clientAccount.getFormattedErrors());
                }
                else{
                    clientService.updateBalance(userView.getUsername(), clientAccount.getResult().getBalance() - userView.getPrice());
                    JOptionPane.showMessageDialog(userView.getContentPane(), "Successful operation");
                    activityService.save(userView.getEmployeeName(), "Processing utility for client " + userView.getUsername() + ": " + userView.getUtility());
                }
            }
        }
    }
}
