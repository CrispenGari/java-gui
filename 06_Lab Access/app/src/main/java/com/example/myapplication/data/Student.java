package com.example.myapplication.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "studentNumber")
    private String studentNumber;

    @ColumnInfo(name = "studentName")
    private String studentName;

    @ColumnInfo(name = "accessAllowed")
    private boolean accessAllowed;

    public Student(@NonNull String studentNumber, String studentName, boolean accessAllowed) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.accessAllowed = accessAllowed;
    }

    @NonNull
    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(@NonNull String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public boolean isAccessAllowed() {
        return accessAllowed;
    }

    public void setAccessAllowed(boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }
}
