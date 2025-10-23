package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField amount;
    JButton withdraw, back;
    String pinnumber;

    Withdrawl(String pinnumber) {
        this.pinnumber = pinnumber;

        setTitle("CASH WITHDRAW");
        setLayout(null);

        // === Background ATM Image ===
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 900, 900);
        add(image);

        // === Text Label ===
        JLabel text = new JLabel("Enter the amount you want to withdraw");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(170, 300, 400, 20);
        image.add(text);

        // === Amount Text Field ===
        amount = new JTextField();
        amount.setFont(new Font("Raleway", Font.BOLD, 18));
        amount.setBounds(170, 350, 320, 25);
        image.add(amount);

        // === Withdraw Button ===
        withdraw = new JButton("Withdraw");
        withdraw.setBounds(355, 485, 150, 30);
        withdraw.addActionListener(this);
        image.add(withdraw);

        // === Back Button ===
        back = new JButton("Back");
        back.setBounds(355, 520, 150, 30);
        back.addActionListener(this);
        image.add(back);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdraw) {
            String number = amount.getText();

            // ✅ Properly format date for MySQL
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            if (number.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
            } else {
                try {
                    Conn conn = new Conn();

                    // ✅ Check balance first
                    int balance = 0;
                    ResultSet rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
                    while (rs.next()) {
                        if (rs.getString("type").equals("Deposit")) {
                            balance += Integer.parseInt(rs.getString("amount"));
                        } else {
                            balance -= Integer.parseInt(rs.getString("amount"));
                        }
                    }

                    int withdrawAmount = Integer.parseInt(number);
                    if (withdrawAmount > balance) {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                        return;
                    }

                    // ✅ Insert withdrawal record with formatted date
                    String query = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinnumber + "', '" + date + "', 'Withdraw', '" + number + "')";
                    conn.s.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Rs. " + number + " Withdrawn Successfully");
                    setVisible(false);
                    new Transactions(pinnumber).setVisible(true);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new Withdrawl("");
    }
}
