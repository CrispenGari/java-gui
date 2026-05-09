package com.example.campusmaintanance.dao;
import com.example.campusmaintanance.config.DBConnection;

import com.example.campusmaintanance.model.Complaint;

import javax.crypto.CipherInputStream;
import java.sql.*;
import java.util.*;

public class ComplaintDao {
    private static final String[] VALID_STATUSES = {"Pending", "In Progress", "Resolved", "Rejected"};

    public void create(Complaint complaint) throws SQLException {
        String sql = """
                INSERT INTO complaints(student_number, residence, room_number, complaint_type, description, created_at)
                VALUES (?, ?, ?, ?, ?, NOW())
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, complaint.getStudentNumber());
            statement.setString(2, complaint.getResidence().toUpperCase().trim());
            statement.setString(3, complaint.getRoomNumber().trim());
            statement.setString(4, complaint.getComplaintType());
            statement.setString(5, complaint.getDescription().trim());
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Complaint> findByStudentNumber(String studentNumber) throws SQLException {
        String sql = """
            SELECT id, student_number, residence, room_number, complaint_type, description, status, created_at
            FROM complaints
            WHERE student_number = ?
            ORDER BY created_at DESC
            """;

        try (
                Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, studentNumber);

            try (ResultSet resultSet = statement.executeQuery()) {
                return mapComplaints(resultSet);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Complaint> findAll() throws SQLException {
        String sql = """
                SELECT id, student_number, residence, room_number, complaint_type, description, status, created_at
                FROM complaints
                ORDER BY created_at DESC
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            return mapComplaints(resultSet);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Complaint findById(int id) throws SQLException, ClassNotFoundException {
        String sql = """
                SELECT id, student_number, residence, room_number, complaint_type, description, status, created_at
                FROM complaints
                WHERE id = ?
                """;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapComplaint(resultSet);
                }
                return null;
            }
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid complaint status.");
        }
        String sql = "UPDATE complaints SET status = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidStatus(String status) {
        for (String validStatus : VALID_STATUSES) {
            if (validStatus.equals(status)) return true;
        }
        return false;
    }

    private List<Complaint> mapComplaints(ResultSet resultSet) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        while (resultSet.next()) {
            complaints.add(mapComplaint(resultSet));
        }
        return complaints;
    }

    private Complaint mapComplaint(ResultSet resultSet) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(resultSet.getInt("id"));
        complaint.setStudentNumber(resultSet.getString("student_number"));
        complaint.setResidence(resultSet.getString("residence"));
        complaint.setRoomNumber(resultSet.getString("room_number"));
        complaint.setComplaintType(resultSet.getString("complaint_type"));
        complaint.setDescription(resultSet.getString("description"));
        complaint.setStatus(resultSet.getString("status"));
        complaint.setCreatedAt(resultSet.getTimestamp("created_at"));
        return complaint;
    }
}
