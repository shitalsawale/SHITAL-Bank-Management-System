package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

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
        area.setEditable(false);
        add(area);

        try {
            Conn conn = new Conn();
            // Fetch transactions for this PIN
            ResultSet rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "' ORDER BY date ASC");

            int balance = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            while (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("date"); // Ensure correct type
                String type = rs.getString("type");
                int amount = rs.getInt("amount");

                String dateStr = (timestamp != null) ? sdf.format(timestamp) : "N/A";

                area.append(dateStr + "\t" + type + "\t" + amount + "\n");

                if (type.equals("Deposit")) balance += amount;
                else balance -= amount;
            }

            area.append("\nCurrent Balance: Rs " + balance);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching mini statement: " + e.getMessage());
        }

        setSize(450, 600);
        setLocation(300, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MiniStatement("");
    }
}
