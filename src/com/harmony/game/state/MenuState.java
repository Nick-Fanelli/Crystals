package com.harmony.game.state;

import com.harmony.game.Credits;
import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.gui.Button;
import com.harmony.game.save.SaveData;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class MenuState extends State {

    public static SaveData saveData;

    private Sprite buttons;

    private boolean storyPressed = false;
    private int storyAnimation = 0;
    private String storyText = "Continue Story";

    private final Button settingsButton = new Button(new Vector2f(Display.width / 2f - 180 , Display.height / 2f + 55),
            360, 78, "Settings", 32);

    private final Button creditsButton = new Button(new Vector2f(Display.width / 2f - 180, Display.height / 2f + 149),
            360, 78, "Credits", 32);

    private final Rectangle storyButton = new Rectangle(Display.width / 2 - 180, Display.height / 2 - 39, 360, 78);
    private final Rectangle storyTextRect = new Rectangle(0, Display.height / 2 + 6, 32, 32);

    @Override
    public void onCreate() {
        buttons = Button.BUTTONS_SPRITE;

        if((saveData = SaveData.loadSaveData()) == null) {
            storyText = "Start Story";
            storyTextRect.x = Display.width / 2 - 118;
        }
    }

    @Override
    public void update() {
        if(Input.hoverRectangle(storyButton) || Input.hoverRectangle(settingsButton.getRectangle()) ||
        Input.hoverRectangle(creditsButton.getRectangle())) Display.setCursor(Cursor.HAND_CURSOR);
        else Display.setCursor(Cursor.DEFAULT_CURSOR);

        if(Input.hoverRectangle(storyButton)) {
            if (Input.isButton(1)) storyAnimation = 1;
            else storyAnimation = 0;
            if (Input.isButtonUp(1)) storyPressed = true;
        }

        if(storyPressed) {
            if(storyText.equals("Continue Story")) {
                System.out.printf("*** Continuing Story At Chapter %s***%n", saveData.currentLevel);
                GameStateManager.setCurrentState(saveData.currentLevel);
            } else {
                System.out.println("*** Stating Story ***");
                GameStateManager.setCurrentState(GameStateManager.PLAYER_STATE);
            }
        }

        if(settingsButton.isPressed()) GameStateManager.setCurrentState(GameStateManager.SETTINGS_STATE);
        if(creditsButton.isPressed()) new Credits();

        settingsButton.update();
        creditsButton.update();
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, Display.width, Display.height);

        Font.STANDARD_FONT.centerTextHorizontal(g, "Crystals", 80, 72);

        g.drawImage(buttons.getSprite(storyAnimation, 0), storyButton.x, storyButton.y, storyButton.width,
                storyButton.height, null);

        Font.STANDARD_FONT.centerTextHorizontal(g, storyText, storyTextRect.y, storyTextRect.width);

        settingsButton.draw(g);
        creditsButton.draw(g);
    }
}
