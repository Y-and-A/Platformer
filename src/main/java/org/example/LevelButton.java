package org.example;

import javax.swing.*;

public class LevelButton extends JButton {
    private Level level;

    public LevelButton(Level level) {
        new JButton(level + "");
        this.addActionListener(e -> {
            this.level = level;
                }
        );
    }
}
