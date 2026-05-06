package org.example;

import java.awt.*;

public class Tile extends GameObject {
    Image image;
    boolean floating;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int FLOATING_HEIGHT = 20;

    public final Rectangle rect;

    public Tile(int x, int y, boolean floating, Image image) {
        super(x, y);

        this.floating = floating;
        width = WIDTH;
        height = floating ? FLOATING_HEIGHT : HEIGHT;
        this.image = image;

        this.rect = new Rectangle(x, y, width, height);
    }

    public static boolean isFloatingTile(int tileId) {
        return tileId >= 21 && tileId <= 24;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, 50, null);
    }
}
