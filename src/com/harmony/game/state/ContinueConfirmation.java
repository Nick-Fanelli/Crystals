package com.harmony.game.state;

import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.gui.Button;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class ContinueConfirmation extends State {

    public static final Button continueButton = new Button(new Vector2f(Display.width / 2f - 180, 350), 360,
            78, "Continue", 32);

    public static final Button exitButton = new Button(new Vector2f(Display.width / 2f - 180, 450), 360,
            78, "Exit to Menu", 32);

    public boolean isDone = false;

    @Override
    public void onCreate() {
        isDone = false;
    }

    @Override
    public void update() {
        if(Input.hoverRectangle(continueButton.getRectangle()) || Input.hoverRectangle(exitButton.getRectangle()))
            Display.setCursor(Cursor.HAND_CURSOR);
        else Display.setCursor(Cursor.DEFAULT_CURSOR);

        if(continueButton.isPressed()) isDone = true;
        if(exitButton.isPressed()) GameStateManager.setCurrentState(GameStateManager.MENU_STATE);

        continueButton.update();
        exitButton.update();
    }

    @Override
    public void draw(Graphics2D g) {
        Font.STANDARD_FONT.centerTextHorizontal(g, "Continue Story?", 100, 64);

        continueButton.draw(g);
        exitButton.draw(g);
    }

    @Override
    public void onDestroy() {

    }
}
