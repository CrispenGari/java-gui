import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Form Example");
        frame.setSize(600, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new GridLayout(3, 4, 10, 5));
        namePanel.setBorder(BorderFactory.createTitledBorder("Name"));

        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField title = new JTextField();
        JTextField nickname = new JTextField();

        namePanel.add(new JLabel("First Name:"));
        namePanel.add(firstName);
        namePanel.add(new JLabel("Last Name:"));
        namePanel.add(lastName);

        namePanel.add(new JLabel("Title:"));
        namePanel.add(title);
        namePanel.add(new JLabel("Nickname:"));
        namePanel.add(nickname);

        namePanel.add(new JLabel("Format:"));
        JComboBox<String> combo = new JComboBox<>(new String[]{
                "Item 1", "Item 2", "Item 3", "Item 4"
        });
        namePanel.add(combo);

        namePanel.add(new JLabel());
        namePanel.add(new JLabel());

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BorderLayout(10, 10));
        emailPanel.setBorder(BorderFactory.createTitledBorder("E-mail"));

        JPanel topEmail = new JPanel(new BorderLayout(10, 10));
        JTextField emailField = new JTextField();
        JButton addBtn = new JButton("Add");

        topEmail.add(new JLabel("E-mail Address:"), BorderLayout.WEST);
        topEmail.add(emailField, BorderLayout.CENTER);
        topEmail.add(addBtn, BorderLayout.EAST);

        emailPanel.add(topEmail, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        model.addElement("Item 1");
        model.addElement("Item 2");
        model.addElement("Item 3");
        model.addElement("Item 4");
        model.addElement("Item 5");

        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.add(new JButton("Edit"));
        buttonPanel.add(new JButton("Remove"));
        buttonPanel.add(new JButton("As Default"));

        JPanel centerEmail = new JPanel(new BorderLayout(10, 10));
        centerEmail.add(scrollPane, BorderLayout.CENTER);
        centerEmail.add(buttonPanel, BorderLayout.EAST);

        emailPanel.add(centerEmail, BorderLayout.CENTER);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(new JLabel("Mail Format:"));

        JRadioButton html = new JRadioButton("HTML");
        JRadioButton plain = new JRadioButton("Plain Text");
        JRadioButton custom = new JRadioButton("Custom");

        ButtonGroup group = new ButtonGroup();
        group.add(html);
        group.add(plain);
        group.add(custom);

        radioPanel.add(html);
        radioPanel.add(plain);
        radioPanel.add(custom);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(new JButton("OK"));
        bottomPanel.add(new JButton("Cancel"));
        mainPanel.add(namePanel);
        mainPanel.add(emailPanel);
        mainPanel.add(radioPanel);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}