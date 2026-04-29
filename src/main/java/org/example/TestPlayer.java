package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestPlayer extends TestEntity {
    private final int WIDTH = 50;
    private final int HEIGHT = 50;

    public TestPlayer() {
    }

    @Override
    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_RIGHT]) this.velocityX += 5;
        if (keys[KeyEvent.VK_LEFT]) this.velocityX -= 5;

        if (keys[KeyEvent.VK_UP]) {
            this.velocityY = -10;
            onFloor = false;
        }

        super.update(keys);
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(this.x, this.y, WIDTH, HEIGHT);
    }
}
