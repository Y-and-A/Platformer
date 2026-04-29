package org.example;

public class GroundEnemy extends Enemy{
    public GroundEnemy(int x, int y, int lives){
        super(x, y, lives);
        canMove = true;
    }
}
