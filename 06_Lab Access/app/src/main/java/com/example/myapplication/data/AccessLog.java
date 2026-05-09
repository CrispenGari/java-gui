package com.example.myapplication.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "access_logs")
public class AccessLog {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "studentNumber")
    private String studentNumber;

    @ColumnInfo(name = "studentName")
    private String studentName;

    @ColumnInfo(name = "result")
    private String result;

    @ColumnInfo(name = "officerId")
    private String officerId;

    @ColumnInfo(name = "verifiedAt")
    private String verifiedAt;

    public AccessLog(String studentNumber, String studentName, String result, String officerId, String verifiedAt) {
        this.studentNumber = studentNumber;
        this.studentName = studentName;
        this.result = result;
        this.officerId = officerId;
        this.verifiedAt = verifiedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(String verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
