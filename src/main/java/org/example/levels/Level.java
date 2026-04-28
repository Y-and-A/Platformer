package org.example.levels;

import org.example.Enemy;
import org.example.Player;
import org.example.Window;

import javax.swing.*;
import java.awt.*;

public abstract class Level extends JPanel {
    protected boolean[][] blocksMap;
    protected Enemy[] enemies;
    protected Player player;
    public int level;
}
