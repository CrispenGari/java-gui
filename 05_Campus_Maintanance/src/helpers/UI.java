package helpers;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UI {
    public static final Color BG = new Color(250, 247, 239);
    public static final Color CARD = new Color(216, 216, 216);
    public static final Color INPUT = new Color(242, 242, 242);
    public static final Color PINK = new Color(242, 232, 232);
    static final Color BUTTON = new Color(255, 243, 205);
    public static final Font FONT = new Font("Arial", Font.PLAIN, 16);
    public static final Font BOLD = new Font("Arial", Font.BOLD, 16);

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setFont(BOLD);
        b.setBackground(BUTTON);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(220, 55));
        return b;
    }

    public static JButton button(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(BOLD);
        b.setForeground(fg);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(220, 55));
        return b;
    }
    public static JTextField field() {
        JTextField f = new JTextField();
        f.setFont(FONT);
        f.setBackground(INPUT);
        f.setBorder(new EmptyBorder(10, 15, 10, 15));
        return f;
    }

    public static JPasswordField password() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT);
        f.setBackground(INPUT);
        f.setBorder(new EmptyBorder(10, 15, 10, 15));
        return f;
    }

    public static JLabel label(String text) {
        return label(text, FONT);
    }
    public static JLabel label(String text, Font font) {
        JLabel l = new JLabel(text);
        l.setFont(font);
        return l;
    }


    public static JPanel page() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(BG);
        p.setBorder(new EmptyBorder(30, 35, 30, 35));
        return p;
    }

    public static void msg(Component parent, String text) {
        JOptionPane.showMessageDialog(parent, text);
    }
}
