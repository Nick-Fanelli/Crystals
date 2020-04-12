package com.harmony.game.state.levels;

import com.harmony.game.entity.Player;
import com.harmony.game.entity.enemy.Enemy;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Console;
import com.harmony.game.item.Item;
import com.harmony.game.object.Chest;
import com.harmony.game.object.Door;
import com.harmony.game.object.GameObject;
import com.harmony.game.state.State;
import com.harmony.game.tiles.ObjectTileMap;
import com.harmony.game.tiles.TileManager;
import com.harmony.game.utils.GUI;

import java.awt.*;
import java.util.ArrayList;

public abstract class Level extends State {

    private final String tilemapLocation;

    protected Console console;
    protected Player player;
    protected TileManager tileManager;

    protected ArrayList<Enemy> enemies = new ArrayList<>();

    public Level(String tilemapLocation) { this.tilemapLocation = tilemapLocation; }

    @Override
    public void onCreate() {
        tileManager = new TileManager(tilemapLocation);

        console = new Console();
        player = new Player(this, (ObjectTileMap) tileManager.getObjectsMap());

        GUI.showGui = true;
    }

    private void gameObjectCollision() {
        for(GameObject gameObject : getGameObjects()) {
            if(!Camera.shouldHandleGameObject(gameObject)) continue;

            if(gameObject instanceof Chest) {
                if(((Chest) gameObject).getItem() == Item.EMPTY) ((Chest) gameObject).setItem(Item.KEY);
                gameObject.isCollidingWith(player);
            }

            if(gameObject instanceof Door) {
                gameObject.isCollidingWith(player);
            }
        }
    }

    @Override
    public void update() {
        player.update();
        super.update();

        try { for (Enemy enemy : enemies) enemy.update(); } catch (Exception e) {}

        gameObjectCollision();

        console.update();
    }

    @Override
    public void draw(Graphics2D g) {
        tileManager.draw(g);

        super.draw(g);

        for(Enemy enemy : enemies) enemy.draw(g);

        player.draw(g);

        console.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public ArrayList<Enemy> getEnemies() { return enemies; }
}
