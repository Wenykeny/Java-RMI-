/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI_Client;

import RMI_Interface.StudentInterface;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import student.StudentDetails;

/**
 *
 * @author moisessuquila
 */
public class RMIClient extends JFrame implements ActionListener {

    JButton btnSearch, btnClear;
    JLabel lblID, lblFName, lblLName, lblContact, lblAddress;
    JTextField txtID, txtFName, txtLName, txtContact, txtAddress;
    ArrayList<JTextField> allTxtFields = new ArrayList<>();

    public RMIClient() {
        super("Student Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(550, 200);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridLayout(6, 2));

        lblID = new JLabel("Student ID:", JLabel.RIGHT);
        lblFName = new JLabel("First Name:", JLabel.RIGHT);
        lblLName = new JLabel("Last Name:", JLabel.RIGHT);
        lblContact = new JLabel("Contact Number:", JLabel.RIGHT);
        lblAddress = new JLabel("Address:", JLabel.RIGHT);
        txtID = new JTextField();
        txtID.requestFocus();
        txtFName = new JTextField();
        txtLName = new JTextField();
        txtContact = new JTextField();
        txtAddress = new JTextField();
        allTxtFields.add(txtID);
        allTxtFields.add(txtFName);
        allTxtFields.add(txtLName);
        allTxtFields.add(txtContact);
        allTxtFields.add(txtAddress);

        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");

        //Prevent the txtfield From getting strings
        txtID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });

        myPanel.add(lblID);
        myPanel.add(txtID);
        myPanel.add(lblFName);
        myPanel.add(txtFName);
        myPanel.add(lblLName);
        myPanel.add(txtLName);
        myPanel.add(lblContact);
        myPanel.add(txtContact);
        myPanel.add(lblAddress);
        myPanel.add(txtAddress);
        myPanel.add(btnClear);
        myPanel.add(btnSearch);

        btnSearch.addActionListener(this);
        btnClear.addActionListener(this);
        add(myPanel);
        setVisible(true);
    }

    private void showResponse(String title, String msg) {
        JOptionPane myJO = new JOptionPane(msg, JOptionPane.PLAIN_MESSAGE);
        JDialog mydialog = myJO.createDialog(null, title);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mydialog.setVisible(false);
            }
        }).start();
        mydialog.setVisible(true);
    }

    private void clearFields() {
        for (JTextField eachTxtField : allTxtFields) {
            eachTxtField.setText("");
        }
        txtID.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object action = e.getSource();
        if (action.equals(btnSearch)) {
            try {
                Registry reg = LocateRegistry.getRegistry("localhost", 8080);
                StudentInterface stub = (StudentInterface) reg.lookup("GetStudent");

                int num = Integer.parseInt(txtID.getText().replaceAll("[a-zA-Z]", ""));
                StudentDetails stud = stub.readFromDatabase(num);

                if (stud != null) {
                    txtFName.setText(stud.getStud_fname());
                    txtLName.setText(stud.getStud_lname());
                    txtContact.setText(stud.getStud_contact());
                    txtAddress.setText(stud.getStud_address());
                } else {
                    clearFields();
                    showResponse("Error", "This student ID does not exist. Provide other ID");
                }

            } catch (RemoteException | NotBoundException | NumberFormatException ex) {
                showResponse("Error ", ex.getMessage() + "\n\nPlese, Provide digits only!");
            }
        }

        if (action.equals(btnClear)) {
            clearFields();
        }
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        new RMIClient();
    }
}
