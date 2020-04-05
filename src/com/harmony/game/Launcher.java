package com.harmony.game;

import javax.swing.*;
import java.awt.*;

public class Launcher {

    private static final Dimension d = new Dimension(1280, 720);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(new GamePanel(d, frame));
        frame.setIgnoreRepaint(true);
        frame.pack();
        frame.setSize(d);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
