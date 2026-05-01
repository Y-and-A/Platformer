package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class TestPlayer extends TestEntity {
//    private String direction;  was ment to be a way to change the image from other places
    public TestPlayer() {
        width = 50;
        height = 70;
        setImage("src/main/resources/player/player-front.png");
        movementForce = 1.0;
        jumpForce = 12.0;

        maxVelocityX = 6.0;
        maxVelocityY = 15.0;
    }

    public void update(boolean[] keys) {
        if (keys[KeyEvent.VK_RIGHT]){
            this.velocityX += movementForce;
            setImage("src/main/resources/player/player-facingRight.png");
        }
        if (keys[KeyEvent.VK_LEFT]){
            this.velocityX -= movementForce;
            setImage("src/main/resources/player/player-facingLeft.png");        }
        if ((keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_SPACE]) && onFloor){
            this.velocityY -= jumpForce;
            setImage("src/main/resources/player/player-facingUp.png");
        }

        super.update();
    }

    public void setImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
