package com.harmony.game.state;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Console;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.tiles.TileManager;
import com.harmony.game.utils.Input;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PracticeState extends State {

    private Console console;
    private Player player;
    private TileManager tileManager;

    private boolean move = true;

    private boolean loadTimer = false;
    private boolean endTimer = false;

    private double stopTime;
    private int delay = 2000;

    @Override
    public void onCreate() {
        tileManager = new TileManager("/tile/tilemap.tmx");

        console = new Console();
        player = new Player((ObjectTileMap) tileManager.getObjectsMap());
        console.setShowConsole(true);

        console.sendMessage("Welcome to GAME_TITLE!!! Use W A S D to move around the screen...");
    }

    @Override
    public void update() {
        player.update();

        if((player.dx != 0 || player.dy != 0) && move) {
            move = false;
            loadTimer = true;
            console.sendMessage("Great Job!!!");
            stopTime = System.currentTimeMillis() + delay;
        }

        if(loadTimer) {
            if(System.currentTimeMillis() >= stopTime) {
                console.sendMessage("Explore the map on your own... Try to find and open the chest...");
                loadTimer = false;
                stopTime = System.currentTimeMillis() + 6500;
                endTimer = true;
            }
        }

        if(endTimer) {
            if(System.currentTimeMillis() >= stopTime) {
                console.setShowConsole(false);
                endTimer = false;
            }
        }

        console.update();
    }

    @Override
    public void draw(Graphics2D g) {
        tileManager.draw(g);
        player.draw(g);

        console.draw(g);
    }
}
