package org.example;

import java.awt.*;

public abstract class TestEntity extends TestGameObject {
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected int maxVelocityX;
    protected int width;
    protected int height;
    protected double movement;
    protected double jumpForce;
    protected final double friction =0.1;
    protected final double drag =0.2;
    protected double gravity = 0.1;
    protected boolean leftCollision;
    protected boolean rightCollision;


    protected int lives;

    protected boolean alive = true;
    protected boolean onFloor;


    public void update(boolean[] keys) {
        //more realistic friction mechanics
        if (velocityX<=0.5&&velocityX>=-0.5)
            velocityX=0;
        //gravity
        if (!onFloor) {
            velocityY += gravity;
            if (velocityX>0)
                velocityX-=drag;
            else velocityX+=drag;
        } else{
            velocityY = 0;
            //friction when on floor
            if (velocityX>0)
                velocityX -=friction;
            else velocityX +=friction;
        }

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
