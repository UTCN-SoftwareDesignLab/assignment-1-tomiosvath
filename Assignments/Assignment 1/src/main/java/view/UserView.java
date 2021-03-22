package view;

import model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UserView extends JFrame {

    private String employeeName = "";

    private final int spacing = 25;
    private final int x = 300;
    private final int bottomY = 350;
    private final int bottomX = 10;
    private final int bottoWidth = 150;

    private JPanel panel;

    private JButton btnDeleteClient;
    private JButton btnShow;
    private JButton btnAddClient;
    private JButton btnUpdateClient;
    private JButton btnUpdateBalance;
    private JButton btnShowAccount;
    private JButton btnSend;
    private JButton btnProcessUtilities;

    private JTextField tfName;

    private JTextField newName;
    private JTextField newIdCardNo;
    private JTextField newNumCode;
    private JTextField newAddress;
    private JTextField newTelephone;
    private JTextField newBalance;

    private JTextField sender;
    private JTextField receiver;
    private JTextField amount;

    private JTextField utility;
    private JTextField price;

    public UserView() throws HeadlessException {
        panel = new JPanel();
        this.setTitle("Login View");
        setBounds(100, 100, 700, 549);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        initializeFields();
        panel.add(tfName);
        panel.add(newName);
        panel.add(newAddress);
        panel.add(newIdCardNo);
        panel.add(newNumCode);
        panel.add(newTelephone);
        panel.add(newBalance);
        panel.add(sender);
        panel.add(receiver);
        panel.add(amount);

        panel.add(utility);
        panel.add(price);

        panel.add(btnDeleteClient);
        panel.add(btnShow);
        panel.add(btnAddClient);
        panel.add(btnUpdateClient);
        panel.add(btnUpdateBalance);
        panel.add(btnShowAccount);
        panel.add(btnSend);
        panel.add(btnProcessUtilities);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfName = new JTextField("Client Name");
        tfName.setBounds(10, spacing, 100, 20);

        sender = new JTextField("Sender");
        sender.setBounds(10, 3 * spacing, 100, 20);
        receiver = new JTextField("Receiver");
        receiver.setBounds(10, 4 * spacing, 100, 20);
        amount = new JTextField("Amount");
        amount.setBounds(10, 5 * spacing, 100, 20);

        utility = new JTextField("Utility description");
        utility.setBounds(10, 8 * spacing, 200, 20);
        price = new JTextField("Utility price");
        price.setBounds(10, 9 * spacing, 100, 20);

        newName = new JTextField("New Name");
        newName.setBounds(x, spacing, 300, 20);
        newIdCardNo = new JTextField("New Id Card Number");
        newIdCardNo.setBounds(x, 2 * spacing, 300, 20);
        newNumCode = new JTextField("New Numerical Code");
        newNumCode.setBounds(x, 3 * spacing, 300, 20);
        newAddress = new JTextField("New Address");
        newAddress.setBounds(x, 4 * spacing, 300, 20);
        newTelephone = new JTextField("New Telephone");
        newTelephone.setBounds(x, 5 * spacing, 300, 20);
        newBalance = new JTextField("New Balance");
        newBalance.setBounds(x, 6 * spacing, 300, 20);

        btnShow = new JButton("Show Clients");
        btnShow.setBounds(bottomX, bottomY, 150, 20);
        btnDeleteClient = new JButton("Delete client");
        btnDeleteClient.setBounds(bottomX + bottoWidth, bottomY, 150, 20);
        btnAddClient = new JButton("Add client");
        btnAddClient.setBounds(bottomX + 2 * bottoWidth, bottomY, 150, 20);
        btnUpdateClient = new JButton("Update client info");
        btnUpdateClient.setBounds(bottomX +  3 * bottoWidth, bottomY, 150, 20);

        btnUpdateBalance = new JButton("Update client balance");
        btnUpdateBalance.setBounds(bottomX, bottomY + 25, 200, 20);
        btnShowAccount = new JButton("Show account information");
        btnShowAccount.setBounds(bottomX + bottoWidth + 50, bottomY + 25, 200, 20);
        btnSend = new JButton("Send money");
        btnSend.setBounds(10, 6 * spacing, 200, 20);
        btnProcessUtilities = new JButton("Process utilities");
        btnProcessUtilities.setBounds(10, 300, 200, 20);

    }

    public void setShowButtonListener(ActionListener showButtonListener){
        btnShow.addActionListener(showButtonListener);
    }

    public void setDeleteClientButtonListener(ActionListener deleteButtonListener){
        btnDeleteClient.addActionListener(deleteButtonListener);
    }

    public void setAddClientButtonListener(ActionListener addClientButtonListener){
        btnAddClient.addActionListener(addClientButtonListener);
    }

    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener){
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }

    public void setBalanceUpdateButtonListener(ActionListener updateButtonListener){
        btnUpdateBalance.addActionListener(updateButtonListener);
    }

    public void setShowAccountButtonListener(ActionListener showAccountButtonListener){
        btnShowAccount.addActionListener(showAccountButtonListener);
    }

    public void setSendButtonListener(ActionListener sendButtonListener){
        btnSend.addActionListener(sendButtonListener);
    }

    public void setProcessUtilitiesListener(ActionListener processUtilitiesListener){
        btnProcessUtilities.addActionListener(processUtilitiesListener);
    }

    public String getSender() {
        return sender.getText();
    }

    public String getReceiver() {
        return receiver.getText();
    }

    public float getAmount() {
        return Float.parseFloat(amount.getText());
    }

    public String getUsername(){
        return tfName.getText();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getNewName() {
        return newName.getText();
    }

    public String getNewIdCardNo() {
        return newIdCardNo.getText();
    }

    public String getNewNumCode() {
        return newNumCode.getText();
    }

    public String getNewAddress() {
        return newAddress.getText();
    }

    public int getNewTelephone() {
        return Integer.parseInt(newTelephone.getText());
    }

    public float getNewBalance() {
        return Float.parseFloat(newBalance.getText());
    }

    public String getUtility(){
        return utility.getText();
    }

    public float getPrice(){
        return Float.parseFloat(price.getText());
    }

    public void showClients(List<Client> clients){
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        model.addColumn("id");
        model.addColumn("name");
        model.addColumn("idCardNo");
        model.addColumn("numCode");
        model.addColumn("address");
        model.addColumn("telephone");
        model.addColumn("accountNo");

        for (Client client: clients){
            model.insertRow(0, new Object[]{
                    String.valueOf(client.getId()),
                    client.getName(),
                    client.getIdCardNo(),
                    String.valueOf(client.getNumCode()),
                    client.getAddress(),
                    "0" + String.valueOf(client.getTelephone()),
                    String.valueOf(client.getAccountNo())});
        }

        JFrame clientFrame = new JFrame();
        clientFrame.setTitle("Clients");
        clientFrame.setSize(1000, 500);
        clientFrame.add(new JScrollPane(table));

        clientFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        clientFrame.setVisible(true);

    }
}
