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

        int balance = 0;

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from bank where pin = '" + pinnumber + "'");
            while
