package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PTH_Item extends JPanel {

    private JTextField text1;
    private JTextField text2;
    private String key;

    public PTH_Item(String key, String value) {
        this.key = key;
        this.setSize(220, 30);
        init(key, value);
        text2.setFocusable(false);
        text1.setFocusable(false);
    }

    private void init(String key, String value) {
        Dimension pre = new Dimension(220, 35);
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.setMinimumSize(pre);
        this.setMaximumSize(pre);

        text1 = new JTextField(7);
        text1.setEditable(false);
        text1.setText(key);
        text1.setBackground(Color.WHITE);
        text1.setMinimumSize(new Dimension(50, 50));
        text1.setMaximumSize(new Dimension(50, 50));
        text1.setFont(new Font("Arial", Font.PLAIN, 15));

        text2 = new JTextField(7);
        text2.setEditable(false);
        text2.setText(value);
        text2.setBackground(Color.WHITE);
        text2.setMinimumSize(new Dimension(50, 50));
        text2.setMaximumSize(new Dimension(50, 50));
        text2.setFont(new Font("Arial", Font.PLAIN, 15));

        this.add(text1);
        this.add(text2);
    }

    public JTextField getText1() {
        return text1;
    }

    public JTextField getText2() {
        return text2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
