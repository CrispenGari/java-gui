package com.example.campusmaintanance.model;

import java.io.Serializable;

public class LoggedUser implements Serializable {
    private final String email;
    private final String role;
    private final String studentNumber;

    public LoggedUser(String email, String role, String studentNumber) {
        this.email = email;
        this.role = role;
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(role);
    }
}
