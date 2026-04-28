package org.example;

import javax.swing.*;
import java.awt.*;

public class LevelButton extends JButton {
    private Level level;

    public LevelButton(Level level) {
        setText(level + "");
        this.addActionListener(e -> {
            this.level = level;
            level.setBackground(Color.GRAY);
            Window.changeScene(level);
            System.out.println("1");

        });
    }
}
