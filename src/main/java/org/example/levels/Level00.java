package org.example.levels;

import org.example.Enemy;
import org.example.GroundEnemy;
import org.example.Window;

import java.awt.*;
import java.util.Arrays;

public class Level00 extends Level {
    public Level00() {
        level = 0;
        setBackground(Color.gray);

        blocksMap = new int[18][13];

        Arrays.fill(blocksMap[17], 1);
        for (int i = 0; i < blocksMap[7].length; i++) {
            if (i > 3 && i < 7)
                blocksMap[7][i] = 1;
            if (i > 9 && i <= 13)
                blocksMap[7][i] = 1;
        }
        int cubeWidth = org.example.Window.WIDTH / blocksMap[0].length;
        int cubeHeight = Window.HEIGHT/blocksMap.length;
        enemies = new Enemy[]{new GroundEnemy(4*cubeWidth,6*cubeHeight,2),new GroundEnemy(10*cubeWidth,6*cubeHeight,2)};
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintCubes(g,blocksMap);
        paintEnemy(g,enemies);
    }
}
