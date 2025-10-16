package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SignupThree extends JFrame implements ActionListener {
    JRadioButton saving, fixed, current, recurring;
    JCheckBox atm, internet, mobile, email, cheque, eStatement;
    JButton submit, cancel;
    String formno;

    SignupThree(String formno) {
        this.formno = formno;
        setLayout(null);
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");

        JLabel accountDetails = new JLabel("Page 3: Account Details");
        accountDetails.setFont(new Font("Raleway", Font.BOLD, 22));
        accountDetails.setBounds(290, 80, 400, 30);
        add(accountDetails);

        JLabel type = new JLabel("Account Type:");
        type.setFont(new Font("Raleway", Font.BOLD, 20));
        type.setBounds(100, 140, 200, 30);
        add(type);

        saving = new JRadioButton("Saving Account");
        fixed = new JRadioButton("Fixed Deposit Account");
        current = new JRadioButton("Current Account");
        recurring = new JRadioButton("Recurring Deposit Account");

        saving.setBounds(100, 180, 200, 30);
        fixed.setBounds(350, 180, 250, 30);
        current.setBounds(100, 220, 200, 30);
        recurring.setBounds(350, 220, 250, 30);

        ButtonGroup groupAccount = new ButtonGroup();
        groupAccount.add(saving);
        groupAccount.add(fixed);
        groupAccount.add(current);
        groupAccount.add(recurring);

        add(saving);
        add(fixed);
        add(current);
        add(recurring);

        JLabel card = new JLabel("Card Number:");
        card.setFont(new Font("Raleway", Font.BOLD, 20));
        card.setBounds(100, 300, 200, 30);
        add(card);

        JLabel number = new JLabel("XXXX-XXXX-XXXX-4141");
        number.setFont(new Font("Raleway", Font.BOLD, 20));
        number.setBounds(330, 300, 300, 30);
        add(number);

        JLabel pin = new JLabel("PIN:");
        pin.setFont(new Font("Raleway", Font.BOLD, 20));
        pin.setBounds(100, 350, 200, 30);
        add(pin);

        JLabel pnumber = new JLabel("XXXX");
        pnumber.setFont(new Font("Raleway", Font.BOLD, 20));
        pnumber.setBounds(330, 350, 300, 30);
        add(pnumber);

        JLabel services = new JLabel("Services Required:");
        services.setFont(new Font("Raleway", Font.BOLD, 20));
        services.setBounds(100, 430, 200, 30);
        add(services);

        atm = new JCheckBox("ATM Card");
        internet = new JCheckBox("Internet Banking");
        mobile = new JCheckBox("Mobile Banking");
        email = new JCheckBox("EMAIL Alerts");
        cheque = new JCheckBox("Cheque Book");
        eStatement = new JCheckBox("E-Statement");

        atm.setBounds(100, 470, 200, 30);
        internet.setBounds(350, 470, 200, 30);
        mobile.setBounds(100, 520, 200, 30);
        email.setBounds(350, 520, 200, 30);
        cheque.setBounds(100, 570, 200, 30);
        eStatement.setBounds(350, 570, 200, 30);

        add(atm);
        add(internet);
        add(mobile);
        add(email);
        add(cheque);
        add(eStatement);

        submit = new JButton("Submit");
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Raleway", Font.BOLD, 14));
        submit.setBounds(250, 650, 100, 30);
        submit.addActionListener(this);
        add(submit);

        cancel = new JButton("Cancel");
        cancel.setBackground(Color.BLACK);
        cancel.setForeground(Color.WHITE);
        cancel.setFont(new Font("Raleway", Font.BOLD, 14));
        cancel.setBounds(420, 650, 100, 30);
        cancel.addActionListener(this);
        add(cancel);

        getContentPane().setBackground(Color.WHITE);
        setSize(850, 800);
        setLocation(350, 10);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submit) {
            String accountType = null;
            if (saving.isSelected()) accountType = "Saving Account";
            else if (fixed.isSelected()) accountType = "Fixed Deposit Account";
            else if (current.isSelected()) accountType = "Current Account";
            else if (recurring.isSelected()) accountType = "Recurring Deposit Account";

            Random random = new Random();
            String cardNumber = "" + Math.abs((random.nextLong() % 90000000L) + 5040936000000000L);
            String pinNumber = "" + Math.abs((random.nextLong() % 9000L) + 1000L);

            String facility = "";
            if (atm.isSelected()) facility += "ATM Card ";
            if (internet.isSelected()) facility += "Internet Banking ";
            if (mobile.isSelected()) facility += "Mobile Banking ";
            if (email.isSelected()) facility += "EMAIL Alerts ";
            if (cheque.isSelected()) facility += "Cheque Book ";
            if (eStatement.isSelected()) facility += "E-Statement ";

            try {
                if (accountType == null) {
                    JOptionPane.showMessageDialog(null, "Account Type is Required");
                } else {
                    Conn c = new Conn();
                    String query1 = "insert into signupthree values('" + formno + "','" + accountType + "','" + cardNumber + "','" + pinNumber + "','" + facility + "')";
                    String query2 = "insert into login values('" + formno + "','" + cardNumber + "','" + pinNumber + "')";
                    c.s.executeUpdate(query1);
                    c.s.executeUpdate(query2);

                    JOptionPane.showMessageDialog(null, "Card Number: " + cardNumber + "\n PIN: " + pinNumber);

                    setVisible(false);
                    new Login().setVisible(true);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (ae.getSource() == cancel) {
            setVisible(false);
            new Login().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new SignupThree("");
    }
}
