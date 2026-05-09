package com.example.myapplication.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class, AccessLog.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "lab_access_verification.db";
    private static volatile AppDatabase instance;
    private static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    public abstract StudentDao studentDao();
    public abstract AccessLogDao accessLogDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME
                            )
                            .addCallback(seedCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }

    private static final RoomDatabase.Callback seedCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseExecutor.execute(() -> {

                if (instance != null && instance.studentDao().countStudents() == 0) {
                    instance.studentDao().insertAll(sampleStudents());
                }
            });
        }
    };

    private static List<Student> sampleStudents() {
        return Arrays.asList(
                new Student("202232773", "Siphesihle Nzimase", true),
                new Student("202326649", "Luvalo Luxolo", true),
                new Student("202249662", "Lerato Dlamini", true),
                new Student("202208089", "Phindile Khumalo", true),
                new Student("223037156", "Noguda Zusakhe", true),
                new Student("202395585", "Michael Moyo", true),
                new Student("202249511", "Thandeka Naidoo", true),
                new Student("202233667", "Blessing Chirwa", true),
                new Student("202250144", "Nomsa Khumalo", true),
                new Student("202228822", "Farai Nyathi", true),
                new Student("201925693", "Tinashe Crispen Garidzira", true)
        );
    }
}
