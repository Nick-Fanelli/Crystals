package com.harmony.game.state;

import com.harmony.game.Game;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.utils.Input;

import java.awt.*;

public class QuitConfirmation extends State {

    private static final Sprite buttons = new Sprite("/ui/buttons_formatted.png", 120, 26);

    private static final Rectangle saveQuitButton = new Rectangle(Display.width / 2 - 300, 300, 625, 75);
    private static final Rectangle cancelButton = new Rectangle(Display.width / 2 - 300, 450, 625, 75);

    private int saveQuitAnimation = 0;
    private int cancelAnimation = 0;

    // TODO: DEPLOY - REMOVE
    public QuitConfirmation() { /*Game.setIsRunning(false);*/ }

    @Override
    public void update() {
        if(Input.hoverRectangle(saveQuitButton) || Input.hoverRectangle(cancelButton)) Display.setCursor(Cursor.HAND_CURSOR);
        else Display.setCursor(Cursor.DEFAULT_CURSOR);

        if(Input.hoverRectangle(saveQuitButton) && Input.isButton(1)) saveQuitAnimation = 1; else saveQuitAnimation = 0;
        if(Input.hoverRectangle(cancelButton) && Input.isButton(1)) cancelAnimation = 1; else cancelAnimation = 0;

        if(Input.hoverRectangle(saveQuitButton) && Input.isButtonUp(1)) {
            Game.setIsRunning(false);
            return;
        }

        if(Input.hoverRectangle(cancelButton) && Input.isButtonUp(1)) {
            GameStateManager.setQuitConfirmation(false);
            GameStateManager.setPause(false);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Background
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, Display.width, Display.height);

        // Draw Buttons
        g.drawImage(buttons.getSprite(saveQuitAnimation, 0), saveQuitButton.x, saveQuitButton.y, saveQuitButton.width,
                saveQuitButton.height, null);
        g.drawImage(buttons.getSprite(cancelAnimation, 0), cancelButton.x, cancelButton.y, cancelButton.width,
                cancelButton.height, null);

        // Draw Text
        Font.TRANSPARENT_FONT.drawText(g, "Save Before Quitting?", Display.width / 2 - 448, 10, 64);
        Font.TRANSPARENT_FONT.drawText(g, "Save & Quit", Display.width / 2 - 220, saveQuitButton.y +
                saveQuitButton.height / 2 - 32, 64);
        Font.TRANSPARENT_FONT.drawText(g, "Cancel", Display.width / 2 - 128, cancelButton.y +
                cancelButton.height / 2 - 32, 64);
    }
}
