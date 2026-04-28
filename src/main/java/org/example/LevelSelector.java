package org.example;

import javax.swing.*;
import java.awt.*;

public class LevelSelector extends JPanel {
    public LevelSelector() {
        setLayout(new GridLayout(3, 3, 10, 10));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
        add(new LevelButton(new Level00()));
    }
}