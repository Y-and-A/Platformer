package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bullet extends Entity{
    private int bulletWidth =50;
    private int bulletHeight  =50;
    double velocityX;
    double velocityY;
    double bulletVelocity=5;
    private Image right;
    private Image left;
    public Bullet(double playerX, double playerY,String direction){
        super(playerX+15,playerY+21);
        this.width = bulletWidth;
        this.height  =bulletHeight;
        loadImages();
        if (direction=="left"){
            this.velocityX = -bulletVelocity;
            image = left;
        }else{
            this.velocityX= bulletVelocity;
            image = right;
        }
    }
    public void update() {
        if (alive) {
            this.x += velocityX;
            this.y += velocityY;
//            System.out.println("x: "+x+" y: "+y);
        }
        else{
            x=-1;
            y=-1;
            velocityX = 0;
            velocityY=0;
            image=null;
        }
    }
    public Rectangle rectangle() {
        return new Rectangle((int) x, (int) y,width,height);
    }

    public void loadImages(){
        try {
            right = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmall.png"));
            left = ImageIO.read(new File("src/main/resources/Bullets/PistolAmmoSmallLeft.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void draw(Graphics g) {
        g.drawImage(image, (int) x, (int) y,width,height,null);
    }
}
