### Lab Access Verification System

Android-Based Lab Access Verification System for the CSC Honors 2026 Android assignment. The application allows security personnel or lab assistants to verify whether a student is authorised to enter a university computer laboratory by manually entering a student number and checking it against a locally persisted approved-student database.

- [Lab Access Verification System](#lab-access-verification-system)
- [Project Summary](#project-summary)
- [Main Features](#main-features)
- [Login Details for Demonstration](#login-details-for-demonstration)
- [Sample Student Numbers](#sample-student-numbers)
- [Database Design](#database-design)
- [students table](#students-table)
- [access_logs table](#access_logs-table)
- [Important Source Files](#important-source-files)
- [How to Run the Project](#how-to-run-the-project)
- [Expected Demonstration Flow](#expected-demonstration-flow)
- [Marking Guide Alignment](#marking-guide-alignment)
- [Notes](#notes)

### Project Summary

This project was refactored from the original Android project into a focused lab access control application. It uses Java, XML layouts, Material Design Components, Room Database, and SQLite persistence. The system validates numeric input, prevents empty submissions, displays clear ACCESS GRANTED or ACCESS DENIED feedback, stores approved students locally, and records verification logs with date and time.

### Main Features

- Security personnel login screen for controlled use of the verification system.
- Numeric-only student number input field.
- Empty-input and invalid-input validation.
- VERIFY button that checks the local Room database.
- ACCESS GRANTED result for approved students.
- ACCESS DENIED result for unknown or unauthorised students.
- Student number and student name display for successful verification.
- Room Database implementation backed by SQLite.
- Preloaded approved student records from the assignment sample data.
- Access log table that records student number, name, result, officer ID, and verification time.
- Recent access history displayed in the application.
- Clear logs feature for demonstration and testing.
- Dark mode toggle.
- Clean user interface based on the Color Hunt palette: `#E8EDF2`, `#2C3947`, `#547A95`, `#C2A56D`.

### Login Details for Demonstration

Use the following login credentials when demonstrating the app:

```text
Officer ID: admin
PIN: 1234
```

### Sample Student Numbers

The following student numbers are seeded automatically into the local database on first launch:

| Student Number | Access Status |
| -------------- | ------------- |
| 202232773      | Allowed       |
| 202326649      | Allowed       |
| 202249662      | Allowed       |
| 202208089      | Allowed       |
| 223037156      | Allowed       |
| 202395585      | Allowed       |
| 202249511      | Allowed       |
| 202233667      | Allowed       |
| 202250144      | Allowed       |
| 202228822      | Allowed       |
| 201925693      | Allowed       |

Any student number not found in the database returns ACCESS DENIED.

### Database Design

The application uses Room as the abstraction layer over SQLite. The database file is named:

```text
lab_access_verification.db
```

### students table

| Field         | Type    | Description                                      |
| ------------- | ------- | ------------------------------------------------ |
| studentNumber | String  | Primary key and unique student identifier        |
| studentName   | String  | Name displayed when access is granted            |
| accessAllowed | Boolean | Determines whether the student may enter the lab |

### access_logs table

| Field         | Type   | Description                        |
| ------------- | ------ | ---------------------------------- |
| id            | Long   | Auto-generated primary key         |
| studentNumber | String | Number entered during verification |
| studentName   | String | Matched name or Unknown Student    |
| result        | String | ACCESS GRANTED or ACCESS DENIED    |
| officerId     | String | Logged-in security personnel ID    |
| verifiedAt    | String | Date and time of verification      |

### Important Source Files

```text
app/src/main/java/com/example/myapplication/MainActivity.java
app/src/main/java/com/example/myapplication/data/Student.java
app/src/main/java/com/example/myapplication/data/AccessLog.java
app/src/main/java/com/example/myapplication/data/StudentDao.java
app/src/main/java/com/example/myapplication/data/AccessLogDao.java
app/src/main/java/com/example/myapplication/data/AppDatabase.java
app/src/main/res/layout/activity_main.xml
app/src/main/res/values/colors.xml
app/src/main/res/values/themes.xml
```

### How to Run the Project

1. Open the folder in Android Studio.
2. Wait for Gradle sync to complete.
3. Run the app on an emulator or Android device.
4. Login using the demonstration credentials.
5. Test a successful verification with `202232773`.
6. Test a failed verification with any number not in the database, such as `123456789`.
7. Test validation by pressing VERIFY with an empty field.
8. Toggle dark mode to demonstrate the bonus interface feature.

### Expected Demonstration Flow

1. Start the application.
2. Login as a security officer.
3. Enter an approved student number.
4. Press VERIFY and show ACCESS GRANTED.
5. Enter an unknown student number.
6. Press VERIFY and show ACCESS DENIED.
7. Show recent access logs.
8. Clear logs and explain local database persistence.

### Marking Guide Alignment

| Assignment Component    | Implementation                                                                                               |
| ----------------------- | ------------------------------------------------------------------------------------------------------------ |
| User Interface Design   | Clean Material UI with assignment-specific title, input field, buttons, status card, logs, and theme palette |
| Database Implementation | Room Database with SQLite-backed `students` and `access_logs` tables                                         |
| Verification Logic      | DAO query checks whether student exists and whether accessAllowed is true                                    |
| Input Validation        | Empty input prevented, numeric-only XML input, additional Java regex validation                              |
| Code Quality            | OOP entities, DAO interfaces, database singleton, background database executor                               |
| Documentation           | README.md and Word implementation report included                                                            |
| Demonstration           | Supports granted access, denied access, validation, logs, and dark mode                                      |

### Notes

Room Database uses SQLite internally, which satisfies the local database requirement while providing safer Java annotations, DAO methods, and compile-time query checking. The database work runs on a background executor to avoid blocking the Android UI thread.
