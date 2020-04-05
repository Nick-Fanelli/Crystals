package com.harmony.game.state;

import java.awt.*;

public class GameStateManager {

    public static final int PRACTICE_STATE = 0;

    private static State currentState;

    public static void setCurrentState(int currentState) {
        if(GameStateManager.currentState != null) GameStateManager.currentState.onDestroy();

        State tempState = null;

        switch (currentState) {
            case PRACTICE_STATE: tempState = new PracticeState(); break;
        }

        if(tempState == null) return;
        tempState.onCreate();
        GameStateManager.currentState = tempState;
    }

    public void update()           { if(currentState != null) currentState.update() ; }
    public void draw(Graphics2D g) { if(currentState != null) currentState.draw(g)  ; }

}
