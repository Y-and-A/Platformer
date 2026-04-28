package org.example.levels;

import org.example.Enemy;
import org.example.Player;
import org.example.Window;

import java.awt.*;
import java.util.Arrays;

public class Level00 extends Level {
    public Level00() {
        level = 0;
        setBackground(Color.gray);

        blocksMap = new boolean[18][13];

        Arrays.fill(blocksMap[17], true);
        for (int i = 0; i < blocksMap[7].length; i++) {
            if (i > 3 && i < 7)
                blocksMap[7][i] = true;
            if (i > 9 && i <= 13)
                blocksMap[7][i] = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cubeWidth = Window.WIDTH / blocksMap[0].length;
        int cubeHeight = Window.HEIGHT/blocksMap.length;

        g.setColor(Color.GREEN);
        for (int r = 0; r < blocksMap.length; r++) {
            for (int c = 0; c < blocksMap[0].length; c++) {
                if (blocksMap[r][c]) {
                    g.fillRect(c * cubeWidth, r * cubeHeight, cubeWidth, cubeHeight);
                }
            }
        }
    }
}
