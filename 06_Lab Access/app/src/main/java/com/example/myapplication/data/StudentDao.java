package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM students WHERE studentNumber = :studentNumber LIMIT 1")
    Student findByStudentNumber(String studentNumber);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Student> students);

    @Query("SELECT COUNT(*) FROM students")
    int countStudents();
}
