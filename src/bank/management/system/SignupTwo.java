package bank.management.system;

import javax.swing.*;
import java.awt.*;

public class SignupTwo extends JFrame {

    SignupTwo(String formno) {
        setLayout(null);

        JLabel l1 = new JLabel("Page 2 : Additional Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        l1.setBounds(290, 80, 400, 30);
        add(l1);

        JLabel l2 = new JLabel("Form No: " + formno);
        l2.setFont(new Font("Raleway", Font.PLAIN, 18));
        l2.setBounds(100, 140, 300, 30);
        add(l2);

        // ✅ Later you can add more fields here (Occupation, PAN, Aadhaar, Income, etc.)

        getContentPane().setBackground(Color.WHITE);

        setSize(850, 800);
        setLocation(350, 10);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SignupTwo("1234");
    }
}
