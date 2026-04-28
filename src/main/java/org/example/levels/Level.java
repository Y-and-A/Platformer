package org.example.levels;

import org.example.Enemy;
import org.example.Player;

import javax.swing.*;

public abstract class Level extends JPanel {
    protected boolean[][] blocksMap;
    protected Enemy[] enemies;
    protected Player player;
    public int level;
}
