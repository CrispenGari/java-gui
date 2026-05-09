package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.example.myapplication.data.AccessLog;
import com.example.myapplication.data.AppDatabase;
import com.example.myapplication.data.Student;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS = "lab_access_preferences";
    private static final String KEY_DARK_MODE = "dark_mode_enabled";
    private static final String DEFAULT_OFFICER = "admin";
    private static final String DEFAULT_PIN = "1234";

    private AppDatabase database;
    private String currentOfficerId = "";

    private View loginCard;
    private LinearLayout appContent;
    private TextInputEditText officerIdInput;
    private TextInputEditText pinInput;
    private TextInputEditText studentNumberInput;
    private TextInputLayout studentInputLayout;
    private TextView currentOfficerText;
    private TextView statusLabel;
    private TextView studentDetailsText;
    private TextView logsText;
    private LinearLayout statusBox;
    private SwitchMaterial darkModeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applySavedTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = AppDatabase.getInstance(this);
        bindViews();
        configureListeners();
        refreshLogs();
    }

    private void applySavedTheme() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        boolean darkMode = prefs.getBoolean(KEY_DARK_MODE, false);
        AppCompatDelegate.setDefaultNightMode(
                darkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    private void bindViews() {
        loginCard = findViewById(R.id.loginCard);
        appContent = findViewById(R.id.appContent);
        officerIdInput = findViewById(R.id.officerIdInput);
        pinInput = findViewById(R.id.pinInput);
        studentNumberInput = findViewById(R.id.studentNumberInput);
        studentInputLayout = findViewById(R.id.studentInputLayout);
        currentOfficerText = findViewById(R.id.currentOfficerText);
        statusLabel = findViewById(R.id.statusLabel);
        studentDetailsText = findViewById(R.id.studentDetailsText);
        logsText = findViewById(R.id.logsText);
        statusBox = findViewById(R.id.statusBox);
        darkModeSwitch = findViewById(R.id.darkModeSwitch);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        darkModeSwitch.setChecked(prefs.getBoolean(KEY_DARK_MODE, false));
    }

    private void configureListeners() {
        MaterialButton loginButton = findViewById(R.id.loginButton);
        MaterialButton verifyButton = findViewById(R.id.verifyButton);
        MaterialButton resetButton = findViewById(R.id.resetButton);
        MaterialButton clearLogsButton = findViewById(R.id.clearLogsButton);

        loginButton.setOnClickListener(v -> handleLogin());
        verifyButton.setOnClickListener(v -> verifyStudent());
        resetButton.setOnClickListener(v -> resetVerificationForm());
        clearLogsButton.setOnClickListener(v -> clearLogs());

        studentNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    studentInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            getSharedPreferences(PREFS, MODE_PRIVATE)
                    .edit()
                    .putBoolean(KEY_DARK_MODE, isChecked)
                    .apply();
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }

    private void handleLogin() {
        String officerId = getInputText(officerIdInput).trim();
        String pin = getInputText(pinInput).trim();

        if (officerId.isEmpty()) {
            officerIdInput.setError("Officer ID is required");
            officerIdInput.requestFocus();
            return;
        }
        if (pin.isEmpty()) {
            pinInput.setError("PIN is required");
            pinInput.requestFocus();
            return;
        }
        if (!DEFAULT_OFFICER.equalsIgnoreCase(officerId) || !DEFAULT_PIN.equals(pin)) {
            Toast.makeText(this, "Invalid Officer ID or PIN", Toast.LENGTH_SHORT).show();
            return;
        }

        currentOfficerId = officerId;
        currentOfficerText.setText("Officer: " + currentOfficerId + " | Local database: Room + SQLite");
        loginCard.setVisibility(View.GONE);
        appContent.setVisibility(View.VISIBLE);
        studentNumberInput.requestFocus();
        showKeyboard(studentNumberInput);
    }

    private void verifyStudent() {
        String studentNumber = getInputText(studentNumberInput).trim();

        if (studentNumber.isEmpty()) {
            studentInputLayout.setError("Please enter a student number before verification.");
            showNeutralStatus("STATUS: INPUT REQUIRED", "The student number field cannot be empty.");
            return;
        }

        if (!studentNumber.matches("\\d+")) {
            studentInputLayout.setError("Only numeric student numbers are allowed.");
            showNeutralStatus("STATUS: INVALID INPUT", "Use digits only, for example 202232773.");
            return;
        }

        AppDatabase.getDatabaseExecutor().execute(() -> {
            Student student = database.studentDao().findByStudentNumber(studentNumber);
            boolean granted = student != null && student.isAccessAllowed();
            String studentName = student == null ? "Unknown Student" : student.getStudentName();
            String result = granted ? "ACCESS GRANTED" : "ACCESS DENIED";
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            database.accessLogDao().insert(new AccessLog(
                    studentNumber,
                    studentName,
                    result,
                    currentOfficerId,
                    timestamp
            ));

            runOnUiThread(() -> {
                if (granted) {
                    showGrantedStatus(studentNumber, studentName, timestamp);
                } else {
                    showDeniedStatus(studentNumber, timestamp);
                }
                hideKeyboard();
                refreshLogs();
            });
        });
    }

    private void showGrantedStatus(String studentNumber, String studentName, String timestamp) {
        statusBox.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_granted));
        statusLabel.setText("ACCESS GRANTED");
        statusLabel.setTextColor(ContextCompat.getColor(this, R.color.success));
        studentDetailsText.setText(
                "Student Number: " + studentNumber +
                        "\nStudent Name: " + studentName +
                        "\nVerified At: " + timestamp
        );
    }

    private void showDeniedStatus(String studentNumber, String timestamp) {
        statusBox.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_denied));
        statusLabel.setText("ACCESS DENIED");
        statusLabel.setTextColor(ContextCompat.getColor(this, R.color.danger));
        studentDetailsText.setText(
                "Student Number: " + studentNumber +
                        "\nReason: Student was not found in the approved local database." +
                        "\nVerified At: " + timestamp
        );
    }

    private void showNeutralStatus(String title, String details) {
        statusBox.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_status_neutral));
        statusLabel.setText(title);
        statusLabel.setTextColor(ContextCompat.getColor(this, R.color.palette_primary_dark));
        studentDetailsText.setText(details);
    }

    private void resetVerificationForm() {
        studentNumberInput.setText("");
        studentInputLayout.setError(null);
        showNeutralStatus("STATUS: READY", "Enter a student number and press VERIFY.");
        studentNumberInput.requestFocus();
    }

    private void refreshLogs() {
        AppDatabase.getDatabaseExecutor().execute(() -> {
            List<AccessLog> logs = database.accessLogDao().recentLogs();
            StringBuilder builder = new StringBuilder();
            if (logs.isEmpty()) {
                builder.append("No verification logs yet.");
            } else {
                for (AccessLog log : logs) {
                    builder.append(log.getVerifiedAt())
                            .append("\n")
                            .append(log.getResult())
                            .append(" - ")
                            .append(log.getStudentNumber())
                            .append(" (")
                            .append(log.getStudentName())
                            .append(")")
                            .append("\nOfficer: ")
                            .append(log.getOfficerId())
                            .append("\n\n");
                }
            }
            runOnUiThread(() -> logsText.setText(builder.toString().trim()));
        });
    }

    private void clearLogs() {
        AppDatabase.getDatabaseExecutor().execute(() -> {
            database.accessLogDao().deleteAll();
            runOnUiThread(() -> {
                logsText.setText("No verification logs yet.");
                Toast.makeText(this, "Access logs cleared", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private String getInputText(TextInputEditText input) {
        return input.getText() == null ? "" : input.getText().toString();
    }

    private void showKeyboard(View view) {
        view.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
