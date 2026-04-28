package org.example;

import org.example.levels.Level;

import javax.swing.*;
import java.awt.*;

public class LevelButton extends JButton {
    private Level level;

    public LevelButton(Level level) {
        setText(level.level + "");
        this.addActionListener(e -> {
            this.level = level;
            setLayout(null);
            level.setBackground(Color.GRAY);
            setFocusable(false);
            Window.changeScene(level);
            System.out.println("1");

        });
    }
}
