package org.example;

import java.awt.*;

public class Tile extends GameObject {
    Image image;
    boolean floating;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int FLOATING_HEIGHT = 20;

    public Tile(int x, int y, boolean floating, Image image) {
        super(x, y);

        this.floating = floating;
        width = WIDTH;
        height = floating ? FLOATING_HEIGHT : HEIGHT;
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, 50, null);
    }

    public static boolean isFloatingTile(int id) {
        return id >= 21 && id <= 24;
    }

    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
