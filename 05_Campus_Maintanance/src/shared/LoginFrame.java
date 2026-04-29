package shared;

import admin.AdminFrame;
import db.DB;
import helpers.LoggedUser;
import helpers.UI;
import user.StudentFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class LoginFrame extends JFrame {
    private final JTextField emailField = UI.field();
    private final JPasswordField passwordField = UI.password();
    private final JComboBox<String> roleBox = new JComboBox<>(new String[]{"student/staff", "admin"});
    private final JLabel passwordLabel = UI.label("Password");
    private final JLabel errorLabel = new JLabel(" ");

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9._%+-]+@ufh\\.ac\\.za$");
    }
    private boolean isValidStudentNumber(String studentNo) {
        return studentNo != null && studentNo.matches("^\\d{9}$");
    }
    public LoginFrame() {
        setTitle("Residence Complaints Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(LoginFrame.class.getResource(
                        "../resources/images/logo.png"
                ))
        );
        setIconImage(icon.getImage());
        JPanel root = UI.page();
        setContentPane(root);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setMaximumSize(new Dimension(650, 600));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel icon_ = new JLabel("", SwingConstants.CENTER);
        Image logoImg = icon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH);
        icon_.setIcon(new ImageIcon(logoImg));

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        form.add(icon_, c);

        c.gridwidth = 2; c.gridy++;
        form.add(UI.label("Email Address/Student Number"), c);
        c.gridy++;
        emailField.setPreferredSize(new Dimension(520, 50));
        form.add(emailField, c);

        c.gridy++;
        form.add(UI.label("Role"), c);
        c.gridy++;
        roleBox.setFont(UI.FONT);
        roleBox.setBackground(UI.INPUT);
        roleBox.setPreferredSize(new Dimension(520, 50));
        form.add(roleBox, c);

        c.gridy++;
        form.add(passwordLabel, c);
        c.gridy++;
        passwordField.setPreferredSize(new Dimension(520, 50));
        form.add(passwordField, c);

        c.gridy++;
        errorLabel.setForeground(new Color(220, 70, 70));
        errorLabel.setFont(UI.FONT);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        form.add(errorLabel, c);

        c.gridy++;
        JButton loginBtn = UI.button("Sign In");
        form.add(loginBtn, c);

        root.add(form);

        roleBox.addActionListener(e -> togglePassword());
        loginBtn.addActionListener(e -> login());
        togglePassword();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void togglePassword() {
        boolean admin = Objects.equals(roleBox.getSelectedItem(), "admin");
        passwordLabel.setVisible(admin);
        passwordField.setVisible(admin);
        revalidate();
        repaint();
    }

    private void login() {
        String email = emailField.getText().trim();
        String role = String.valueOf(roleBox.getSelectedItem());
        String pass = new String(passwordField.getPassword());

        if (role.equals("admin")) {
            if (!isValidEmail(email)) {
                errorLabel.setText("Admin must use a valid @ufh.ac.za email.");
                return;
            }
        } else {
            if (!(isValidEmail(email) || isValidStudentNumber(email))) {
                errorLabel.setText("Enter a valid UFH email or 10-digit student number.");
                return;
            }
        }
        if (email.isEmpty()) {
            errorLabel.setText("Please enter your email address.");
            return;
        }

        try(Connection con = DB.getConnection()){
            if ("admin".equals(role)){
                System.out.println("Email: " + email);
                System.out.println("Password: " + pass);
                PreparedStatement stmt = con.prepareStatement(
                        "SELECT email, password FROM admin_table WHERE email=? AND password=?LIMIT 1"
                );
                stmt.setString(1, email.trim().toLowerCase());
                stmt.setString(2, pass);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    dispose();
                    LoggedUser user = new LoggedUser(
                            rs.getString("email"),
                            role, rs.getString("email").replace("@ufh.ac.za", "")
                    );
                    new AdminFrame(user).setVisible(true);
                } else {
                    errorLabel.setText("Failed to authenticate. Invalid credentials");
                }
            }else{
                dispose();
                LoggedUser user = new LoggedUser(
                        email + "@ufh.ac.za", role,
                        email
                );
                new StudentFrame(user).setVisible(true);
            }
        }catch (SQLException ex) {
            errorLabel.setText("Database error: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
}
