package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnect {

    private static Connection conn;

    public static Connection getConn() throws Exception {

        if (conn == null || conn.isClosed()) {

            Properties props = new Properties();

            try (InputStream is =
                         DBConnect.class
                                 .getClassLoader()
                                 .getResourceAsStream("db.properties")) {

                if (is == null) {
                    throw new RuntimeException("db.properties file not found");
                }

                props.load(is);
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        }

        return conn;
    }
}
