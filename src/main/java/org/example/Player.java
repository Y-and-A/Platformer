package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    public Direction facingDirection = Direction.RIGHT;
    public Direction shootDirection = Direction.RIGHT;
    Point lastSolidPos;

    public final int MAX_AMMO = 6;
    public final int FIRE_INTERVAL = 200;
    public final int RELOAD_SPEED = 700;
    public int currentAmmo = MAX_AMMO;
    public int canShootIn = FIRE_INTERVAL;

    public Player(int x, int y) {
        super(x, y - 1, 49, 70);
        lastSolidPos = new Point(x, y);

        lives = 5;
        jumpForce = 14.0;
        maxVelocityX = 7.0;
        maxVelocityY = 18.0;
        movementForce = 2;
        hitForceX = 17;
        hitForceY = 10;

        image = Assets.playerFront;
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        if (onFloor) {
            lastSolidPos.setLocation((int) x, (int) y);
        }

        canShootIn -= 10;
        super.update();

        if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
            this.velocityX += movementForce;
            image = Assets.playerRight;
            facingDirection = Direction.RIGHT;
            shootDirection = Direction.RIGHT;
        }
        if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) {
            this.velocityX -= movementForce;
            image = Assets.playerLeft;
            facingDirection = Direction.LEFT;
            shootDirection = Direction.LEFT;
        }

        boolean jumpKeyPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_W];
        boolean jumpKeyPrev = prevKeys[KeyEvent.VK_UP] || prevKeys[KeyEvent.VK_SPACE] || prevKeys[KeyEvent.VK_W];

        if (jumpKeyPressed && !jumpKeyPrev && onFloor) {
            this.velocityY -= jumpForce;
            facingDirection = Direction.UP;
            image = Assets.playerUp;
        }
        if (!jumpKeyPressed && jumpKeyPrev && this.velocityY < 0) {
            this.velocityY *= 0.5;
            facingDirection = Direction.DOWN;
//            image = Assets.playerFront;
        }

        if (y > Window.HEIGHT) {
            lives--;
            if (lives > 0) {
                x = lastSolidPos.x;
                y = lastSolidPos.y;
                velocityX = 0;
                velocityY = 0;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
        g.setColor(Color.blue);
    }
}
