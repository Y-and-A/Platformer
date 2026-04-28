package org.example;

import org.example.levels.*;
import javax.swing.*;
import java.awt.*;

public class LevelSelector extends JPanel {
    public LevelSelector() {
        setLayout(new GridLayout(3, 3, 10, 10));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level01()));
        add(new LevelButton(new Level02()));
        add(new LevelButton(new Level03()));
        add(new LevelButton(new Level04()));
        add(new LevelButton(new Level05()));
        add(new LevelButton(new Level06()));
        add(new LevelButton(new Level07()));
        add(new LevelButton(new Level08()));
    }
}