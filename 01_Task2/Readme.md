### JSlinder

The following code create and renders a slider on a JPanel and then get the value of the slider and show it on a JLabel.

```java
import javax.swing.*;

public class Main{

    public static void main(String [] args){
        ImageIcon icon = new ImageIcon(
           "./icon.png"
        );
        JFrame frame = new JFrame("My Frame");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon.getImage());
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Slider Value: 50");
        panel.add(label);
        JSlider slider = new JSlider(0, 100, 50);
        panel.add(slider);

        slider.addChangeListener(e -> {
            int value = slider.getValue();
            label.setText("Slider Value: " + value);
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
```
