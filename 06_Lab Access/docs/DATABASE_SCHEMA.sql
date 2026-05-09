-- SQLite schema represented by the Room entities in the Android project.
-- Room creates these tables automatically at runtime.

CREATE TABLE students (
    studentNumber TEXT NOT NULL PRIMARY KEY,
    studentName TEXT,
    accessAllowed INTEGER NOT NULL
);

CREATE TABLE access_logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    studentNumber TEXT,
    studentName TEXT,
    result TEXT,
    officerId TEXT,
    verifiedAt TEXT
);
