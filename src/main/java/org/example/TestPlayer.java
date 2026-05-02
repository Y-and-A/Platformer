package org.example;

import javax.imageio.ImageIO;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class TestPlayer extends TestEntity {
    private Image imgLeft, imgRight, imgFront, imgUp;

//    private String direction;  was ment to be a way to change the image from other places
    public TestPlayer() {
        width = 50;
        height = 70;

        movementForce = 2.5 * ACCELERATION_MULTIPLIER;
        jumpForce = 14.0 * VELOCITY_MULTIPLIER;
        maxVelocityX = 7.0 * VELOCITY_MULTIPLIER;
        maxVelocityY = 16.0  * VELOCITY_MULTIPLIER;

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

    public void update(boolean[] keys, double deltaTime) {
        if (keys[KeyEvent.VK_RIGHT]){
            this.velocityX += movementForce * deltaTime;
            image = imgRight;
        }
        if (keys[KeyEvent.VK_LEFT]){
            this.velocityX -= movementForce * deltaTime;
            image = imgLeft;
        }
        if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE]) && onFloor){
            this.velocityY -= jumpForce;
            image = imgUp;
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
