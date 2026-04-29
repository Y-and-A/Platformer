package org.example;

public abstract class Entity {
    protected int x;
    protected int y;

    protected int lives;

    protected boolean dead;
    protected boolean canMove;
    protected boolean onFloor;

    protected boolean isDead;
    protected Entity(int x, int y, int lives){
        this.x = x;
        this.y = y;
        this.lives = lives;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
