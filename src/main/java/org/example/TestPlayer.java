package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class TestPlayer extends TestEntity {
    public TestPlayer() {
        width = 50;
        height = 70;

        movementForce = 1.0;
        jumpForce = 12.0;

        maxVelocityX = 6.0;
        maxVelocityY = 15.0;
    }

    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_RIGHT]) this.velocityX += movementForce;
        if (keys[KeyEvent.VK_LEFT]) this.velocityX -= movementForce;
        if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE]) && onFloor) this.velocityY -= jumpForce;

        super.update();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.blue);
        Rectangle2D.Double playerRect = new Rectangle2D.Double(this.x, this.y, width, height);
        g2d.fill(playerRect);
    }
}
