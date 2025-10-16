package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MiniStatement extends JFrame {

    MiniStatement(String pinnumber) {
        setTitle("Mini Statement");
        setLayout(null);

        JLabel heading = new JLabel("Mini Statement");
        heading.setBounds(150, 10, 300, 25);
        heading.setFont(new Font("System", Font.BOLD, 20));
        add(heading);

        JTextArea area = new JTextArea();
        area.setBounds(20, 50, 400, 400);
        add(area);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from bank where pin = '" + pinnumber + "'");

            int balance = 0;
            while (rs.next()) {
                area.append(rs.getString("date") + "\t" + rs.getString("type") + "\t" + rs.getString("amount") + "\n");
                if (rs.getString("type").equals("Deposit")) balance += Integer.parseInt(rs.getString("amount"));
                else balance -= Integer.parseInt(rs.getString("amount"));
            }

            area.append("\nCurrent Balance: Rs " + balance);

        } catch (Exception e) {
            System.out.println(e);
        }

        setSize(450, 600);
        setLocation(300, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }
}
