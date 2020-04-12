package com.harmony.game.state;

import com.harmony.game.graphics.Display;
import com.harmony.game.graphics.Font;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.save.SaveData;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;
import org.w3c.dom.css.Rect;

import java.awt.*;

public class MenuState extends State {

    private Sprite buttons;

    private boolean storyPressed = false;
    private int storyAnimation = 0;
    private String storyText = "Continue Story";
    private SaveData saveData;

    private final Rectangle storyButton = new Rectangle(Display.width / 2 - 180, Display.height / 2 - 39, 360, 78);
    private final Rectangle storyTextRect = new Rectangle(Display.width / 2 - 150, Display.height / 2 - 16, 32, 32);

    @Override
    public void onCreate() {
        buttons = new Sprite("/ui/buttons_formatted.png", 120, 26);

        if((saveData = SaveData.loadSaveData()) == null) {
            storyText = "Start Story";
            storyTextRect.x = Display.width / 2 - 118;
        }
    }

    @Override
    public void update() {
        if(Input.hoverRectangle(storyButton)) {
            if(Input.isButton(1)) storyAnimation = 1; else storyAnimation = 0;
            if(Input.isButtonUp(1)) storyPressed = true;
        }

        if(storyPressed) {
            if(storyText.equals("Continue Story")) {
                System.out.printf("*** Continuing Story At Chapter %s***%n", saveData.currentLevel);
                GameStateManager.setCurrentState(saveData.currentLevel);
            } else {
                System.out.println("*** Stating Story ***");
                GameStateManager.setCurrentState(GameStateManager.PRACTICE_STATE);
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(33, 30, 39));
        g.fillRect(0, 0, Display.width, Display.height);

        Font.TRANSPARENT_FONT.drawText(g, "Crystals", Display.width / 2 - 180, 15, 64);

        g.drawImage(buttons.getSprite(storyAnimation, 0), storyButton.x, storyButton.y, storyButton.width,
                storyButton.height, null);

        Font.TRANSPARENT_FONT.drawText(g, storyText, storyTextRect.x, storyTextRect.y, storyTextRect.width);
    }
}
