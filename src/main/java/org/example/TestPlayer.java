package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestPlayer extends TestEntity {
    public TestPlayer() {
        width = 50;
        height = 70;
        maxVelocityX = 4;
    }

    @Override
    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_RIGHT]) {
            if (onFloor) {
                this.velocityX += 1;
            }else this.velocityX += 0.6;
            velocityX = Math.min(maxVelocityX, velocityX);
        }
        if (keys[KeyEvent.VK_LEFT]) {
            if (onFloor) {
                this.velocityX -= 1;
            }else this.velocityX -= 0.6;
            velocityX = Math.max(-maxVelocityX, velocityX);
        }

        if (keys[KeyEvent.VK_UP]) {
            if (onFloor) {
                this.velocityY = -5;
                onFloor = false;
            }
        }

        super.update(keys);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(this.x, this.y, width, height);
    }
}
