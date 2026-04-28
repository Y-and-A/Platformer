package org.example;

import javax.swing.*;

public abstract class Level extends JPanel {
    protected boolean[][] blocksMap;
    protected Enemy[] enemies;
    protected Player player;
    protected int level;
}
