package com.revpay.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/xepdb1";
    private static final String USERNAME = "REVPAYAPP";   // your DB username
    private static final String PASSWORD = "uday";   // your DB password

    private DBConnection() {
        // prevent object creation
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
