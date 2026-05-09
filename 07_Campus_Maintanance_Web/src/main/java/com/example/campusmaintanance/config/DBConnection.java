package com.example.campusmaintanance.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/campus_maintainance";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "root";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = valueOrDefault(System.getenv("DB_URL"), DEFAULT_URL);
        String user = valueOrDefault(System.getenv("DB_USER"), DEFAULT_USER);
        String password = valueOrDefault(System.getenv("DB_PASSWORD"), DEFAULT_PASSWORD);
        return DriverManager.getConnection(url, user, password);
    }

    private static String valueOrDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
