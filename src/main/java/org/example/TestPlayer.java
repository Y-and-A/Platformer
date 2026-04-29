package org.example;

import java.awt.*;

public class TestPlayer extends TestEntity {
    private final int WIDTH = 50;
    private final int HEIGHT = 50;

    public TestPlayer() {
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(this.x, this.y, WIDTH, HEIGHT);
    }
}
