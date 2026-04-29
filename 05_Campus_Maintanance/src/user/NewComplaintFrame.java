package user;
import db.DB;
import helpers.LoggedUser;
import helpers.UI;
import shared.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class NewComplaintFrame extends JFrame {
    private final LoggedUser user;
    private final Runnable refresh;
    private final JTextField residenceField = UI.field();
    private final JTextField roomField = UI.field();
    private final JTextArea descriptionArea = new JTextArea(5, 20);
    private final ButtonGroup group = new ButtonGroup();

    NewComplaintFrame(LoggedUser user, Runnable refresh) {
        this.user = user;
        this.refresh = refresh;

        setTitle("New Complaint");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(NewComplaintFrame.class.getResource(
                        "../resources/images/logo.png"
                ))     );
        setIconImage(icon.getImage());

        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(UI.BG);
        root.setBorder(new EmptyBorder(45, 55, 35, 55));
        setContentPane(root);

        root.add(UI.label("Hi, " + user.studentNumber, new Font("Arial", Font.BOLD, 28)), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        root.add(center, BorderLayout.CENTER);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(12, 16, 12, 16);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridx = 0; c.gridy = 0;
        center.add(UI.label("Residence"), c);
        c.gridx = 1;
        center.add(UI.label("Room Number"), c);

        c.gridx = 0; c.gridy = 1;
        residenceField.setPreferredSize(new Dimension(350, 50));
        center.add(residenceField, c);
        c.gridx = 1;
        roomField.setPreferredSize(new Dimension(350, 50));
        center.add(roomField, c);

        JPanel types = new JPanel(new GridLayout(2, 3, 40, 25));
        types.setOpaque(false);
        String[] options = {"Windows", "Plumbing", "Electrical Problem", "Carpentry", "Other"};
        for (String option : options) {
            JRadioButton rb = new JRadioButton(option);
            rb.setFont(UI.FONT);
            rb.setOpaque(false);
            rb.setActionCommand(option);
            group.add(rb);
            types.add(rb);
        }
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; c.insets = new Insets(50, 16, 50, 16);
        center.add(types, c);

        c.insets = new Insets(12, 16, 12, 16);
        c.gridy = 3;
        center.add(UI.label("Description"), c);

        c.gridy = 4; c.weighty = 1; c.fill = GridBagConstraints.BOTH;
        descriptionArea.setFont(UI.FONT);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBackground(UI.PINK);
        descriptionArea.setBorder(new EmptyBorder(12, 12, 12, 12));
        center.add(new JScrollPane(descriptionArea), c);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        JButton submit = UI.button("Submit Complaint");
        bottom.add(submit);
        root.add(bottom, BorderLayout.SOUTH);

        submit.addActionListener(e -> submitComplaint());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void submitComplaint() {
        if (residenceField.getText().trim().isEmpty() || roomField.getText().trim().isEmpty()
                || group.getSelection() == null || descriptionArea.getText().trim().isEmpty()) {
            UI.msg(this, "Please complete all fields.");
            return;
        }

        String sql = "INSERT INTO complaints(student_number,residence,room_number,complaint_type,description, created_at) VALUES(?,?,?,?,?,?);";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.studentNumber.trim());
            ps.setString(2, residenceField.getText().trim().toUpperCase());
            ps.setString(3, roomField.getText().trim());
            ps.setString(4, group.getSelection().getActionCommand());
            ps.setString(5, descriptionArea.getText().trim());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
            UI.msg(this, "Complaint submitted successfully.");
            refresh.run();
            dispose();
        } catch (SQLException ex) {
            UI.msg(this, "Could not submit complaint: " + ex.getMessage());
        }
    }
}

