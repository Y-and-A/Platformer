package org.example.levels;

import org.example.Enemy;
import org.example.Window;

import javax.swing.*;
import java.awt.*;

public abstract class Level extends JPanel {
    protected int[][] blocksMap;
    protected Enemy[] enemies;
    public int level;

    public void paintCubes(Graphics g,int[][] blocksMap){
        int cubeWidth = Window.WIDTH / blocksMap[0].length;
        int cubeHeight = Window.HEIGHT/blocksMap.length;

        g.setColor(Color.GREEN);
        for (int r = 0; r < blocksMap.length; r++) {
            for (int c = 0; c < blocksMap[0].length; c++) {
                if (blocksMap[r][c]==1) {
                    g.setColor(Color.green);
                    g.fillRect(c * cubeWidth, r * cubeHeight, cubeWidth, cubeHeight);
                }
//                if (blocksMap[r][c]==2){
//                    g.setColor(Color.red);
//                    g.fillRect(c * cubeWidth, r * cubeHeight, cubeWidth, cubeHeight);
//                }
            }
        }
    }
    public void paintEnemy(Graphics g, Enemy[] enemies){
        g.setColor(Color.red);
        for (int i = 0; i < enemies.length; i++) {
            g.fillRect(enemies[i].getX(),enemies[i].getY(),20,30);
        }
    }
}
