package org.example;

public abstract class Entity {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected int lives;

    protected boolean dead;
    protected boolean canMove;
    protected boolean onFloor;

    protected boolean isDead;
    protected Entity(int x, int y,int   width,int height, int lives){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lives = lives;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
