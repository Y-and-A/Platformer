package org.example;

import java.awt.*;

//TODO change the name "floating tile" it's confusing, it's just a smaller tile...
public class Tile extends GameObject {
    Image image;
    boolean floating;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int FLOATING_HEIGHT = 20;

    public static final int ENEMY_ID = 90;
    public static final int PLAYER_ID = 99;


    public Tile(double x, double y, boolean floating, Image image) {
        super(x, y);

        this.floating = floating;
        width = WIDTH;
        height = floating ? FLOATING_HEIGHT : HEIGHT;
        this.image = image;
    }

    public static boolean isFloatingTile(int tileId) {
        return tileId >= 21 && tileId <= 24;
    }

    public static boolean isSolid(int tileId) {
        return tileId > 0 && tileId < 90;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, HEIGHT, null);
    }
}
