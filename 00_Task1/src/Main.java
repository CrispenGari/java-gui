import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main{
    public static void main(String[] args){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource(
                "./icon.jpg"
        )));
        JFrame frame = new JFrame("Hello Swing");
        frame.setLayout(new BorderLayout());
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        top.setBounds(0, 0, 600, 100);
        JLabel label = new JLabel("Enter Name");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        top.add(label);
        JTextField name = new JTextField(38);
        name.setFont(new Font("Arial", Font.BOLD, 12));
        name.setPreferredSize(new Dimension(400, 25));
        top.add(name);
        JButton add = new JButton("Add");
        add.setFocusable(false);
        top.add(add);
        JPanel center = new JPanel(new BorderLayout(15, 10));
        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        center.add(new JLabel(), BorderLayout.EAST);
        center.add(new JLabel(), BorderLayout.WEST);
        center.add(scrollPane, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));

        JButton clear = new JButton("Clear");
        add.setFocusable(false);
        bottom.add(clear);
        JButton count = new JButton("Count");
        add.setFocusable(false);
        bottom.add(count);
        add.addActionListener(e->{
            String value = name.getText();
            if(value.trim().length() <3){
                JOptionPane.showMessageDialog(frame, "The name must be at least 3 characters long.", "Error adding name.", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.addElement(value);
            name.setText("");
        });

        clear.addActionListener(e->{
            int res = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all names?", "Clear all names.",
                    JOptionPane.YES_NO_OPTION);
            if(res==0){
                model.clear();
            }
        });

        count.addActionListener(e->{
           JOptionPane.showMessageDialog(frame, "The total number of names are: '" + model.size() + "'.", "Total names.", JOptionPane.INFORMATION_MESSAGE);
        });
        frame.add(top, BorderLayout.NORTH);
        frame.add(center, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}