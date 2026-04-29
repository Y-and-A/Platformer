package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
//TODO make players hitbox slightly larger then his image in order to fix "z clipping" on player movement
public class TestPlayer extends TestEntity {
    public TestPlayer() {
        width = 50;
        height = 70;
        maxVelocityX = 4;
        movement = 3;
        jumpForce = 5;
    }

    @Override
    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_RIGHT]) {
//            if (onFloor) {
                this.velocityX += movement;
//            }
            velocityX = Math.min(maxVelocityX, velocityX);
        }
        if (keys[KeyEvent.VK_LEFT]) {
//            if (onFloor) {
                this.velocityX -= movement;
//            };
            velocityX = Math.max(-maxVelocityX, velocityX);
        }

        if (keys[KeyEvent.VK_UP]) {
            if (onFloor) {
                this.velocityY -= jumpForce;
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
