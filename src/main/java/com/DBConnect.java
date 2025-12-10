package com;
import java.sql.*;

public class DBConnect {

    private static Connection conn;

    public static Connection getConn() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/registration?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "root"   // your actual MySQL password
                );

                System.out.println("DB Connected Successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("DB Connection Failed!");
            }
        }
        return conn;
    }

}
