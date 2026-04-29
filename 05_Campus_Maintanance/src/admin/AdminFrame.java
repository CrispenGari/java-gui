package admin;

import db.DB;
import helpers.LoggedUser;
import helpers.UI;
import shared.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class AdminFrame extends JFrame {
    private final LoggedUser user;
    private int selectedComplaintId = -1;

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Student #", "Complaint", "Status"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };

    private final JTextArea detailsArea = new JTextArea();

    public AdminFrame(LoggedUser user) {
        this.user = user;

        setTitle("Admin Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Objects.requireNonNull(AdminFrame.class.getResource(
                        "../resources/images/logo.png"
                ))
        );
        setIconImage(icon.getImage());
        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBackground(UI.BG);
        root.setBorder(new EmptyBorder(45, 55, 35, 55));
        setContentPane(root);

        JTable table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(UI.FONT);
        table.getTableHeader().setFont(UI.BOLD);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setBackground(UI.CARD);
        table.removeColumn(table.getColumnModel().getColumn(0));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(null);

        detailsArea.setFont(UI.FONT);
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setBackground(UI.PINK);
        detailsArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        JScrollPane detailsScroll = new JScrollPane(detailsArea);
        detailsScroll.setBorder(null);
        detailsScroll.setPreferredSize(new Dimension(250, 140));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, detailsScroll);
        split.setResizeWeight(0.75);
        split.setBorder(null);
        root.add(split, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        JButton changeBtn = UI.button("Change Status");
        JButton logoutButton = UI.button("Logout", Color.RED, Color.WHITE);
        bottom.add(changeBtn);
        bottom.add(logoutButton);
        root.add(bottom, BorderLayout.SOUTH);
        logoutButton.addActionListener(e->{
            dispose();
            new LoginFrame().setVisible(true);
        });
        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                selectedComplaintId = (int) model.getValueAt(modelRow, 0);
                loadDetails(selectedComplaintId);
            }
        });

        changeBtn.addActionListener(e -> changeStatus());
        loadComplaints();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void loadComplaints() {
        model.setRowCount(0);
        String sql = """
                SELECT id, student_number, complaint_type, status
                FROM complaints
                ORDER BY created_at DESC
                """;
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("student_number"),
                        rs.getString("complaint_type"),
                        rs.getString("status")
                });
            }
        } catch (SQLException ex) {
            UI.msg(this, "Could not load admin table: " + ex.getMessage());
        }
    }

    private void loadDetails(int id) {
        String sql = """
                SELECT id,  student_number, residence, room_number, complaint_type,
                description, status, created_at
                FROM complaints WHERE id=?
                """;
        try (Connection con = DB.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                detailsArea.setText(
                        "Student #: " + rs.getString("student_number") + "\n" +
                                "Residence: " + rs.getString("residence") + "\n" +
                                "Room Number: " + rs.getString("room_number") + "\n" +
                                "Complaint Type: " + rs.getString("complaint_type") + "\n" +
                                "Status: " + rs.getString("status") + "\n" +
                                "Date: " + rs.getTimestamp("created_at") + "\n\n" +
                                "Description:\n" + rs.getString("description")
                );
            }
        } catch (SQLException ex) {
            UI.msg(this, "Could not load complaint details: " + ex.getMessage());
        }
    }

    private void changeStatus() {
        if (selectedComplaintId == -1) {
            UI.msg(this, "Please click/select a complaint row first.");
            return;
        }

        String[] statuses = {"Pending", "In Progress", "Resolved", "Rejected"};
        String selected = (String) JOptionPane.showInputDialog(
                this,
                "Select new complaint status:",
                "Change Status",
                JOptionPane.PLAIN_MESSAGE,
                null,
                statuses,
                statuses[0]
        );

        if (selected == null) return;

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE complaints SET status=? WHERE id=?")) {
            ps.setString(1, selected);
            ps.setInt(2, selectedComplaintId);
            ps.executeUpdate();

            loadComplaints();
            loadDetails(selectedComplaintId);
            UI.msg(this, "Status updated successfully.");
        } catch (SQLException ex) {
            UI.msg(this, "Could not update status: " + ex.getMessage());
        }
    }
}