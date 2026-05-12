package org.example;

import java.awt.*;

public class Tile extends GameObject {
    Image image;
    boolean platform;

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int PLATFORM_HEIGHT = 20;

    public static final int ENEMY_ID = 90;
    public static final int PLAYER_ID = 99;


    public Tile(double x, double y, boolean platform, Image image) {
        super(x, y);

        this.platform = platform;
        width = WIDTH;
        height = platform ? PLATFORM_HEIGHT : HEIGHT;
        this.image = image;
    }

    public static boolean isPlatform(int tileId) {
        return tileId >= 21 && tileId <= 24;
    }

    public static boolean isMushroom(int tileId) {
        return tileId ==66;
    }

    public static boolean isSolid(int tileId) {
        return tileId > 0 && tileId < 90;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, HEIGHT, null);
    }
}
