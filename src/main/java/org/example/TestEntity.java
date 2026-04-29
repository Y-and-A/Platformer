package org.example;

import java.awt.*;

public abstract class TestEntity extends TestGameObject {
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected int maxVelocityX;
    protected int width;
    protected int height;
    private final float friction =0.2F;


    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;

    protected double gravity = 0.1;

    public void update(boolean[] keys) {
        if (!onFloor) {
            velocityY += gravity;
        } else{
            velocityY = 0;
            if (velocityX>0)
                velocityX -=friction;
            else velocityX +=friction;
        }
        if (velocityX<=0.5&&velocityX>=-0.5)
            velocityX=0;
        int moveTo = this.x+(int) velocityX;
        if (moveTo>TestWindow.screenSize.width-this.width)
            this.x =TestWindow.screenSize.width-this.width;
        else if (moveTo<0)
            this.x = 0;
        else
            this.x += (int) velocityX;
        this.y += (int) velocityY;
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

}
