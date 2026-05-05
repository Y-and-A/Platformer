package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {
    private Image imgLeft, imgRight, imgFront, imgUp;

    private final double wallJumpCooldown = 3000;
    private double canWallJumpIn;
    public Player(int x,int y) {
        super(x,y,49,70);
        lives=3;
        jumpForce = 14.0 ;
        maxVelocityX = 7.0;
        maxVelocityY = 16.0;
        wallJumpForce = 6.0;
        moveX =2;

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

    public void update(boolean[] keys, boolean[] prevKeys) {
        if (canWallJumpIn > 0) canWallJumpIn -= 100 ;

        if (canWallJumpIn <= 0) {
            if (keys[KeyEvent.VK_RIGHT]){
                this.velocityX += moveX;
                image = imgRight;
            }
            if (keys[KeyEvent.VK_LEFT]){
                this.velocityX -= moveX;
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
            if (canWallJumpIn <= 0) {
                if (keys[KeyEvent.VK_RIGHT]) {
                    this.velocityY = -wallJumpForce;
                    this.velocityX = -maxVelocityX;
                    image = imgLeft;
                } else if (keys[KeyEvent.VK_LEFT]) {
                    this.velocityY = -wallJumpForce;
                    this.velocityX = maxVelocityX;
                    image = imgRight;
                }
            }
            canWallJumpIn = wallJumpCooldown;
        }

        if (y > Window.HEIGHT) y = 0;//TODO DEATH LOGIC HERE
        super.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y,width,height,null);
        g.setColor(Color.blue);
    }
}
