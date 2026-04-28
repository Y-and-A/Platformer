package org.example;

import javax.swing.*;
import java.awt.*;

public class LevelSelector extends JPanel {
    public LevelSelector() {
        setBounds(0, 0, Window.WIDTH, Window.HEIGHT);

        add(new JButton("Level 1"));
        setBackground(Color.BLUE);
    }
}
