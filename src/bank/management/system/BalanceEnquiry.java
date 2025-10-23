package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BalanceEnquiry extends JFrame {

    BalanceEnquiry(String pinnumber) {
        setTitle("Balance Enquiry");
        setLayout(null);

        JLabel heading = new JLabel("Balance Enquiry");
        heading.setBounds(150, 50, 300, 30);
        heading.setFont(new Font("System", Font.BOLD, 20));
        add(heading);

        JTextArea area = new JTextArea();
        area.setBounds(20, 100, 400, 400);
        area.setEditable(false);
        add(area);

        int balance = 0;

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from bank where pin = '" + pinnumber + "'");
            while (rs.next()) {
                String type = rs.getString("type");
                int amount = Integer.parseInt(rs.getString("amount"));
                if (type.equals("Deposit")) balance += amount;
                else balance -= amount;
            }
            area.setText("Current Balance: Rs " + balance);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching balance: " + e.getMessage());
        }

        setSize(450, 600);
        setLocation(300, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("");
    }
}
