package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gun extends GameObject {
    private int bulletVelocity = 3;
    private int reloadSpead = 2;
    private int maxAmmo = 6;
    private int currentAmmo = maxAmmo;
    private double velocityX;
    private double velocityY;
    public List<Bullet> bullets = Collections.synchronizedList(new ArrayList<>());

    public Gun(double x, double y) {
        super(x, y);
    }


    public void shotBullet(Direction direction) {//TODO add reload mechanism
        double playerX = this.x;
        double playerY = this.y;
//        bullets.add(new Bullet(playerX, playerY,direction));
        currentAmmo--;

        /*
        double slop = (playerY - mouseY) / (playerX - mouseX);
        double angle = Math.atan(slop);


        velocityX = Math.cos(angle) * bulletVelocity;
        velocityY = Math.sin(angle) * bulletVelocity;

        bullets.add(new Bullet(playerX, playerY, velocityX, velocityY));
        bullets.add(new Bullet(playerX, playerY, direction));
         */


    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }
}
