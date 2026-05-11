package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Entity {
    public Direction facingDirection = Direction.RIGHT;
    int[] lastSolidPos = new int[2];
    public int maxAmmo = 6;
    public int currentAmmo = 6;
    public int shoutingCooldown = 200;
    public int reloadSpeed =700;
    public int canShootIn=shoutingCooldown;

    public Player(int x, int y) {
        super(x, y - 1, 49, 70);

        lives = 5;
        jumpForce = 14.0;
        maxVelocityX = 7.0;
        maxVelocityY = 16.0;
        wallJumpForce = 6.0;
        movementForce = 2;
        hitForceX = 17;
        hitForceY = 10;

        image = Assets.playerFront;
    }

    public void update(boolean[] keys, boolean[] prevKeys) {
        if (onFloor) {
            lastSolidPos[0] = (int) x;
            lastSolidPos[1] = (int) y;
        }
        canShootIn-=10;
        super.update();

        if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D]) {
            this.velocityX += movementForce;
            image = Assets.playerRight;
            facingDirection = Direction.RIGHT;
        }
        if (keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A]) {
            this.velocityX -= movementForce;
            image = Assets.playerLeft;
            facingDirection = Direction.LEFT;
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
            image = Assets.playerFront;
        }

        if (y > Window.HEIGHT) {
            x = lastSolidPos[0];
            y = lastSolidPos[1];
            velocityX = 0;
            velocityY = 0;
            lives--;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y, width, height, null);
        g.setColor(Color.blue);
    }
}
