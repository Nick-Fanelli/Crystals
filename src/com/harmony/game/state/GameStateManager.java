package com.harmony.game.state;

import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.state.levels.Level1;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class GameStateManager {

    public static final int PRACTICE_STATE = 0;
    public static final int LEVEL_1_STATE = 1;

    private static State currentState;
    private static int currentStateId;
    private static boolean run = false;

    private static Graphics2D g;

    private Camera camera = new Camera(new BoxCollider(null, new Vector2f(0, 0), Display.width, Display.height));

    public GameStateManager(Graphics2D g) {
        GameStateManager.g = g;
    }

    public static void setCurrentState(int currentState) {
        run = false;

        currentStateId = currentState;

        if(GameStateManager.currentState != null) GameStateManager.currentState.onDestroy();

        State tempState = null;

        switch (currentState) {
            case PRACTICE_STATE: tempState = new PracticeState();       break;
            case LEVEL_1_STATE: tempState  = new Level1();              break;
        }

        if(tempState == null) return;
        GameStateManager.currentState = tempState;
        showChapter();
        Camera.position.reset();
        tempState.onCreate();
        run = true;
    }

    private static void showChapter() {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Display.width, Display.height);
        Font.STANDARD_FONT.drawText(g, "Chapter " + currentStateId, (Display.width / 2) - 200,
                (Display.height / 2) - 32, 64);
        Display.update();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if(currentState == null || !run) return;
        currentState.update();
        Camera.update();
        GUI.update();
    }

    public void draw() {
        if(currentState == null || !run) return;
        currentState.draw(g);
        Camera.draw(g);
        GUI.draw(g);
    }

    public static void nextLevel() {
        setCurrentState(currentStateId + 1);
    }

    public static State getCurrentState() { return currentState; }

}
