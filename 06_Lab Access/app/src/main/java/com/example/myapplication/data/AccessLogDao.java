package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccessLogDao {
    @Insert
    void insert(AccessLog log);

    @Query("SELECT * FROM access_logs ORDER BY id DESC LIMIT 12")
    List<AccessLog> recentLogs();

    @Query("DELETE FROM access_logs")
    void deleteAll();
}
