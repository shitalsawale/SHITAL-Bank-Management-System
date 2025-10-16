package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {

    JPasswordField pinField, repinField;
    JButton change, back;
    String pinnumber;

    PinChange(String pinnumber) {
        this.pinnumber = pinnumber;

        setTitle("PIN CHANGE");
        setLayout(null);

        JLabel text = new JLabel("Change Your PIN");
        text.setFont(new Font("System", Font.BOLD, 20));
        text.setBounds(220, 50, 300, 30);
        add(text);

        JLabel pinLabel = new JLabel("New PIN:");
        pinLabel.setBounds(100, 150, 150, 25);
        add(pinLabel);

        pinField = new JPasswordField();
        pinField.setBounds(250, 150, 150, 25);
        add(pinField);

        JLabel repinLabel = new JLabel("Re-Enter New PIN:");
        repinLabel.setBounds(100, 200, 150, 25);
        add(repinLabel);

        repinField = new JPasswordField();
        repinField.setBounds(250, 200, 150, 25);
        add(repinField);

        change = new JButton("Change");
        change.setBounds(150, 300, 100, 25);
        change.addActionListener(this);
        add(change);

        back = new JButton("Back");
        back.setBounds(300, 300, 100, 25);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);
        setSize(600, 400);
        setLocation(350, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == change) {
            String newPin = pinField.getText();
            String rePin = repinField.getText();

            if (!newPin.equals(rePin)) {
                JOptionPane.showMessageDialog(null, "Entered PINs do not match");
                return;
            }

            try {
                Conn conn = new Conn();
                String query1 = "update login set pinNumber = '" + newPin + "' where pinNumber = '" + pinnumber + "'";
                String query2 = "update bank set pin = '" + newPin + "' where pin = '" + pinnumber + "'";
                conn.s.executeUpdate(query1);
                conn.s.executeUpdate(query2);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(newPin).setVisible(true);

            } catch (Exception e) {
                System.out.println(e);
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PinChange("");
    }
}
