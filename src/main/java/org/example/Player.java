package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    public Direction facingDirection = Direction.RIGHT;

    public Player(int x, int y) {
        super(x, y, 49, 70);

        lives = 5;
        jumpForce = 14.0;
        maxVelocityX = 7.0;
        maxVelocityY = 16.0;
        wallJumpForce = 6.0;
        movementForce = 2;

        image = Assets.playerFront;
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        if (lives <= 0) alive = false;

        if (keys[KeyEvent.VK_RIGHT]) {
            this.velocityX += movementForce;
            image = Assets.playerRight;
            facingDirection = Direction.RIGHT;
        }
        if (keys[KeyEvent.VK_LEFT]) {
            this.velocityX -= movementForce;
            image = Assets.playerLeft;
            facingDirection = Direction.LEFT;
        }

        boolean jumpKeyPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE];
        boolean jumpKeyPrev = prevKeys[KeyEvent.VK_UP] || prevKeys[KeyEvent.VK_SPACE];

        if (jumpKeyPressed && !jumpKeyPrev && onFloor) {
            this.velocityY -= jumpForce;
            facingDirection = Direction.UP;
            image = Assets.playerUp;
        }
        if (!jumpKeyPressed && jumpKeyPrev && this.velocityY < 0) {
            this.velocityY *= 0.5;
            facingDirection = Direction.DOWN;
            image = Assets.playerFront;
        }

        if (y > Window.HEIGHT) {
            y = 0;
            x = 0;
            lives--;
        }
        super.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
        g.setColor(Color.blue);
    }
}
