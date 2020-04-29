package com.harmony.game.gui;

import com.harmony.game.graphics.Font;
import com.harmony.game.graphics.Sprite;
import com.harmony.game.utils.Input;
import com.harmony.game.utils.Vector2f;

import java.awt.*;

public class Button {

    public static final Sprite BUTTONS_SPRITE = new Sprite("/ui/buttons_formatted.png", 120, 26);

    private final Rectangle rectangle;
    private boolean isPressed = false;
    private int currentFrame = 0;

    private String text;
    private final int fontSize;

    public enum ButtonTrigger { BUTTON_UP, BUTTON_DOWN }
    private final ButtonTrigger trigger;

    public Button(Vector2f position, int width, int height, String text, int fontSize, ButtonTrigger trigger) {
        this.rectangle = new Rectangle((int) position.x, (int) position.y, width, height);

        this.text = text;
        this.fontSize = fontSize;
        this.trigger = trigger;
    }

    public Button(Vector2f position, int width, int height, String text, int fontSize) {
        this(position, width, height, text, fontSize, ButtonTrigger.BUTTON_UP);
    }

    public void update() {
        if(Input.hoverRectangle(rectangle)) {
            if(Input.isButton(1)) currentFrame = 1; else currentFrame = 0;
            if(trigger == ButtonTrigger.BUTTON_UP) {
                isPressed = Input.isButtonUp(1);
            } else {
                isPressed = Input.isButtonDown(1);
            }
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(BUTTONS_SPRITE.getSprite(currentFrame, 0), rectangle.x, rectangle.y, rectangle.width, rectangle.height, null);
        Font.STANDARD_FONT.centerTextRect(g, text, fontSize, rectangle);
    }

    public boolean isPressed() { return isPressed; }
    public void setPressed(boolean isPressed) { this.isPressed = isPressed; }

    public Rectangle getRectangle() { return rectangle; }

    public void setText(String text) { this.text = text; }
}
