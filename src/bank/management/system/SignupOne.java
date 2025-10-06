package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class SignupOne extends JFrame implements ActionListener {
    long random;

    // Text fields
    JTextField nameTextField, fnameTextField, emailTextField, addressTextField, cityTextField, stateTextField, pinTextField;

    // Combo boxes
    JComboBox<String> day, month, year;

    // Gender radio buttons
    JRadioButton male, female, other;
    ButtonGroup genderGroup;
    JButton next;

    // Marital status radio buttons
    JRadioButton married, unmarried, otherStatus;
    ButtonGroup maritalGroup;

    SignupOne() { 
        setLayout(null);

        // Generate random form number
        Random ran = new Random();
        random = Math.abs((ran.nextLong() % 9000L) + 1000L);

        // Form Number
        JLabel formno = new JLabel("APPLICATION FORM NO. " + random);
        formno.setFont(new Font("Raleway", Font.BOLD, 38));
        formno.setBounds(140, 20, 600, 40);
        add(formno);

        JLabel personalDetails = new JLabel("Page 1 : Personal Details");
        personalDetails.setFont(new Font("Raleway", Font.BOLD, 22));
        personalDetails.setBounds(290, 80, 400, 30);
        add(personalDetails);

        // Name
        JLabel name = new JLabel("Name :");
        name.setFont(new Font("Raleway", Font.BOLD, 20));
        name.setBounds(100, 140, 150, 30);
        add(name);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        nameTextField.setBounds(300, 140, 400, 30);
        add(nameTextField);

        // Enable Enter key to move to next field
        nameTextField.addActionListener(e -> fnameTextField.requestFocus());

        // Father's Name
        JLabel fname = new JLabel("Father's Name :");
        fname.setFont(new Font("Raleway", Font.BOLD, 20));
        fname.setBounds(100, 190, 200, 30);
        add(fname);

        fnameTextField = new JTextField();
        fnameTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        fnameTextField.setBounds(300, 190, 400, 30);
        add(fnameTextField);

        // Enter key to move to DOB day combo box
        fnameTextField.addActionListener(e -> day.requestFocus());

        // Date of Birth
        JLabel dob = new JLabel("Date of Birth :");
        dob.setFont(new Font("Raleway", Font.BOLD, 20));
        dob.setBounds(100, 240, 200, 30);
        add(dob);

        String[] daysArr = new String[31];
        for(int i=1; i<=31; i++) daysArr[i-1] = ""+i;
        day = new JComboBox<>(daysArr);
        day.setBounds(300, 240, 60, 30);
        add(day);

        String[] monthsArr = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        month = new JComboBox<>(monthsArr);
        month.setBounds(370, 240, 80, 30);
        add(month);

        String[] yearsArr = new String[70];
        for(int i=0; i<70; i++) yearsArr[i] = ""+(1955+i);
        year = new JComboBox<>(yearsArr);
        year.setBounds(460, 240, 100, 30);
        add(year);

        // Enable Enter key to move to gender
        year.addActionListener(e -> male.requestFocus());

        // Gender
        JLabel gender = new JLabel("Gender :");
        gender.setFont(new Font("Raleway", Font.BOLD, 20));
        gender.setBounds(100, 290, 200, 30);
        add(gender);

        male = new JRadioButton("Male");
        male.setBounds(300, 290, 100, 30);
        male.setBackground(Color.WHITE);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(420, 290, 100, 30);
        female.setBackground(Color.WHITE);
        add(female);

        other = new JRadioButton("Other");
        other.setBounds(540, 290, 100, 30);
        other.setBackground(Color.WHITE);
        add(other);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);

        // Enable Enter key to move to email
        male.addActionListener(e -> emailTextField.requestFocus());
        female.addActionListener(e -> emailTextField.requestFocus());
        other.addActionListener(e -> emailTextField.requestFocus());

        // Email
        JLabel email = new JLabel("Email :");
        email.setFont(new Font("Raleway", Font.BOLD, 20));
        email.setBounds(100, 340, 200, 30);
        add(email);

        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        emailTextField.setBounds(300, 340, 400, 30);
        add(emailTextField);

        emailTextField.addActionListener(e -> married.requestFocus());

        // Marital Status
        JLabel marital = new JLabel("Marital Status :");
        marital.setFont(new Font("Raleway", Font.BOLD, 20));
        marital.setBounds(100, 390, 200, 30);
        add(marital);

        married = new JRadioButton("Married");
        married.setBounds(300, 390, 100, 30);
        married.setBackground(Color.WHITE);
        add(married);

        unmarried = new JRadioButton("Unmarried");
        unmarried.setBounds(420, 390, 120, 30);
        unmarried.setBackground(Color.WHITE);
        add(unmarried);

        otherStatus = new JRadioButton("Other");
        otherStatus.setBounds(560, 390, 100, 30);
        otherStatus.setBackground(Color.WHITE);
        add(otherStatus);

        maritalGroup = new ButtonGroup();
        maritalGroup.add(married);
        maritalGroup.add(unmarried);
        maritalGroup.add(otherStatus);

        // Enter key moves to Address
        married.addActionListener(e -> addressTextField.requestFocus());
        unmarried.addActionListener(e -> addressTextField.requestFocus());
        otherStatus.addActionListener(e -> addressTextField.requestFocus());

        // Address
        JLabel address = new JLabel("Address :");
        address.setFont(new Font("Raleway", Font.BOLD, 20));
        address.setBounds(100, 440, 200, 30);
        add(address);

        addressTextField = new JTextField();
        addressTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        addressTextField.setBounds(300, 440, 400, 30);
        add(addressTextField);
        addressTextField.addActionListener(e -> cityTextField.requestFocus());

        // City
        JLabel city = new JLabel("City :");
        city.setFont(new Font("Raleway", Font.BOLD, 20));
        city.setBounds(100, 490, 200, 30);
        add(city);

        cityTextField = new JTextField();
        cityTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        cityTextField.setBounds(300, 490, 400, 30);
        add(cityTextField);
        cityTextField.addActionListener(e -> stateTextField.requestFocus());

        // State
        JLabel state = new JLabel("State :");
        state.setFont(new Font("Raleway", Font.BOLD, 20));
        state.setBounds(100, 540, 200, 30);
        add(state);

        stateTextField = new JTextField();
        stateTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        stateTextField.setBounds(300, 540, 400, 30);
        add(stateTextField);
        stateTextField.addActionListener(e -> pinTextField.requestFocus());

        // Pin Code
        JLabel pincode = new JLabel("Pin Code :");
        pincode.setFont(new Font("Raleway", Font.BOLD, 20));
        pincode.setBounds(100, 590, 200, 30);
        add(pincode);

        pinTextField = new JTextField();
        pinTextField.setFont(new Font("Raleway", Font.PLAIN, 14));
        pinTextField.setBounds(300, 590, 400, 30);
        add(pinTextField);
        pinTextField.addActionListener(e -> next.requestFocus());

        // NEXT Button
        next = new JButton("NEXT");
        next.setBackground(Color.BLACK);
        next.setForeground(Color.WHITE);
        next.setFont(new Font("Raleway", Font.BOLD, 14));
        next.setBounds(620, 660, 80, 30);
        next.addActionListener(this);
        add(next);

        // Background
        getContentPane().setBackground(Color.WHITE);

        setSize(850, 800);
        setLocation(350, 10);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            // Collect data from form
            String formno = "" + random;
            String name = nameTextField.getText();
            String fname = fnameTextField.getText();
            String dob = day.getSelectedItem() + "/" + month.getSelectedItem() + "/" + year.getSelectedItem();

            String gender = null;
            if (male.isSelected()) gender = "Male";
            else if (female.isSelected()) gender = "Female";
            else if (other.isSelected()) gender = "Other";

            String email = emailTextField.getText();

            String marital = null;
            if (married.isSelected()) marital = "Married";
            else if (unmarried.isSelected()) marital = "Unmarried";
            else if (otherStatus.isSelected()) marital = "Other";

            String address = addressTextField.getText();
            String city = cityTextField.getText();
            String state = stateTextField.getText();
            String pincode = pinTextField.getText();

            // Validate required fields
            if (name.equals("") || fname.equals("") || gender == null || email.equals("") || marital == null || address.equals("") || city.equals("") || state.equals("") || pincode.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the required fields");
            } else {
                try {
                    // Save data to database
                    conn c1 = new conn();
                    String query = "INSERT INTO signup VALUES('" + formno + "', '" + name + "', '" + fname + "', '" + dob + "', '" + gender + "', '" + email + "', '" + marital + "', '" + address + "', '" + city + "', '" + state + "', '" + pincode + "')";
                    c1.s.executeUpdate(query);

                    // Move to next page
                    setVisible(false);
                    new SignupTwo(formno); // pass form number to next page
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        new SignupOne();
    }
}
