
CREATE DATABASE IF NOT EXISTS campus_maintainance;
USE campus_maintainance;
-- DROP TABLE IF EXISTS complaints;
-- DROP TABLE IF EXISTS admin_table;

CREATE TABLE admin_table (
     id INT AUTO_INCREMENT PRIMARY KEY,
     email VARCHAR(100) NOT NULL UNIQUE,
     password VARCHAR(100)
);

CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_number INT(10) NOT NULL,
    residence VARCHAR(100) NOT NULL,
    room_number VARCHAR(50) NOT NULL,
    complaint_type VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('Pending','In Progress','Resolved','Rejected') DEFAULT 'Pending',
    created_at DATETIME NOT NULL
);

INSERT INTO admin_table(email,password) VALUES ('admin@ufh.ac.za', 'admin123'), ('maintanance@ufh.ac.za', 'maintenance123');

