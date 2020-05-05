package com.harmony.game.state.chapters;

import com.harmony.game.entity.Player;
import com.harmony.game.entity.enemy.Enemy;
import com.harmony.game.entity.npc.NPC;
import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Console;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.gui.Button;
import com.harmony.game.gui.GUI;
import com.harmony.game.item.Drops;
import com.harmony.game.item.Item;
import com.harmony.game.object.Chest;
import com.harmony.game.object.Door;
import com.harmony.game.object.GameObject;
import com.harmony.game.state.GameStateManager;
import com.harmony.game.state.State;
import com.harmony.game.tiles.TileManager;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class Chapter extends State {

    private static final Button MENU_BUTTON = new Button(new Vector2f(Display.width / 2f - 180,
            Display.height / 2f - 23), 360, 78, "Main Menu", 32);

    private static final Button SETTINGS_BUTTON = new Button(new Vector2f(Display.width / 2f - 180,
            Display.height / 2f + 75), 360, 78, "Settings", 32);

    private static final Button CANCEL_BUTTON = new Button(new Vector2f(Display.width / 2f - 180,
            Display.height / 2f + 173), 360, 78, "Cancel", 32);

    private static final Button PAUSE_BUTTON = new Button(new Vector2f(Display.width - 85, 5), 80, 40,
            "Pause", 20);

    private final String tilemapLocation;

    protected Console console;
    protected Player player;
    protected TileManager tileManager;

    protected ArrayList<Enemy> enemies = new ArrayList<>();
    protected ArrayList<NPC> npcs = new ArrayList<>();

    protected boolean isControlled = false;
    protected boolean isPaused = false;

    protected boolean immobilizeEntities = false;

    public Chapter(String tilemapLocation) { this.tilemapLocation = tilemapLocation; }

    @Override
    public void onCreate() {
        tileManager = new TileManager(tilemapLocation);

        console = new Console();
        player = new Player(this, tileManager.getObjectsMap());

        GUI.showGui = true;
    }

    private void gameObjectCollision() {
        for(GameObject gameObject : getGameObjects()) {
            if(!Camera.shouldHandleGameObject(gameObject)) continue;

            if(gameObject.isCollideable())

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
        if(Input.isKeyDown(KeyEvent.VK_ESCAPE)) {
            isPaused    = !isPaused;
            GUI.showGui = !isPaused;
            Display.setCursor(Cursor.DEFAULT_CURSOR);
        }

        if(isPaused) {
            MENU_BUTTON.update();
            SETTINGS_BUTTON.update();
            CANCEL_BUTTON.update();

            if(Input.hoverRectangle(MENU_BUTTON.getRectangle()) || Input.hoverRectangle(SETTINGS_BUTTON.getRectangle()) ||
            Input.hoverRectangle(CANCEL_BUTTON.getRectangle())) Display.setCursor(Cursor.HAND_CURSOR);
            else Display.setCursor(Cursor.DEFAULT_CURSOR);

            if(CANCEL_BUTTON.isPressed()) {
                isPaused    = !isPaused;
                GUI.showGui = !isPaused;
                Display.setCursor(Cursor.DEFAULT_CURSOR);
                CANCEL_BUTTON.setPressed(false);
            }

            if(SETTINGS_BUTTON.isPressed()) {
                GameStateManager.setCurrentState(GameStateManager.SETTINGS_STATE);
                SETTINGS_BUTTON.setPressed(false);
            }

            if(MENU_BUTTON.isPressed()) {
                GameStateManager.setCurrentState(GameStateManager.MENU_STATE);
                MENU_BUTTON.setPressed(false);
            }

            return;
        }

        PAUSE_BUTTON.update();

        if(Input.hoverRectangle(PAUSE_BUTTON.getRectangle())) Display.setCursor(Cursor.HAND_CURSOR);
        else Display.setCursor(Cursor.DEFAULT_CURSOR);

        if(PAUSE_BUTTON.isPressed()) {
            isPaused    = !isPaused;
            GUI.showGui = !GUI.showGui;
            Display.setCursor(Cursor.DEFAULT_CURSOR);
            PAUSE_BUTTON.setPressed(false);
            return;
        }

        if(GUI.showMap && Input.isKeyDown(KeyEvent.VK_M)) GUI.displayFullMap = !GUI.displayFullMap;

        player.setGameObjects(getGameObjects());
        player.update();
        Drops.update(player);
        super.update();
        if(!immobilizeEntities) try { for (Enemy enemy : enemies) enemy.update(); } catch (Exception ignored) {}
        for(NPC npc : npcs) npc.update();
        gameObjectCollision();
        console.update();
    }

    @Override
    public void draw(Graphics2D g) {
        if(isPaused) {
            MENU_BUTTON.draw(g);
            SETTINGS_BUTTON.draw(g);
            CANCEL_BUTTON.draw(g);

            Font.STANDARD_FONT.centerTextHorizontal(g, "Paused", 100, 64);
            Font.STANDARD_FONT.centerTextHorizontal(g, "WARNING: If you exit to The Main Menu or Settings, you may loose your progress!",
                    200, 32, new Color(187, 44, 44));

            return;
        }

        tileManager.draw(g);
        Drops.draw(g);
        super.draw(g);
        for(Enemy enemy : enemies) enemy.draw(g);
        for(NPC npc : npcs) npc.draw(g);
        player.draw(g);
        console.draw(g);
        PAUSE_BUTTON.draw(g);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        console.cleanUp();
        player.onDestroy();
        for(NPC npc : npcs) npc.onDestroy();
    }

    public boolean isControlled() { return isControlled; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public Player getPlayer() { return player; }
    public Console getConsole() { return console; }

    public void setControlled(boolean controlled) { isControlled = controlled; }
}

