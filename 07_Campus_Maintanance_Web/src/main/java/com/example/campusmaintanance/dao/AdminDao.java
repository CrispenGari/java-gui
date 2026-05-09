package com.example.campusmaintanance.dao;

import com.example.campusmaintanance.config.DBConnection;
import java.sql.*;

public class AdminDao {
    public boolean authenticate(String email, String password) throws SQLException {
        String sql = "SELECT id FROM admin_table WHERE email = ? AND password = ? LIMIT 1";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.toLowerCase().trim());
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
