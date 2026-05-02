package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    private Image imgLeft, imgRight, imgFront, imgUp;

    private double wallJumpTimer = 0;
    private final double WALL_JUMP_LOCK_TIME = 0.4;

//    private String direction; was ment to be a way to change the image from other places
    public Player() {
        width = 50;
        height = 70;

        movementForce = 2.5 * ACCELERATION_MULTIPLIER;
        jumpForce = 14.0 * VELOCITY_MULTIPLIER;
        maxVelocityX = 7.0 * VELOCITY_MULTIPLIER;
        maxVelocityY = 16.0  * VELOCITY_MULTIPLIER;
        wallJumpForce = 10.0 * VELOCITY_MULTIPLIER;

        try {
            imgLeft = ImageIO.read(new File("src/main/resources/player/player-facingLeft.png"));
            imgRight = ImageIO.read(new File("src/main/resources/player/player-facingRight.png"));
            imgFront = ImageIO.read(new File("src/main/resources/player/player-front.png"));
            imgUp = ImageIO.read(new File("src/main/resources/player/player-facingUp.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        image = imgFront;
    }

    public void update(boolean[] keys, boolean[] prevKeys, double deltaTime) {
        if (wallJumpTimer > 0) wallJumpTimer -= deltaTime;

        if (wallJumpTimer <= 0) {
            if (keys[KeyEvent.VK_RIGHT]){
                this.velocityX += movementForce * deltaTime;
                image = imgRight;
            }
            if (keys[KeyEvent.VK_LEFT]){
                this.velocityX -= movementForce * deltaTime;
                image = imgLeft;
            }
        }

        boolean jumpKeyPressed = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE];
        boolean jumpKeyPrev = prevKeys[KeyEvent.VK_UP] || prevKeys[KeyEvent.VK_SPACE];

        if (jumpKeyPressed && !jumpKeyPrev && onFloor) {
            this.velocityY -= jumpForce;
            image = imgUp;
        }
        if (!jumpKeyPressed && jumpKeyPrev && this.velocityY < 0) {
            this.velocityY *= 0.5;
        }

        if (!onFloor && onWall && jumpKeyPressed && !jumpKeyPrev) {
            wallJumpTimer = WALL_JUMP_LOCK_TIME;

            if (keys[KeyEvent.VK_RIGHT]) {
                this.velocityY = -wallJumpForce;
                this.velocityX = -maxVelocityX;
                image = imgLeft;
            }
            else if (keys[KeyEvent.VK_LEFT]) {
                this.velocityY = -wallJumpForce;
                this.velocityX = maxVelocityX;
                image = imgRight;
            }
        }

        super.update(deltaTime);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, (int) x, (int) y,width,height,null);
        g2d.setColor(Color.blue);
//        Rectangle2D.Double playerRect = new Rectangle2D.Double(this.x, this.y, width, height);
//        g2d.fill(playerRect);
    }
}
