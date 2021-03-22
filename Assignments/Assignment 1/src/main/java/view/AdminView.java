package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame{
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JTextField tfNewPassword;

    private JTextField date1;
    private JTextField date2;

    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnGenerate;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblNewPassword;

    public AdminView() throws HeadlessException {
        this.setTitle("Admin View");
        setSize(300, 500);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(lblUsername);
        add(tfUsername);
        add(lblPassword);
        add(tfPassword);
        add(lblNewPassword);
        add(tfNewPassword);
        add(date1);
        add(date2);
        add(btnDelete);
        add(btnUpdate);
        add(btnGenerate);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JTextField();
        tfNewPassword = new JTextField();
        date1 = new JTextField("Starting date");
        date2 = new JTextField("Ending date");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnGenerate = new JButton("Generate");
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");
        lblNewPassword = new JLabel("New Password");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public String getNewPassword(){ return tfNewPassword.getText();}

    public String getDate1(){
        return date1.getText();
    }

    public String getDate2(){
        return date2.getText();
    }

    public void setDeleteButtonListener(ActionListener deleteButtonListener) {
        btnDelete.addActionListener(deleteButtonListener);
    }

    public void setUpdateButtonListener(ActionListener updateButtonListener) {
        btnUpdate.addActionListener(updateButtonListener);
    }

    public void setGenerateButtonListener(ActionListener generateButtonListener){
        btnGenerate.addActionListener(generateButtonListener);
    }
}
