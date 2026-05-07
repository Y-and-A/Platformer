package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LevelButton extends JButton {
    private final int LEVEL;
    private final Image image;

    public LevelButton(int level) {
        this.LEVEL = level;
        try {
            int lvl = (level <= 2) ? level : 0;
            String path = "src/main/resources/levelsSneakPeak/level" + lvl + ".png";
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setFocusable(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.drawString("Level " + LEVEL, getWidth() / 2, getHeight() / 2);
    }
}
