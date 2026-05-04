package org.example;

import java.awt.*;

public class Tile extends GameObject {
    Image image;
    boolean floating;

    public Tile(int x, int y, boolean floating, Image image) {
        super(x, y);
        width = 50;
        if (floating) {
            height = 20;
            this.floating = true;
        } else
            height =  50;
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, 50, null);
    }

    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
