package com.harmony.game.state;

import com.harmony.game.graphics.Camera;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.physics.collision.BoxCollider;
import com.harmony.game.save.SaveData;
import com.harmony.game.state.levels.Level1;
import com.harmony.game.utils.GUI;
import com.harmony.game.utils.Timer;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class GameStateManager {

    public static final int MENU_STATE          = -1;
    public static final int PLAYER_STATE        = -2;
    public static final int PRACTICE_STATE      = 0;
    public static final int LEVEL_1_STATE       = 1;

    private static QuitConfirmation quitConfirmationState;

    private static State currentState;
    private static int currentStateId;

    private static boolean pause = true;
    private static boolean quitConfirmation = false;

    private static Graphics2D g;
    private static Timer timer = new Timer();

    private Camera camera = new Camera(new BoxCollider(null, new Vector2f(0, 0), Display.width, Display.height));

    public GameStateManager(Graphics2D g) {
        GameStateManager.g = g;
    }

    public static void setCurrentState(int currentState) {
        pause = true;

        System.out.println("# Setting Current State To ID: " + currentState);

        currentStateId = currentState;

        if(GameStateManager.currentState != null) GameStateManager.currentState.onDestroy();

        State tempState = null;

        switch (currentState) {
            case MENU_STATE:     tempState = new MenuState();           break;
            case PLAYER_STATE:   tempState = new PlayerState();         break;
            case PRACTICE_STATE: tempState = new PracticeState();       break;
            case LEVEL_1_STATE:  tempState = new Level1();              break;
        }

        if(tempState == null) return;
        GameStateManager.currentState = tempState;
        showChapter();
        Camera.position.reset();
        tempState.onCreate();
        pause = false;
    }

    private static void showChapter() {
        if(currentStateId < 0) return;
        timer.delay(3000);
    }

    public void update() {
        if(quitConfirmation) quitConfirmationState.update();
        if(currentState == null || pause) return;
        if(timer.done()) currentState.update();
        Camera.update();
        GUI.update();
    }

    public void draw() {
        if(quitConfirmation) quitConfirmationState.draw(g);
        if(currentState == null || pause) return;
        currentState.draw(g);
        Camera.draw(g);
        GUI.draw(g);

        if(!timer.done()) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Display.width, Display.height);
            Font.STANDARD_FONT.drawText(g, "Chapter " + currentStateId, (Display.width / 2) - 192,
                    (Display.height / 2) - 32, 64);
            Display.update();
        }
    }

    public static void nextLevel() {
        MenuState.saveData = new SaveData(currentStateId + 1, MenuState.saveData.playerSave);
        MenuState.saveData.save();
        setCurrentState(currentStateId + 1);
    }

    public static void requestCloseConfirmation() {
        if(quitConfirmation) return;
        System.out.println("*** Requesting Close Confirmation ***");
        pause = true;
        quitConfirmationState = new QuitConfirmation();
        quitConfirmation = true;
    }

    public static State getCurrentState() { return currentState; }
    public static void setPause(boolean pause) { GameStateManager.pause = pause; }
    public static void setQuitConfirmation(boolean quitConfirmation) { GameStateManager.quitConfirmation = quitConfirmation; }

}
