package org.example;

import java.awt.*;

public abstract class GameObject {
    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected GameObject(double x,double y){
        this.x = x;
        this.y = y;
    }
    protected GameObject(int x,int y,int width,int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

        abstract public void draw(Graphics g);
}
