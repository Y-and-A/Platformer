package org.example;

import javax.swing.*;
import java.awt.*;

public class TitleScreenButtons extends JButton {
    private final int buttonWidth = 120;
    private final int buttonHieght = 30;

    public TitleScreenButtons(String text,int x,int y) {
        setBounds(x-buttonWidth/2,y-buttonHieght/2, buttonWidth, buttonHieght);
        setFocusable(false);
        setText(text);
        setBorder(null);
    }
}
