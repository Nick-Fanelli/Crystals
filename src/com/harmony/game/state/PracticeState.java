package com.harmony.game.state;

import com.harmony.game.entity.Player;
import com.harmony.game.graphics.Console;
import com.harmony.game.item.Item;
import com.harmony.game.object.Chest;
import com.harmony.game.object.GameObject;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.tiles.TileManager;
import com.harmony.game.utils.GUI;
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
    private boolean hasKey = false;

    private double stopTime;
    private int delay = 2000;

    @Override
    public void onCreate() {
        tileManager = new TileManager("/tile/practice/tilemap.tmx");

        console = new Console();
        player = new Player((ObjectTileMap) tileManager.getObjectsMap());
        console.setShowConsole(true);

        console.sendMessage("Welcome to GAME_TITLE!!! Use W A S D to move around the screen...");
    }

    private void togglePlayerSpeed() {
        if(player.getMaxMoveSpeed() < 10) player.setSpeed(10);
        else player.setSpeed(4);
    }

    private void gameObjectCollision() {
        for(GameObject gameObject : getGameObjects()) {
            if(gameObject instanceof Chest) {
                if(((Chest) gameObject).getItem() == Item.EMPTY) ((Chest) gameObject).setItem(Item.KEY);
                gameObject.isCollidingWith(player);
            }
        }
    }

    @Override
    public void update() {
        player.update();

        super.update(); // Update Enemies

        gameObjectCollision();

        if(Input.isKeyDown(KeyEvent.VK_F2)) togglePlayerSpeed();

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

        if(!hasKey && GUI.hasKey) {
            hasKey = true;
            console.setShowConsole(true);
            console.sendMessage("Great Job... Notice the key collected in the upper right hand corner.\n\n" +
                    "Now try and find the door to move on.");
            endTimer = true;
            stopTime = System.currentTimeMillis() + 6500;
        }

        console.update();
    }

    @Override
    public void draw(Graphics2D g) {
        tileManager.draw(g);

        player.draw(g);

        super.draw(g); // Draw Enemies

        console.draw(g);
    }
}
