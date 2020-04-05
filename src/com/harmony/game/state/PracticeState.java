package com.harmony.game.state;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Console;
import com.harmony.game.tiles.TileManager;

import java.awt.*;

public class PracticeState extends State {

    private Console console;
    private Player player;
    private TileManager tileManager;

    private boolean move = true;

    private boolean loadTimer = false;

    private double stopTime;
    private int delay = 2000;

    @Override
    public void onCreate() {
        console = new Console();
        player = new Player();
        tileManager = new TileManager("/tile/tilemap.tmx");

//        console.setShowConsole(true);
//        console.sendMessage("Welcome!!! Use the W A S D keys to move around... Try It...");
    }

    @Override
    public void update() {
        player.update();
//
//        if((player.dx != 0 || player.dy != 0) && move) {
//            move = false;
//            loadTimer = true;
//            console.sendMessage("Great Job!!!");
//            stopTime = System.currentTimeMillis() + delay;
//        }
//
//        if(loadTimer) {
//            if(System.currentTimeMillis() >= stopTime) {
//                console.sendMessage("Hold on while I load a practice map...");
//                loadTimer = false;
//            }
//        }

        console.update();
    }

    @Override
    public void draw(Graphics2D g) {
        tileManager.draw(g);

        player.draw(g);

        console.draw(g);
    }
}
