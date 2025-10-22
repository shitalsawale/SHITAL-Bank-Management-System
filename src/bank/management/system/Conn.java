package bank.management.system;

import java.sql.*;

public class Conn {
    Connection c;
    Statement s;

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql:///bankmanagementsystem", "root", "Shital@2005"); // replace 1234 with your password
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }
}



