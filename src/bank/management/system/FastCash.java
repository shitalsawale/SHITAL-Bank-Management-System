package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;

public class FastCash extends JFrame implements ActionListener {

    JButton r100, r500, r1000, r2000, r5000, r10000, back;
    String pinnumber;

    FastCash(String pinnumber) {
        this.pinnumber = pinnumber;

        setTitle("FAST CASH");
        setLayout(null);

        // ATM Background
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i2));
        image.setBounds(0, 0, 900, 900);
        add(image);

        JLabel text = new JLabel("Select Withdraw Amount");
        text.setForeground(Color.WHITE);
        text.setFont(new Font("System", Font.BOLD, 16));
        text.setBounds(250, 300, 400, 20);
        image.add(text);

        // Buttons
        r100 = createButton("Rs 100", 170, 350);
        r500 = createButton("Rs 500", 355, 350);
        r1000 = createButton("Rs 1000", 170, 400);
        r2000 = createButton("Rs 2000", 355, 400);
        r5000 = createButton("Rs 5000", 170, 450);
        r10000 = createButton("Rs 10000", 355, 450);
        back = createButton("Back", 355, 500);

        image.add(r100); image.add(r500);
        image.add(r1000); image.add(r2000);
        image.add(r5000); image.add(r10000);
        image.add(back);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton b = new JButton(text);
        b.setBounds(x, y, 150, 30);
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent ae) {
        int amount = 0;

        if (ae.getSource() == r100) amount = 100;
        else if (ae.getSource() == r500) amount = 500;
        else if (ae.getSource() == r1000) amount = 1000;
        else if (ae.getSource() == r2000) amount = 2000;
        else if (ae.getSource() == r5000) amount = 5000;
        else if (ae.getSource() == r10000) amount = 10000;
        else if (ae.getSource() == back) {
            setVisible(false);
            new Transactions(pinnumber).setVisible(true);
            return;
        }

        if (amount != 0) {
            try {
                Conn conn = new Conn();

                int balance = 0;
                ResultSet rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pinnumber + "'");
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit"))
                        balance += Integer.parseInt(rs.getString("amount"));
                    else
                        balance -= Integer.parseInt(rs.getString("amount"));
                }

                if (amount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    return;
                }

                // ✅ Format date properly for MySQL
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                // ✅ Use explicit column names
                String query = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinnumber + "', '" + date + "', 'Withdraw', '" + amount + "')";
                conn.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, "Rs " + amount + " Debited Successfully");
                setVisible(false);
                new Transactions(pinnumber).setVisible(true);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        new FastCash("");
    }
}
