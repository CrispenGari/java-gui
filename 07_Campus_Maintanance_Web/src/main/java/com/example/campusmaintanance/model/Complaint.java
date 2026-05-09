package com.example.campusmaintanance.model;

import java.sql.Timestamp;

public class Complaint {
    private int id;
    private String studentNumber;
    private String residence;
    private String roomNumber;
    private String complaintType;
    private String description;
    private String status;
    private Timestamp createdAt;

    public Complaint() {}

    public Complaint(String studentNumber, String residence, String roomNumber, String complaintType, String description) {
        this.studentNumber = studentNumber;
        this.residence = residence;
        this.roomNumber = roomNumber;
        this.complaintType = complaintType;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public String getResidence() { return residence; }
    public void setResidence(String residence) { this.residence = residence; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getComplaintType() { return complaintType; }
    public void setComplaintType(String complaintType) { this.complaintType = complaintType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getSummary() {
        return complaintType + " - " + description;
    }
}
