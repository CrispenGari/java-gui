package user;

import db.DB;
import helpers.LoggedUser;
import helpers.UI;
import shared.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class StudentFrame extends JFrame {
    private final LoggedUser user;
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Complaint", "Date", "Status"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };

    public StudentFrame(LoggedUser user) {
        this.user = user;
        setTitle("Student/Staff Complaints");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(StudentFrame.class.getResource(
                        "../resources/images/logo.png"
                ))
        );
        setIconImage(icon.getImage());

        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(UI.BG);
        root.setBorder(new EmptyBorder(50, 55, 35, 55));
        setContentPane(root);

        JLabel greeting = UI.label("Hi, " + user.studentNumber, new Font("Arial", Font.BOLD, 28));
        root.add(greeting, BorderLayout.NORTH);

        JTable table = new JTable(model);
        table.setRowHeight(42);
        table.setFont(UI.FONT);
        table.getTableHeader().setFont(UI.BOLD);
        table.setBackground(UI.CARD);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(null);
        root.add(scroll, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));

        bottom.setOpaque(false);
        JButton newBtn = UI.button("New Complaint");
        JButton logoutButton = UI.button("Logout", Color.RED, Color.WHITE);
        bottom.add(newBtn);
        bottom.add(logoutButton);
        root.add(bottom, BorderLayout.SOUTH);

        logoutButton.addActionListener(e->{
            dispose();
            new LoginFrame().setVisible(true);
        });
        newBtn.addActionListener(e -> new NewComplaintFrame(user, this::loadComplaints).setVisible(true));
        loadComplaints();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    void loadComplaints() {
        model.setRowCount(0);
        String sql = "SELECT complaint_type, description, created_at, status FROM complaints WHERE student_number=? ORDER BY created_at DESC";
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.studentNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String complaint = rs.getString("complaint_type") + " - " + rs.getString("description");
                model.addRow(new Object[]{complaint, rs.getTimestamp("created_at"), rs.getString("status")});
            }
        } catch (SQLException ex) {
            UI.msg(this, "Could not load complaints: " + ex.getMessage());
        }
    }
}
