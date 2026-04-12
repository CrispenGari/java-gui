import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class Main {

    private JLabel preview;

    public Main() {
        JFrame frame = new JFrame("Sign");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel fontPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JComboBox<String> fontBox = new JComboBox<>(new String[]{"Dialog", "Serif", "SansSerif"});
        JComboBox<Integer> sizeBox = new JComboBox<>(new Integer[]{10, 12, 14, 18, 24});

        JCheckBox bold = new JCheckBox("Bold");
        JCheckBox italic = new JCheckBox("Italic");

        JRadioButton left = new JRadioButton("Left", true);
        JRadioButton center = new JRadioButton("Center");
        JRadioButton right = new JRadioButton("Right");

        ButtonGroup alignGroup = new ButtonGroup();
        alignGroup.add(left);
        alignGroup.add(center);
        alignGroup.add(right);

        fontPanel.add(new JLabel("Font"));
        fontPanel.add(fontBox);
        fontPanel.add(new JLabel("Size"));
        fontPanel.add(sizeBox);
        fontPanel.add(bold);
        fontPanel.add(italic);
        fontPanel.add(new JLabel("Alignment"));
        fontPanel.add(left);
        fontPanel.add(center);
        fontPanel.add(right);

        JPanel colorPanel = new JPanel(new GridLayout(2, 6, 10, 5));

        JSlider rText = new JSlider(0, 255, 0);
        JSlider gText = new JSlider(0, 255, 0);
        JSlider bText = new JSlider(0, 255, 0);

        JSlider rBg = new JSlider(0, 255, 255);
        JSlider gBg = new JSlider(0, 255, 255);
        JSlider bBg = new JSlider(0, 255, 255);

        colorPanel.add(new JLabel("Text Red"));
        colorPanel.add(rText);
        colorPanel.add(new JLabel("Green"));
        colorPanel.add(gText);
        colorPanel.add(new JLabel("Blue"));
        colorPanel.add(bText);

        colorPanel.add(new JLabel("BG Red"));
        colorPanel.add(rBg);
        colorPanel.add(new JLabel("Green"));
        colorPanel.add(gBg);
        colorPanel.add(new JLabel("Blue"));
        colorPanel.add(bBg);

        JPanel textPanel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField("This is the text");
        textPanel.add(new JLabel("Text"), BorderLayout.WEST);
        textPanel.add(textField, BorderLayout.CENTER);

        JPanel borderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JComboBox<String> borderColor = new JComboBox<>(new String[]{"Black", "White", "Red"});
        JComboBox<Integer> borderWidth = new JComboBox<>(new Integer[]{1, 3, 5, 8});
        JCheckBox transparent = new JCheckBox("Transparent background");

        JButton reset = new JButton("Reset");

        borderPanel.add(new JLabel("Border Color"));
        borderPanel.add(borderColor);
        borderPanel.add(new JLabel("Width"));
        borderPanel.add(borderWidth);
        borderPanel.add(transparent);
        borderPanel.add(reset);

        preview = new JLabel("This is the text");
        preview.setPreferredSize(new Dimension(600, 150));
        preview.setOpaque(true);
        preview.setHorizontalAlignment(JLabel.LEFT);
        preview.setBackground(Color.WHITE);
        preview.setForeground(Color.BLACK);
        preview.setBorder(new LineBorder(Color.BLACK, 1));

        JPanel previewPanel = new JPanel();
        previewPanel.add(preview);

        main.add(fontPanel);
        main.add(colorPanel);
        main.add(textPanel);
        main.add(borderPanel);

        frame.add(main, BorderLayout.NORTH);
        frame.add(previewPanel, BorderLayout.CENTER);

        ActionListener updateFontAction = e -> updateFont(fontBox, sizeBox, bold, italic);
        fontBox.addActionListener(updateFontAction);
        sizeBox.addActionListener(updateFontAction);
        bold.addActionListener(updateFontAction);
        italic.addActionListener(updateFontAction);

        ActionListener updateAlign = e -> {
            if (left.isSelected()) preview.setHorizontalAlignment(JLabel.LEFT);
            if (center.isSelected()) preview.setHorizontalAlignment(JLabel.CENTER);
            if (right.isSelected()) preview.setHorizontalAlignment(JLabel.RIGHT);
        };

        left.addActionListener(updateAlign);
        center.addActionListener(updateAlign);
        right.addActionListener(updateAlign);

        ChangeListener colorChange = e -> {
            preview.setForeground(new Color(rText.getValue(), gText.getValue(), bText.getValue()));
            if (!transparent.isSelected()) {
                preview.setBackground(new Color(rBg.getValue(), gBg.getValue(), bBg.getValue()));
            }
        };

        rText.addChangeListener(colorChange);
        gText.addChangeListener(colorChange);
        bText.addChangeListener(colorChange);
        rBg.addChangeListener(colorChange);
        gBg.addChangeListener(colorChange);
        bBg.addChangeListener(colorChange);

        textField.addActionListener(e -> preview.setText(textField.getText()));

        borderColor.addActionListener(e -> updateBorder(borderColor, borderWidth));
        borderWidth.addActionListener(e -> updateBorder(borderColor, borderWidth));

        transparent.addActionListener(e -> preview.setOpaque(!transparent.isSelected()));

        reset.addActionListener(e -> resetDefaults(textField));

        frame.setVisible(true);
    }

    private void updateFont(JComboBox<String> fontBox, JComboBox<Integer> sizeBox,
                            JCheckBox bold, JCheckBox italic) {

        int style = Font.PLAIN;
        if (bold.isSelected()) style |= Font.BOLD;
        if (italic.isSelected()) style |= Font.ITALIC;

        preview.setFont(new Font(
                (String) fontBox.getSelectedItem(),
                style,
                (Integer) sizeBox.getSelectedItem()
        ));
    }

    private void updateBorder(JComboBox<String> colorBox, JComboBox<Integer> widthBox) {
        Color color = Color.BLACK;
        if (colorBox.getSelectedItem().equals("White")) color = Color.WHITE;
        if (colorBox.getSelectedItem().equals("Red")) color = Color.RED;

        preview.setBorder(new LineBorder(color, (Integer) widthBox.getSelectedItem()));
    }

    private void resetDefaults(JTextField textField) {
        preview.setText("This is the text");
        preview.setForeground(Color.BLACK);
        preview.setBackground(Color.WHITE);
        preview.setFont(new Font("Dialog", Font.PLAIN, 12));
        preview.setBorder(new LineBorder(Color.BLACK, 1));
        preview.setHorizontalAlignment(JLabel.LEFT);
        preview.setOpaque(true);
        textField.setText("This is the text");
    }

    public static void main(String[] args) {
        new Main();
    }
}