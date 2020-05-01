package com.harmony.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Credits {

    private static final StringBuilder HTML = new StringBuilder();

    static {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("res/credits.html")));

            String line;

            while((line = br.readLine()) != null) { HTML.append(line); }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Credits() {
        JFrame frame = new JFrame("Credits for Crystals © 2020");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Game.requestFocus = false;

        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel();
        label.setText(HTML.toString());
        JScrollPane pane = new JScrollPane(label);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setPreferredSize(new Dimension(600, 600));
        pane.setHorizontalScrollBar(null);

        frame.getContentPane().add(pane, BorderLayout.NORTH);
        frame.pack();

        frame.setAlwaysOnTop(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Game.requestFocus = true;
            }
        });
    }

}
