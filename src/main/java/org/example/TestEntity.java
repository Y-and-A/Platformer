package org.example;

import java.awt.*;

public abstract class TestEntity extends TestGameObject {
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected int maxVelocityX;
    protected int width;
    protected int height;
    private final float friction =0.2F;
    protected boolean leftCollision;
    protected boolean rightCollision;


    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;

    protected double gravity = 0.1;

    public void update(boolean[] keys) {
        //gravity
        if (!onFloor) {
            velocityY += gravity;
        } else{
            velocityY = 0;
            //friction when on floor
            if (velocityX>0)
                velocityX -=friction;
            else velocityX +=friction;
        }
        //more realistic friction mechanics
        if (velocityX<=0.5&&velocityX>=-0.5)
            velocityX=0;
        //check for out of bounds
        int moveTo = this.x+(int) velocityX;
        if (moveTo>TestWindow.screenSize.width-this.width)
            this.x =TestWindow.screenSize.width-this.width;
        else if (moveTo<0)
            this.x = 0;
        //check for collisions
        if (moveTo>x){//moving right
            if (!rightCollision)
                this.x += (int) velocityX;
        }
        else {//moving left
            if (!leftCollision) {
                this.x += (int) velocityX;
            }
        }
        this.y += (int) velocityY;
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,width,height);
    }

}
