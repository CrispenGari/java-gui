import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public Main() {
        frame = new JFrame("Department Courses");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ================= MENU =================
        JMenuBar menuBar = new JMenuBar();
        JMenu courseMenu = new JMenu("Course Levels");

        JMenuItem csc100 = new JMenuItem("CSC100");
        JMenuItem csc200 = new JMenuItem("CSC200");
        JMenuItem csc300 = new JMenuItem("CSC300");
        JMenuItem csc500 = new JMenuItem("CSC500");

        courseMenu.add(csc100);
        courseMenu.add(csc200);
        courseMenu.add(csc300);
        courseMenu.add(csc500);

        menuBar.add(courseMenu);
        frame.setJMenuBar(menuBar);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Module Code", "Module Name"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);

        csc100.addActionListener(e -> loadCSC100());
        csc200.addActionListener(e -> loadCSC200());
        csc300.addActionListener(e -> loadCSC300());
        csc500.addActionListener(e -> loadCSC500());

        frame.setVisible(true);
    }


    private void clearTable() {
        model.setRowCount(0);
    }

    private void loadCSC100() {
        clearTable();
        model.addRow(new String[]{"CSC101", "Introduction to Programming"});
        model.addRow(new String[]{"CSC102", "Computer Fundamentals"});
    }

    private void loadCSC200() {
        clearTable();
        model.addRow(new String[]{"CSC201", "Data Structures"});
        model.addRow(new String[]{"CSC202", "Database Systems"});
    }

    private void loadCSC300() {
        clearTable();
        model.addRow(new String[]{"CSC301", "Operating Systems"});
        model.addRow(new String[]{"CSC302", "Software Engineering"});
    }

    private void loadCSC500() {
        clearTable();
        model.addRow(new String[]{"CSC501", "Artificial Intelligence"});
        model.addRow(new String[]{"CSC502", "Machine Learning"});
    }

    public static void main(String[] args) {
        new Main();
    }
}